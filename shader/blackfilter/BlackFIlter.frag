#version 400 core

// precision mediump float;

uniform float uAlpha;

out vec4 vColor;
void main() {
    vColor = vec4(0, 0, 0, uAlpha);
}