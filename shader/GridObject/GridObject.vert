#version 400 core

layout(location = 0) in vec2 aPosition;
layout(location = 1) in vec2 aTexcoord;

uniform mat4 modelMat;
layout(std140) uniform CameraTransform {
    mat4 projMat;
    mat4 viewMat;
};
uniform vec2 OriginOffset;

out vec2 vTexcoord;
flat out vec2 Origin;

void main() {
    vTexcoord = aTexcoord;
    // Origin = (projMat * viewMat * vec4(0.0, 0.0, 0.0, 1.0)).xy * vec2(0.5, -0.5) + OriginOffset;
    Origin = (projMat * (viewMat * vec4(OriginOffset, 0.0, 1.0))).xy * 0.5 + 0.5;
    // Origin = (projMat * (viewMat * (modelMat * vec4(0.0, 0.0, 0.0, 1.0)))).xy * vec2(0.5, -0.5);
    gl_Position = projMat * (viewMat * (modelMat * vec4(aPosition, 0.0, 1.0)));
    // gl_Position = vec4(aPosition * 2, 0.0, 1.0);
}