package datatypes;

/**
 * Simple class to store an RBG colour value
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */
public class Colour
{
	public byte red;
	public byte green;
	public byte  blue;
	
	/**
	 * Constructor
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 */
	public Colour(byte r, byte g, byte b)
	{
		this.red = r;
		this.green = g;
		this.blue = b;
	}
}
