#version 400 core

// precision mediump float;

uniform sampler2D uTexture;

in vec2 vTexcoord;
out vec4 vColor;
void main() {
    vColor = texture(uTexture, vTexcoord);
    // if (vTexcoord.x < 0.01 || vTexcoord.x > 0.99 || vTexcoord.y < 0.01 || vTexcoord.y > 0.99) {
    //     vColor = vec4(1.0, 0.0, 0.0, 1.0);
    // }
}