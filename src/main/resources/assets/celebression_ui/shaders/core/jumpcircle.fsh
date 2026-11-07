#version 150

in vec2 in_uv;
in vec4 in_color;

uniform float width;
uniform vec4 first_color;
uniform vec4 second_color;
uniform vec2 direction;

out vec4 OutColor;

void main() {
    vec2 gradDir = normalize(direction);
    vec2 centeredUv = in_uv - vec2(0.5);
    float lenUv = length(centeredUv);
    vec2 safeUvDir = lenUv > 1e-5 ? centeredUv / lenUv : vec2(0.0, 0.0);
    float gradient = abs(dot(gradDir, safeUvDir));

    vec4 color = mix(first_color, second_color, gradient);
    color.a *= in_color.a;
    color.a *= 1.0 - smoothstep(width - 0.1, width, abs(0.4 - lenUv));
    color.a *= 1.0 - smoothstep(0.49, 0.5, lenUv);

    if (color.a < 0.001) {
        discard;
    }

    OutColor = vec4(color.rgb * color.a, color.a);
}
