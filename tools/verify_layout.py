"""Check first-party entry points, mixins and embedded resource paths without claiming a build."""
from pathlib import Path
import json
import re

ROOT = Path(__file__).resolve().parents[1]
SOURCES = ROOT / 'src/main/java'
INTEGRATION = ROOT / 'src/integration/java'
RESOURCES = ROOT / 'src/main/resources'

def require(condition, message):
    if not condition:
        raise SystemExit(message)

def source_exists(name):
    return any((root / (name.split('$', 1)[0].replace('.', '/') + '.java')).is_file() for root in (SOURCES, INTEGRATION))

def main():
    metadata = json.loads((RESOURCES / 'fabric.mod.json').read_text())
    require(metadata['id'] == 'celebression', 'Unexpected mod id')
    require(metadata['name'] == 'celebression', 'Unexpected display name')
    require((RESOURCES / metadata['icon']).is_file(), 'Missing mod icon')
    entries = 0
    for names in metadata['entrypoints'].values():
        for name in names:
            require(source_exists(name), f'Missing entry point: {name}')
            entries += 1
    mixins = 0
    for config_name in metadata['mixins']:
        config = json.loads((RESOURCES / config_name).read_text())
        for side in ('mixins', 'client', 'server'):
            for name in config.get(side, []):
                qualified = config['package'] + '.' + name
                require(source_exists(qualified), f'Missing mixin: {qualified}')
                mixins += 1
        if 'refmap' in config:
            require((RESOURCES / config['refmap']).is_file(), 'Missing refmap')
    for dependency in metadata['jars']:
        require((RESOURCES / dependency['file']).is_file(), 'Missing embedded dependency')
    java_sources = list(SOURCES.rglob('*.java')) + list(INTEGRATION.rglob('*.java'))
    for path in java_sources:
        text = path.read_text()
        package = re.search(r'^package\s+([\w.]+);', text, re.M)
        require(package is not None, f'Missing package: {path}')
        source_root = SOURCES if path.is_relative_to(SOURCES) else INTEGRATION
        require(path.parent.relative_to(source_root).as_posix() == package[1].replace('.', '/'), f'Package/path mismatch: {path}')
    print(f'Layout OK: {len(java_sources)} Java files, {entries} entry points, {mixins} mixins, {len(metadata["jars"])} embedded dependencies.')
    print('This checks layout only, not compilation, runtime behavior or UI integration.')

if __name__ == '__main__':
    main()
