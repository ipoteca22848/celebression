#version 150

#moj_import <celebression_ui:common.glsl>

in vec3 Position;
in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 in_uv;
out vec4 in_color;

void main() {
    in_uv = rvertexcoord(gl_VertexID);
    in_color = Color;
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
}
