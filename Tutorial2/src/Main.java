
/**
 * A spinning cube.
 */
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;

import datatypes.Face;
import datatypes.Edge;
import datatypes.Mesh;
import datatypes.Vertex;

public class Main implements GLEventListener
{
	private static final GLU glu = new GLU();
	private static final int width = 640;
	private static final int height = 480;
	private static float rotation = 0.0f;	
	
	private static Mesh cube;

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("The Spinning Cube To End All Spinning Cubes");
		GLCanvas canvas = new GLCanvas();
		canvas.addGLEventListener(new Main());
		frame.add(canvas);
		frame.setSize(width, height);

		frame.setVisible(true);

		//call the display() method 60 time per second
		FPSAnimator animator = new FPSAnimator(canvas, 60);
		animator.add(canvas);
		animator.start();
		
		setData();
	}

	private static void setData()
	{
		Vertex v1 = new Vertex(0.0f, 0.0f, 0.0f);
		Vertex v2 = new Vertex(0.0f, 1.0f, 0.0f);
		Vertex v3 = new Vertex(1.0f, 1.0f, 0.0f);
		Vertex v4 = new Vertex(1.0f, 0.0f, 0.0f);
		Vertex v5 = new Vertex(1.0f, 0.0f, 1.0f);
		Vertex v6 = new Vertex(1.0f, 1.0f, 1.0f);
		Vertex v7 = new Vertex(0.0f, 1.0f, 1.0f);		
		Vertex v8 = new Vertex(0.0f, 0.0f, 1.0f);
		
		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v3);
		Edge e3 = new Edge(v3, v4);
		Edge e4 = new Edge(v4, v1);
		
		Edge e5 = new Edge(v5, v6);
		Edge e6 = new Edge(v6, v7);
		Edge e7 = new Edge(v7, v8);
		Edge e8 = new Edge(v8, v5);
		
		Edge e9 = new Edge(v2, v7);
		Edge e10 = new Edge(v7, v6);
		Edge e11 = new Edge(v6, v3);
		Edge e12 = new Edge(v3, v2);
		
		Edge e13 = new Edge(v8, v1);
		Edge e14 = new Edge(v1, v4);
		Edge e15 = new Edge(v4, v5);
		Edge e16 = new Edge(v5, v8);
		
		Edge e17 = new Edge(v8, v7);
		Edge e18 = new Edge(v7, v2);
		Edge e19 = new Edge(v2, v1);
		Edge e20 = new Edge(v1, v8);
		
		Edge e21 = new Edge(v4, v3);
		Edge e22 = new Edge(v3, v6);
		Edge e23 = new Edge(v6, v5);
		Edge e24 = new Edge(v5, v4);
		
		cube = new Mesh("Cube");
		cube.addFace(new Face(e1, e2, e3, e4));
		cube.addFace(new Face(e5, e6, e7, e8));
		cube.addFace(new Face(e9, e10, e11, e12));
		cube.addFace(new Face(e13, e14, e15, e16));
		cube.addFace(new Face(e17, e18, e19, e20));
		cube.addFace(new Face(e21, e22, e23, e24));
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
		
		cube.display(gl);
		
		gl.glEnd();
		
//		// front
//		Vertex v1 = new Vertex(0.0f, 0.0f, 0.0f);
//		Vertex v2 = new Vertex(0.0f, 1.0f, 0.0f);
//		Vertex v3 = new Vertex(1.0f, 1.0f, 0.0f);
//		Vertex v4 = new Vertex(1.0f, 0.0f, 0.0f);
//		// back
//		Vertex v5 = new Vertex(1.0f, 0.0f, 1.0f);
//		Vertex v6 = new Vertex(1.0f, 1.0f, 1.0f);
//		Vertex v7 = new Vertex(0.0f, 1.0f, 1.0f);
//		Vertex v8 = new Vertex(0.0f, 0.0f, 1.0f);
//		// top
//		Vertex v9 = new Vertex(0.0f, 1.0f, 0.0f);
//		Vertex v10 = new Vertex(0.0f, 1.0f, 1.0f);
//		Vertex v11 = new Vertex(1.0f, 1.0f, 1.0f);
//		Vertex v12 = new Vertex(1.0f, 1.0f, 0.0f);
//		// bottom
//		Vertex v13 = new Vertex(0.0f, 0.0f, 1.0f);
//		Vertex v14 = new Vertex(0.0f, 0.0f, 0.0f);
//		Vertex v15 = new Vertex(1.0f, 0.0f, 0.0f);
//		Vertex v16 = new Vertex(1.0f, 0.0f, 1.0f);
//		// left
//		Vertex v17 = new Vertex(0.0f, 0.0f, 1.0f);
//		Vertex v18 = new Vertex(0.0f, 1.0f, 1.0f);
//		Vertex v19 = new Vertex(0.0f, 1.0f, 0.0f);
//		Vertex v20 = new Vertex(0.0f, 0.0f, 0.0f);
//		// right
//		Vertex v21 = new Vertex(1.0f, 0.0f, 0.0f);
//		Vertex v22 = new Vertex(1.0f, 1.0f, 0.0f);
//		Vertex v23 = new Vertex(1.0f, 1.0f, 1.0f);
//		Vertex v24 = new Vertex(1.0f, 0.0f, 1.0f);
		
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
