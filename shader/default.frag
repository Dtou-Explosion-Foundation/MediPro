#version 400

precision mediump float;
// in float time; 
// in vec2 resolution;

void main() {
    // vec2 uv = gl_FragCoord.xy / resolution.xy;
    // gl_FragColor = vec4(uv, time, 1.0);
    // float c = gl_FragCoord.x /resolution.x;
    // gl_FragColor = vec4(c,c,c,1.0);
    gl_FragColor = vec4(1.0,0.0,0.0,1.0);
}