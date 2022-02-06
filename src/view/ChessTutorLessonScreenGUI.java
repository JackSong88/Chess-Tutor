/* Author: Jack Song
 * 
 * This class is a GUI screen class for the lessons screen. It offers lessons that introduces the 
 * player to the game chess. Each lesson will cover a different concept/topic and each lesson will feature
 * a scenario the user can play with.
 * 
 * Created with the help of WindowBuilder.
 */

package view;

//imports
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import fileinput.ChessTutorLessonsFileInput;
import game.Board;
import game.Game;
import model.Move;
import model.Player;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ChessTutorLessonScreenGUI extends JFrame implements ActionListener, MouseListener {

	//Fields
	
	//JPanel
	private JPanel titlePanel;
	private JPanel lessonContentPanel;
	private JPanel lessonControlPanel;
	private JPanel displayMessagePanel;
	
	//JLabels
	private JLabel titleLabel;
	private JLabel scenarioLabel;
	private JLabel[][] chessBoardSquaresLabels;
	private JLabel columnAxisLabel;
	private JLabel rowAxisLabel;
	private JLabel displayMessageLabel;
	
	//JTextArea
	private JTextArea lessonDescriptionTextArea;
	
	//JButtons
	private JButton resetScenarioButton;
	private JButton nextLessonButton;
	private JButton previousLessonButton;

	//Menu Bar
	private ChessTutorMenuBarGUI chessTutorMenuBar = new ChessTutorMenuBarGUI();

	private int BOARD_SIZE = 4; //chess board size
	private int lessonNumber = 0; //which lesson the user is on
	private final int LESSONS_COUNT = 15; //total number of lessons

	//chess game simulation fields
	private Game chessGame;
	private Player lessonsPlayer; //only player as user is going through lesson

	//fields for moving pieces
	private int clickedX;
	private int clickedY;
	private boolean hasClicked;

	//file input field
	private ChessTutorLessonsFileInput lessonsFileInput;

	//colour constants
	private final Color BACKGROUND_COLOR = new Color(34, 36, 38);
	private final Color TEXT_COLOR = new Color(245, 247, 250);
	private final Color BUTTON_BACKGROUND_COLOR = new Color(49, 45, 87);
	private final Color BOARD_COLOR_1 = new Color(177, 136, 99);
	private final Color BOARD_COLOR_2 = new Color(238, 217, 181);
	private final Color HIGHTLIGHT_COLOR = new Color(105, 171, 82);
	private final Color CHECKED_COLOR = new Color(222, 71, 71);
	private final Color AVAILABLE_MOVE_COLOR = new Color(26, 57, 110);

	//ui manager to change message ui
	private UIManager uiManager = new UIManager();

	//font constant
	private Font OPENSANS17;

	//constructor
	public ChessTutorLessonScreenGUI() {

		//initialize new lessons file input
		lessonsFileInput = new ChessTutorLessonsFileInput();

		//initialize fonts
		initFonts();

		//sets up frame and its properties
		setupFrame();

		//initialize all the GUI elements
		initElements();

		//set up lesson chess board
		setupChessBoard();

		//add all action and mouse listeners
		addActionAndMouseListeners();
	}

	//this method sets up the GUI screen's frame.
	private void setupFrame() {

		//Set properties - minimum size, title, layout etc.
		setMinimumSize(new Dimension(1110, 970));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("iChess - Lesson Screen");
		getContentPane().setLayout(null);

		//set menu Bar
		setJMenuBar(chessTutorMenuBar);

		//set icon image for the frame (top left corner)
		setIconImage(new ImageIcon("images/FrameIconImage.jpg").getImage());

		//set it to visible
		setVisible(true);
	}

	//this method initializes all the GUI elements
	private void initElements() {

		//Set up Title Panel
		titlePanel = new JPanel();
		titlePanel.setBackground(BACKGROUND_COLOR);
		titlePanel.setBounds(0, 0, 1106, 100);
		getContentPane().add(titlePanel);
		titlePanel.setLayout(null);

		//Set up Title Label
		titleLabel = new JLabel();
		titleLabel.setForeground(TEXT_COLOR);
		titleLabel.setText(lessonsFileInput.getLessons()[lessonNumber].getLessonTitle());
		titleLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 46));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 21, 1106, 79);
		titlePanel.add(titleLabel);

		//------------------------------------------------------------

		//setup lesson content panel
		lessonContentPanel = new JPanel();
		lessonContentPanel.setBackground(BACKGROUND_COLOR);
		lessonContentPanel.setBounds(0, 100, 1106, 650);
		getContentPane().add(lessonContentPanel);
		lessonContentPanel.setLayout(null);

		//setup lesson description text area
		lessonDescriptionTextArea = new JTextArea();
		lessonDescriptionTextArea.setBackground(Color.DARK_GRAY);
		lessonDescriptionTextArea.setForeground(TEXT_COLOR);
		lessonDescriptionTextArea.setEditable(false);
		lessonDescriptionTextArea.setText(lessonsFileInput.getLessons()[lessonNumber].getLessonDescription());

		//automatically goes to nextline to prevent text overflow in text area
		lessonDescriptionTextArea.setLineWrap(true);
		lessonDescriptionTextArea.setWrapStyleWord(true);

		lessonDescriptionTextArea.setFont(OPENSANS17);
		lessonDescriptionTextArea.setBounds(525, 40, 540, 570);
		lessonContentPanel.add(lessonDescriptionTextArea);

		//setup scenario label
		scenarioLabel = new JLabel();
		scenarioLabel.setForeground(TEXT_COLOR);
		scenarioLabel.setText("Scenario: " + lessonsFileInput.getLessons()[lessonNumber].getScenarioName());
		scenarioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scenarioLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 30));
		scenarioLabel.setBounds(38, 55, 476, 60);
		lessonContentPanel.add(scenarioLabel);

		//setup reset scenario button
		resetScenarioButton = new JButton("Reset");
		resetScenarioButton.setRolloverEnabled(false);
		resetScenarioButton.setFocusPainted(false);
		resetScenarioButton.setBackground(BUTTON_BACKGROUND_COLOR);
		resetScenarioButton.setForeground(TEXT_COLOR);
		resetScenarioButton.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		resetScenarioButton.setBounds(185, 565, 180, 60);
		lessonContentPanel.add(resetScenarioButton);

		//axis label for the columns
		columnAxisLabel = new JLabel("    a            b            c            d");
		columnAxisLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 26));
		columnAxisLabel.setForeground(Color.WHITE);
		columnAxisLabel.setBounds(89, 516, 386, 48);
		lessonContentPanel.add(columnAxisLabel);

		//axis label for the rows
		rowAxisLabel = new JLabel("<html>4<br><br>    3<br><br>    2<br><br>    1<html>");
		rowAxisLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rowAxisLabel.setVerticalAlignment(SwingConstants.TOP);
		rowAxisLabel.setForeground(Color.WHITE);
		rowAxisLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 30));
		rowAxisLabel.setBounds(10, 136, 78, 381);
		lessonContentPanel.add(rowAxisLabel);

		//setup 4x4 chess board
		chessBoardSquaresLabels = new JLabel[BOARD_SIZE][BOARD_SIZE];

		//create and set up each individual square
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {

				//create new label
				chessBoardSquaresLabels[row][column] = new JLabel();

				//ensure the colours are alternating on the chess board

				//if the sum of row and column value is even, the cell colour should be color1
				if ((row + column) % 2 == 0)
					chessBoardSquaresLabels[row][column].setBackground(BOARD_COLOR_1);

				//otherwise it will be color2. Ensures alternating colours
				else
					chessBoardSquaresLabels[row][column].setBackground(BOARD_COLOR_2);

				//other properties
				chessBoardSquaresLabels[row][column].setOpaque(true);
				chessBoardSquaresLabels[row][column].setBounds(75 + column * 100, 420 - row * 100, 100, 100); //to ensure bottom left is 0, 0 for the board

				//add label to panel
				lessonContentPanel.add(chessBoardSquaresLabels[row][column]);

			}
		}

		//---------------------------------------------------------------

		//setup lesson control panel
		lessonControlPanel = new JPanel();
		lessonControlPanel.setBackground(BACKGROUND_COLOR);
		lessonControlPanel.setBounds(0, 750, 1106, 172);
		getContentPane().add(lessonControlPanel);
		lessonControlPanel.setLayout(null);

		//setup previous lesson button
		previousLessonButton = new JButton("Previous Lesson");
		previousLessonButton.setRolloverEnabled(false);
		previousLessonButton.setFocusPainted(false);
		previousLessonButton.setBackground(BUTTON_BACKGROUND_COLOR);
		previousLessonButton.setForeground(TEXT_COLOR);
		previousLessonButton.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		previousLessonButton.setBounds(140, 20, 290, 90);
		lessonControlPanel.add(previousLessonButton);

		//setup next lesson button
		nextLessonButton = new JButton("Next Lesson");
		nextLessonButton.setRolloverEnabled(false);
		nextLessonButton.setFocusPainted(false);
		nextLessonButton.setBackground(BUTTON_BACKGROUND_COLOR);
		nextLessonButton.setForeground(TEXT_COLOR);
		nextLessonButton.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		nextLessonButton.setBounds(654, 20, 290, 90);
		lessonControlPanel.add(nextLessonButton);
	}

	//this method sets up the lesson chess board 
	private void setupChessBoard() {

		//create players, one white, one black. User will be playing as white during lessons. No AI as they are still learning
		lessonsPlayer = new Player(true, true);

		//start new chess game simulation with board size 4
		chessGame = new Game(new Board(BOARD_SIZE), lessonsPlayer, new Player(false, false));

		//place each piece on the chess board
		for (Piece currentPiece : lessonsFileInput.getLessons()[lessonNumber].getPieceList()) {
			chessGame.getChessBoard().setPiece(currentPiece, currentPiece.getX(), currentPiece.getY());
		}

		//update board GUI after setting up board
		updateBoardGUI();
	}

	//this method updates the board GUI after a move
	private void updateBoardGUI() {

		//loop through every cell
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {

				//clear all image icons first
				chessBoardSquaresLabels[row][column].setIcon(null);

				//if cell is not empty, update cell with new image icon
				if (!chessGame.getChessBoard().isCellEmpty(row, column))
					chessBoardSquaresLabels[row][column]
							.setIcon(chessGame.getChessBoard().getPiece(row, column).getPieceImageIcon());

				//if the sum of row and column value is even, the cell colour should be color1
				if ((row + column) % 2 == 0)
					chessBoardSquaresLabels[row][column].setBackground(BOARD_COLOR_1);

				//otherwise it will be color2. Ensures alternating colours
				else
					chessBoardSquaresLabels[row][column].setBackground(BOARD_COLOR_2);
			}
		}

		//repaint to remove painted elements
		repaint();

		//mark or demark kings
		markKingsChecked();
	}

	//this method updates the lesson GUI after a change in lesson
	private void updateLessonGUI() {
		titleLabel.setText(lessonsFileInput.getLessons()[lessonNumber].getLessonTitle());
		scenarioLabel.setText("Scenario: " + lessonsFileInput.getLessons()[lessonNumber].getScenarioName());
		lessonDescriptionTextArea.setText(lessonsFileInput.getLessons()[lessonNumber].getLessonDescription());
	}

	//this method resets the chessboard to its original scenario position
	private void resetChessBoard() {

		//reread in the file to ensure the pieces are at its starting position
		lessonsFileInput = new ChessTutorLessonsFileInput();

		//clear the board first
		chessGame.getChessBoard().clearBoard();

		//place each piece on the chess board
		for (Piece currentPiece : lessonsFileInput.getLessons()[lessonNumber].getPieceList()) {

			//if the current piece is a pawn, reset the made first move value.
			if (currentPiece instanceof Pawn)
				((Pawn) currentPiece).setMadeFirstMove(false);

			//if the current piece is a King, reset the moved value

			else if (currentPiece instanceof King)
				((King) currentPiece).setMoved(false);

			//if the current piece is a Rook, reset the moved value
			else if (currentPiece instanceof Rook)
				((Rook) currentPiece).setMoved(false);

			//set the piece on the board
			chessGame.getChessBoard().setPiece(currentPiece, currentPiece.getX(), currentPiece.getY());
		}

		//set hasClicked to false
		hasClicked = false;

		//set game to not ended
		chessGame.setGameEnded(false);

		//update the GUI
		updateBoardGUI();
	}

	//this method adds action and mouse listeners to GUI elements
	private void addActionAndMouseListeners() {

		//add action listeners
		previousLessonButton.addActionListener(this);
		nextLessonButton.addActionListener(this);
		resetScenarioButton.addActionListener(this);

		//add mouse listeners for each square/cell on the chess board
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				chessBoardSquaresLabels[row][column].addMouseListener(this);
			}
		}

	}

	//detects actions performed on GUI elements and performs appropriate actions
	@Override
	public void actionPerformed(ActionEvent event) {

		//if reset scenario button was clicked, reset the scenario
		if (event.getSource() == resetScenarioButton)
			resetChessBoard();

		//if next lesson or previous lesson button was clicked
		else if (event.getSource() == nextLessonButton || event.getSource() == previousLessonButton) {

			//if next lesson button is pressed, go to next lesson
			if (event.getSource() == nextLessonButton) {

				if (lessonNumber >= LESSONS_COUNT - 1) {

					//UI Manager for JOptionPanes colours
					uiManager.put("OptionPane.background", BACKGROUND_COLOR);
					uiManager.put("Panel.background", BACKGROUND_COLOR);
					
					//ok button text
					uiManager.put("OptionPane.okButtonText", "Got it!");

					//setup display message panel 
					displayMessagePanel = new JPanel();
					displayMessagePanel.setPreferredSize(new Dimension(300, 100));
					displayMessagePanel.setLayout(null);

					//setup display message label 
					displayMessageLabel = new JLabel();
					displayMessageLabel.setText("<html>This is the last lesson! More to come in the future!<html>");
					displayMessageLabel.setForeground(TEXT_COLOR);
					displayMessageLabel.setBounds(0, 0, 300, 100);
					displayMessageLabel.setVerticalAlignment(SwingConstants.CENTER);
					displayMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
					displayMessageLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 22));
					displayMessagePanel.add(displayMessageLabel);

					//display message
					JOptionPane.showMessageDialog(this, displayMessagePanel, "WARNING!", JOptionPane.PLAIN_MESSAGE);

					return;

				} else //otherwise, go to next lesson
					lessonNumber++;
			}

			//if previous lesson button was clicked, go to previous lesson
			else {

				//ensure the lesson number doesn't go below 0 when the user press previous button
				if (lessonNumber == 0) {

					//UI Manager for JOptionPanes colours
					uiManager.put("OptionPane.background", BACKGROUND_COLOR);
					uiManager.put("Panel.background", BACKGROUND_COLOR);
					
					//ok button text
					uiManager.put("OptionPane.okButtonText", "Got it!");

					//setup display message panel 
					displayMessagePanel = new JPanel();
					displayMessagePanel.setPreferredSize(new Dimension(400, 100));
					displayMessagePanel.setLayout(null);

					//setup display message label 
					displayMessageLabel = new JLabel();
					displayMessageLabel.setText(
							"<html>This is the first lesson! Press the next lesson button to go to next lesson!<html>");
					displayMessageLabel.setForeground(TEXT_COLOR);
					displayMessageLabel.setBounds(0, 0, 400, 100);
					displayMessageLabel.setVerticalAlignment(SwingConstants.CENTER);
					displayMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
					displayMessageLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 22));
					displayMessagePanel.add(displayMessageLabel);

					//display message
					JOptionPane.showMessageDialog(this, displayMessagePanel, "Warning Message",
							JOptionPane.PLAIN_MESSAGE);

					return;

				} else //otherwise, go to previous lesson
					lessonNumber--;
			}

			//reset the board with new pieces
			resetChessBoard();

			//update lesson GUI (labels, textareas)
			updateLessonGUI();
		}

	}

	//detects if a click was performed by a mouse and performs appropriate actions
	@Override
	public void mouseClicked(MouseEvent event) {

		//if game has ended, no actions will be performed
		if (chessGame.isGameEnded())
			return;

		//loop through the board and determine which cell was clicked
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				if (event.getSource() == chessBoardSquaresLabels[row][column]) {

					//Checks to see if user selects a piece of their own
					if (!chessGame.getChessBoard().isCellEmpty(row, column)
							&& lessonsPlayer.isWhite() == chessGame.getChessBoard().getPiece(row, column).isWhite()
							&& !hasClicked) {

						//set has clicked to true as user has clicked a piece
						hasClicked = true;

						//record clicked piece's information
						clickedX = row;
						clickedY = column;

						//highlight piece background
						chessBoardSquaresLabels[row][column].setBackground(HIGHTLIGHT_COLOR);

						//highlight all possible moves for the piece at (row, column)
						highlightPossibleMoves(chessGame.getChessBoard().getPiece(row, column));

					} else if (hasClicked) { //if user makes a move with the selected piece

						//make move
						//If it is able to make the move, it will move and update the board
						//If it is not able (illegal move) it will just deselect the piece
						//user would have to click a piece again then move.
						chessGame.makeMove(lessonsPlayer, clickedX, clickedY, row, column);

						//check if the piece is a pawn and if the pawn is able to promote
						if (!chessGame.getChessBoard().isCellEmpty(row, column)
								&& chessGame.getChessBoard().getPiece(row, column) instanceof Pawn
								&& ((Pawn) chessGame.getChessBoard().getPiece(row, column))
										.canPromote(chessGame.getChessBoard())) {

							//create new panel that will prompt the user to select which piece to promote
							ChessTutorPawnPromotionScreenGUI pawnPromotionSelectionPanel = new ChessTutorPawnPromotionScreenGUI();

							//UI Manager for JOptionPanes colours
							uiManager.put("OptionPane.background", BACKGROUND_COLOR);
							uiManager.put("Panel.background", BACKGROUND_COLOR);
							
							//ok button text
							uiManager.put("OptionPane.okButtonText", "Confirm");

							//show message dialogue for user to make selection
							JOptionPane.showMessageDialog(this, pawnPromotionSelectionPanel, "iChess - Pawn Promotion",
									JOptionPane.PLAIN_MESSAGE);

							//set new piece depending on which button the user pressed
							switch (pawnPromotionSelectionPanel.getSelectionNumber()) {

							//queen
							case 0:
								chessGame.getChessBoard()
										.setPiece(new Queen(row, column,
												chessGame.getChessBoard().getPiece(row, column).isWhite(), false), row,
												column);
								break;

							//rook
							case 1:
								chessGame.getChessBoard()
										.setPiece(new Rook(row, column,
												chessGame.getChessBoard().getPiece(row, column).isWhite(), false), row,
												column);
								break;

							//knight
							case 2:
								chessGame.getChessBoard()
										.setPiece(new Knight(row, column,
												chessGame.getChessBoard().getPiece(row, column).isWhite(), false), row,
												column);
								break;

							//bishop
							case 3:
								chessGame.getChessBoard()
										.setPiece(new Bishop(row, column,
												chessGame.getChessBoard().getPiece(row, column).isWhite(), false), row,
												column);
								break;
							}

						}

						//set has clicked to false
						hasClicked = false;

						//Reset background colours after move has been made
						//if the sum of x and y value is even, the cell colour should be color1
						if ((clickedX + clickedY) % 2 == 0)
							chessBoardSquaresLabels[clickedX][clickedY].setBackground(BOARD_COLOR_1);

						//otherwise it will be color2. Ensures alternating colours
						else
							chessBoardSquaresLabels[clickedX][clickedY].setBackground(BOARD_COLOR_2);

						//update GUI
						updateBoardGUI();

					}
				}
			}
		}

	}

	//this method finds the kings on the board and mark them as checked if they are in check
	private void markKingsChecked() {

		//loop through every square on the board
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {

				//if the king is in check, paint background red to indicate its in check
				if (!chessGame.getChessBoard().isCellEmpty(row, column)
						&& chessGame.getChessBoard().getPiece(row, column) instanceof King
						&& ((King) chessGame.getChessBoard().getPiece(row, column)).isInCheck(chessGame.getChessBoard(),
								row, column)) {
					chessBoardSquaresLabels[row][column].setBackground(CHECKED_COLOR);

					//in addition, check if the king is check mated
					if (((King) chessGame.getChessBoard().getPiece(row, column)).isCheckmated(chessGame.getChessBoard(),
							row, column)) {

						//UI Manager for JOptionPanes
						uiManager.put("OptionPane.background", BACKGROUND_COLOR);
						uiManager.put("Panel.background", BACKGROUND_COLOR);
						
						//ok button text
						uiManager.put("OptionPane.okButtonText", "Got it!");

						//setup display message panel 
						displayMessagePanel = new JPanel();
						displayMessagePanel.setPreferredSize(new Dimension(500, 350));
						displayMessagePanel.setLayout(null);

						//setup display message label 
						displayMessageLabel = new JLabel();
						displayMessageLabel.setText("GAME OVER!! "
								+ (chessGame.getChessBoard().getPiece(row, column).isWhite() ? "BLACK WINS!!!!"
										: "WHITE WINS!!!"));
						displayMessageLabel.setForeground(TEXT_COLOR);
						displayMessageLabel.setBounds(0, 0, 500, 350);
						displayMessageLabel.setVerticalAlignment(SwingConstants.CENTER);
						displayMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
						displayMessageLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 32));
						displayMessagePanel.add(displayMessageLabel);

						//display message
						JOptionPane.showMessageDialog(this, displayMessagePanel, "GAME OVER!",
								JOptionPane.PLAIN_MESSAGE);

						//set game ended if checkmated
						chessGame.setGameEnded(true);
					}

				} else if (!chessGame.getChessBoard().isCellEmpty(row, column)
						&& chessGame.getChessBoard().getPiece(row, column) instanceof King) { //if the king is not in check, paint the background the normal color

					//if the sum of row and column value is even, the cell colour should be color1
					if ((row + column) % 2 == 0)
						chessBoardSquaresLabels[row][column].setBackground(BOARD_COLOR_1);

					//otherwise it will be color2. Ensures alternating colours
					else
						chessBoardSquaresLabels[row][column].setBackground(BOARD_COLOR_2);

				}
			}
		}

	}

	//this method highlights all the possible moves for a selected piece(quality of life feature)
	private void highlightPossibleMoves(Piece selectedPiece) {

		//generate all possible moves for the chess piece
		selectedPiece.generatePossibleMoves(chessGame.getChessBoard());

		//loop through all the possible moves, highlight it on the board
		for (Move currentPossibleMove : selectedPiece.getPossibleMoves()) {

			//get the graphics used to draw circles
			Graphics drawGraphics = getGraphics();

			//set the color
			drawGraphics.setColor(AVAILABLE_MOVE_COLOR);

			//draw circle at the center of the possible move on the board
			drawCenteredCircle(drawGraphics, 134 + currentPossibleMove.getY() * 100,
					627 - currentPossibleMove.getX() * 100, 30);

		}
	}

	//draws a centered circle at x,y with radius r
	private void drawCenteredCircle(Graphics g, int x, int y, int radius) {

		//new x and y values for centered circle
		x = x - (radius / 2);
		y = y - (radius / 2);

		//draw circle
		g.fillOval(x, y, radius, radius);
	}

	//necessary MouseListener method
	@Override
	public void mouseEntered(MouseEvent arg0) {
		//unused but necessary
	}

	//necessary MouseListener method
	@Override
	public void mouseExited(MouseEvent arg0) {
		//unused but necessary
	}

	//necessary MouseListener method
	@Override
	public void mousePressed(MouseEvent arg0) {
		//unused but necessary
	}

	//necessary MouseListener method
	@Override
	public void mouseReleased(MouseEvent arg0) {
		//unused but necessary
	}

	//this method initializes all the fonts
	private void initFonts() {

		try {
			GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment(); //graphics environment

			//read fonts from files
			OPENSANS17 = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\OpenSans.ttf")).deriveFont(17f);

			//register the font
			graphicsEnvironment.registerFont(OPENSANS17);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}

	}
}
