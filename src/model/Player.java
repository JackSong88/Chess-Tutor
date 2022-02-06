/* Author: Jack Song
 * 
 * Player model class that models a chess player, the colour the player is playing and if its a human player
 */

package model;

public class Player {
	
	//fields
	private boolean isHuman;
	private boolean isWhite;
	
	//constructor
	public Player(boolean isHuman, boolean isWhite) {
		this.isHuman = isHuman;
		this.isWhite = isWhite;
	}

	//getters and setters
	public boolean isHuman() {
		return isHuman;
	}

	public void setHuman(boolean isHuman) {
		this.isHuman = isHuman;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	//toString
	@Override
	public String toString() {
		return "Player [isHuman=" + isHuman + ", isWhite=" + isWhite + "]";
	}
	
	
	
}
