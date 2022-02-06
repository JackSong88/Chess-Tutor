/* Author: Jack Song
 * 
 * This class is a game model class that models and helps simulate a standard chess game.
 */

package game;

//imports
import model.Player;
import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Rook;

public class Game {
	
	//Fields
	private Board chessBoard;
	private Player currentPlayer;
	private Player player1;
	private Player player2;
	private boolean gameEnded;
	
	//constructor
	public Game(Board chessBoard, Player player1, Player player2) {
		
		//set fields
		this.chessBoard = chessBoard;
		this.player1 = player1;
		this.player2 = player2;
		
		//current current player to the player who goes first
		if(player1.isWhite())
			this.currentPlayer = player1;
		else
			this.currentPlayer = player2;
		
		//game has not ended
		gameEnded=false;
	}
	
	//getters
	public Board getChessBoard() {
		return chessBoard;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	
	public boolean isGameEnded() {
		return gameEnded;
	}

	//setter
	public void setGameEnded(boolean gameEnded) {
		this.gameEnded = gameEnded;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	//this method makes a chess move.
	public void makeMove(Player player, int startX, int startY, int endX, int endY) {
		
		//get the piece that's moving
		Piece movePiece = chessBoard.getPiece(startX, startY);
		
		//check if it's a valid move
		if(movePiece.isValidMove(chessBoard, startX, startY, endX, endY)) {
			
			//move the pieces if its a valid move
			chessBoard.setPiece(movePiece, endX, endY);
			chessBoard.setPiece(null, startX, startY);
			
			//update the piece x and y value
			movePiece.setX(endX);
			movePiece.setY(endY);
			
			//check to see if the piece is a pawn
			if(movePiece instanceof Pawn) {
				
				//set the first move to true if it has moved
				((Pawn) movePiece).setMadeFirstMove(true);
				
			} else if(movePiece instanceof King) {			//or, if the piece is a King
				
				//variable to make code more organized
				King moveKing = ((King) movePiece);
				
				//if the king has not moved check to see if it castled and update the rook position
				if(!moveKing.isMoved()) {
					
					//change in Y
					int dY= endY-startY;
					
					//if change in Y is 2
					if(dY==2) {
						
						//if the king is white
						if(moveKing.isWhite()) {
							
							//get the castled rook
							Rook castledRook = ((Rook) chessBoard.getPiece(0, chessBoard.getSize()-1));
							
							//move the rook depending on what type of castle it is
							chessBoard.setPiece(castledRook, moveKing.getX(), moveKing.getY()-1);
							chessBoard.setPiece(null, 0, chessBoard.getSize()-1);
							
							//update x and y values
							castledRook.setX(moveKing.getX());
							castledRook.setY(moveKing.getY()-1);
							
						} else {			//otherwise its black
							
							//get the castled rook
							Rook castledRook = ((Rook) chessBoard.getPiece(chessBoard.getSize()-1, chessBoard.getSize()-1));
							
							//move the rook depending on what type of castle it is
							chessBoard.setPiece(castledRook, moveKing.getX(), moveKing.getY()-1);
							chessBoard.setPiece(null, chessBoard.getSize()-1, chessBoard.getSize()-1);
							
							//update x and y values
							castledRook.setX(moveKing.getX());
							castledRook.setY(moveKing.getY()+1);
						}
						
					} else if(dY==-2)  {  //if change in Y is -2
						
						//if the king is white
						if(moveKing.isWhite()) {
							
							//get the castled rook
							Rook castledRook = ((Rook) chessBoard.getPiece(0, 0));
							
							//move the rook depending on what type of castle it is
							chessBoard.setPiece(castledRook, moveKing.getX(), moveKing.getY()+1);
							chessBoard.setPiece(null, 0, 0);
							
							//update x and y values
							castledRook.setX(moveKing.getX());
							castledRook.setY(moveKing.getY()-1);
							
						} else {			//otherwise its black
							
							//get the castled rook
							Rook castledRook = ((Rook) chessBoard.getPiece(chessBoard.getSize()-1, 0));
							
							//move the rook depending on what type of castle it is
							chessBoard.setPiece(castledRook, moveKing.getX(), moveKing.getY()+1);
							chessBoard.setPiece(null, chessBoard.getSize()-1, 0);
							
							//update x and y values
							castledRook.setX(moveKing.getX());
							castledRook.setY(moveKing.getY()+1);
						}
					}
				}
				
				//set the king as moved.
				((King) movePiece).setMoved(true);
				
			} else if(movePiece instanceof Rook) {			//or, if the piece is a Rook
				
				//set the rook as moved.
				((Rook) movePiece).setMoved(true);
			}
			
			
			//change player turn to other player
			if(currentPlayer.equals(player1)) 
				currentPlayer=player2;
			else 
				currentPlayer=player1;
			
			
		}
	}
}
