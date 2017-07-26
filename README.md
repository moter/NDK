# NDK
Android apk 增量更新，结合NDK自己编译so类库
编译so类库需要关注的点：
1，CMakeLists.txt
add_library( # Sets the name of the library.
             apkpatch-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/bzip2/blocksort.c
             src/main/cpp/bzip2/bzip2.c
             src/main/cpp/bzip2/bzip2recover.c
             src/main/cpp/bzip2/bzlib.c
             src/main/cpp/bzip2/compress.c
             src/main/cpp/bzip2/crctable.c
             src/main/cpp/bzip2/decompress.c
             src/main/cpp/bzip2/huffman.c
             src/main/cpp/bzip2/randtable.c
             src/main/cpp/com_moci_lib_bsdiff_PatchUtils.c)
需要把要编译的文件引入，否则会出现引用错误
2，生成的so库在build/intermediates/cmake下，不过需要运行程序或者打包程序才会生成
