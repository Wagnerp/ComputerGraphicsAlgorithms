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
	private static final int framerate = 60;
	
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
		
//		setQuadData();
		setTriangleData();
	}

	/**
	 * Sets the data using 4-point faces
	 */
	private static void setQuadData()
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
		Edge e10 = new Edge(v6, v3);
		Edge e11 = new Edge(v8, v1);
		Edge e12 = new Edge(v4, v5);		
		
		cube = new Mesh("Cube");
		cube.addFace(new Face(e1, e2, e3, e4));
		cube.addFace(new Face(e5, e6, e7, e8));
		cube.addFace(new Face(e9, e6.invert(), e10, e2.invert()));
		cube.addFace(new Face(e11, e4.invert(), e12, e8.invert()));
		cube.addFace(new Face(e7.invert(), e9.invert(), e1.invert(), e11.invert()));
		cube.addFace(new Face(e3.invert(), e10.invert(), e5.invert(), e12.invert()));
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
		Edge e10 = new Edge(v6, v3);
		Edge e11 = new Edge(v8, v1);
		Edge e12 = new Edge(v4, v5);		
		Edge e13 = new Edge(v3, v1);	
		Edge e14 = new Edge(v7, v5);
		Edge e15 = new Edge(v6, v2);
		Edge e16 = new Edge(v4, v8);
		Edge e17 = new Edge(v2, v8);
		Edge e18 = new Edge(v6, v4);
		
		cube = new Mesh("Cube");
		cube.addFace(new Face(e2, e1, e13));
		cube.addFace(new Face(e4, e3, e13)); 
		cube.addFace(new Face(e6, e5, e14));
		cube.addFace(new Face(e8, e7, e14.invert()));
		cube.addFace(new Face(e6.invert(), e9, e15));
		cube.addFace(new Face(e2.invert(), e10, e15.invert()));
		cube.addFace(new Face(e4.invert(), e11, e16));
		cube.addFace(new Face(e8.invert(), e12, e16.invert()));
		cube.addFace(new Face(e9.invert(), e7.invert(), e17));
		cube.addFace(new Face(e11.invert(), e1.invert(), e17.invert()));
		cube.addFace(new Face(e10.invert(), e3.invert(), e18));
		cube.addFace(new Face(e12.invert(), e5.invert(), e18.invert()));
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
		cube.display(gl);
		
		rotation += 1.0;
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {	}
	
	@Override
	public void dispose(GLAutoDrawable arg0) { }
}
