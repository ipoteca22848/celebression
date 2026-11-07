#version 150

#moj_import <celebression_ui:common.glsl>

uniform sampler2D Sampler0;
uniform vec2 Size;
uniform float BlurRadius;
uniform vec4 Radius;
uniform float Smoothness;

in vec2 FragCoord;
in vec2 TexCoord;
in vec4 FragColor;
out vec4 OutColor;

void main() {
    if (BlurRadius < 0.1) {
        vec4 baseColor = texture(Sampler0, TexCoord) * FragColor;
        baseColor.a *= ralpha(Size, FragCoord, Radius, Smoothness);
        OutColor = baseColor;
        return;
    }

    vec4 color = vec4(0.0);
    float totalWeight = 0.0;
    float step = 1.0 / Size.y;
    float sigma = BlurRadius / 3.0;
    float sigma2 = 2.0 * sigma * sigma;

    float r = min(BlurRadius, 64.0);

    for (float i = -r; i <= r; i++) {
        float weight = exp(-(i * i) / sigma2);
        color += texture(Sampler0, TexCoord + vec2(0.0, i * step)) * weight;
        totalWeight += weight;
    }

    vec4 finalColor = (color / totalWeight) * FragColor;
    finalColor.a *= ralpha(Size, FragCoord, Radius, Smoothness);

    if (finalColor.a < 0.001) discard;

    OutColor = finalColor;
}
