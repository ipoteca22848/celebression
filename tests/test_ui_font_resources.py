from pathlib import Path
import re
import unittest

ROOT = Path(__file__).resolve().parents[1]

class UiFontResourcesTest(unittest.TestCase):
    def test_all_fonts_requested_by_initializer_exist(self):
        source = (ROOT / 'src/integration/java/ru/method/celebression/integration/ui/manager/fontManager/FontUtils.java').read_text()
        prefix = re.search(r'fontsDir\s*=\s*"([^"]+)"', source)[1]
        fonts = re.findall(r'initializationFont\([^,]+,\s*"([^"]+)"\)', source)
        self.assertGreater(len(fonts), 0)
        for name in fonts:
            with self.subTest(font=name):
                self.assertRegex(prefix + name, r'^/[a-z0-9_./-]+$')
                path = ROOT / 'src/main/resources' / (prefix + name).lstrip('/')
                self.assertTrue(path.is_file(), f'Missing UI font: {prefix}{name}')
                self.assertGreater(path.stat().st_size, 100)

if __name__ == '__main__':
    unittest.main()
