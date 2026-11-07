#version 150

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
        OutColor = texture(Sampler0, TexCoord) * FragColor;
        return;
    }

    vec4 color = vec4(0.0);
    float totalWeight = 0.0;
    float step = 1.0 / Size.x;
    float sigma = BlurRadius / 3.0;
    float sigma2 = 2.0 * sigma * sigma;

    float r = min(BlurRadius, 64.0);

    for (float i = -r; i <= r; i++) {
        float weight = exp(-(i * i) / sigma2);
        color += texture(Sampler0, TexCoord + vec2(i * step, 0.0)) * weight;
        totalWeight += weight;
    }

    OutColor = (color / totalWeight) * FragColor;
}
