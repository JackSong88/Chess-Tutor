/* Author: Jack Song
 * 
 * This class is a file input class for the lessons content. It reads the data from a local csv file
 */

package fileinput;

//imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Lesson;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ChessTutorLessonsFileInput {

	//Fields
	private Lesson[] lessons;
	private final int LESSONS_COUNT = 15;

	//constructor
	public ChessTutorLessonsFileInput() {

		//initialize lessons array
		lessons = new Lesson[LESSONS_COUNT];

		try {

			//Scanner to read in the csv file
			Scanner input = new Scanner(new File("files/ChessTutorLessonContent.csv"));
			input.useDelimiter(",|\\r\\n"); //uses delimiter to make file input easier and to prevent any errors

			//remove the header from the input
			input.nextLine();

			//index variable to keep track of lesson number
			int lessonIndex = 0;

			//continue the loop until there's no more information to read
			while (input.hasNextLine()) {

				//initialize each lesson object in the array
				lessons[lessonIndex] = new Lesson();

				//read in information for each lesson
				lessons[lessonIndex].setLessonTitle(input.next());
				lessons[lessonIndex].setScenarioName(input.next());

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
						lessons[lessonIndex].getPieceList().add(new Pawn(pieceX, pieceY, isPieceWhite, false));
						break;

					//Knight case
					case "Knight":
						lessons[lessonIndex].getPieceList().add(new Knight(pieceX, pieceY, isPieceWhite, false));
						break;

					//Bishop case
					case "Bishop":
						lessons[lessonIndex].getPieceList().add(new Bishop(pieceX, pieceY, isPieceWhite, false));
						break;

					//Rook case
					case "Rook":
						lessons[lessonIndex].getPieceList().add(new Rook(pieceX, pieceY, isPieceWhite, false));
						break;

					//Queen case
					case "Queen":
						lessons[lessonIndex].getPieceList().add(new Queen(pieceX, pieceY, isPieceWhite, false));
						break;

					//King case
					case "King":
						lessons[lessonIndex].getPieceList().add(new King(pieceX, pieceY, isPieceWhite, false));
						break;
					}

				} while (piecesInfoIndex < piecesInfo.length);

				//temp variable to store lesson description
				String lessonDescription = input.next();

				//replace all quotation marks with nothing
				lessonDescription = lessonDescription.replaceAll("\"", "");

				//replace all "COMMA" with commas. "COMMA" is a place holder for commas as it interferes with csv files
				lessonDescription = lessonDescription.replaceAll("COMMA", ",");

				//store lesson description information
				lessons[lessonIndex].setLessonDescription(lessonDescription);
				
				//increase lesson index
				lessonIndex++;
			}

		} catch (FileNotFoundException error) {

			//Displays error message if the file is not found
			System.out.println("File not found - please check the name/location");

		}
	}

	//getters
	public Lesson[] getLessons() {
		return lessons;
	}	
	
}
