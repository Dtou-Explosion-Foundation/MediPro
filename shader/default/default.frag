#version 400 core

precision mediump float;

in vec4 vColor;

// out vec4 outColor;

void main()
{
    gl_FragColor  = vColor;
}