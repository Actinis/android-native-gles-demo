#ifndef RENDERER_H
#define RENDERER_H

#include <GLES3/gl3.h>
#include <string>
#include <stdexcept>

class Renderer {
public:
    Renderer();

    ~Renderer();

    void initialize();

    void render(float angle);

    void cleanup();

private:
    GLuint shaderProgram;
    GLuint VAO, VBO;
    GLint angleLocation;

    GLuint compileShader(GLenum type, const char *source);

    void setupShaders();

    void setupBuffers();

    void checkGLError(const std::string &operation);
};

class RendererException : public std::runtime_error {
public:
    RendererException(const std::string &message) : std::runtime_error(message) {}
};

#endif // RENDERER_H
