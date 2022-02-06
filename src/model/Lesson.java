/*
 * Author: Jack Song
 * 
 * Lesson model class for lessons taught, lesson title. scenario name, pieces and description for each lesson.
 */

package model;

//imports
import java.util.ArrayList;

import pieces.Piece;

public class Lesson {

	//fields
	private String lessonTitle;
	private String scenarioName;
	private ArrayList<Piece> pieceList;
	private String lessonDescription;

	//constructor
	public Lesson(String lessonTitle, String scenarioName, ArrayList<Piece> pieceList, String lessonDescription) {
		this.lessonTitle = lessonTitle;
		this.scenarioName = scenarioName;
		this.pieceList = pieceList;
		this.lessonDescription = lessonDescription;
	}

	//overloaded constructor with no parameters
	public Lesson() {

		this.lessonTitle = "No Title";
		this.scenarioName = "No Scenario";
		this.lessonDescription = "No description";
		this.pieceList = new ArrayList<Piece>();
	}

	//getters and setters
	public String getLessonTitle() {
		return lessonTitle;
	}

	public void setLessonTitle(String lessonTitle) {
		this.lessonTitle = lessonTitle;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	
	public ArrayList<Piece> getPieceList() {
		return pieceList;
	}

	public void setPieceList(ArrayList<Piece> pieceList) {
		this.pieceList = pieceList;
	}

	public String getLessonDescription() {
		return lessonDescription;
	}

	public void setLessonDescription(String lessonDescription) {
		this.lessonDescription = lessonDescription;
	}

	//toString method
	@Override
	public String toString() {
		return "Lesson [lessonTitle=" + lessonTitle + ", scenarioName=" + scenarioName + ", pieceList=" + pieceList
				+ ", lessonDescription=" + lessonDescription + "]";
	}
	
	

}
