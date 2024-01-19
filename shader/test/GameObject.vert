#version 400 core

layout(location = 0) in vec2 aPosition;
layout(location = 1) in vec2 aTexcoord;

layout(std140) uniform CameraTransform {
    mat4 projMat;
    mat4 viewMat;
};

uniform mat4 modelMat;

// uniform uModelMatrix { mat4 modelMat; };

out vec2 vTexcoord;

void main() {
    vTexcoord = aTexcoord;
    // gl_Position = vec4(aPosition, 1.0, 1.0);
    // gl_Position = modelMat * vec4(aPosition, 1.0, 1.0);
    // gl_Position = mat4(1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0,
    //                    0.0, 0.4, 0.0, 0.0, 1.0) *
    //               vec4(aPosition, 1.0, 1.0);

    // gl_Position = viewMat * (modelMat * vec4(aPosition, 1.0, 1.0));
    gl_Position = projMat * (viewMat * (modelMat * vec4(aPosition, 1.0, 1.0)));
}