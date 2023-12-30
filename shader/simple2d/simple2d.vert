#version 400 core

layout(location = 0) in vec2 aPosition;
layout(location = 1) in vec2 aTexcoord;

out vec2 vTexcoord;

void main() {
    vTexcoord = aTexcoord;
    gl_Position = vec4(aPosition, 1.0, 1.0);
    // gl_Position = vec4(1.0, 1.0, 1.0, 1.0);
}