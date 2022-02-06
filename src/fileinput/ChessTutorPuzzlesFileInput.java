/* Author: Jack Song
 * 
 * This class is a file input class for the puzzles content. It reads the data from a local csv file
 */

package fileinput;

//imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Puzzle;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ChessTutorPuzzlesFileInput {

	//Fields
	private Puzzle[] puzzles;
	private final int PUZZLES_COUNT = 5;

	//constructor
	public ChessTutorPuzzlesFileInput() {

		//initialize puzzles array
		puzzles = new Puzzle[PUZZLES_COUNT];

		try {

			//Scanner to read in the csv file
			Scanner input = new Scanner(new File("files/chessTutorPuzzleContent.csv"));
			input.useDelimiter(",|\\r\\n"); //uses delimiter to make file input easier and to prevent any errors

			//remove the header from the input
			input.nextLine();

			//index variable to keep track of puzzle number
			int puzzlesIndex = 0;

			//continue the loop until there's no more information to read
			while (input.hasNextLine()) {

				//initialize each puzzle object in the array
				puzzles[puzzlesIndex] = new Puzzle();

				//read in information for each puzzle
				puzzles[puzzlesIndex].setPuzzleType(input.next());

				String piecesInfo[] = input.next().split("\\.");

				//pieces info index 
				int piecesInfoIndex = 0;

				//continue to loop until all pieces info are read
				do {

					//store information in temp variables
					String pieceType = piecesInfo[piecesInfoIndex++];
					int pieceX = Integer.parseInt(piecesInfo[piecesInfoIndex++]);
					int pieceY = Integer.parseInt(piecesInfo[piecesInfoIndex++]);
					boolean isPieceWhite = Boolean.parseBoolean(piecesInfo[piecesInfoIndex++]);

					//check which piece it is then initialize that piece type with the read information
					switch (pieceType) {

					//Pawn case
					case "Pawn":
						puzzles[puzzlesIndex].getPieceList().add(new Pawn(pieceX, pieceY, isPieceWhite, false));
						break;

					//Knight case
					case "Knight":
						puzzles[puzzlesIndex].getPieceList().add(new Knight(pieceX, pieceY, isPieceWhite, false));
						break;

					//Bishop case
					case "Bishop":
						puzzles[puzzlesIndex].getPieceList().add(new Bishop(pieceX, pieceY, isPieceWhite, false));
						break;

					//Rook case
					case "Rook":
						puzzles[puzzlesIndex].getPieceList().add(new Rook(pieceX, pieceY, isPieceWhite, false));
						break;

					//Queen case
					case "Queen":
						puzzles[puzzlesIndex].getPieceList().add(new Queen(pieceX, pieceY, isPieceWhite, false));
						break;

					//King case
					case "King":
						puzzles[puzzlesIndex].getPieceList().add(new King(pieceX, pieceY, isPieceWhite, false));
						break;
					}

				} while (piecesInfoIndex < piecesInfo.length);
				
				//increase puzzle index
				puzzlesIndex++;
			}

		} catch (FileNotFoundException error) {

			//Displays error message if the file is not found
			System.out.println("File not found - please check the name/location");

		}
	}

	//getters
	public Puzzle[] getPuzzles() {
		return puzzles;
	}
	
}
