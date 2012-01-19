
MD3 File Loader 


To run using Eclipse:

1, Extract folder
2, Create a new C++ project in Eclipse
4, Drag the 'src' folder into the project 
5, Drag the 'data' folder into the project
7, Right-click the project and choose: Properties->C/C++ Build->Settings->GCC C++ Linker->Libraries
8, Add the SDL GL and GLU libraries  
8, Save the project
9, Build and run the program using the corresponding buttons in Eclipse (or the keyboard shortcuts: Build- Ctrl+B, Run- Ctrl+F11)


Notes:

The MD3 loader program currently only works for models with one surface. I have included the broken code (GameObject.cpp file) for the 
multi-surface MD3 loader. The code can be found in the root folder in the file BROKEN_MD3_multi_surface.
