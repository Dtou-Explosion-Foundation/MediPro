#version 400 core

layout(location = 0) in vec2 aPosition;
layout(location = 1) in vec2 aTexcoord;

// uniform CameraTransform {
//     mat4 proj;
//     mat4 view;
// };

uniform mat4 modelMat;
// uniform ModelTransform { mat4 model; };

out vec2 vTexcoord;

void main() {
    vTexcoord = aTexcoord;
    // gl_Position = vec4(aPosition, 1.0, 1.0);
    gl_Position = modelMat * vec4(aPosition, 1.0, 1.0);
    // gl_Position = mat4(1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0,
    //                    0.0, 0.5, 0.0, 0.0, 1.0) *
    //               vec4(aPosition, 1.0, 1.0);
    // gl_Position = proj * (view * (model * vec4(aPosition, 1.0, 1.0)));
    // gl_Position = vec4(1.0, 1.0, 1.0, 1.0);
}