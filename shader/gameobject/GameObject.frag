#version 400 core

// precision mediump float;

uniform sampler2D uTexture;

in vec2 vTexcoord;
out vec4 vColor;
void main() {
    // https://note.com/omakazu/n/n61722e3b6bcc
    // float g = (vTexcoord.x + vTexcoord.y) / 2.0;
    // vColor = vec4(vTexcoord.x, vTexcoord.y, 0.0, 1.0);
    // vColor = vec4(1.0, g, 1.0, 1.0) * 1 + texture2D(uTexture, vTexcoord) * 0.1;
    vColor = texture(uTexture, vTexcoord);
    // texture2D(uTexture, vTexcoord) * 0.9 + vec4(1.0, 0.0, 1.0, 1.0) * 0.1;
}