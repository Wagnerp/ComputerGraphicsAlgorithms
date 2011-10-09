/*
 * Main.h
 *
 *  Created on: Oct 6, 2011
 *      Author: tom
 */

#ifndef MAIN_H_
#define MAIN_H_

class Main {
public:
	Main();
	virtual ~Main();

	void main_loop_function();
	void GL_Setup(int width, int height);
};

#endif /* MAIN_H_ */
