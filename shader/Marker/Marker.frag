#version 400 core

uniform sampler2D uTexture;
uniform vec4 uColor;

in vec2 vTexcoord;

out vec4 vColor;

void main() {
    // vColor = vec4(uColor.rgb, 0.2);
    vColor = uColor;
}