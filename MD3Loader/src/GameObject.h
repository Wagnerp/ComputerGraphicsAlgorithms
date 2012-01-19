/*
 * GameObject.h
 *
 *  Created on: 27-Nov-2009
 *      Author: tom
 */

#include <GL/gl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string>
#include <iostream>
#include <fstream>

using namespace std;

#ifndef GAMEOBJECT_H_
#define GAMEOBJECT_H_

class GameObject {
public:
	GameObject();
	GameObject(const char * filename);
	virtual ~GameObject();

	virtual void draw();
private:
	typedef float vec3[3];

	struct md3_header
	{
      int ident;                  // magic number: "IDP3"
	  int version;                // MD3 version number; always 15
	  char name[64];			  // MD3 name, can be left blank. usually its pathname in the PK3
	  int flags;				  // ??
	  int num_frames;			  // Number of Frames
	  int num_tags;				  // Number of Tag objects,
	  int num_surfaces;			  // Number of Surface objects
	  int num_skins;			  // Artifact from MD2 file format
	  int ofs_frames_start;		  // Offset from start of MD3 object where Frame objects start (written sequentially, when you read one Frame object, you do not need to seek() for the next object)
	  int ofs_tags_start;	  	  // Offset from start of MD3 to where Tag objects start (written sequentially)
	  int ofs_surfaces_start; 	  // Offset from start of MD3 where Surface objects start (written sequentially)
	  int ofs_MD3_end;			  // Offset from start of MD3 to the end of the MD3 object. Note there is no offset for Skin objects
	};

	struct md3_frame
	{
	  vec3 min_bounds;		 	  // First corner of the bounding box
	  vec3 max_bounds;		  	  // Second corner of the bounding box
	  vec3 local_origin;		  // Local origin, usually (0, 0, 0)
	  float radius;				  // Radius of bounding sphere
	  char name[64];			  // Name of Frame. ASCII character string
	};

	struct md3_tag
	{
	  char name[64];		  	  // Name of Tag object
	  vec3 coord;		  	  	  // Relative position of Tag object
	  vec3 axis[3];		  	  	  // Orientation of Tag object relative to rest of model
	};

	struct md3_surface_header
	{
	  int ident;		  	  	  // Magic number: "IDP3"
	  char name[64];		  	  // MD3 name, usually its pathname in the PK3
	  int flags;				  // ??
	  int num_frames;			  // Number of Frame objects in surface object
	  int num_shaders;			  // Number of Shader (skin) objects in this Surface
	  int num_verts;			  // Number of Vertex objects in this Surface
	  int num_triangles;		  // Number of Triangle objects in this Surface
	  int ofs_triangles_start;	  // Relative offset from surfaces_start where the list of Triangle objects starts
	  int ofs_shaders_start; 	  // Relative offset from surfaces_start where the list of Shader objects starts
	  int ofs_st_start;		   	  // Relative offset from surfaces_start where the list of St objects (Texture Coordinates, S-T vertices) starts
	  int ofs_xyznormal_start;	  // Relative offset from surfaces_start where the list of Vertex objects (X-Y-Z-N vertices) starts
	  int ofs_surfaces_end;		  // Relative offset from surfaces_start to where the Surface object ends
	};

	struct md3_shader
	{
	  char name[64];		  	  // Name of shader used by mesh
	  int shader_index;		  	  // Shader index number. No idea how this is allocated, but presumably in sequential order of definiton
	};

	struct md3_triangle
	{
	  int indexes[3];		  	  // List of corners of Triangle object. Vertex numbers are used instead of actual coordinates, as the coordinates are implicit in the Vertex object. The triangles have clockwise winding
	};

	struct md3_texcoord
	{
	  float st[2]; 	 	 		  // Texture coordinates of vertex, normalized to the range [0, 1]. Values outside the range indicate wraparounds/repeats
	};

	struct md3_vertex
	{
	  short coord[3];		  	  // x, y, z coordinates
	  char normal[2];			  // Environmental mapping coordinates
	};

	struct md3_surface
	{
		md3_surface_header * header;
		md3_shader * shader;
		md3_triangle * triangle;
		md3_texcoord * st;
		md3_vertex * xyznormal;
	};

	vec3 * vertices;
	int num_vertices;
	int num_triangles;
	int num_surf;

	md3_header * md3header;
	md3_frame * frame;
	md3_tag * tag;
	md3_surface_header * surfaceH;
	md3_shader * shader;
	md3_triangle * triangle;
	md3_texcoord * texcoord;
	md3_vertex * vertex;
};

#endif /* GAMEOBJECT_H_ */
