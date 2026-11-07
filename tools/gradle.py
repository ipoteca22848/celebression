"""Run the standard Gradle wrapper with the environment's HTTP proxy configuration."""
import os
from pathlib import Path
import subprocess
import sys
from urllib.parse import urlsplit

root = Path(__file__).resolve().parents[1]
options = []
for protocol in ('http', 'https'):
    proxy = os.environ.get(protocol.upper() + '_PROXY') or os.environ.get(protocol + '_proxy')
    if not proxy:
        continue
    parsed = urlsplit(proxy)
    if parsed.hostname:
        options += [f'-D{protocol}.proxyHost={parsed.hostname}', f'-D{protocol}.proxyPort={parsed.port or 80}']
options += ['-Dhttp.nonProxyHosts=localhost|127.*|[::1]']
# Debian's managed trust store includes the cloud proxy CA; a downloaded JDK does not.
managed_trust = Path('/etc/ssl/certs/java/cacerts')
if managed_trust.is_file() and os.environ.get('CODEX_PROXY_CERT'):
    options.append(f'-Djavax.net.ssl.trustStore={managed_trust}')
environment = os.environ.copy()
jdk_home = environment.get('CELEBRESSION_JAVA_HOME')
if not jdk_home:
    candidate = root.parent / '.tools/jdk-21.0.13+9'
    if (candidate / 'bin/javac').is_file():
        jdk_home = str(candidate)
if jdk_home:
    environment['JAVA_HOME'] = jdk_home
    environment['PATH'] = str(Path(jdk_home) / 'bin') + os.pathsep + environment.get('PATH', '')
environment.setdefault('GRADLE_USER_HOME', str(root.parent / '.gradle-home'))
command = ['bash' , str(root / 'gradlew'), *options, *sys.argv[1:]]
raise SystemExit(subprocess.call(command, cwd=root, env=environment))
