// Java imports
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;

// Package imports
import datatypes.Face;
import datatypes.Edge;
import datatypes.Mesh;
import datatypes.Vertex;

/**
 * The main class, largely OpenGL boilerplate code
 *
 *	TODO When you get a spare 5 mins, implement butterfly subdivision
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Main implements GLEventListener
{
	// Some basic window/display options
	private static final String windowTitle = "Cube Using Face-Edge-Vertex";
	private static final int width = 640;
	private static final int height = 480;
	private static final int framerate = 30;
	
	private static final GLU glu = new GLU();
	private static float rotation = 0.0f;
	
	// the cube object
	private static Mesh cube;

	public static void main(String[] args)
	{
		JFrame frame = new JFrame(windowTitle);
		GLCanvas canvas = new GLCanvas();
		canvas.addGLEventListener(new Main());
		frame.add(canvas);
		frame.setSize(width, height);
		frame.setVisible(true);

		FPSAnimator animator = new FPSAnimator(canvas, framerate);
		animator.add(canvas);
		animator.start();
		
		setTriangleData();
	}
	
	/**
	 * Sets the data using 3-point faces
	 */
	private static void setTriangleData()
	{		
		Vertex v1 = new Vertex(0.0f, 0.0f, 0.0f);
		Vertex v2 = new Vertex(0.0f, 1.0f, 0.0f);
		Vertex v3 = new Vertex(1.0f, 1.0f, 0.0f);
		Vertex v4 = new Vertex(1.0f, 0.0f, 0.0f);
		Vertex v5 = new Vertex(1.0f, 0.0f, -1.0f);
		Vertex v6 = new Vertex(1.0f, 1.0f, -1.0f);
		Vertex v7 = new Vertex(0.0f, 1.0f, -1.0f);		
		Vertex v8 = new Vertex(0.0f, 0.0f, -1.0f);
		
		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v3);
		Edge e3 = new Edge(v3, v1);
		Edge e4 = new Edge(v3, v4);
		Edge e5 = new Edge(v4, v1);
		Edge e6 = new Edge(v5, v6);
		Edge e7 = new Edge(v6, v7);
		Edge e8 = new Edge(v7, v5);
		Edge e9 = new Edge(v7, v8);
		Edge e10 = new Edge(v8, v5);
		Edge e11 = new Edge(v2, v7);
		Edge e12 = new Edge(v6, v2);		
		Edge e13 = new Edge(v6, v3);	
		Edge e14 = new Edge(v8, v1);
		Edge e15 = new Edge(v4, v8);
		Edge e16 = new Edge(v4, v5);
		Edge e17 = new Edge(v2, v8);
		Edge e18 = new Edge(v6, v4);
					
		// Hide some code in the Mesh class
		cube = new Mesh("Cube");
		// front face
		cube.addFace(new Face(e1, e2, e3,    new byte[]{0,0,0})); 
		cube.addFace(new Face(e3, e4, e5,    new byte[]{1,0,0}));
		// back face	
		cube.addFace(new Face(e6, e7, e8,    new byte[]{0,0,0}));
		cube.addFace(new Face(e8, e9, e10,   new byte[]{1,0,0}));
		// top face
		cube.addFace(new Face(e11, e7, e12,  new byte[]{0,1,0}));
		cube.addFace(new Face(e12, e13, e2,  new byte[]{1,0,1}));
		// bottom face
		cube.addFace(new Face(e14, e5, e15,  new byte[]{0,1,0}));
		cube.addFace(new Face(e15, e16, e10, new byte[]{1,0,1}));
		// left face
		cube.addFace(new Face(e9, e11, e17,  new byte[]{1,1,0}));
		cube.addFace(new Face(e17, e1, e14,  new byte[]{1,1,1}));
		// right face
		cube.addFace(new Face(e4, e13, e18,  new byte[]{1,1,0}));
		cube.addFace(new Face(e18, e6, e16,  new byte[]{1,1,1}));
	}
	
	@Override
	public void init(GLAutoDrawable glDrawable)
	{
		final GL2 gl = (GL2)glDrawable.getGL();

		gl.glViewport (0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION); 
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, (float)(width)/(float)(height), 1.0f, 100.0f); 
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
	}

	@Override
	public void display(GLAutoDrawable glDrawable)
	{
		final GL2 gl = (GL2)glDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -4.0f);

		// Rotate The cube around the y axis
		gl.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);
		
		// draw the mesh
		cube.draw(gl);
		Mesh subdividedCube = cube.subdivide();
		
		rotation += 1.0;
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {	}
	
	@Override
	public void dispose(GLAutoDrawable arg0) { }
}
