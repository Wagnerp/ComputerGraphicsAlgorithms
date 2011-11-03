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
		gl.glTranslatef(0.0f, 0.0f, -10.0f);

		// Rotate The cube around the y axis
		gl.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);
		
		// draw the mesh
		cube.draw(gl);
		Mesh subdividedCube = cube.subdivide();
		
		rotation += 1.0;
	}
	
	/**
	 * Sets the data using 3-point faces
	 */
	private static void setTriangleData()
	{		
		Vertex v1  = new Vertex(0.0f, 2.0f, 0.0f);
		Vertex v2  = new Vertex(1.0f, 2.0f, 0.0f);
		Vertex v3  = new Vertex(2.0f, 2.0f, 0.0f);
		Vertex v4  = new Vertex(0.0f, 1.0f, 0.0f);
		Vertex v5  = new Vertex(1.0f, 1.0f, 0.0f);
		Vertex v6  = new Vertex(2.0f, 1.0f, 0.0f);
		Vertex v7  = new Vertex(0.0f, 0.0f, 0.0f);
		Vertex v8  = new Vertex(1.0f, 0.0f, 0.0f);
		Vertex v9  = new Vertex(2.0f, 0.0f, 0.0f);
		Vertex v10 = new Vertex(2.0f, 2.0f, -2.0f);
		Vertex v11 = new Vertex(1.0f, 2.0f, -2.0f);
		Vertex v12 = new Vertex(0.0f, 2.0f, -2.0f);
		Vertex v13 = new Vertex(2.0f, 1.0f, -2.0f);
		Vertex v14 = new Vertex(1.0f, 1.0f, -2.0f);
		Vertex v15 = new Vertex(0.0f, 1.0f, -2.0f);
		Vertex v16 = new Vertex(2.0f, 0.0f, -2.0f);
		Vertex v17 = new Vertex(1.0f, 0.0f, -2.0f);
		Vertex v18 = new Vertex(0.0f, 0.0f, -2.0f);
		Vertex v19 = new Vertex(0.0f, 2.0f, -1.0f);
		Vertex v20 = new Vertex(1.0f, 2.0f, -1.0f);
		Vertex v21 = new Vertex(2.0f, 2.0f, -1.0f);
		Vertex v22 = new Vertex(0.0f, 0.0f, -1.0f);
		Vertex v23 = new Vertex(1.0f, 0.0f, -1.0f);
		Vertex v24 = new Vertex(2.0f, 0.0f, -1.0f);
		Vertex v25 = new Vertex(0.0f, 1.0f, -1.0f);
		Vertex v26 = new Vertex(2.0f, 1.0f, -1.0f);
		
		Edge e1  = new Edge(v4, v1);
		Edge e2  = new Edge(v1, v5);
		Edge e3  = new Edge(v5, v4);
		Edge e4  = new Edge(v1, v2);
		Edge e5  = new Edge(v2, v5);
		Edge e6  = new Edge(v2, v6);
		Edge e7  = new Edge(v6, v5);
		Edge e8  = new Edge(v2, v3);
		Edge e9  = new Edge(v3, v6);
		Edge e10 = new Edge(v7, v4);
		Edge e11 = new Edge(v4, v8);
		Edge e12 = new Edge(v8, v7);
		Edge e13 = new Edge(v5, v8);
		Edge e14 = new Edge(v5, v9);
		Edge e15 = new Edge(v9, v8);
		Edge e16 = new Edge(v6, v9);
		Edge e17 = new Edge(v13, v10);
		Edge e18 = new Edge(v10, v14);
		Edge e19 = new Edge(v14, v13);
		Edge e20 = new Edge(v10, v11);
		Edge e21 = new Edge(v11, v14);
		Edge e22 = new Edge(v11, v15);
		Edge e23 = new Edge(v15, v14);
		Edge e24 = new Edge(v11, v12);
		Edge e25 = new Edge(v12, v15);
		Edge e26 = new Edge(v16, v13);
		Edge e27 = new Edge(v13, v17);
		Edge e28 = new Edge(v17, v16);
		Edge e29 = new Edge(v14, v17);
		Edge e30 = new Edge(v14, v18);
		Edge e31 = new Edge(v18, v17);
		Edge e32 = new Edge(v15, v18);
		Edge e33 = new Edge(v19, v12);
		Edge e34 = new Edge(v12, v20);
		Edge e35 = new Edge(v20, v19);
		Edge e36 = new Edge(v11, v20);
		Edge e37 = new Edge(v11, v21);
		Edge e38 = new Edge(v21, v20);
		Edge e39 = new Edge(v10, v21);
		Edge e40 = new Edge(v1, v19);
		Edge e41 = new Edge(v19, v2);
		Edge e42 = new Edge(v20, v2);
		Edge e43 = new Edge(v20, v3);
		Edge e44 = new Edge(v21, v3);
		Edge e45 = new Edge(v22, v7);
		Edge e46 = new Edge(v7, v23);
		Edge e47 = new Edge(v23, v22);
		Edge e48 = new Edge(v8, v23);
		Edge e49 = new Edge(v8, v24);
		Edge e50 = new Edge(v24, v23);
		Edge e51 = new Edge(v9, v24);
		Edge e52 = new Edge(v18, v22);
		Edge e53 = new Edge(v22, v17);
		Edge e54 = new Edge(v23, v17);
		Edge e55 = new Edge(v23, v16);
		Edge e56 = new Edge(v24, v16);
		Edge e57 = new Edge(v3, v26);
		Edge e58 = new Edge(v26, v6);
		Edge e59 = new Edge(v21, v26);
		Edge e60 = new Edge(v21, v13);
		Edge e61 = new Edge(v13, v26);
		Edge e62 = new Edge(v6, v24);
		Edge e63 = new Edge(v26, v24);
		Edge e64 = new Edge(v26, v16);
		Edge e65 = new Edge(v12, v25);
		Edge e66 = new Edge(v25, v15);
		Edge e67 = new Edge(v19, v25);
		Edge e68 = new Edge(v19, v4);
		Edge e69 = new Edge(v4, v25);
		Edge e70 = new Edge(v15, v22);
		Edge e71 = new Edge(v25, v22);
		Edge e72 = new Edge(v25, v7);
		
		// Hide some code in the Mesh class
		cube = new Mesh("Cube");
		// front face
		cube.addFace(new Face(e1, e2, e3,       new byte[]{0,0,0})); 
		cube.addFace(new Face(e4, e5, e2,    	 new byte[]{0,0,1}));
		cube.addFace(new Face(e5, e6, e7,    	 new byte[]{1,0,0}));
		cube.addFace(new Face(e8, e9, e6,  	  	 new byte[]{0,0,1}));
		cube.addFace(new Face(e10, e11, e12,    new byte[]{0,0,0}));
		cube.addFace(new Face(e3, e13, e11,     new byte[]{1,0,1}));
		cube.addFace(new Face(e13, e14, e15,    new byte[]{1,0,0}));
		cube.addFace(new Face(e7, e16, e14,     new byte[]{1,0,1}));
		// back face	
		cube.addFace(new Face(e17, e18, e19,    new byte[]{0,0,0}));
		cube.addFace(new Face(e20, e21, e18,   new byte[]{0,0,1}));
		cube.addFace(new Face(e21, e22, e23,   new byte[]{1,0,0}));
		cube.addFace(new Face(e24, e25, e22,   new byte[]{0,0,1}));
		cube.addFace(new Face(e26, e27, e28,   new byte[]{0,0,0}));
		cube.addFace(new Face(e19, e29, e27,   new byte[]{1,0,1}));
		cube.addFace(new Face(e29, e30, e31,   new byte[]{1,0,0}));
		cube.addFace(new Face(e23, e32, e30,   new byte[]{1,0,1}));
		// top face
		cube.addFace(new Face(e33, e34, e35,   new byte[]{0,0,0}));
		cube.addFace(new Face(e24, e36, e34,   new byte[]{1,0,1}));
		cube.addFace(new Face(e36, e37, e38,   new byte[]{1,0,0}));
		cube.addFace(new Face(e20, e39, e37,   new byte[]{1,0,1}));
		cube.addFace(new Face(e40, e41, e4,    new byte[]{0,0,1}));
		cube.addFace(new Face(e35, e42, e41,   new byte[]{1,0,1}));
		cube.addFace(new Face(e42, e43, e8,    new byte[]{1,0,1}));
		cube.addFace(new Face(e38, e44, e43,   new byte[]{1,0,1}));
		// bottom face
		cube.addFace(new Face(e45, e46, e47,   new byte[]{0,0,0}));
		cube.addFace(new Face(e12, e48, e46,   new byte[]{1,0,1}));
		cube.addFace(new Face(e48, e49, e50,   new byte[]{1,0,0}));
		cube.addFace(new Face(e15, e51, e49,   new byte[]{1,0,1}));
		cube.addFace(new Face(e52, e53, e31,   new byte[]{0,0,1}));
		cube.addFace(new Face(e47, e54, e53,   new byte[]{1,0,1}));
		cube.addFace(new Face(e54, e55, e28,   new byte[]{1,0,1}));
		cube.addFace(new Face(e50, e56, e55,   new byte[]{1,0,1}));
		// left face
		cube.addFace(new Face(e25, e65, e66,   new byte[]{1,0,0}));
		cube.addFace(new Face(e33, e67, e65,   new byte[]{1,0,1}));
		cube.addFace(new Face(e67, e68, e69,   new byte[]{1,0,0}));
		cube.addFace(new Face(e40, e1, e68,    new byte[]{1,1,1}));
		cube.addFace(new Face(e32, e70, e52,   new byte[]{1,0,1}));
		cube.addFace(new Face(e66, e71, e70,   new byte[]{1,0,1}));
		cube.addFace(new Face(e71, e72, e45,   new byte[]{1,0,1}));
		cube.addFace(new Face(e69, e10, e72,   new byte[]{1,1,1}));
		// right face
		cube.addFace(new Face(e9, e57, e58,   new byte[]{1,0,0}));
		cube.addFace(new Face(e44, e59, e57,  new byte[]{1,0,1}));
		cube.addFace(new Face(e59, e60, e61,   new byte[]{1,0,0}));
		cube.addFace(new Face(e60, e39, e17,   new byte[]{1,1,1}));
		cube.addFace(new Face(e16, e62, e51,   new byte[]{1,0,1}));
		cube.addFace(new Face(e58, e61, e62,   new byte[]{1,1,1}));
		cube.addFace(new Face(e63, e64, e56,   new byte[]{1,0,1}));
		cube.addFace(new Face(e61, e26, e64,   new byte[]{1,1,1}));
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {	}
	
	@Override
	public void dispose(GLAutoDrawable arg0) { }
}
