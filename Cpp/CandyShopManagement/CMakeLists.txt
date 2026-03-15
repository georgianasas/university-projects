cmake_minimum_required(VERSION 3.16)
project(Lab9)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_executable(Lab9
        main.cpp
        produs.cpp
        produs.h
        repo.cpp
        repo.h
        service.cpp
        service.h
        ui.cpp
        ui.h
        teste.cpp
        teste.h
        RepoFile.cpp
        RepoFile.h
        cmake-build-debug/produse.txt
        TonomatBani.cpp
        TonomatBani.h
        validator.h
        exceptii.h
)
