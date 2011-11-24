// Java imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class Main implements GLEventListener, KeyListener
{
	// Some basic window/display options
	private static final String WINDOW_TITLE = "Butterfly Subdivision";
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	private static final int FRAMERATE = 30;
	
	private static final GLU GLU = new GLU();
	
	private static float rotation = 0.0f;
	private static float rotationSpeed = 1.0f;
	private static final float ROTATION_INCREMENT = 0.25f;
	
	private static Boolean rotate = true;	
	private static Boolean showSubdividedMesh = false;	
	
	private static JFrame frame;
	
	// the cube object
	private static Mesh cube;
	// the subdivided cube object
	private static Mesh subdividedCube;

	public static void main(String[] args)
	{
		frame = new JFrame(WINDOW_TITLE);
		GLCanvas canvas = new GLCanvas();
		canvas.addGLEventListener(new Main());
		frame.add(canvas);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		
		FPSAnimator animator = new FPSAnimator(canvas, FRAMERATE);
		animator.add(canvas);
		animator.start();
		
		setTriangleDataSmall();
//		setTriangleDataBig();
//		setTriangleDataPyramid();

		// initially calculate the winged edges
		cube.calculateWingingFaces();
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
		
		// decide which mesh to draw
		if(!showSubdividedMesh) cube.draw(gl);
		else if(subdividedCube != null) subdividedCube.draw(gl);
		
		if(rotate) rotation += rotationSpeed;
	}
	
	@Override
	public void init(GLAutoDrawable glDrawable)
	{
		final GL2 gl = (GL2)glDrawable.getGL();

		gl.glViewport (0, 0, WIDTH, HEIGHT);
		gl.glMatrixMode(GL2.GL_PROJECTION); 
		gl.glLoadIdentity();
		GLU.gluPerspective(45.0f, (float)(WIDTH)/(float)(HEIGHT), 1.0f, 100.0f); 
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		
		frame.addKeyListener(this);
	}
	
	/** 
	 * Handle key released events 
	 */
	public void keyReleased(KeyEvent e) 
	{		
		switch(e.getKeyChar())
		{
			case 's':
				subdividedCube = subdividedCube.subdivide();
				showSubdividedMesh = true;
				break;
			case 'a':
				showSubdividedMesh = !showSubdividedMesh;
				break;
			case 'r':
				rotate = !rotate;
				break;
			case 'q':
				subdividedCube = cube;
			case '-':
				if(rotationSpeed > 0.20) rotationSpeed -= ROTATION_INCREMENT;
				break;
			case '=':
				if(rotationSpeed < 5.00) rotationSpeed += ROTATION_INCREMENT;
			default:
				//System.out.println("'" + e.getKeyChar() + "' not mapped");
				break;
		}
	}
	public void keyTyped(KeyEvent e) { }
	public void keyPressed(KeyEvent e) { }
	
	/**
	 * Default_method_description
	 */
	private static void setTriangleDataSmall()
	{
		Vertex v1 = new Vertex(0.0f, 0.0f, 0.0f);
		Vertex v2 = new Vertex(0.0f, 1.0f, 0.0f);
		Vertex v3 = new Vertex(1.0f, 1.0f, 0.0f);
		Vertex v4 = new Vertex(1.0f, 0.0f, 0.0f);
		Vertex v5 = new Vertex(1.0f, 0.0f, -1.0f);
		Vertex v6 = new Vertex(1.0f, 1.0f, -1.0f);
		Vertex v7 = new Vertex(0.0f, 1.0f, -1.0f);		
		Vertex v8 = new Vertex(0.0f, 0.0f, -1.0f);
		
		Edge e1 = new Edge(v1, v2, "e1");
		Edge e2 = new Edge(v2, v3, "e2");
		Edge e3 = new Edge(v3, v1, "e3");
		Edge e4 = new Edge(v3, v4, "e4");
		Edge e5 = new Edge(v4, v1, "e5");
		Edge e6 = new Edge(v5, v6, "e6");
		Edge e7 = new Edge(v6, v7, "e7");
		Edge e8 = new Edge(v7, v5, "e8");
		Edge e9 = new Edge(v7, v8, "e9");
		Edge e10 = new Edge(v8, v5, "e10");
		Edge e11 = new Edge(v2, v7, "e11");
		Edge e12 = new Edge(v6, v2, "e12");		
		Edge e13 = new Edge(v6, v3, "e13");	
		Edge e14 = new Edge(v8, v1, "e14");
		Edge e15 = new Edge(v4, v8, "e15");
		Edge e16 = new Edge(v4, v5, "e16");
		Edge e17 = new Edge(v2, v8, "e17");
		Edge e18 = new Edge(v6, v4, "e18");
					
		// Hide some code in the Mesh class
		cube = subdividedCube = new Mesh("Cube");
		// front face
		cube.addFace(new Face(e1, e2, e3,    new byte[]{0,0,0},"fr1")); 
		cube.addFace(new Face(e3, e4, e5,    new byte[]{1,0,0},"fr2"));
		// back face	
		cube.addFace(new Face(e6, e7, e8,    new byte[]{0,0,0},"ba1"));
		cube.addFace(new Face(e8, e9, e10,   new byte[]{1,0,0},"ba2"));
		// top face
		cube.addFace(new Face(e11, e7, e12,  new byte[]{0,1,0},"to1"));
		cube.addFace(new Face(e12, e13, e2,  new byte[]{1,0,1},"to2"));
		// bottom face
		cube.addFace(new Face(e14, e5, e15,  new byte[]{0,1,0},"bo1"));
		cube.addFace(new Face(e15, e16, e10, new byte[]{1,0,1},"bo2"));
		// left face
		cube.addFace(new Face(e9, e11, e17,  new byte[]{1,1,0},"le1"));
		cube.addFace(new Face(e17, e1, e14,  new byte[]{1,1,1},"le2"));
		// right face
		cube.addFace(new Face(e4, e13, e18,  new byte[]{1,1,0},"ri1"));
		cube.addFace(new Face(e18, e6, e16,  new byte[]{1,1,1},"ri2"));
	}
	
	private static void setTriangleDataPyramid()
	{
		Vertex v1 = new Vertex(-1.0f, 0.0f, 0.0f);
		Vertex v2 = new Vertex(1.0f, 0.0f, 1.0f);
		Vertex v3 = new Vertex(1.0f, 0.0f, -1.0f);
		Vertex v4 = new Vertex(0.0f, 2.0f, 0.0f);
		
		Edge e1 = new Edge(v1, v4);
		Edge e2 = new Edge(v2, v4);
		Edge e3 = new Edge(v3, v4);
		Edge e4 = new Edge(v1, v2);
		Edge e5 = new Edge(v2, v3);
		Edge e6 = new Edge(v3, v1);
					
		// Hide some code in the Mesh class
		cube = subdividedCube = new Mesh("Pyramid");
		cube.addFace(new Face(e1, e2, e4, new byte[]{0,1,1},"f1")); 
		cube.addFace(new Face(e2, e3, e5, new byte[]{0,1,1},"f1"));
		cube.addFace(new Face(e3, e1, e6, new byte[]{0,1,1},"f3"));
		cube.addFace(new Face(e4, e5, e6, new byte[]{0,0,0},"base"));
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {	}
	
	@Override
	public void dispose(GLAutoDrawable arg0) { }
}
