cmake_minimum_required(VERSION 3.18.1)

project("secret")

add_library(
        secret
        SHARED
        secret_manager.cpp
)

find_library(
        log-lib
        log
)

target_link_libraries(
        secret
        ${log-lib}
)