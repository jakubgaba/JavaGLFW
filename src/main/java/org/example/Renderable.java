package org.example;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public interface Renderable {
    void create();
    void render();
    void cleanup();
    int createShaderProgram(String vertex, String fragment);
}
