package org.example.shapes;

import org.example.Renderable;
import org.example.ShaderUtils;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Triangle implements Renderable {
    private int vao, vbo, shaderProgram;
    private long startTime = System.currentTimeMillis();
    private int iTimeLocation;

    @Override
    public void create() {
        float[] vertices = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        String vertexShaderSource = ShaderUtils.loadShaderSource("src/main/java/org/example/shaders/vertex_shader.glsl");
        String fragmentShaderSource = ShaderUtils.loadShaderSource("src/main/java/org/example/shaders/fragment_shader.glsl");
        shaderProgram = createShaderProgram(vertexShaderSource, fragmentShaderSource);
        iTimeLocation = glGetUniformLocation(shaderProgram, "iTime");
    }

    @Override
    public void render() {
        float time = (System.currentTimeMillis() - startTime) / 1000.0f;
        glUseProgram(shaderProgram);
        glBindVertexArray(vao);
        glUniform1f(iTimeLocation, time);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glBindVertexArray(0);
        glUseProgram(0);
    }

    @Override
    public void cleanup() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteProgram(shaderProgram);
    }

    @Override
    public int createShaderProgram(String vertex, String fragment) {
        int vertexShader = compileShader(GL_VERTEX_SHADER, vertex);
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragment);
        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        checkLinkErrors(program);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        return program;
    }

    private int compileShader(int type, String source) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);
        checkCompileErrors(shader, type == GL_VERTEX_SHADER ? "VERTEX" : "FRAGMENT");
        return shader;
    }

    private void checkCompileErrors(int shader, String type) {
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException(type + " SHADER COMPILATION ERROR: " + glGetShaderInfoLog(shader));
        }
    }

    private void checkLinkErrors(int program) {
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("PROGRAM LINKING ERROR: " + glGetProgramInfoLog(program));
        }
    }
}