/* Author: Jack Song
 * 
 * This class is used to simulate the functions of a bishop chess piece
 */

package pieces;

//imports
import javax.swing.ImageIcon;
import game.Board;

public class Bishop extends Piece {

	//Constructor
	public Bishop(int x, int y, boolean isWhite, boolean isCaptured) {

		//call super piece constructor
		super(x, y, isWhite, isCaptured);

		//set piece image icon
		setImageIcon();
	}

	//This method checks if a move is valid and not illegal.
	@Override
	public boolean isValidMove(Board board, int startX, int startY, int destinationX, int destinationY) {

		//make sure if the destination cell is not empty, ensure the piece doesn't capture a piece of its own colour 
		if (!board.isCellEmpty(destinationX, destinationY)
				&& board.getPiece(destinationX, destinationY).isWhite() == this.isWhite())
			return false;

		//make sure if the destination cell is not empty, ensure the piece doesn't capture enemy king
		if (!board.isCellEmpty(destinationX, destinationY)
				&& board.getPiece(destinationX, destinationY) instanceof King)
			return false;

		//if move results the king being in check, then it's not a valid move
		if (this.moveResultsInCheck(board, startX, startY, destinationX, destinationY))
			return false;

		//if bishop's path is blocked, return false as the move is invalid.
		if (isBishopPathBlocked(board, startX, startY, destinationX, destinationY))
			return false;

		//Calculate the change in x and y
		int dX = Math.abs(destinationX - startX);
		int dY = Math.abs(destinationY - startY);

		//If they are equal, that means the bishop has moved in a diagonal which is legal-- return true
		if (dX == dY)
			return true;

		//otherwise return false as the move is not possible
		else
			return false;
	}

	//checks if anything is blocked in the bishop's path
	private boolean isBishopPathBlocked(Board board, int startX, int startY, int destinationX, int destinationY) {

		//determine which direction the bishop goes
		int dirX = 0;
		int dirY = 0;

		//if the X value of the destination is greater than beginning, change in X is positive
		if (destinationX > startX)
			dirX = 1;

		//if the X value of the destination is smaller than beginning, change in X is negative
		else if (destinationX < startX)
			dirX = -1;

		//if the Y value of the destination is greater than beginning, change in Y is positive
		if (destinationY > startY)
			dirY = 1;

		//if the Y value of the destination is smaller than beginning, change in Y is negative
		else if (destinationY < startY)
			dirY = -1;

		//go through every cell in the bishop's path and see if there are any obstacles
		for (int step = 1; step < Math.abs(destinationX - startX); step++) {

			//if there is an obstacle, return true as the path is blocked
			if (!board.isCellEmpty(startX + dirX * step, startY + dirY * step))
				return true;
		}

		//if no obstacles are found, return false as the path is not blocked
		return false;
	}

	//this method sets the image icon of the piece depending on the colour and type
	private void setImageIcon() {

		//if piece is white
		if (this.isWhite())
			this.setPieceImageIcon(new ImageIcon("images/WhiteBishop.png"));

		//otherwise
		else
			this.setPieceImageIcon(new ImageIcon("images/BlackBishop.png"));

	}

}
