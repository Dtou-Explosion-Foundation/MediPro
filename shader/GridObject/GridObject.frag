#version 400 core

uniform sampler2D uTexture;
uniform vec2 Grids;

in vec2 vTexcoord;
flat in vec2 Origin;

out vec4 vColor;

void main() {
    vec2 Pos = vTexcoord;
    // vec2 Pos = vTexcoord * 2 - 1;

    Pos = Pos - Origin;
    Pos = Pos * Grids;
    Pos = mod(Pos, 1);
    // vColor = vec4(vTexcoord, 0, 1);
    if (length(vTexcoord - Origin) < 0.002)
        vColor = vec4(0, 1, 0, 1);
    else if (mod(Pos.x - 0.5, 1) < 0.01 || mod(Pos.y - 0.5, 1) < 0.01)
        vColor = vec4(1, 0, 0, 1);
    else
        vColor = texture(uTexture, Pos);

    // if (length(Pos - Origin) < 0.01)
    //     // if (abs(Pos.x - Origin.x) < 0.01)
    //     // if (abs(Pos.x) < 0.01)
    //     vColor = vec4(0, 1, 0, 1);
    // else
    //     vColor = vec4(0, 0, 0, 1);
}