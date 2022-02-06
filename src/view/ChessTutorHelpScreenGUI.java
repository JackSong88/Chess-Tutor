/* Author: Jack Song
 * 
 * This class is a GUI screen class for the help screen. It helps users and gives instructions on how to 
 * navigate the program or specific screen.
 * 
 * Created with the help of WindowBuilder.
 */

package view;

//imports
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ChessTutorHelpScreenGUI extends JFrame implements ActionListener {

	//Fields

	//panels
	private JPanel titlePanel;
	private JPanel helpPanel;
	private JPanel helpMessagePanel;

	//Labels
	private JLabel titleLabel;
	private JLabel helpMessageTitleLabel;
	private JLabel helpMessageLabel;

	//buttons
	private JButton lessonScreenHelpButton;
	private JButton generalHelpButton;
	private JButton playAgainstAiScreenHelpButton;
	private JButton puzzleScreenHelpButton;
	private JButton closeWindowButton;

	//Menu Bar
	private ChessTutorMenuBarGUI chessTutorMenuBar = new ChessTutorMenuBarGUI();

	//ui manager to change message ui
	private UIManager uiManager = new UIManager();

	//colour constants
	private final Color BACKGROUND_COLOR = new Color(34, 36, 38);
	private final Color TEXT_COLOR = new Color(245, 247, 250);
	private final Color BUTTON_BACKGROUND_COLOR = new Color(49, 45, 87);

	//font
	private Font OPENSANS17;
	private JLabel lessonHelpLabel;
	private JLabel generalHelpLabel;
	private JLabel playAgainstAiHelpLabel;
	private JLabel puzzlesHelpLabel;

	//constructor
	public ChessTutorHelpScreenGUI() {

		//sets up frame and its properties
		setUpFrame();

		//initialize fonts
		initFonts();

		//initialize all the GUI elements
		initElements();

		//add action listeners to elements
		addActionListeners();

		//setup message box
		createMessageBoxGUI();
	}

	//this method sets up the GUI screen's frame.
	private void setUpFrame() {

		//Set properties - minimum size, title, layout etc.
		setMinimumSize(new Dimension(1000, 970));
		setResizable(false);
		setTitle("iChess - Help Screen");
		getContentPane().setLayout(null);
		getContentPane().setBackground(BACKGROUND_COLOR);

		//set menu Bar
		setJMenuBar(chessTutorMenuBar);

		//set icon image for the frame (top left corner)
		setIconImage(new ImageIcon("images/FrameIconImage.jpg").getImage());

		//set it to visible
		setVisible(true);
	}

	//this method initializes all the GUI elements
	private void initElements() {

		//Setup title panel
		titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setBackground(new Color(34, 36, 38));
		titlePanel.setBounds(0, 0, 996, 100);
		getContentPane().add(titlePanel);

		//setup Title Label
		titleLabel = new JLabel();
		titleLabel.setText("iChess - Help");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(245, 247, 250));
		titleLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 52));
		titleLabel.setBounds(0, 20, 996, 79);
		titlePanel.add(titleLabel);

		//-------------------------------------------------------------------

		//setup help panel
		helpPanel = new JPanel();
		helpPanel.setBackground(BACKGROUND_COLOR);
		helpPanel.setBounds(0, 100, 996, 824);
		helpPanel.setLayout(null);
		getContentPane().add(helpPanel);

		//setup general help button
		generalHelpButton = new JButton("General Help");
		generalHelpButton.setRolloverEnabled(false);
		generalHelpButton.setForeground(new Color(245, 247, 250));
		generalHelpButton.setFont(new Font("Century Gothic", Font.PLAIN, 33));
		generalHelpButton.setFocusPainted(false);
		generalHelpButton.setBackground(BUTTON_BACKGROUND_COLOR);
		generalHelpButton.setBounds(125, 69, 250, 250);
		helpPanel.add(generalHelpButton);

		//setup play against ai screen help button
		playAgainstAiScreenHelpButton = new JButton();
		playAgainstAiScreenHelpButton.setRolloverEnabled(false);
		playAgainstAiScreenHelpButton.setForeground(new Color(245, 247, 250));
		playAgainstAiScreenHelpButton.setFont(new Font("Century Gothic", Font.PLAIN, 42));
		playAgainstAiScreenHelpButton.setFocusPainted(false);
		playAgainstAiScreenHelpButton.setBackground(BUTTON_BACKGROUND_COLOR);
		playAgainstAiScreenHelpButton.setBounds(600, 69, 250, 250);
		playAgainstAiScreenHelpButton.setIcon(new ImageIcon("images/ChessTutorPlayAgainstAiScreenPreview.jpg"));
		helpPanel.add(playAgainstAiScreenHelpButton);

		//setup lesson screen help button
		lessonScreenHelpButton = new JButton();
		lessonScreenHelpButton.setRolloverEnabled(false);
		lessonScreenHelpButton.setForeground(new Color(245, 247, 250));
		lessonScreenHelpButton.setFont(new Font("Century Gothic", Font.PLAIN, 42));
		lessonScreenHelpButton.setFocusPainted(false);
		lessonScreenHelpButton.setBackground(BUTTON_BACKGROUND_COLOR);
		lessonScreenHelpButton.setBounds(125, 403, 250, 250);
		lessonScreenHelpButton.setIcon(new ImageIcon("images/ChessTutorLessonScreenPreview.jpg"));
		helpPanel.add(lessonScreenHelpButton);

		//setup additional screens help button
		puzzleScreenHelpButton = new JButton();
		puzzleScreenHelpButton.setRolloverEnabled(false);
		puzzleScreenHelpButton.setForeground(new Color(245, 247, 250));
		puzzleScreenHelpButton.setFont(new Font("Century Gothic", Font.PLAIN, 42));
		puzzleScreenHelpButton.setFocusPainted(false);
		puzzleScreenHelpButton.setBackground(BUTTON_BACKGROUND_COLOR);
		puzzleScreenHelpButton.setBounds(600, 403, 250, 250);
		puzzleScreenHelpButton.setIcon(new ImageIcon("images/ChessTutorPuzzleScreenPreview.jpg"));
		helpPanel.add(puzzleScreenHelpButton);

		//setup close window button
		closeWindowButton = new JButton("Close");
		closeWindowButton.setRolloverEnabled(false);
		closeWindowButton.setForeground(new Color(245, 247, 250));
		closeWindowButton.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		closeWindowButton.setFocusPainted(false);
		closeWindowButton.setBackground(BUTTON_BACKGROUND_COLOR);
		closeWindowButton.setBounds(405, 691, 170, 80);
		helpPanel.add(closeWindowButton);
		
		lessonHelpLabel = new JLabel("Lessons");
		lessonHelpLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 28));
		lessonHelpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lessonHelpLabel.setBounds(125, 364, 252, 40);
		lessonHelpLabel.setForeground(TEXT_COLOR);
		helpPanel.add(lessonHelpLabel);
		
		generalHelpLabel = new JLabel("General Help");
		generalHelpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		generalHelpLabel.setForeground(new Color(245, 247, 250));
		generalHelpLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 28));
		generalHelpLabel.setBounds(125, 29, 252, 40);
		helpPanel.add(generalHelpLabel);
		
		playAgainstAiHelpLabel = new JLabel("Play Against AI");
		playAgainstAiHelpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playAgainstAiHelpLabel.setForeground(new Color(245, 247, 250));
		playAgainstAiHelpLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 28));
		playAgainstAiHelpLabel.setBounds(600, 29, 252, 40);
		helpPanel.add(playAgainstAiHelpLabel);
		
		puzzlesHelpLabel = new JLabel("Puzzles");
		puzzlesHelpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		puzzlesHelpLabel.setForeground(new Color(245, 247, 250));
		puzzlesHelpLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 28));
		puzzlesHelpLabel.setBounds(598, 364, 252, 40);
		helpPanel.add(puzzlesHelpLabel);

		//ensure all elements appear on screen
		repaint();
		revalidate();
	}

	//this method adds action listeners to GUI elements
	private void addActionListeners() {
		generalHelpButton.addActionListener(this);
		playAgainstAiScreenHelpButton.addActionListener(this);
		lessonScreenHelpButton.addActionListener(this);
		puzzleScreenHelpButton.addActionListener(this);
		closeWindowButton.addActionListener(this);
	}

	//detects actions performed on GUI elements and performs appropriate actions
	@Override
	public void actionPerformed(ActionEvent event) {

		//if general help button is clicked
		if (event.getSource() == generalHelpButton) {

			//set message title
			helpMessageTitleLabel.setText("General Help");

			//set message content
			helpMessageLabel
					.setText("<html> iChess is a chess tutor application that can teach anyone how to play chess."
							+ "It will offer lessons that are suitable for beginners and intermediate players. Each Lesson will also contain "
							+ "a scenario where the user can practice.<br><br>"
							+ "There are also other fun features such as puzzles or playing against an AI which can be accessed from the home screen."
							+ " <br>The home screen can be accessed from anywhere through the menu bar at the top of the screen! <html>");

			//display message
			JOptionPane.showMessageDialog(this, helpMessagePanel, "General Help", JOptionPane.PLAIN_MESSAGE);

		} else if (event.getSource() == playAgainstAiScreenHelpButton) { //if home screen help button is clicked

			//set message title
			helpMessageTitleLabel.setText("Play Against AI Screen Help");

			//set message content
			helpMessageLabel.setText(
					"<html> The play against AI screen matches you with an AI of your own choosing. There are three difficulties: "
							+ "Easy, Medium, and Hard, each with a unique playstyle. <br><br> You can also choose which colour to pick, black or white. "
							+ "Remember, white goes first so the board will show the AI's move as soon as you see the screen if you are playing black."
							+ "<br><br> The Play Against AI screen can be accessed from the home <br>page.  <html>");

			//display message
			JOptionPane.showMessageDialog(this, helpMessagePanel, "Play Against AI Screen Help",
					JOptionPane.PLAIN_MESSAGE);

		} else if (event.getSource() == lessonScreenHelpButton) { //if lesson screen help button is clicked

			//set message title
			helpMessageTitleLabel.setText("Lesson Screen Help");

			//set message content
			helpMessageLabel
					.setText("<html> To being a lesson, click the \"Chess Fundamentals\" button on<br> the home screen <br><br>"
							+ "Arriving in the lesson screen, you will be able to cycle <br>through all the avaliable lessons (More to be added) "
							+ "and learn many lessons relating to chess. There will also be scenarios where you can practice the knowledge learned. <html>");

			//display message
			JOptionPane.showMessageDialog(this, helpMessagePanel, "Lesson Screen Help", JOptionPane.PLAIN_MESSAGE);

		} else if (event.getSource() == puzzleScreenHelpButton) { //if additional screen help button is clicked

			//set message title
			helpMessageTitleLabel.setText("Puzzle Screen Help");

			//set message content
			helpMessageLabel
					.setText("<html> To try out a puzzle, click the \"Puzzles\" button on the home screen<br><br>"
							+ "In the puzzle screen, you will be able to try out puzzles (right now all relating to checkmate). "
							+ "Above the board, you will be <br>able to see your task / puzzle type. <br><br> An AI will also respond to your "
							+ "moves! Try to play forcing <br>moves to prevent the AI from making any free moves!<html>");

			//display message
			JOptionPane.showMessageDialog(this, helpMessagePanel, "Puzzle Screen Help", JOptionPane.PLAIN_MESSAGE);

		} else if (event.getSource() == closeWindowButton) { //if close button is clicked

			//close window
			setVisible(false);
			dispose();
		}
	}

	//this method creates the GUI for pop up messages
	private void createMessageBoxGUI() {

		//UI Manager for JOptionPanes
		uiManager.put("OptionPane.background", BACKGROUND_COLOR);
		uiManager.put("Panel.background", BACKGROUND_COLOR);
		
		//ok button text
		uiManager.put("OptionPane.okButtonText", "Got it!");

		//setup help message panel
		helpMessagePanel = new JPanel();
		helpMessagePanel.setPreferredSize(new Dimension(500, 350));
		helpMessagePanel.setLayout(null);

		//setup help message title label
		helpMessageTitleLabel = new JLabel();
		helpMessageTitleLabel.setVerticalAlignment(SwingConstants.TOP);
		helpMessageTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		helpMessageTitleLabel.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		helpMessageTitleLabel.setForeground(TEXT_COLOR);
		helpMessageTitleLabel.setBounds(0, 0, 500, 30);
		helpMessagePanel.add(helpMessageTitleLabel);

		//setup help message Label
		helpMessageLabel = new JLabel();
		helpMessageLabel.setBounds(10, 50, 500, 300);
		helpMessageLabel.setFont(OPENSANS17);
		helpMessageLabel.setForeground(TEXT_COLOR);
		helpMessagePanel.add(helpMessageLabel);
	}

	//this method initializes all the fonts
	private void initFonts() {

		try {
			GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment(); //graphics environment

			//read fonts from files
			OPENSANS17 = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\OpenSans.ttf")).deriveFont(17f);

			//register the font
			graphicsEnvironment.registerFont(OPENSANS17);

			//catch exceptions
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}

	}
}
