/*
 * Author: Jack Song
 * 
 * Lesson model class for puzzle title/type and  for each lesson.
 */

package model;

//imports
import java.util.ArrayList;
import pieces.Piece;

public class Puzzle {

	//Fields
	private String puzzleType;
	private ArrayList<Piece> pieceList;

	//constructor
	public Puzzle(String puzzleType, ArrayList<Piece> pieceList) {
		this.puzzleType = puzzleType;
		this.pieceList = pieceList;
	}

	//overloaded constructor with no parameters
	public Puzzle() {

		this.puzzleType = "No Puzzle Type";
		this.pieceList = new ArrayList<Piece>();
	}

	//getters and setters
	public String getPuzzleType() {
		return puzzleType;
	}

	public void setPuzzleType(String puzzleType) {
		this.puzzleType = puzzleType;
	}

	public ArrayList<Piece> getPieceList() {
		return pieceList;
	}

	public void setPieceList(ArrayList<Piece> pieceList) {
		this.pieceList = pieceList;
	}

	//toString method
	@Override
	public String toString() {
		return "Puzzle [puzzleType=" + puzzleType + ", pieceList=" + pieceList + "]";
	}
	
	
	
}
