#version 400 core

layout(location = 0) in vec2 aPosition;
layout(location = 1) in vec2 aTexcoord;

uniform mat4 modelMat;
layout(std140) uniform CameraTransform {
    mat4 projMat;
    mat4 viewMat;
};

out vec2 vTexcoord;

void main() {
    vTexcoord = aTexcoord;
    gl_Position = projMat * viewMat * modelMat * vec4(aPosition, 0.0, 1.0);
    // gl_Position = vec4(aPosition, 0.0, 1.0);
}