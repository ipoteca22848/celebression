import importlib.util
from pathlib import Path
import unittest

spec = importlib.util.spec_from_file_location('remapper', Path(__file__).parents[1] / 'tools/remap_sources.py')
remapper = importlib.util.module_from_spec(spec)
spec.loader.exec_module(remapper)

TINY = '''tiny\t2\t0\tintermediary\tnamed
c\tnet/minecraft/class_310\tnet/minecraft/client/MinecraftClient
\tm\t()V\tmethod_1551\tgetInstance
\tf\tI\tfield_1724\tplayer
c\tnet/minecraft/class_310$class_999\tnet/minecraft/client/MinecraftClient$Inner
'''

class SourceRemappingTest(unittest.TestCase):
    def test_package_member_and_descriptor(self):
        mappings = remapper.read_mappings(TINY)
        source = 'import net.minecraft.class_310; class_310.method_1551().field_1724; @At(target="Lnet/minecraft/class_310;")'
        result = remapper.translate(source, *mappings)
        self.assertEqual(result, 'import net.minecraft.client.MinecraftClient; MinecraftClient.getInstance().player; @At(target="Lnet/minecraft/client/MinecraftClient;")')

    def test_nested_class_and_ordinary_identifiers(self):
        result = remapper.translate('net.minecraft.class_310.class_999; String method_15510 = "render";', *remapper.read_mappings(TINY))
        self.assertEqual(result, 'net.minecraft.client.MinecraftClient.Inner; String method_15510 = "render";')

    def test_ambiguity_stops_generation(self):
        mappings = remapper.read_mappings(TINY + '\tm\t()V\tmethod_1551\totherName\n')
        with self.assertRaisesRegex(ValueError, 'Ambiguous mapping method_1551'):
            remapper.translate('method_1551()', *mappings)

    def test_reflection_names_and_comments_stay_in_runtime_namespace(self):
        source = 'Class.forName("net.minecraft.class_310"); // method_1551\n'
        self.assertEqual(remapper.translate(source, *remapper.read_mappings(TINY)), source)

    def test_nested_injection_annotation_is_translated(self):
        source = '@Inject(method = "method_1551", at = @At(target = "Lnet/minecraft/class_310;"))'
        expected = '@Inject(method = "getInstance", at = @At(target = "Lnet/minecraft/client/MinecraftClient;"))'
        self.assertEqual(remapper.translate(source, *remapper.read_mappings(TINY)), expected)

    def test_record_component_accessor(self):
        mappings = remapper.read_mappings(TINY + '\tm\t()I\tcomp_945\tvalue\n')
        self.assertEqual(remapper.translate('packet.comp_945()', *mappings), 'packet.value()')

    def test_stable_mojang_import_is_preserved(self):
        mappings = remapper.read_mappings(TINY + 'c\tcom/mojang/blaze3d/systems/RenderSystem\tcom/mojang/blaze3d/systems/RenderSystem\n')
        source = 'import com.mojang.blaze3d.systems.RenderSystem;\nRenderSystem.enableBlend();'
        self.assertEqual(remapper.translate(source, *mappings), source)

    def test_conflicting_screen_import_is_qualified(self):
        mappings = remapper.read_mappings(TINY + 'c\tnet/minecraft/class_419\tnet/minecraft/client/gui/screen/DisconnectedScreen\n')
        source = 'import net.minecraft.class_419;\nimport my.DisconnectedScreen;\nclass_419 screen;'
        result = remapper.translate(source, *mappings)
        self.assertIn('import my.DisconnectedScreen;', result)
        self.assertIn('net.minecraft.client.gui.screen.DisconnectedScreen screen;', result)
        self.assertNotIn('import net.minecraft.client.gui.screen.DisconnectedScreen;', result)

    def test_namespace_order_is_read_from_header(self):
        mappings = remapper.read_mappings('tiny\t2\t0\tnamed\tintermediary\nc\tnet/minecraft/client/MinecraftClient\tnet/minecraft/class_310\n')
        self.assertEqual(remapper.translate('net.minecraft.class_310', *mappings), 'net.minecraft.client.MinecraftClient')

if __name__ == '__main__':
    unittest.main()
