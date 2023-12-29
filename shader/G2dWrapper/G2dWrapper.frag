#version 400 core

// precision mediump float;

uniform sampler2D uTexture;

in vec2 vTexcoord;
out vec4 vColor;
void main() {
    vColor = texture(uTexture, vTexcoord);
}