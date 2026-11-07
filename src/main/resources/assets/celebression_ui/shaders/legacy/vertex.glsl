#version 120

attribute vec2 a_Position;
attribute vec2 a_TexCoord;

uniform mat4 ProjMat;

void main() {
    gl_TexCoord[0] = vec4(a_TexCoord, 0.0, 1.0);
    gl_Position = ProjMat * vec4(a_Position, 0.0, 1.0);
}
