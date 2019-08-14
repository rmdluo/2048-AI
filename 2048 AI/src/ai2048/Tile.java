package ai2048;

import java.awt.Graphics;

public class Tile extends Polygon {
	private int value;

	public Tile(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		value = 0;
	}

	public Tile(Point[] inShape, Point inPosition, double inRotation, int value) {
		super(inShape, inPosition, inRotation);
		if (value < 0)
			this.value = 0;
		else
			this.value = value;
	}

	/**
	 * converts to string
	 * <p>This method converts the tile to a string.</p>
	 * @author Raymond Luo
	 * @return the string representation
	 */
	public String toString() {
		if (value == 0)
			return "_";
		else
			return Integer.toString(value);
	}

	/**
	 * draws tile
	 * <p>This method draws the tile.</p>
	 * @author Raymond Luo
	 * @param brush the brush to draw with
	 */
	public void paint(Graphics brush) {
		// get x coordinates and y coordinates from the points
		Point[] points = getPoints();
		int[] xCoords = new int[points.length];
		int[] yCoords = new int[points.length];

		for (int index = 0; index < points.length; index++) {
			xCoords[index] = (int) points[index].getX();
			yCoords[index] = (int) points[index].getY();
		}

		// draw the tile
		if(value != 0)
			brush.drawString(Integer.toString(value), (int) position.getX(), (int) position.getY());
		else
			brush.drawString("", (int) position.getX(), (int) position.getY());
	}
	
	//gets value
	public int getValue() {
		return value;
	}
	
	//sets the position
	public void setPosition(Point position) {
		this.position = position.clone();
	}
}
