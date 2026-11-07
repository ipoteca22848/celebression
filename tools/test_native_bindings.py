"""Exercise the original native verifier without starting Minecraft or contacting a server."""
from pathlib import Path
import os
import shutil
import subprocess
import sys

root = Path(__file__).resolve().parents[1]
isolated = root / 'build/native-binding-test/classes'
isolated.mkdir(parents=True, exist_ok=True)
main = root / 'build/classes/java/main'
classes = [
    'ru/method/celebression/service/NativeLibraryLoader.class',
    'ru/method/celebression/service/NativeProtectionStatus.class',
    'ru/method/celebression/util/StringDecryptor.class',
    'ru/method/linkenvisuals/obf/Q0.class',
    'ru/method/linkenvisuals/obf/ma.class',
    'ru/method/linkenvisuals/obf/zz.class',
]
for name in classes:
    output = isolated / name
    output.parent.mkdir(parents=True, exist_ok=True)
    shutil.copyfile(main / name, output)
classpath = os.pathsep.join(map(str, [isolated, root / 'build/classes/java/test', root / 'build/resources/main']))
environment = os.environ.copy()
# The bundled Linux library requests libc.so; Debian exposes the runtime as libc.so.6
# and uses libc.so for a linker script. Resolve that soname only inside this test.
libc = Path('/lib/x86_64-linux-gnu/libc.so.6')
if sys.platform.startswith('linux') and libc.is_file():
    compatibility = root / 'build/native-binding-test/lib'
    compatibility.mkdir(parents=True, exist_ok=True)
    alias = compatibility / 'libc.so'
    if not alias.exists():
        alias.symlink_to(libc)
    inherited = environment.get('LD_LIBRARY_PATH', '')
    environment['LD_LIBRARY_PATH'] = str(compatibility) + (os.pathsep + inherited if inherited else '')
command = [sys.argv[1], '-Xcheck:jni', '-cp', classpath, 'NativeBindingsSmoke']
raise SystemExit(subprocess.call(command, cwd=root, env=environment))
