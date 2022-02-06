/* Author: Jack Song
 * 
 * This class is a board model class that models the chess board and helps simulate chess.
 */

package game;

//imports
import java.util.Objects;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Board {
	
	//fields
	private Piece[][] chessBoard;
	private int size;
	
	//constructor
	public Board(int size) {
		this.size=size;
		chessBoard = new Piece[this.size][this.size];
	}

	//gets the piece at row x and column y.
	public Piece getPiece(int x, int y) {
		return chessBoard[x][y];
	}
	
	//set the piece at row x and column y
	public void setPiece(Piece piece, int x, int y) {
		chessBoard[x][y] = piece;
	}
	
	//get board size
	public int getSize() {
		return size;
	}

	//set board size
	public void setSize(int size) {
		this.size = size;
	}

	//utility methods
	
	//checks if the cell at row x and column y is empty
	public boolean isCellEmpty(int x, int y) {
		
		//prevents overflow and return false if its off the board
		if(x<0 || x>=this.size || y<0 || y>=this.size)
			return false;
		
		//if the square is null, that means its empty
		if(Objects.isNull(chessBoard[x][y]))
			return true;
		else
			return false;
	}
	
	//This method resets the chess board to its original position.
	public void reset8By8ChessBoard() {
		
		//initialize / reset white pieces
		
		//All pieces other than pawns
		chessBoard[0][0] = new Rook(0, 0, true, false);
		chessBoard[0][1] = new Knight(0, 1, true, false);
		chessBoard[0][2] = new Bishop(0, 2, true, false);
		chessBoard[0][3] = new Queen(0, 3, true, false);
		chessBoard[0][4] = new King(0, 4, true, false);
		chessBoard[0][5] = new Bishop(0, 5, true, false);
		chessBoard[0][6] = new Knight(0, 6, true, false);
		chessBoard[0][7] = new Rook(0, 7, true, false);
		
		//Pawns
		for(int column=0; column<=7; column++) {
			chessBoard[1][column] = new Pawn(1, column, true, false);
		}
		
		//---------------------------------------------------------------
		
		//initialize / reset black pieces
		
		//All pieces other than pawns
		chessBoard[7][0] = new Rook(7, 0, false, false);
		chessBoard[7][1] = new Knight(7, 1, false, false);
		chessBoard[7][2] = new Bishop(7, 2, false, false);
		chessBoard[7][3] = new Queen(7, 3, false, false);
		chessBoard[7][4] = new King(7, 4, false, false);	
		chessBoard[7][5] = new Bishop(7, 5, false, false);
		chessBoard[7][6] = new Knight(7, 6, false, false);
		chessBoard[7][7] = new Rook(7, 7, false, false);
		
		//Pawns
		for(int column=0; column<=7; column++) {
			chessBoard[6][column] = new Pawn(6, column, false, false);
		}
	}
	
	//this method clears the board with no pieces
	public void clearBoard() {
		
		//set each cell to null
		for(int row=0; row<this.size; row++) {
			for(int column=0; column<this.size; column++) {
				chessBoard[row][column]=null;
			}
		}
	}
	
}
