#version 400 core

// precision mediump float;

uniform sampler2D uTexture;

in vec2 vTexcoord;
out vec4 vColor;
void main() {
    vColor = texture(uTexture, vTexcoord);
    // if (vColor.a == 0.0) {
    //     vColor = vec4(vTexcoord.x, vTexcoord.y, 0.0, 1.0);
    // }
    // vColor = vec4(vTexcoord.x, vTexcoord.y, 0.0, 1.0);
    // vColor = texture2D(uTexture, vTexcoord) * 0.9 + vec4(1.0, 0.0, 1.0, 1.0) * 0.1;
}