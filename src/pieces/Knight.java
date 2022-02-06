/* Author: Jack Song
 * 
 * This class is used to simulate the functions of a Knight chess piece
 */

package pieces;

//imports
import javax.swing.ImageIcon;

import game.Board;

public class Knight extends Piece {

	//Constructor
	public Knight(int x, int y, boolean isWhite, boolean isCaptured) {

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

		//Calculate the change in x and y
		int dX = Math.abs(destinationX - startX);
		int dY = Math.abs(destinationY - startY);

		//If they multiply to 2, that means the knight moved in L shape which is legal.
		if (dX * dY == 2)
			return true;

		//otherwise it's an invalid move
		else
			return false;
	}

	//this method sets the image icon of the piece depending on the colour and type
	private void setImageIcon() {

		//if piece is white
		if (this.isWhite())
			this.setPieceImageIcon(new ImageIcon("images/WhiteKnight.png"));

		//otherwise
		else
			this.setPieceImageIcon(new ImageIcon("images/BlackKnight.png"));

	}
}
