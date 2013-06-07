Credit Bureau
=============

Instalacion de Software
-----------------------
1. Instalar cmake desde http://www.cmake.org/
2. Agregar al PATH la siguiente ruta: "C:\Program Files (x86)\CMake 2.8"

Instrucciones para compilar
---------------------------
1. Ingresar al directorio src/c

### Windows
2. Generar el archivo Makefile con esta instruccion:
    `cmake -G "MinGW Makefiles"`
3. Compilar el programa con la siguiente instruccion:
    `mingw32-make.exe`

### OSX
2. Generar el archivo Makefile con esta instruccion:
    `cmake .`
3. Compilar el programa con la siguiente instruccion:
    `make`
