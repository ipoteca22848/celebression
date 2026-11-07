#version 150

#moj_import <celebression_ui:common.glsl>

in vec2 FragCoord;
in vec2 TexCoord;
in vec4 FragColor;

uniform sampler2D Sampler0;
uniform vec2 Size;
uniform vec4 Radius;
uniform float Smoothness;
uniform float Strength;

out vec4 OutColor;

const int SAMPLES = 12;

void main() {
    vec2 center = vec2(0.5);
    vec2 dir = TexCoord - center;

    vec3 sum = vec3(0.0);
    float totalWeight = 0.0;

    for (int i = 0; i < SAMPLES; i++) {
        float t = float(i) / float(SAMPLES - 1);
        float weight = 1.0 - t * 0.5;
        vec2 offset = dir * Strength * t * 0.15;
        sum += texture(Sampler0, TexCoord - offset).rgb * weight;
        totalWeight += weight;
    }

    sum /= totalWeight;

    vec4 color = vec4(sum, 1.0) * FragColor;

    float radialDist = length(TexCoord - vec2(0.5));
    float softAlpha = 1.0 - smoothstep(0.1, 0.5, radialDist);
    
    color.a *= ralpha(Size, FragCoord, Radius, Smoothness) * softAlpha;

    if (color.a < 0.001) discard;

    OutColor = color;
}
