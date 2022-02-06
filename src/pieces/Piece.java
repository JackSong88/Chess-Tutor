/* Author: Jack Song
 * 
 * This class is an abstract class that models a chess piece, it contains two utility methods, 
 * one to generate all possible moves for a piece on a certain board and one to check if a piece's move will 
 * result in the check of its king. There's also a abstract method to check if a move is valid for a certain piece.
 */

package pieces;

//imports
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.ImageIcon;

import game.Board;
import model.Move;

public abstract class Piece {

	//Fields
	private int x;
	private int y;
	private boolean isWhite;
	private boolean isCaptured;
	private ArrayList<Move> possibleMoves;
	private ImageIcon pieceImageIcon;

	//Constructor
	public Piece(int x, int y, boolean isWhite, boolean isCaptured) {
		this.x = x;
		this.y = y;
		this.isWhite = isWhite;
		this.isCaptured = isCaptured;
		possibleMoves = new ArrayList<Move>();
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

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	public boolean isCaptured() {
		return isCaptured;
	}

	public void setCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}

	public ImageIcon getPieceImageIcon() {
		return pieceImageIcon;
	}

	public void setPieceImageIcon(ImageIcon pieceImageIcon) {
		this.pieceImageIcon = pieceImageIcon;
	}

	public ArrayList<Move> getPossibleMoves() {
		return possibleMoves;
	}

	public void setPossibleMoves(ArrayList<Move> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	//utility methods

	//this method generates all the possible moves for a piece
	public void generatePossibleMoves(Board board) {

		//clear the array list
		this.getPossibleMoves().clear();

		//loop through all the Cells on the board
		for (int row = 0; row < board.getSize(); row++) {
			for (int column = 0; column < board.getSize(); column++) {

				//check if it can be a valid move
				if (this.isValidMove(board, x, y, row, column))

					//add to possible moves list if it can
					this.getPossibleMoves().add(new Move(row, column));
			}
		}
	}

	//this method checks if the move will result in the king being checked
	public boolean moveResultsInCheck(Board board, int startX, int startY, int destinationX, int destinationY) {

		//set temp piece variables for replacing
		Piece destinationPiece = board.getPiece(destinationX, destinationY);
		Piece currentPiece = board.getPiece(startX, startY);
		Piece oppositeColouredKing = null;

		//also set opposite coloured king as a temp piece
		//prevents conflict with isInCheck algorithm
		for (int row = 0; row < board.getSize(); row++) {
			for (int column = 0; column < board.getSize(); column++) {
				
				//get opposite coloured king
				if (!board.isCellEmpty(row, column) && currentPiece.isWhite != board.getPiece(row, column).isWhite()
						&& board.getPiece(row, column) instanceof King)
					oppositeColouredKing = board.getPiece(row, column);
			}
		}

		//temp set pieces
		board.setPiece(currentPiece, destinationX, destinationY);
		board.setPiece(null, startX, startY);
		
		//prevents nullpointerexception
		if(!Objects.isNull(oppositeColouredKing))
			board.setPiece(null, oppositeColouredKing.getX(), oppositeColouredKing.getY());

		//check to see if king would be in check
		for (int row = 0; row < board.getSize(); row++) {
			for (int column = 0; column < board.getSize(); column++) {

				//if the current the square at (row,column) is the king of the moved piece and the move results the king being in check.
				//return true
				if (!board.isCellEmpty(row, column) && currentPiece.isWhite == board.getPiece(row, column).isWhite()
						&& board.getPiece(row, column) instanceof King
						&& ((King) board.getPiece(row, column)).isInCheck(board, row, column)) {

					//reset pieces
					board.setPiece(destinationPiece, destinationX, destinationY);
					board.setPiece(currentPiece, startX, startY);
					
					//prevents nullpointerexception
					if(!Objects.isNull(oppositeColouredKing))
						board.setPiece(oppositeColouredKing, oppositeColouredKing.getX(), oppositeColouredKing.getY());

					return true;
				}
			}
		}

		//reset pieces
		board.setPiece(destinationPiece, destinationX, destinationY);
		board.setPiece(currentPiece, startX, startY);
		
		//prevents nullpointerexception
		if(!Objects.isNull(oppositeColouredKing))
			board.setPiece(oppositeColouredKing, oppositeColouredKing.getX(), oppositeColouredKing.getY());

		//return false as the king with the same colour as the current piece is not in check
		return false;

	}

	//abstract methods
	//this method checks if a move is valid
	public abstract boolean isValidMove(Board board, int startX, int startY, int destinationX, int destinationY);

}
