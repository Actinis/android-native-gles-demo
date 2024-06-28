#include "Renderer.h"
#include <cmath>
#include <vector>
#include <iostream>
#include <random>

// Vertex Shader
const char *vertexShaderSource =
        "#version 300 es\n"
        "layout (location = 0) in vec3 aPos;\n"
        "uniform float uAngle;\n"
        "void main() {\n"
        "    float s = sin(uAngle);\n"
        "    float c = cos(uAngle);\n"
        "    mat3 rotationMatrix = mat3(\n"
        "        c, -s, 0.0,\n"
        "        s, c, 0.0,\n"
        "        0.0, 0.0, 1.0\n"
        "    );\n"
        "    gl_Position = vec4(rotationMatrix * aPos, 1.0);\n"
        "}\n";

// Fragment Shader
const char *fragmentShaderSource =
        "#version 300 es\n"
        "precision mediump float;\n"
        "out vec4 FragColor;\n"
        "void main() {\n"
        "    FragColor = vec4(1.0, 0.5, 0.2, 1.0); // Orange color\n"
        "}\n";

Renderer::Renderer() : shaderProgram(0), VAO(0), VBO(0), angleLocation(-1) {}

Renderer::~Renderer() {
    cleanup();
}

void Renderer::initialize() {
    setupShaders();
    setupBuffers();
}

void Renderer::render(float angle) {
    glClear(GL_COLOR_BUFFER_BIT);
    checkGLError("glClear");

    glUseProgram(shaderProgram);
    checkGLError("glUseProgram");
    std::cerr << "glUseProgram " << shaderProgram << std::endl;

    glUniform1f(angleLocation, angle);
    std::cerr << "glUniform1f location=" << angleLocation << ", angle=" << angle << std::endl;
    checkGLError("glUniform1f");

    glBindVertexArray(VAO);
    checkGLError("glBindVertexArray");

    glDrawArrays(GL_TRIANGLES, 0, 3);
    checkGLError("glDrawArrays");
}

void Renderer::cleanup() {
    if (VAO != 0) {
        glDeleteVertexArrays(1, &VAO);
        VAO = 0;
    }
    if (VBO != 0) {
        glDeleteBuffers(1, &VBO);
        VBO = 0;
    }
    if (shaderProgram != 0) {
        glDeleteProgram(shaderProgram);
        shaderProgram = 0;
    }
}

GLuint Renderer::compileShader(GLenum type, const char *source) {
    GLuint shader = glCreateShader(type);
    checkGLError("glCreateShader");

    glShaderSource(shader, 1, &source, nullptr);
    checkGLError("glShaderSource");

    glCompileShader(shader);
    checkGLError("glCompileShader");

    GLint success;
    glGetShaderiv(shader, GL_COMPILE_STATUS, &success);
    checkGLError("glGetShaderiv");

    GLchar infoLog[512];
    glGetShaderInfoLog(shader, sizeof(infoLog), nullptr, infoLog);
    checkGLError("glGetShaderInfoLog");

    if (!success) {
//        GLchar infoLog[512];
//        glGetShaderInfoLog(shader, sizeof(infoLog), nullptr, infoLog);
        throw RendererException(std::string("Shader compilation failed: ") + infoLog);
    }

    return shader;
}

void Renderer::setupShaders() {
    GLuint vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderSource);
    GLuint fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentShaderSource);

    shaderProgram = glCreateProgram();
    checkGLError("glCreateProgram");

    glAttachShader(shaderProgram, vertexShader);
    checkGLError("glAttachShaderVertex");

    glAttachShader(shaderProgram, fragmentShader);
    checkGLError("glAttachShaderFragment");

    glLinkProgram(shaderProgram);
    checkGLError("glLinkProgram");

    GLint success;
    glGetProgramiv(shaderProgram, GL_LINK_STATUS, &success);
    checkGLError("glGetProgramiv");

    GLchar infoLog[512];
    glGetProgramInfoLog(shaderProgram, sizeof(infoLog), nullptr, infoLog);
    checkGLError("glGetProgramInfoLog");

    if (!success) {
//        GLchar infoLog[512];
//        glGetProgramInfoLog(shaderProgram, sizeof(infoLog), nullptr, infoLog);
        throw RendererException(std::string("Shader program linking failed: ") + infoLog);
    }

    glDeleteShader(vertexShader);
    checkGLError("glDeleteShaderVertex");

    glDeleteShader(fragmentShader);
    checkGLError("glDeleteShaderFragment");

    angleLocation = glGetUniformLocation(shaderProgram, "uAngle");
    checkGLError("glGetUniformLocation");

    if (angleLocation == -1) {
        throw RendererException("Failed to get uniform location for 'uAngle'");
    }
}

void Renderer::setupBuffers() {
    std::vector<float> vertices = {
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    glGenVertexArrays(1, &VAO);
    checkGLError("glGenVertexArrays");

    glGenBuffers(1, &VBO);
    checkGLError("glGenBuffers");

    glBindVertexArray(VAO);
    checkGLError("glBindVertexArray");

    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    checkGLError("glBindBuffer1");

    glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(float), vertices.data(), GL_STATIC_DRAW);
    checkGLError("glBufferData");

    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void *) 0);
    checkGLError("glVertexAttribPointer");

    glEnableVertexAttribArray(0);
    checkGLError("glEnableVertexAttribArray");

    glBindBuffer(GL_ARRAY_BUFFER, 0);
    checkGLError("glBindBuffer2");

    glBindVertexArray(0);
    checkGLError("glBindVertexArray");
}

void Renderer::checkGLError(const std::string &operation) {
    GLenum error;
    while ((error = glGetError()) != GL_NO_ERROR) {
        std::string errorStr;
        switch (error) {
            case GL_INVALID_ENUM:
                errorStr = "GL_INVALID_ENUM";
                break;
            case GL_INVALID_VALUE:
                errorStr = "GL_INVALID_VALUE";
                break;
            case GL_INVALID_OPERATION:
                errorStr = "GL_INVALID_OPERATION";
                break;
            case GL_OUT_OF_MEMORY:
                errorStr = "GL_OUT_OF_MEMORY";
                break;
            case GL_INVALID_FRAMEBUFFER_OPERATION:
                errorStr = "GL_INVALID_FRAMEBUFFER_OPERATION";
                break;
            default:
                errorStr = "Unknown error";
                break;
        }
        throw RendererException("OpenGL error in " + operation + ": " + errorStr);
    }
}
