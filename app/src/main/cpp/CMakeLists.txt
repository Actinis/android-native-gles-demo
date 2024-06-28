cmake_minimum_required(VERSION 3.22.1)

project("native-lib")

find_library(GLESv3-lib GLESv3)

add_library(${CMAKE_PROJECT_NAME} SHARED
        Renderer.cpp
        native-lib.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        log
        ${GLESv3-lib}
)
