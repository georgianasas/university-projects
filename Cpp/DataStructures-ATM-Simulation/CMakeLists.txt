cmake_minimum_required(VERSION 3.30)
project(seminar2_sda)

set(CMAKE_CXX_STANDARD 20)

add_executable(seminar2_sda main.cpp
        Collection.h
        ATM.h
        ATM.cpp
        Tranzactie.h
        Tranzactie.cpp
        comparators.h
        Multime.h
        Iterator.h
)
