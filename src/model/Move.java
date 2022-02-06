/* Author: Jack Song
 * 
 * Model class to store the coordinates of a possible move, x and y
 */

package model;

public class Move {

	//fields
	private int x;
	private int y;

	//constructor
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//getters and setters
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	//toString
	@Override
	public String toString() {
		return "Move [x=" + x + ", y=" + y + "]";
	}
	
	
}
