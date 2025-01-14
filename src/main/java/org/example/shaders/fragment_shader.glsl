#version 330 core

out vec4 FragColor;
uniform float iTime;
void main() {

    vec2 uv = gl_FragCoord.xy / vec2(800, 600);
    vec3 col = 1.5 + 1.5 * cos(iTime + uv.x + vec3(0, 2, 4));
    FragColor = vec4(col, 1.0);

}
