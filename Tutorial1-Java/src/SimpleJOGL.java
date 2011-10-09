/**
 * A spinning cube.
 */
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;

public class SimpleJOGL implements GLEventListener
{
	private static final GLU glu = new GLU();
	private static final int width = 640;
	private static final int height = 480;
	private static float rotation = 0.0f;	
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("The Spinning Cube To End All Spinning Cubes");
		GLCanvas canvas = new GLCanvas();
		canvas.addGLEventListener(new SimpleJOGL());
		frame.add(canvas);
		frame.setSize(width, height);
		
		frame.setVisible(true);
		
		//call the display() method 60 time per second
		FPSAnimator animator = new FPSAnimator(canvas, 60);
		animator.add(canvas);
		animator.start();
	}
	
	/**
	* Draw the quads... 
	*/
	@Override
	public void display(GLAutoDrawable glDrawable)
	{
		final GL2 gl = (GL2)glDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -7.0f);
		
		// Rotate The cube around the y axis
		gl.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);
		
		gl.glBegin(GL2.GL_QUADS);
			gl.glColor3f(0.0f,1.0f,0.0f);	// Color Blue
			// front
				gl.glVertex3f(0.0f, 0.0f, 0.0f); 
				gl.glVertex3f(0.0f, 1.0f, 0.0f); 
				gl.glVertex3f(1.0f, 1.0f, 0.0f);
				gl.glVertex3f(1.0f, 0.0f, 0.0f);
			gl.glColor3f(1.0f,0.5f,0.0f);	// Color Orange
			// back
				gl.glVertex3f(1.0f, 0.0f, 1.0f);
				gl.glVertex3f(1.0f, 1.0f, 1.0f); 
				gl.glVertex3f(0.0f, 1.0f, 1.0f);
				gl.glVertex3f(0.0f, 0.0f, 1.0f);
			gl.glColor3f(1.0f,0.0f,0.0f);	// Color Red	
			// top
				gl.glVertex3f(0.0f, 1.0f, 0.0f);
				gl.glVertex3f(0.0f, 1.0f, 1.0f);	
				gl.glVertex3f(1.0f, 1.0f, 1.0f);
				gl.glVertex3f(1.0f, 1.0f, 0.0f);
			gl.glColor3f(1.0f,1.0f,0.0f);	// Color Yellow
			// bottom
				gl.glVertex3f(0.0f, 0.0f, 1.0f);
				gl.glVertex3f(0.0f, 0.0f, 0.0f); 
				gl.glVertex3f(1.0f, 0.0f, 0.0f);
				gl.glVertex3f(1.0f, 0.0f, 1.0f);
			gl.glColor3f(0.0f,0.0f,1.0f);	// Color Blue
			// left
				gl.glVertex3f(0.0f, 0.0f, 1.0f);
				gl.glVertex3f(0.0f, 1.0f, 1.0f); 
				gl.glVertex3f(0.0f, 1.0f, 0.0f);
				gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glColor3f(1.0f,0.0f,1.0f);	// Color Violet
			// right
				gl.glVertex3f(1.0f, 0.0f, 0.0f);
				gl.glVertex3f(1.0f, 1.0f, 0.0f); 
				gl.glVertex3f(1.0f, 1.0f, 1.0f);
				gl.glVertex3f(1.0f, 0.0f, 1.0f);
	   gl.glEnd();	
		  
		rotation += 1.0;
	}

	@Override
	public void dispose(GLAutoDrawable arg0) { }

	/**
	* All of this code is boilerplate, it sets up the OpenGL context correctly. 
	* 
	* In particular, it sets up the correct projection matrix so that if things are far away they look
	* smaller than if they’re up close.
	* 
	* The gluPerspective call sets the near clipping pane to be at 1.0 and the far to be at 100.0, 
	* thus your cube will need * to be between these two planes.
	*/
	@Override
	public void init(GLAutoDrawable glDrawable)
	{
		final GL2 gl = (GL2)glDrawable.getGL();
		
		// Set the state of our new OpenGL context
		gl.glViewport (0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION); 
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, (float)(width)/(float)(height), 1.0f, 100.0f); 
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		// Enable Smooth Shading
		gl.glShadeModel(GL2.GL_SMOOTH);
		
		// Black Background
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		
		// Depth Buffer Setup
		gl.glClearDepth(1.0f);

		// Enables Depth Testing
		gl.glEnable(GL.GL_DEPTH_TEST);
		
		// The Type Of Depth Testing To Do
		gl.glDepthFunc(GL.GL_LEQUAL);
		
		// Start culling back faces
		gl.glEnable(GL.GL_CULL_FACE);
		
		// Really Nice Perspective Calculations
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {	}
}

