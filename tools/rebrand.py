"""Rebrand first-party source and resource references; never touch embedded dependencies."""
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
REPLACEMENTS = {
    'LinkenVisuals': 'Celebression',
    'Linkenvisuals': 'Celebression',
    'linkenVisuals': 'celebression',
    'LINKENVISUALS': 'CELEBRESSION',
    'linkenvisuals': 'celebression',
    'Linken': 'Celebression',
    'linken': 'celebression',
    'LINKEN': 'CELEBRESSION',
}
PATTERN = re.compile('|'.join(map(re.escape, REPLACEMENTS)))
TEXT_EXTENSIONS = {'.java', '.json', '.properties', '.gradle', '.txt', '.vsh', '.fsh', '.glsl', '.mcmeta', '.cfg', '.xml', '.yaml', '.yml'}

def excluded(path):
    return any(part in {'.git', '.gradle', 'build', 'tools', 'provenance'} for part in path.parts) or path.name.lower().startswith(('license', 'notice', 'copying'))

def main():
    changed = 0
    for path in ROOT.rglob('*'):
        if not path.is_file() or excluded(path.relative_to(ROOT)) or path.suffix not in TEXT_EXTENSIONS:
            continue
        try:
            original = path.read_text(encoding='utf-8')
        except UnicodeDecodeError:
            continue
        updated = PATTERN.sub(lambda match: REPLACEMENTS[match.group()], original)
        if updated != original:
            path.write_text(updated, encoding='utf-8')
            changed += 1
    # Children before parents: update filenames and resource/package directories together.
    for path in sorted(ROOT.rglob('*'), key=lambda p: len(p.parts), reverse=True):
        if excluded(path.relative_to(ROOT)):
            continue
        name = PATTERN.sub(lambda match: REPLACEMENTS[match.group()], path.name)
        if name != path.name:
            target = path.with_name(name)
            if target.exists():
                raise RuntimeError(f'Rename collision: {target}')
            path.rename(target)
    print(f'Rebranded {changed} text files')

if __name__ == '__main__':
    main()
