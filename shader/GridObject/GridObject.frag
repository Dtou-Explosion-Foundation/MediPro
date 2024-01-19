#version 400 core

uniform sampler2D uTexture;
uniform vec2 Grids;

in vec2 vTexcoord;
flat in vec2 Origin;

out vec4 vColor;

void main() {
    vec2 Pos = vTexcoord;
    Pos = Pos - Origin;
    Pos = Pos * Grids;
    Pos = mod(Pos, 1);
    if (mod(Pos.x - 0.5, 1) < 0.01 || mod(Pos.y - 0.5, 1) < 0.01)
        vColor = vec4(1, 0, 0, 1);
    else
        vColor = texture(uTexture, Pos);

    // if (vTexcoord.x < 0.01 || vTexcoord.x > 0.99 || vTexcoord.y < 0.01 || vTexcoord.y > 0.99) {
    //     vColor = vec4(1.0, 0.0, 0.0, 1.0);
    // }
}