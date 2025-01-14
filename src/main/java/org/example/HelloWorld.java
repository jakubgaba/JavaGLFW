package org.example;

import org.example.shapes.Circle;
import org.example.shapes.Triangle;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class HelloWorld {
    private long window;
    private Triangle triangle;
    private Circle circle;

    public void run() {
        Initialize.init();
        window = Initialize.getWindow();
        GL.createCapabilities();
        triangle = new Triangle();
        circle = new Circle();
        circle.create();
        triangle.create();
        loop();
        cleanup();
    }

    private void loop() {

        glClearColor(0.2f, 0.1f, 0.1f, 1.0f);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            triangle.render();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void cleanup() {
        triangle.cleanup();
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}