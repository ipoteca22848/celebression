#version 120

uniform sampler2D font;
uniform vec4 inColor;
uniform float width;
uniform float maxWidth;

void main() {
    float f = clamp(smoothstep(0.5, 1.0, 1.0 - (gl_FragCoord.x - maxWidth) / width), 0.0, 1.0);
    vec4 color = texture2D(font, gl_TexCoord[0].xy);
    if (color.a > 0.0) {
        color.a = color.a * f;
    }
    gl_FragColor = color * inColor;
}
