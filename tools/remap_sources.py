"""Generate Yarn-named Java sources from the archive's intermediary sources.

Only intermediary IDs (class_N, method_N, field_N) are translated. Ambiguous
member IDs fail explicitly; no guesses or placeholder method bodies are used.
The checked-in sources are never changed.
"""
import argparse
from pathlib import Path
import re
import shutil
import zipfile


def read_mappings(text):
    lines = text.splitlines()
    header = lines[0].split('\t')
    if header[:3] != ['tiny', '2', '0']:
        raise ValueError('Expected Tiny v2 mappings')
    namespaces = header[3:]
    source_index = namespaces.index('intermediary')
    target_index = namespaces.index('named')
    classes, candidates = {}, {}
    for line in lines[1:]:
        parts = line.lstrip('\t').split('\t')
        indent = len(line) - len(line.lstrip('\t'))
        if indent == 0 and parts[0] == 'c':
            names = parts[1:]
            classes[names[source_index]] = names[target_index]
        elif indent == 1 and parts[0] in {'m', 'f'}:
            names = parts[2:]
            source = names[source_index]
            if re.fullmatch(r'(method|field|comp)_\d+', source):
                candidates.setdefault(source, set()).add(names[target_index] or source)
    ambiguous = {k: sorted(v) for k, v in candidates.items() if len(v) != 1}
    members = {k: next(iter(v)) for k, v in candidates.items() if len(v) == 1}
    return classes, members, ambiguous


class IdentifierTranslator:
    def __init__(self, classes, members, ambiguous):
        self.classes = classes
        self.qualified = {}
        self.members = members
        self.ambiguous = ambiguous
        self.full = {}
        self.simple = {}
        for old, new in classes.items():
            self.full[old] = new
            self.full[old.replace('/', '.').replace('$', '.')] = new.replace('/', '.').replace('$', '.')
            self.full[old.replace('/', '.')] = new.replace('/', '.').replace('$', '.')
            for a, b in zip(old.rsplit('/', 1)[-1].split('$'), new.rsplit('/', 1)[-1].split('$')):
                if re.fullmatch(r'class_\d+', a):
                    self.simple.setdefault(a, set()).add(b)
        # Intermediary class IDs have a fixed syntax; avoid a huge alternation
        # over every Minecraft class for each source-code fragment.
        self.full_expression = re.compile(r'(?<![\w$])net[./]minecraft[./]class_\d+(?:(?:\$|\.)class_\d+)*(?![\w$])')
        self.identifiers = re.compile(r'\b(?:class|method|field|comp)_\d+\b')
        self.descriptors = re.compile(r'L(net/minecraft/[\w$/]+);')

    def replace_identifier(self, match):
        token = match.group()
        if token in self.ambiguous:
            raise ValueError(f'Ambiguous mapping {token}: {self.ambiguous[token]}')
        if token in self.members:
            return self.members[token]
        if token in self.qualified:
            return self.qualified[token]
        if token in self.simple:
            names = self.simple[token]
            if len(names) != 1:
                raise ValueError(f'Ambiguous class name {token}: {sorted(names)}')
            return next(iter(names))
        return token

    def __call__(self, text):
        text = self.descriptors.sub(lambda m: 'L' + self.classes.get(m[1], m[1]) + ';', text)
        if self.full_expression:
            text = self.full_expression.sub(lambda m: self.full.get(m.group(), m.group()), text)
        return self.identifiers.sub(self.replace_identifier, text)


# Reflection fallbacks deliberately carry production (intermediary) names.
# Preserve ordinary string literals; only Mixin annotation strings are names
# that the annotation processor must see in the compilation namespace.
PROTECTED = re.compile(r'(?s)""".*?"""|"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'|//[^\n]*|/\*.*?\*/')
MIXIN_ANNOTATION = re.compile(r'@(?:[\w.]+\.)?(?:Mixin|Inject|Redirect|ModifyArg|ModifyArgs|ModifyVariable|ModifyConstant|ModifyExpressionValue|ModifyReturnValue|WrapOperation|Accessor|Invoker|At|Slice)\s*\(')

def translate(text, classes, members, ambiguous, identifiers=None):
    identifiers = identifiers or IdentifierTranslator(classes, members, ambiguous)
    imports = re.findall(r'^import ([\w.$]+);', text, re.M)
    ordinary = {name.rsplit('.', 1)[-1] for name in imports if not name.startswith('net.minecraft.')}
    identifiers.qualified = {}
    for name in imports:
        mapped = classes.get(name.replace('.', '/'))
        if name.startswith('net.minecraft.') and mapped and mapped.rsplit('/', 1)[-1] in ordinary:
            identifiers.qualified[name.rsplit('.', 1)[-1]] = mapped.replace('/', '.')
            text = text.replace('import ' + name + ';', '')

    protected = list(PROTECTED.finditer(text))
    mask = list(text)
    for match in protected:
        mask[match.start():match.end()] = ' ' * (match.end() - match.start())
    mask = ''.join(mask)
    annotations = []
    for match in MIXIN_ANNOTATION.finditer(mask):
        end = match.end()
        depth = 1
        while depth and end < len(mask):
            if mask[end] == '(': depth += 1
            elif mask[end] == ')': depth -= 1
            end += 1
        if depth:
            raise ValueError('Unterminated Mixin annotation')
        annotations.append((match.start(), end))
    result = []
    offset = 0
    for match in protected:
        result.append(identifiers(text[offset:match.start()]))
        token = match.group()
        if token.startswith('"') and any(start <= match.start() < end for start, end in annotations):
            result.append(identifiers(token))
        else:
            result.append(token)
        offset = match.end()
    result.append(identifiers(text[offset:]))
    return ''.join(result)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--mappings', type=Path, required=True)
    parser.add_argument('--source', type=Path, required=True)
    parser.add_argument('--output', type=Path, required=True)
    args = parser.parse_args()
    source, output = args.source.resolve(), args.output.resolve()
    if source == output or source in output.parents or output in source.parents:
        raise ValueError('Input and output must be separate trees')
    with zipfile.ZipFile(args.mappings) as archive:
        mappings = read_mappings(archive.read('mappings/mappings.tiny').decode('utf-8'))
    # Validate every conversion before replacing a previous generated tree.
    identifiers = IdentifierTranslator(*mappings)
    rendered = [(p.relative_to(source), translate(p.read_text(), *mappings, identifiers=identifiers)) for p in sorted(source.rglob('*.java'))]
    if not rendered:
        raise ValueError('No Java input files')
    if output.exists():
        shutil.rmtree(output)
    for relative, text in rendered:
        destination = output / relative
        destination.parent.mkdir(parents=True, exist_ok=True)
        destination.write_text(text)
    print(f'Generated {len(rendered)} Yarn source files in {output}')

if __name__ == '__main__':
    main()
