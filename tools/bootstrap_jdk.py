"""Install the pinned complete Temurin JDK into the checkout's parent .tools directory."""
from pathlib import Path
import hashlib
import os
import subprocess
import tarfile
import tempfile
import urllib.request

VERSION = '21.0.13+9'
SHA256 = 'd7f5d5cf865a2902b70ff549b97586a7d3f234e016f041ab447ea740c7d66d1c'
URL = 'https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.13%2B9/OpenJDK21U-jdk_x64_linux_hotspot_21.0.13_9.tar.gz'


def install():
    tools = Path(__file__).resolve().parents[2] / '.tools'
    destination = tools / ('jdk-' + VERSION)
    if (destination / 'bin/javac').is_file() and (destination / 'lib/ct.sym').is_file():
        subprocess.run([str(destination / 'bin/javac'), '--version'], check=True)
        return destination
    tools.mkdir(parents=True, exist_ok=True)
    with tempfile.NamedTemporaryFile(dir=tools, suffix='.tar.gz', delete=False) as output:
        archive = Path(output.name)
        try:
            with urllib.request.urlopen(URL, timeout=60) as response:
                while block := response.read(1024 * 1024):
                    output.write(block)
            output.close()
            with archive.open('rb') as stream:
                actual = hashlib.file_digest(stream, 'sha256').hexdigest()
            if actual != SHA256:
                raise RuntimeError('Temurin checksum mismatch; archive will not be extracted')
            if destination.exists():
                raise RuntimeError(f'Incomplete JDK exists at {destination}; preserve or remove it before retrying')
            with tarfile.open(archive) as contents:
                contents.extractall(tools, filter='data')
        finally:
            archive.unlink(missing_ok=True)
    subprocess.run([str(destination / 'bin/javac'), '--version'], check=True)
    return destination

if __name__ == '__main__':
    print(install())
