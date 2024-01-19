#version 400 core

// precision mediump float;

uniform sampler2D uTexture;

in vec2 vTexcoord;
out vec4 vColor;
void main() {
    vColor = texture(uTexture, vTexcoord);
    // float a = 0.05;
    // // vec2 p = gl_FragCoord.xy;
    // vec2 p = vTexcoord;
    // if (p.x < a || p.x > 1 - a || p.y < a || p.y > 1 - a) {
    //     vColor = vec4(1.0, 0.0, 0.0, 1.0);
    // }
}