/*
 * For OpenGL files, you need to right-click the project,
 * select Properties > C/C++ Build > Settings
 * From the 'Tool Settings' tab; GCC C++ Linker > Libraries -l
 * Add 'glut' and 'SDL'
 *
 */

#include <GLUT/glut.h>
#include <GL/glu.h>
#include <SDL/SDL.h>
#include <iostream>

using namespace std;

#define RUN_GRAPHICS_DISPLAY 0x00;
// Evil, evil global variable
float rot = 0.0f;

/*
 * SDL timers run in separate threads.  In the timer thread
 * push an event onto the event queue.  This event signifies
 * to call display() from the thread in which the OpenGL
 * context was created.
 */
Uint32 display(Uint32 interval, void *param)
{
	SDL_Event event;
	event.type = SDL_USEREVENT;
	event.user.code = RUN_GRAPHICS_DISPLAY;
	event.user.data1 = 0;
	event.user.data2 = 0;
	SDL_PushEvent(&event);
	return interval;
}

void display()
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// Reset The Current Modelview Matrix
	glLoadIdentity();
	glTranslatef(0.0f, 0.0f, -10.0f);
	glRotatef(rot, 0.0f, 0.5f, 0.0f);
	glColor3f(1.0f,1.0f,1.0f); // Set colour to white

	glBegin(GL_QUADS);		// Draw The Cube Using quads
	glColor3f(0.0f,1.0f,0.0f);	// Color Blue
	glVertex3f(-1.0f, 1.0f,-1.0f);	// Top Right Of The Quad (Top)
	glVertex3f(1.0f, 1.0f,-1.0f);	// Top Left Of The Quad (Top)
	glVertex3f(-1.0f, 1.0f, 1.0f);	// Bottom Left Of The Quad (Top)
	glVertex3f( 1.0f, 1.0f, 1.0f);	// Bottom Right Of The Quad (Top)
	glColor3f(1.0f,0.5f,0.0f);	// Color Orange
	glVertex3f( 1.0f,-1.0f, 1.0f);	// Top Right Of The Quad (Bottom)
	glVertex3f(-1.0f,-1.0f, 1.0f);	// Top Left Of The Quad (Bottom)
	glVertex3f(-1.0f,-1.0f,-1.0f);	// Bottom Left Of The Quad (Bottom)
	glVertex3f( 1.0f,-1.0f,-1.0f);	// Bottom Right Of The Quad (Bottom)
	glColor3f(1.0f,0.0f,0.0f);	// Color Red
	glVertex3f( 1.0f, 1.0f, 1.0f);	// Top Right Of The Quad (Front)
	glVertex3f(-1.0f, 1.0f, 1.0f);	// Top Left Of The Quad (Front)
	glVertex3f(-1.0f,-1.0f, 1.0f);	// Bottom Left Of The Quad (Front)
	glVertex3f( 1.0f,-1.0f, 1.0f);	// Bottom Right Of The Quad (Front)
	glColor3f(1.0f,1.0f,0.0f);	// Color Yellow
	glVertex3f( 1.0f,-1.0f,-1.0f);	// Top Right Of The Quad (Back)
	glVertex3f(-1.0f,-1.0f,-1.0f);	// Top Left Of The Quad (Back)
	glVertex3f(-1.0f, 1.0f,-1.0f);	// Bottom Left Of The Quad (Back)
	glVertex3f( 1.0f, 1.0f,-1.0f);	// Bottom Right Of The Quad (Back)
	glColor3f(0.0f,0.0f,1.0f);	// Color Blue
	glVertex3f(-1.0f, 1.0f, 1.0f);	// Top Right Of The Quad (Left)
	glVertex3f(-1.0f, 1.0f,-1.0f);	// Top Left Of The Quad (Left)
	glVertex3f(-1.0f,-1.0f,-1.0f);	// Bottom Left Of The Quad (Left)
	glVertex3f(-1.0f,-1.0f, 1.0f);	// Bottom Right Of The Quad (Left)
	glColor3f(1.0f,0.0f,1.0f);	// Color Violet
	glVertex3f( 1.0f, 1.0f,-1.0f);	// Top Right Of The Quad (Right)
	glVertex3f( 1.0f, 1.0f, 1.0f);	// Top Left Of The Quad (Right)
	glVertex3f( 1.0f,-1.0f, 1.0f);	// Bottom Left Of The Quad (Right)
	glVertex3f( 1.0f,-1.0f,-1.0f);	// Bottom Right Of The Quad (Right)
	glEnd();			// End Drawing The Cube


	rot += 0.5f;
	// Don't forget to swap the buffers
	SDL_GL_SwapBuffers();
}

int main(int argc, char ** argv)
{
	SDL_Surface * surf;
	Uint32 width = 640;
	Uint32 height = 480;
	Uint32 colour_depth = 16; // in bits
	Uint32 delay = 1000/60; // in milliseconds

	// Initialise SDL - when using C/C++ it's common to have to
	// initialise libraries by calling a function within them.
	if (SDL_Init(SDL_INIT_VIDEO|SDL_INIT_AUDIO|SDL_INIT_TIMER)<0)
	{
			cout << "Failed to initialise SDL: " << SDL_GetError() << endl;
			SDL_Quit();
	}

	// When we close a window quit the SDL application
	atexit(SDL_Quit);

	// Create a new window with an OpenGL surface
	if (!(surf = SDL_SetVideoMode(width, height, colour_depth, SDL_OPENGL)))
	{
			cout << "Failed to initialise video mode: " << SDL_GetError() << endl;
			SDL_Quit();
	}

	// Set the state of our new OpenGL context
	glViewport(0,0,(GLsizei)(width),(GLsizei)(height));
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	gluPerspective(45.0f,(GLfloat)(width)/(GLfloat)(height),1.0f,1000.0f);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	glShadeModel(GL_SMOOTH);							// Enable Smooth Shading
	glClearColor(0.0f, 0.0f, 0.0f, 0.5f);				// Black Background
	glClearDepth(1.0f);									// Depth Buffer Setup
	glEnable(GL_DEPTH_TEST);							// Enables Depth Testing
	glDepthFunc(GL_LEQUAL);								// The Type Of Depth Testing To Do
	glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);	// Really Nice Perspective Calculations

	// Call the function "display" every delay milliseconds
	SDL_AddTimer(delay, display, NULL);

	// Add the main event loop
	SDL_Event event;
	while (SDL_WaitEvent(&event))
	{
			switch (event.type)
			{
				case SDL_QUIT:
					SDL_Quit();
				case SDL_USEREVENT:
					display();
			}
	}
}
