/**
 * The trademark OpenGL spinning cube
 *
 * @author Tom
 * @version 0.1
 * @history 14.10.2011: Created class
 */

#include<iostream>
#include <GL/gl.h>
#include <GL/glu.h>
#include <SDL/SDL.h>

#define RUN_GRAPHICS_DISPLAY 0x00;

using namespace std;

float rotation = 0.0f;

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
   glLoadIdentity();
   glTranslatef(0.0f, 0.0f, -7.0f);

   // Rotate The cube around the y axis
   glRotatef(rotation, 0.0f, 1.0f, 0.0f);
   glRotatef(rotation, 1.0f, 1.0f, 1.0f);

   glBegin( GL_QUADS);
   glColor3f(0.0f, 1.0f, 0.0f); // Color Blue
   // front
   glVertex3f(0.0f, 0.0f, 0.0f);
   glVertex3f(0.0f, 1.0f, 0.0f);
   glVertex3f(1.0f, 1.0f, 0.0f);
   glVertex3f(1.0f, 0.0f, 0.0f);
   glColor3f(1.0f, 0.5f, 0.0f); // Color Orange
   // back
   glVertex3f(1.0f, 0.0f, 1.0f);
   glVertex3f(1.0f, 1.0f, 1.0f);
   glVertex3f(0.0f, 1.0f, 1.0f);
   glVertex3f(0.0f, 0.0f, 1.0f);
   glColor3f(1.0f, 0.0f, 0.0f); // Color Red
   // top
   glVertex3f(0.0f, 1.0f, 0.0f);
   glVertex3f(0.0f, 1.0f, 1.0f);
   glVertex3f(1.0f, 1.0f, 1.0f);
   glVertex3f(1.0f, 1.0f, 0.0f);
   glColor3f(1.0f, 1.0f, 0.0f); // Color Yellow
   // bottom
   glVertex3f(0.0f, 0.0f, 1.0f);
   glVertex3f(0.0f, 0.0f, 0.0f);
   glVertex3f(1.0f, 0.0f, 0.0f);
   glVertex3f(1.0f, 0.0f, 1.0f);
   glColor3f(0.0f, 0.0f, 1.0f); // Color Blue
   // left
   glVertex3f(0.0f, 0.0f, 1.0f);
   glVertex3f(0.0f, 1.0f, 1.0f);
   glVertex3f(0.0f, 1.0f, 0.0f);
   glVertex3f(0.0f, 0.0f, 0.0f);
   glColor3f(1.0f, 0.0f, 1.0f); // Color Violet
   // right
   glVertex3f(1.0f, 0.0f, 0.0f);
   glVertex3f(1.0f, 1.0f, 0.0f);
   glVertex3f(1.0f, 1.0f, 1.0f);
   glVertex3f(1.0f, 0.0f, 1.0f);
   glEnd();

   rotation += 1.0;

   // Don’t forget to swap the buffers
   SDL_GL_SwapBuffers();
}

int main(int argc, char ** argv)
{
   SDL_Surface * surf;
   Uint32 width = 640;
   Uint32 height = 480;
   Uint32 colour_depth = 16; // in bits
   Uint32 delay = 1000/60; // in milliseconds

   if(SDL_Init(SDL_INIT_VIDEO|SDL_INIT_AUDIO|SDL_INIT_TIMER) < 0)
   {
      cout << "Failed to initialise SDL: " << SDL_GetError() << endl;
      SDL_Quit();
   }

   atexit(SDL_Quit);

   if (!(surf = SDL_SetVideoMode(width, height, colour_depth, SDL_OPENGL)))
   {
      cout << "Failed to initialise video mode: " << SDL_GetError() << endl;
      SDL_Quit();
   }

   glViewport(0, 0, (GLsizei) (width), (GLsizei) (height));
   glMatrixMode (GL_PROJECTION);
   glLoadIdentity();
   gluPerspective(45.0f, (GLfloat) (width) / (GLfloat) (height), 1.0f, 1000.0f);
   glMatrixMode(GL_MODELVIEW);
   glLoadIdentity();
   glShadeModel(GL_SMOOTH);
   glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
   glClearDepth(1.0f);
   glEnable(GL_DEPTH_TEST);
   glDepthFunc(GL_LEQUAL);
   glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
   SDL_AddTimer(delay , display , NULL) ;

   SDL_Event event;
   while (SDL_WaitEvent(&event))
   {
      switch (event . type)
      {
         case SDL_QUIT:
            SDL_Quit();
            break;
         case SDL_USEREVENT:
            display();
      }
   }
}
