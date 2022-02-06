/* Author: Jack Song
 * 
 * This class is used to simulate the functions of a Pawn chess piece
 */

package pieces;

//imports
import javax.swing.ImageIcon;

import game.Board;

public class Pawn extends Piece {

	//Field
	private boolean madeFirstMove = false; //if the pawn has made its first move yet

	//Constructor
	public Pawn(int x, int y, boolean isWhite, boolean isCaptured) {

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
		int dX = destinationX - startX; // not absolute as direction matters for pawns
		int dY = Math.abs(destinationY - startY);

		//check if the pawn is white or black
		//if its white
		if (this.isWhite()) {

			//if the pawn already made its first move
			if (madeFirstMove) {

				//if pawn moved in a straight line and only 1 space, its a valid move
				//and there's no piece at the destination
				if (dX == 1 && dY == 0 && board.isCellEmpty(destinationX, destinationY))
					return true;

				//if the pawn moves diagonally 1 square, it is valid if there is an opposite colour piece.
				else if (dX == 1 && dY == 1 && !board.isCellEmpty(destinationX, destinationY)
						&& board.getPiece(destinationX, destinationY).isWhite() != this.isWhite())
					return true;

				//otherwise, return false
				else
					return false;

				//if the pawn has not made its first move yet
			} else {

				//if pawn moved in a straight line and 1 space, its a valid move
				//and there's no piece at the destination
				if (dX == 1 && dY == 0 && board.isCellEmpty(destinationX, destinationY))
					return true;

				//also a valid move if it moves in a straight line and 2 spaces since its the first move.
				//And there's no piece in between and there's no piece at destination
				else if (dX == 2 && dY == 0 && board.isCellEmpty(destinationX - 1, destinationY)
						&& board.isCellEmpty(destinationX, destinationY))
					return true;

				//if the pawn moves diagonally 1 square, it is valid if there is an opposite colour piece.
				else if (dX == 1 && dY == 1 && !board.isCellEmpty(destinationX, destinationY)
						&& board.getPiece(destinationX, destinationY).isWhite() != this.isWhite())
					return true;

				//otherwise, return false
				return false;
			}

			//if its black
			//only change is check if dX is -(value) since directions are different for white and black pawns
		} else {

			//if the pawn already made its first move
			if (madeFirstMove) {

				//if pawn moved in a straight line and only 1 space, its a valid move
				//and there's no piece at the destination
				if (dX == -1 && dY == 0 && board.isCellEmpty(destinationX, destinationY))
					return true;

				//if the pawn moves diagonally 1 square, it is valid if there is an opposite colour piece.
				else if (dX == -1 && dY == 1 && !board.isCellEmpty(destinationX, destinationY)
						&& board.getPiece(destinationX, destinationY).isWhite() != this.isWhite())
					return true;

				//otherwise, return false
				else
					return false;

				//if the pawn has not made its first move yet
			} else {

				//if pawn moved in a straight line and 1 space, its a valid move
				//and there's no piece at the destination
				if (dX == -1 && dY == 0 && board.isCellEmpty(destinationX, destinationY))
					return true;

				//also a valid move if it moves in a straight line and 2 spaces since its the first move.
				//And there's no piece in between and there's no piece at destination
				else if (dX == -2 && dY == 0 && board.isCellEmpty(destinationX + 1, destinationY)
						&& board.isCellEmpty(destinationX, destinationY))
					return true;

				//if the pawn moves diagonally 1 square, it is valid if there is an opposite colour piece.
				else if (dX == -1 && dY == 1 && !board.isCellEmpty(destinationX, destinationY)
						&& board.getPiece(destinationX, destinationY).isWhite() != this.isWhite())
					return true;

				//otherwise, return false
				return false;
			}
		}
	}

	//this method sets the image icon of the piece depending on the colour and type
	private void setImageIcon() {

		//if piece is white
		if (this.isWhite())
			this.setPieceImageIcon(new ImageIcon("images/WhitePawn.png"));

		//otherwise
		else
			this.setPieceImageIcon(new ImageIcon("images/BlackPawn.png"));

	}

	//this method checks if a pawn can promote
	public boolean canPromote(Board board) {

		//if the piece is white and has reached to the opposite end of the board
		if (this.isWhite() && this.getX() == board.getSize() - 1)
			return true;

		//if the piece is black and has reached to the opposite end of the board
		else if (!this.isWhite() && this.getX() == 0)
			return true;

		//otherwise the pawn cannot promote
		else
			return false;

	}

	//setter
	public void setMadeFirstMove(boolean madeFirstMove) {
		this.madeFirstMove = madeFirstMove;
	}
}
