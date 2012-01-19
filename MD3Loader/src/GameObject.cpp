/*
 * GameObject.cpp
 *
 *  Created on: 27 Nov 2009
 *      Author: tom
 */

#include "GameObject.h"

GameObject::GameObject()
{
}

GameObject::GameObject(const char * filename)
{
	//open file
	ifstream md3file;
	md3file.open(filename, ios::in|ios::binary);

	//read in md3 header
	this->md3header = (struct md3_header *) malloc(sizeof(struct md3_header));
	md3file.read((char *) md3header, sizeof(struct md3_header));

	//check for valid file
	if((this->md3header->ident != 860898377) || (this->md3header->version != 15))
	{
		cerr << "Error: bad version or identifier" << endl;
	}

	//read in frame data
	this->frame = (struct md3_frame *) calloc(this->md3header->num_frames, sizeof(struct md3_frame));
	md3file.seekg(this->md3header->ofs_frames_start, ios::beg);
	md3file.read((char *) this->frame, sizeof(struct md3_frame) * this->md3header->num_frames);

	//read in tag data
	this->tag = (struct md3_tag *) calloc(this->md3header->num_tags, sizeof(struct md3_tag));
	md3file.seekg(this->md3header->ofs_frames_start, ios::beg);
	md3file.read((char *) this->tag, sizeof(struct md3_tag) * this->md3header->num_tags);

	//read in surface header
	this->surfaceH = (struct md3_surface_header *) malloc(sizeof(struct md3_surface_header));
	md3file.seekg(this->md3header->ofs_surfaces_start, ios::beg);
	md3file.read((char *) this->surfaceH, sizeof(struct md3_surface_header));

	//read in shader data
	this->shader = (struct md3_shader *) malloc(sizeof(struct md3_shader));
	md3file.seekg(this->surfaceH->ofs_shaders_start + this->md3header->ofs_surfaces_start, ios::beg);
	md3file.read((char *) this->shader, sizeof(struct md3_shader) * this->surfaceH->num_shaders);

	//read in triangle data
	this->triangle = (struct md3_triangle *) calloc(this->surfaceH->num_triangles, sizeof(struct md3_triangle));
	md3file.seekg(this->surfaceH->ofs_triangles_start + this->md3header->ofs_surfaces_start, ios::beg);
	md3file.read((char *) this->triangle, sizeof(struct md3_triangle) * this->surfaceH->num_triangles);

	//read in texcoord data
	this->texcoord = (struct md3_texcoord *) calloc(this->surfaceH->num_verts, sizeof(struct md3_texcoord));
	md3file.seekg(this->surfaceH->ofs_st_start + this->md3header->ofs_surfaces_start, ios::beg);
	md3file.read((char *) this->texcoord, sizeof(struct md3_texcoord) * this->surfaceH->num_verts);

	//read in vertex data
	this->vertex = (struct md3_vertex *) calloc(this->surfaceH->num_verts * this->md3header->num_frames, sizeof(struct md3_vertex));
	md3file.seekg(this->surfaceH->ofs_xyznormal_start + this->md3header->ofs_surfaces_start, ios::beg);
	md3file.read((char *) this->vertex, sizeof(struct md3_vertex) * this->surfaceH->num_verts * this->surfaceH->num_frames);
}

GameObject::~GameObject()
{
}

void GameObject::draw()
{
	glBegin(GL_TRIANGLES);
	//for(int i = 0; i < this->num_surf; i++)
	//{
		for(int j = 0; j < this->surfaceH->num_triangles; j++)
		{
			for(int k = 0; k < 3; k++)
			{
				glVertex3f(
							this->vertex[(this->triangle[j].indexes[k])].coord[0]*1/64,
							this->vertex[(this->triangle[j].indexes[k])].coord[1]*1/64,
							this->vertex[(this->triangle[j].indexes[k])].coord[2]*1/64
						  );
			}
		}
	//}
	glEnd();
}
