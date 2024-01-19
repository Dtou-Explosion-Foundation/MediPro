#version 400 core

// precision mediump float;

uniform sampler2D uTexture;

in vec2 vTexcoord;
out vec4 vColor;
void main() {
    // https://note.com/omakazu/n/n61722e3b6bcc
    // vColor = vec4(1.0, 0.0, 1.0, 1.0);
    vColor = texture(uTexture, vTexcoord);
    // texture2D(uTexture, vTexcoord) * 0.9 + vec4(1.0, 0.0, 1.0, 1.0) * 0.1;
}