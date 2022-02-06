/* Author: Jack Song
 * 
 * This class is a GUI screen class for play against AI screen. The user can play against an AI
 * and set game properties of their own choosing. For example, which colour they play as or the difficulty
 * of the AI.
 * 
 * Created with the help of WindowBuilder.
 */

package view;

//imports
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import java.awt.Graphics;
import java.awt.Image;

import java.awt.Color;

import javax.swing.ImageIcon;

public class ChessTutorPlayAgainstAiScreenGUI extends JFrame implements MouseListener {

	//Fields

	//JPanel
	private JPanel titlePanel;
	private JPanel playAgainstAiContentPanel;
	private JPanel displayMessagePanel;

	//JLabels
	private JLabel titleLabel;
	private JLabel[][] chessBoardSquaresLabels;
	private JLabel columnAxisLabel;
	private JLabel rowAxisLabel;
	private JLabel displayMessageLabel;

	//Menu Bar
	private ChessTutorMenuBarGUI chessTutorMenuBar = new ChessTutorMenuBarGUI();

	private int BOARD_SIZE = 8; //chess board size

	//user preferences for game settings
	private boolean isPlayerColorWhite; //player's colour of choice
	private int aiDifficulty; //player's ai difficulty of choice

	//chess game simulation fields
	private Game chessGame;

	//fields for moving pieces
	private int clickedX;
	private int clickedY;
	private boolean hasClicked;

	//colour constants
	private final Color BACKGROUND_COLOR = new Color(34, 36, 38);
	private final Color TEXT_COLOR = new Color(245, 247, 250);
	private final Color BOARD_COLOR_1 = new Color(177, 136, 99);
	private final Color BOARD_COLOR_2 = new Color(238, 217, 181);
	private final Color HIGHTLIGHT_COLOR = new Color(105, 171, 82);
	private final Color CHECKED_COLOR = new Color(222, 71, 71);
	private final Color AVAILABLE_MOVE_COLOR = new Color(26, 57, 110);

	//ui manager to change message ui
	private UIManager uiManager = new UIManager();

	//Random number generator 
	private Random randomNumberGenerator = new Random();

	//constructor
	public ChessTutorPlayAgainstAiScreenGUI() {

		//get user preferences for the game
		getUserPreferences();

		//sets up frame and its properties
		setupFrame();

		//initialize all the GUI elements
		initElements();

		//set up lesson chess board
		setupChessBoard();

		//add all action and mouse listeners
		addMouseListeners();
	}

	//this method gets the user's preferences for playing against AI
	private void getUserPreferences() {

		//create new panel that will prompt the user to select game settings
		ChessTutorPlayAgainstAiSettingsScreenGUI userGamePreferencesPanel = new ChessTutorPlayAgainstAiSettingsScreenGUI();

		//UI Manager for JOptionPanes colours
		uiManager.put("OptionPane.background", BACKGROUND_COLOR);
		uiManager.put("Panel.background", BACKGROUND_COLOR);

		//ok button text
		uiManager.put("OptionPane.okButtonText", "START GAME");

		//show message dialogue for user to make selection
		JOptionPane.showMessageDialog(this, userGamePreferencesPanel, "iChess - Play Against AI Settings",
				JOptionPane.PLAIN_MESSAGE);

		//set user preferences selected to this class' field
		isPlayerColorWhite = userGamePreferencesPanel.isColourWhite();
		aiDifficulty = userGamePreferencesPanel.getAiDifficulty();

	}

	//this method sets up the GUI screen's frame.
	private void setupFrame() {

		//Set properties - minimum size, title, layout etc.
		setMinimumSize(new Dimension(1000, 1000));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("iChess - Play Against AI");
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
		titlePanel.setBounds(0, 0, 996, 80);
		getContentPane().add(titlePanel);
		titlePanel.setLayout(null);

		//Set up Title Label
		titleLabel = new JLabel();

		//set title text depending on AI difficulty

		//easy difficulty
		if (aiDifficulty == 0)
			titleLabel.setText("Play Against AI - Easy Difficulty");

		//medium difficulty
		else if (aiDifficulty == 1)
			titleLabel.setText("Play Against AI - Medium Difficulty");

		//hard difficulty
		else if (aiDifficulty == 2)
			titleLabel.setText("Play Against AI - Hard Difficulty");

		//set other properties
		titleLabel.setForeground(TEXT_COLOR);
		titleLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 46));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 10, 995, 79);
		titlePanel.add(titleLabel);

		//------------------------------------------------------------

		//setup lesson content panel
		playAgainstAiContentPanel = new JPanel();
		playAgainstAiContentPanel.setBackground(BACKGROUND_COLOR);
		playAgainstAiContentPanel.setBounds(0, 80, 996, 868);
		getContentPane().add(playAgainstAiContentPanel);
		playAgainstAiContentPanel.setLayout(null);

		//axis label for the columns
		columnAxisLabel = new JLabel(
				"    a           b           c           d           e           f           g           h");
		columnAxisLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 26));
		columnAxisLabel.setForeground(Color.WHITE);
		columnAxisLabel.setBounds(137, 745, 750, 48);
		playAgainstAiContentPanel.add(columnAxisLabel);

		//axis label for the rows
		rowAxisLabel = new JLabel();

		//set axis depending on player's colour
		if (isPlayerColorWhite)
			rowAxisLabel.setText(
					"<html>8<br><br>    7<br><br>    6<br><br>    5<br><br>    4<br><br>    3<br><br>    2<br><br>    1<html>");
		else
			rowAxisLabel.setText(
					"<html>1<br><br>    2<br><br>    3<br><br>    4<br><br>    5<br><br>    6<br><br>    7<br><br>    8<html>");

		rowAxisLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rowAxisLabel.setVerticalAlignment(SwingConstants.TOP);
		rowAxisLabel.setForeground(Color.WHITE);
		rowAxisLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 28));
		rowAxisLabel.setBounds(60, 25, 78, 690);
		playAgainstAiContentPanel.add(rowAxisLabel);

		//setup 8x8 chess board
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

				//if the player color is white display the board
				if (isPlayerColorWhite)
					chessBoardSquaresLabels[row][column].setBounds(135 + column * 90, 650 - row * 90, 90, 90);

				//otherwise flip the board
				else
					chessBoardSquaresLabels[row][column].setBounds(135 + column * 90, 20 + row * 90, 90, 90);

				//add label to panel
				playAgainstAiContentPanel.add(chessBoardSquaresLabels[row][column]);

			}
		}

	}

	//this method sets up the lesson chess board 
	private void setupChessBoard() {

		//create player and AI.
		Player humanPlayer = new Player(true, isPlayerColorWhite);
		Player aiPlayer = new Player(false, !isPlayerColorWhite);

		//start new chess game simulation with board size 8
		chessGame = new Game(new Board(BOARD_SIZE), humanPlayer, aiPlayer);

		//Set to original positions
		chessGame.getChessBoard().reset8By8ChessBoard();

		//if the AI goes first
		//move the AI at the beginning
		if (chessGame.getCurrentPlayer().equals(chessGame.getPlayer2())) {

			//easy AI
			if (aiDifficulty == 0)
				easyAiMove();

			//medium AI
			else if (aiDifficulty == 1)
				mediumAiMove();

			//hard AI
			else if (aiDifficulty == 2)
				hardAiMove();
		}

		//update board GUI
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
				if (!chessGame.getChessBoard().isCellEmpty(row, column)) {

					//rescale the image to match the board
					Image scaledImage = chessGame.getChessBoard().getPiece(row, column).getPieceImageIcon().getImage();
					scaledImage = scaledImage.getScaledInstance(90, 90, Image.SCALE_SMOOTH);

					//set image icon
					chessBoardSquaresLabels[row][column].setIcon(new ImageIcon(scaledImage));

				}

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

	//this method adds action and mouse listeners to GUI elements
	private void addMouseListeners() {

		//add mouse listeners for each square/cell on the chess board
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				chessBoardSquaresLabels[row][column].addMouseListener(this);
			}
		}

	}

	//detects if a click was performed by a mouse and performs appropriate actions
	@Override
	public void mouseClicked(MouseEvent event) {

		//if game has ended, no actions will be performed
		if (chessGame.isGameEnded())
			return;

		//only if its the user's turn
		if (chessGame.getCurrentPlayer().equals(chessGame.getPlayer1())) {

			//loop through the board and determine which cell was clicked
			for (int row = 0; row < BOARD_SIZE; row++) {
				for (int column = 0; column < BOARD_SIZE; column++) {
					if (event.getSource() == chessBoardSquaresLabels[row][column]) {

						//Checks to see if user selects a piece of their own
						if (!chessGame.getChessBoard().isCellEmpty(row, column) && chessGame.getPlayer1()
								.isWhite() == chessGame.getChessBoard().getPiece(row, column).isWhite()
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
							chessGame.makeMove(chessGame.getPlayer1(), clickedX, clickedY, row, column);

							//check if the piece is a pawn and if the pawn is able to promote
							if (!chessGame.getChessBoard().isCellEmpty(row, column)
									&& chessGame.getChessBoard().getPiece(row, column) instanceof Pawn
									&& ((Pawn) chessGame.getChessBoard().getPiece(row, column))
											.canPromote(chessGame.getChessBoard())) {

								//create new window that prompts user to select which piece to promote
								ChessTutorPawnPromotionScreenGUI pawnPromotionSelection = new ChessTutorPawnPromotionScreenGUI();

								//UI Manager for JOptionPanes colours
								uiManager.put("OptionPane.background", BACKGROUND_COLOR);
								uiManager.put("Panel.background", BACKGROUND_COLOR);

								//ok button text
								uiManager.put("OptionPane.okButtonText", "CONFIRM");

								//show message dialogue for user to make selection
								JOptionPane.showMessageDialog(this, pawnPromotionSelection, "iChess - Pawn Promotion",
										JOptionPane.PLAIN_MESSAGE);

								//set new piece depending on which button the user pressed
								switch (pawnPromotionSelection.getSelectionNumber()) {

								//queen
								case 0:
									chessGame.getChessBoard()
											.setPiece(new Queen(row, column,
													chessGame.getChessBoard().getPiece(row, column).isWhite(), false),
													row, column);
									break;

								//rook
								case 1:
									chessGame.getChessBoard()
											.setPiece(new Rook(row, column,
													chessGame.getChessBoard().getPiece(row, column).isWhite(), false),
													row, column);
									break;

								//knight
								case 2:
									chessGame.getChessBoard()
											.setPiece(new Knight(row, column,
													chessGame.getChessBoard().getPiece(row, column).isWhite(), false),
													row, column);
									break;

								//bishop
								case 3:
									chessGame.getChessBoard()
											.setPiece(new Bishop(row, column,
													chessGame.getChessBoard().getPiece(row, column).isWhite(), false),
													row, column);
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

							//update GUI for player move
							updateBoardGUI();

							//ai makes move, move difficulty depends on user selection
							//only if user has moved and is the AI's turn and the game has not ended
							if (chessGame.getCurrentPlayer().equals(chessGame.getPlayer2())
									&& !chessGame.isGameEnded()) {

								//easy AI
								if (aiDifficulty == 0)
									easyAiMove();

								//medium AI
								else if (aiDifficulty == 1)
									mediumAiMove();

								//hard AI
								else if (aiDifficulty == 2)
									hardAiMove();

								//update GUI for AI move
								updateBoardGUI();
							}

						}
					}
				}
			}
		}

		//check for stalemate / draw
		isGameStalemate();
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

						//set game ended as true
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

	//checks if the game is a stalemate
	private void isGameStalemate() {

		//total number of pieces on the board
		int pieceCount =0;
		
		//loop through every square on the board
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				
				//if there is a piece at (row, column) add it to the count
				if(!chessGame.getChessBoard().isCellEmpty(row, column))
					pieceCount++;
			}
		}
		
		//only 2 pieces left, have to be both kings. Results in a stalemate
		//display message
		if(pieceCount==2) {
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
			displayMessageLabel.setText("GAME OVER!! IT'S A DRAW");
			displayMessageLabel.setForeground(TEXT_COLOR);
			displayMessageLabel.setBounds(0, 0, 500, 350);
			displayMessageLabel.setVerticalAlignment(SwingConstants.CENTER);
			displayMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			displayMessageLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 32));
			displayMessagePanel.add(displayMessageLabel);

			//display message
			JOptionPane.showMessageDialog(this, displayMessagePanel, "GAME OVER!",
					JOptionPane.PLAIN_MESSAGE);

			//set game ended as true
			chessGame.setGameEnded(true);
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

			//circle location depends on colour
			if (selectedPiece.isWhite())

				//draw circle at the center of the possible move on the board
				drawCenteredCircle(drawGraphics, 189 + currentPossibleMove.getY() * 90,
						833 - currentPossibleMove.getX() * 90, 30);

			else

				//draw circle at the center of the possible move on the board
				drawCenteredCircle(drawGraphics, 189 + currentPossibleMove.getY() * 90,
						203 + currentPossibleMove.getX() * 90, 30);

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

	/* Easy AI:
	 * this method simulates a move performed by an easy AI
	 * randomly selects a piece that has an available move
	 * performs the first available move, does not consider any other moves.
	 */
	private void easyAiMove() {

		//arraylist to hold all possible pieces that can be moved by the AI
		ArrayList<Piece> allPossiblePiecesList = new ArrayList<Piece>();

		//loop through every square on the board
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {

				//if a piece belongs to the AI, add it to the possible pieces list
				if (!chessGame.getChessBoard().isCellEmpty(row, column) && chessGame.getChessBoard()
						.getPiece(row, column).isWhite() == chessGame.getPlayer2().isWhite()) {

					//generate all possible moves for the piece
					chessGame.getChessBoard().getPiece(row, column).generatePossibleMoves(chessGame.getChessBoard());

					//if the piece can move
					if (chessGame.getChessBoard().getPiece(row, column).getPossibleMoves().size() > 0)

						//add to pieces list
						allPossiblePiecesList.add(chessGame.getChessBoard().getPiece(row, column));
				}
			}
		}

		//get a random index of the piece that will be played
		int randomPieceIndex = randomNumberGenerator.nextInt(allPossiblePiecesList.size());

		//get the piece that was selected
		Piece randomPlayablePiece = allPossiblePiecesList.get(randomPieceIndex);

		//get the move - will always be the first move
		Move aiMove = randomPlayablePiece.getPossibleMoves().get(0);

		//make the move
		chessGame.makeMove(chessGame.getPlayer2(), randomPlayablePiece.getX(), randomPlayablePiece.getY(),
				aiMove.getX(), aiMove.getY());

		//loop through every square on the board
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {

				//if it's AI's pawn and it can promote, always promote to queen
				if (!chessGame.getChessBoard().isCellEmpty(row, column)
						&& chessGame.getChessBoard().getPiece(row, column) instanceof Pawn
						&& chessGame.getChessBoard().getPiece(row, column).isWhite() == chessGame.getPlayer2().isWhite()
						&& ((Pawn) chessGame.getChessBoard().getPiece(row, column))
								.canPromote(chessGame.getChessBoard())) {

					//set piece to queen
					chessGame.getChessBoard().setPiece(new Queen(row, column, chessGame.getPlayer2().isWhite(), false),
							row, column);
				}

			}
		}
	}

	/* Medium AI:
	 * this method simulates a move performed by an Medium AI
	 * randomly selects a piece that has an available move
	 * randomly selects a possible move from the selected piece
	 */
	private void mediumAiMove() {

		//arraylist to hold all possible pieces that can be moved by the AI
		ArrayList<Piece> allPossiblePiecesList = new ArrayList<Piece>();

		//loop through every square on the board
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {

				//if a piece belongs to the AI, add it to the possible pieces list
				if (!chessGame.getChessBoard().isCellEmpty(row, column) && chessGame.getChessBoard()
						.getPiece(row, column).isWhite() == chessGame.getPlayer2().isWhite()) {

					//generate all possible moves for the piece
					chessGame.getChessBoard().getPiece(row, column).generatePossibleMoves(chessGame.getChessBoard());

					//if the piece can move
					if (chessGame.getChessBoard().getPiece(row, column).getPossibleMoves().size() > 0)

						//add to pieces list
						allPossiblePiecesList.add(chessGame.getChessBoard().getPiece(row, column));
				}
			}
		}

		//get a random index of the piece that will be played
		int randomPieceIndex = randomNumberGenerator.nextInt(allPossiblePiecesList.size());

		//get the piece that was selected
		Piece randomPlayablePiece = allPossiblePiecesList.get(randomPieceIndex);

		//get a random move index from the piece
		int randomMoveIndex = randomNumberGenerator.nextInt(randomPlayablePiece.getPossibleMoves().size());

		//get the move
		Move randomMove = randomPlayablePiece.getPossibleMoves().get(randomMoveIndex);

		//make the move
		chessGame.makeMove(chessGame.getPlayer2(), randomPlayablePiece.getX(), randomPlayablePiece.getY(),
				randomMove.getX(), randomMove.getY());

		//loop through every square on the board
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {

				//if it's AI's pawn and it can promote, always promote to queen
				if (!chessGame.getChessBoard().isCellEmpty(row, column)
						&& chessGame.getChessBoard().getPiece(row, column) instanceof Pawn
						&& chessGame.getChessBoard().getPiece(row, column).isWhite() == chessGame.getPlayer2().isWhite()
						&& ((Pawn) chessGame.getChessBoard().getPiece(row, column))
								.canPromote(chessGame.getChessBoard())) {

					//set piece to queen
					chessGame.getChessBoard().setPiece(new Queen(row, column, chessGame.getPlayer2().isWhite(), false),
							row, column);
				}

			}
		}
	}

	/* Hard AI:
	 * this method simulates a move performed by an hard AI
	 * Hard AIs are more aggressive compared to easy and medium AI
	 * It will take the first piece that is available to be taken
	 * Usually harder to defend all pieces that attacking
	 * If there are no pieces avaiable to capture, use the same medium AI algorithm
	 */
	private void hardAiMove() {

		//arraylist to hold all possible pieces that can be moved by the AI
		ArrayList<Piece> allPossiblePiecesList = new ArrayList<Piece>();

		//loop through every square on the board
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {

				//if a piece belongs to the AI, add it to the possible pieces list
				if (!chessGame.getChessBoard().isCellEmpty(row, column) && chessGame.getChessBoard()
						.getPiece(row, column).isWhite() == chessGame.getPlayer2().isWhite()) {

					//generate all possible moves for the piece
					chessGame.getChessBoard().getPiece(row, column).generatePossibleMoves(chessGame.getChessBoard());

					//if the piece can move
					if (chessGame.getChessBoard().getPiece(row, column).getPossibleMoves().size() > 0)

						//add to pieces list
						allPossiblePiecesList.add(chessGame.getChessBoard().getPiece(row, column));
				}
			}
		}

		//the piece ai will move
		Piece aiMovePiece = null;

		//ai move
		Move aiMove = null;

		//loop through all the possible pieces
		for (Piece currentPossiblePiece : allPossiblePiecesList) {

			//loop through the the available
			for (Move possibleMove : currentPossiblePiece.getPossibleMoves()) {

				//if a piece's move takes the opponent's piece, set it as the move
				if (!chessGame.getChessBoard().isCellEmpty(possibleMove.getX(), possibleMove.getY())
						&& chessGame.getChessBoard().getPiece(possibleMove.getX(), possibleMove.getY())
								.isWhite() != chessGame.getPlayer2().isWhite()) {
					aiMovePiece = currentPossiblePiece;
					aiMove = possibleMove;
					break;
				}
			}

			//if there is already a piece set, break;
			if (!Objects.isNull(aiMovePiece))
				break;

		}

		//if no capture moves are available, use the same algorithm as medium difficulty
		if (Objects.isNull(aiMovePiece)) {

			//get a random index of the piece that will be played
			int randomPieceIndex = randomNumberGenerator.nextInt(allPossiblePiecesList.size());

			//get the piece that was selected
			aiMovePiece = allPossiblePiecesList.get(randomPieceIndex);

			//get a random move index from the piece
			int randomMoveIndex = randomNumberGenerator.nextInt(aiMovePiece.getPossibleMoves().size());

			//ai's move, default will be a random move
			aiMove = aiMovePiece.getPossibleMoves().get(randomMoveIndex);

		}

		//make the move
		chessGame.makeMove(chessGame.getPlayer2(), aiMovePiece.getX(), aiMovePiece.getY(), aiMove.getX(),
				aiMove.getY());

		//loop through every square on the board
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {

				//if it's AI's pawn and it can promote, always promote to queen
				if (!chessGame.getChessBoard().isCellEmpty(row, column)
						&& chessGame.getChessBoard().getPiece(row, column) instanceof Pawn
						&& chessGame.getChessBoard().getPiece(row, column).isWhite() == chessGame.getPlayer2().isWhite()
						&& ((Pawn) chessGame.getChessBoard().getPiece(row, column))
								.canPromote(chessGame.getChessBoard())) {

					//set piece to queen
					chessGame.getChessBoard().setPiece(new Queen(row, column, chessGame.getPlayer2().isWhite(), false),
							row, column);
				}

			}
		}
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
}
