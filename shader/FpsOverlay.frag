#version 400 core

uniform sampler2D uTexture;

in vec2 vTexcoord;

out vec4 vColor;

void main() {
    vColor = texture(uTexture, vTexcoord);
    // vColor = texture(uTexture, vTexcoord) * 0.7 + vec4(1, 1, 1, 1) * 0.3;
    // vColor = vec4(1.0, 1.0, 0.8, 1.0);
    // vColor = vec4(vTexcoord, 0.0, 1.0);
    // vColor = vec4(gl_FragCoord.xy, 0.0, 1.0);
    // float a = 0.05;
    // if (gl_FragCoord.x < a || gl_FragCoord.x > 1 - a || gl_FragCoord.y < a || gl_FragCoord.y > 1 - a) {
    //     vColor = vec4(1.0, 0.0, 0.0, 1.0);
    // }
}