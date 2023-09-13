# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name. The project name can be accessed via ${ PROJECT_NAME},
# Since this is the top level CMakeLists.txt, the project name is also accessible
# with ${CMAKE_PROJECT_NAME} (both CMake variables are in-sync within the top level
# build script scope).
project("nativelib")

# 指定包含的头文件路径
include_directories( src/main/cpp )

# 搜索自动目录下的所有源代码文件，并存储在 SRC_LIST 变量中
aux_source_directory( src/main/cpp SRC_LIST )

# 指定生成的库的名称、类型、源码
add_library(${CMAKE_PROJECT_NAME} SHARED # 设置生成库的类型（SHARED 动态库，STATIC 静态库）
        # 指定源码的路径
        nativelib.cpp
        Register.cpp)

# 查找预编译库，并将其保存到指定的变量名中
find_library( # 变量名，可以自己随便取
        log-lib
        # 预编译库的路径，默认搜索路径为 cmake 包含的系统库，因此如果是 NDK 的公共库只需要指定库的名称即可。
        log )

# Specifies libraries CMake should link to your target library. You
# can link libraries from various origins, such as libraries defined in this
# build script, prebuilt third-party libraries, or Android system libraries.
target_link_libraries(${CMAKE_PROJECT_NAME}
        # List libraries link to the target library
        android
        log)