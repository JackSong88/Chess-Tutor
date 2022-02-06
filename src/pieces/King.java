/* Author: Jack Song
 * 
 * This class is used to simulate the functions of a King chess piece
 */

package pieces;

//imports
import javax.swing.ImageIcon;

import game.Board;
import model.Move;

public class King extends Piece {

	//field
	private boolean moved = false; //if the king has moved yet

	//Constructor
	public King(int x, int y, boolean isWhite, boolean isCaptured) {

		//call super piece constructor
		super(x, y, isWhite, isCaptured);

		//set piece image icon
		setImageIcon();
	}

	//getter and setter
	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
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

		//make sure the destination isn't surrounded by the enemy king
		if (isDestinationSurroundedByKing(board, destinationX, destinationY))
			return false;

		//if move results in check, then it's not a valid move
		if (this.moveResultsInCheck(board, startX, startY, destinationX, destinationY))
			return false;

		//Calculate the change in x and y
		int dX = Math.abs(destinationX - startX);
		int dY = destinationY - startY; //no absolute value, direction needed to test for castling

		//If dX and dY is less than or equal to 1, that means the king made a legal move by moving only 1 Cell in any direction
		//and destination is does not result in check
		if (dX <= 1 && Math.abs(dY) <= 1)
			return true;

		//if the king moved 2 squares left and right, check if the king is able to castle
		//if so, then it's a valid move
		else if (dX == 0 && Math.abs(dY) == 2 && isCastlingValid(board, dY))
			return true;

		//otherwise it's an invalid move
		else
			return false;
	}

	//this method checks if there the enemy king is adjacent to the destination
	private boolean isDestinationSurroundedByKing(Board board, int x, int y) {

		//direction array to hold all the directions a king can go
		int dir[][] = { { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 } };

		//check each surrounding cell for kings
		for (int direction = 0; direction < dir.length; direction++) {

			//temp variables to store new coordinates
			int newX = x + dir[direction][0];
			int newY = y + dir[direction][1];

			//if its within bounds of the board and there is a enemy king, return true
			if (newX >= 0 && newX < board.getSize() && newY >= 0 && newY < board.getSize()
					&& !board.isCellEmpty(newX, newY) && board.getPiece(newX, newY) instanceof King
					&& this.isWhite() != board.getPiece(newX, newY).isWhite())
				return true;
		}

		//if no king was found, return false
		return false;
	}

	//this method checks to see if the king can castle
	private boolean isCastlingValid(Board board, int dY) {
		
		//if king is already in check, it can't castle
		if(this.isInCheck(board,this.getX(),this.getY()))
			return false;
		
		//if king moved 2 squares right
		if (dY == 2) {

			//if there's a piece blocking
			if(!board.isCellEmpty(this.getX(), this.getY()+1))
				return false;
			
			//if squares are in checks between
			if (destinationResultsInCheck(board, this.getX(), this.getY(), this.getX(), this.getY() + 1))
				return false;

			//if the king is white, hasn't moved yet, if the bottom right corner is a rook and that rook hasn't moved, 
			else if (this.isWhite() && !this.moved && !board.isCellEmpty(0, board.getSize() - 1)
					&& board.getPiece(0, board.getSize() - 1) instanceof Rook
					&& !((Rook) board.getPiece(0, board.getSize() - 1)).isMoved())
				return true;

			//if the king is black, hasn't moved yet and if the top right corner is a rook and that rook hasn't moved, 
			else if (!this.isWhite() && !this.moved && !board.isCellEmpty(board.getSize() - 1, board.getSize() - 1)
					&& board.getPiece(board.getSize() - 1, board.getSize() - 1) instanceof Rook
					&& !((Rook) board.getPiece(board.getSize() - 1, board.getSize() - 1)).isMoved())
				return true;

		} else if (dY == -2) { //if king moved 2 squares right

			//if there's a piece blocking
			if(!board.isCellEmpty(this.getX(), this.getY()+1))
				return false;
			
			//if squares are in checks between
			if (destinationResultsInCheck(board, this.getX(), this.getY(), this.getX(), this.getY() - 1))
				return false;

			//if the king is white, hasn't moved yet, if the bottom left corner is a rook and that rook hasn't moved, 
			else if (!this.moved && !board.isCellEmpty(0, 0) && board.getPiece(0, 0) instanceof Rook
					&& !((Rook) board.getPiece(0, 0)).isMoved())
				return true;

			//if the king is black, hasn't moved yet and if the top left corner is a rook and that rook hasn't moved, 
			else if (!this.isWhite() && !this.moved && !board.isCellEmpty(board.getSize() - 1, 0)
					&& board.getPiece(board.getSize() - 1, 0) instanceof Rook
					&& !((Rook) board.getPiece(board.getSize() - 1, 0)).isMoved())
				return true;
		}

		//if no conditions are met, return false
		return false;

	}

	//this method checks if the king or a position is/will be in check from any of the enemy pieces.
	//x and y are king's position
	public boolean isInCheck(Board board, int x, int y) {

		//loop through the board
		for (int row = 0; row < board.getSize(); row++) {
			for (int column = 0; column < board.getSize(); column++) {

				//temp variables
				Piece kingPiece = board.getPiece(x, y);
				Piece tempReplacementPiece = new Rook(x, y, kingPiece.isWhite(), false);

				//temporarily replace the king as a rook.
				//if it was still a king, isValidMove will automatically be false
				//since the a piece can't take a king
				//so it's temporarily replaced to allow the check to occur
				board.setPiece(tempReplacementPiece, x, y);

				//if an enemy piece is able to capture the king, that means its in check.
				if (!board.isCellEmpty(row, column) && board.getPiece(row, column).isWhite() != this.isWhite()
						&& board.getPiece(row, column).isValidMove(board, row, column, x, y)) {

					//set the piece back to the king
					board.setPiece(kingPiece, x, y);

					//return true
					return true;
				}

				//set the piece back to the king no matter what.
				board.setPiece(kingPiece, x, y);
			}
		}

		//if no pieces are checking the king, return false
		return false;
	}

	//this method checks if the king is checkmated
	public boolean isCheckmated(Board board, int x, int y) {

		//generate all possible moves for the king
		this.generatePossibleMoves(board);

		//if the king has moves, its not checkmated
		if (this.getPossibleMoves().size() > 0)
			return false;

		//if the king has no moves left
		else if (this.getPossibleMoves().size() == 0) {

			//check to see if other pieces are able to block

			//loop through the board
			for (int row = 0; row < board.getSize(); row++) {
				for (int column = 0; column < board.getSize(); column++) {

					//if a piece has the same colour as the king, check if it's able to block 
					if (!board.isCellEmpty(row, column) && board.getPiece(row, column).isWhite() == this.isWhite()
							&& !(board.getPiece(row, column) instanceof King)) {

						//temp piece variable to store the piece located at (row,column)
						Piece currentPiece = board.getPiece(row, column);

						//generate possible moves for that piece
						currentPiece.generatePossibleMoves(board);

						//loop through the possible moves
						for (Move currentPossibleCell : currentPiece.getPossibleMoves()) {

							//temp variable to store the possible move destination piece
							Piece possibleCellPiece;

							//if destination is not empty, store the destination piece
							if (!board.isCellEmpty(currentPossibleCell.getX(), currentPossibleCell.getY()))
								possibleCellPiece = board.getPiece(currentPossibleCell.getX(),
										currentPossibleCell.getY());

							//otherwise, its null
							else
								possibleCellPiece = null;

							//move the piece to test if it can block
							board.setPiece(currentPiece, currentPossibleCell.getX(), currentPossibleCell.getY());
							board.setPiece(null, row, column);

							/*
							boolean moveChecksOppositeColouredKing = false;
							
							//loop through the board again to find opposite coloured king
							for (int row2 = 0; row2 < board.getSize(); row2++) {
								for (int column2 = 0; column2 < board.getSize(); column2++) {
							
									//find the opposite coloured king
									if (!board.isCellEmpty(row2, column2)
											&& board.getPiece(row2, column2).isWhite() != this.isWhite()
											&& board.getPiece(row2, column2) instanceof King) {
							
										//if the move results in check, set moveChecksOppositeColouredKing to true
										if (((King) board.getPiece(row2, column2)).isInCheck(board, x, y))
											moveChecksOppositeColouredKing = true;
										
										//no need to continue looping
										break;
									}
								}
							}
							
							//prevents making an illegal move that will check the opposite coloured king when already checked,
							if (moveChecksOppositeColouredKing)
								continue;
								*/

							//if the king is not in check anymore, that means a piece can block
							if (!this.isInCheck(board, x, y)) {

								//reset piece move
								board.setPiece(possibleCellPiece, currentPossibleCell.getX(),
										currentPossibleCell.getY());
								board.setPiece(currentPiece, row, column);

								//return false;
								return false;
							}

							//reset piece move
							board.setPiece(possibleCellPiece, currentPossibleCell.getX(), currentPossibleCell.getY());
							board.setPiece(currentPiece, row, column);
						}

					}
				}
			}
		}

		//if no pieces can block or the king cannot move out the way, return true
		return true;
	}

	//this method checks if the move will result in the king being checked
	private boolean destinationResultsInCheck(Board board, int startX, int startY, int destinationX, int destinationY) {

		//set temp piece variables for replacing
		Piece destinationPiece = board.getPiece(destinationX, destinationY);
		Piece currentKing = board.getPiece(startX, startY);

		//temp set pieces
		board.setPiece(currentKing, destinationX, destinationY);
		board.setPiece(null, startX, startY);

		//check to see if king would be in check
		if (((King) board.getPiece(destinationX, destinationY)).isInCheck(board, destinationX, destinationY)) {

			//reset pieces
			board.setPiece(destinationPiece, destinationX, destinationY);
			board.setPiece(currentKing, startX, startY);

			return true;

		} else {

			//reset pieces
			board.setPiece(destinationPiece, destinationX, destinationY);
			board.setPiece(currentKing, startX, startY);

			return false;
		}
	}

	//this method sets the image icon of the piece depending on the colour and type
	private void setImageIcon() {

		//if piece is white
		if (this.isWhite())
			this.setPieceImageIcon(new ImageIcon("images/WhiteKing.png"));

		//otherwise
		else
			this.setPieceImageIcon(new ImageIcon("images/BlackKing.png"));

	}
}
