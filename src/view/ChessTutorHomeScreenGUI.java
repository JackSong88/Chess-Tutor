/* Author: Jack Song
 * 
 * This class is a GUI screen class for the home screen. It is the first screen the user
 * sees when the program is opened and acts as a link between all the other screens of the program.
 * 
 * Created with the help of WindowBuilder.
 */

package view;

//imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ChessTutorHomeScreenGUI extends JFrame implements ActionListener {

	//Fields
	
	//JPanels
	private JPanel titlePanel;
	private JPanel contentPanel;
	
	//JLabels
	private JLabel titleLabel;
	private JLabel subtitleLabel;
	
	//JButtons
	private JButton chessLessonsButton;
	private JButton playAgainstAiButton;
	private JButton puzzlesButton;
	
	//Menu Bar
	private ChessTutorMenuBarGUI chessTutorMenuBar = new ChessTutorMenuBarGUI();

	//colour constants
	private final Color BACKGROUND_COLOR = new Color(0f,0f,0f,.75f);	//colour black, 75% transparency
	private final Color TEXT_COLOR = new Color(245, 247, 250);
	private final Color BUTTON_BACKGROUND_COLOR = new Color(36, 34, 59);

	//constructor
	public ChessTutorHomeScreenGUI() {

		//sets up frame and its properties
		setUpFrame();

		//initialize all the GUI elements
		initElements();

		//add action listeners to elements
		addActionListeners();
	}

	//this method sets up the GUI screen's frame.
	private void setUpFrame() {

		//Set properties - minimum size, title, layout etc.
		setMinimumSize(new Dimension(1000, 970));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("iChess - Home Screen");
		getContentPane().setLayout(null);
		getContentPane().setBackground(BACKGROUND_COLOR);

		//set menu Bar
		setJMenuBar(chessTutorMenuBar);

		//rescale the image to match the frame
		ImageIcon backgroundImage = new ImageIcon("images/HomeScreenBackground.jpg");
		Image scaledImage = backgroundImage.getImage();
		scaledImage = scaledImage.getScaledInstance(1000, 970, Image.SCALE_SMOOTH);

		//set content pane as the background image
		setContentPane(new JLabel(new ImageIcon(scaledImage)));

		//set icon image for the frame (top left corner)
		setIconImage(new ImageIcon("images/FrameIconImage.jpg").getImage());
		
		//set it to visible
		setVisible(true);
	}

	//this method initializes all the GUI elements
	private void initElements() {

		//Setup Title Panel
		titlePanel = new JPanel();
		titlePanel.setOpaque(false);
		titlePanel.setBackground(BACKGROUND_COLOR);
		titlePanel.setBounds(0, 0, 996, 190);
		titlePanel.setLayout(null);
		getContentPane().add(titlePanel);

		//Setup Title Label
		titleLabel = new JLabel("iChess");
		titleLabel.setForeground(TEXT_COLOR);
		titleLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 76));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 30, 996, 100);
		titlePanel.add(titleLabel);

		//setup subtitle label
		subtitleLabel = new JLabel("Your Personal Chess Tutor");
		subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		subtitleLabel.setForeground(new Color(245, 247, 250));
		subtitleLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 40));
		subtitleLabel.setBounds(0, 110, 996, 88);
		titlePanel.add(subtitleLabel);

		//--------------------------------------------------------------

		//Setup Content Panel
		contentPanel = new JPanel();
		contentPanel.setBackground(BACKGROUND_COLOR);
		contentPanel.setBounds(150, 200, 700, 650);
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		//Content Panel Buttons

		//Setup chess lessons button
		chessLessonsButton = new JButton("Chess Fundamentals");
		chessLessonsButton.setRolloverEnabled(false);
		chessLessonsButton.setFocusPainted(false);
		chessLessonsButton.setBackground(BUTTON_BACKGROUND_COLOR);
		chessLessonsButton.setForeground(TEXT_COLOR);
		chessLessonsButton.setFont(new Font("Century Gothic", Font.PLAIN, 35));
		chessLessonsButton.setBounds(100, 60, 500, 120);
		contentPanel.add(chessLessonsButton);

		//Setup chess puzzles button
		playAgainstAiButton = new JButton("Play Against AI");
		playAgainstAiButton.setRolloverEnabled(false);
		playAgainstAiButton.setFocusPainted(false);
		playAgainstAiButton.setBackground(BUTTON_BACKGROUND_COLOR);
		playAgainstAiButton.setForeground(TEXT_COLOR);
		playAgainstAiButton.setFont(new Font("Century Gothic", Font.PLAIN, 35));
		playAgainstAiButton.setBounds(100, 270, 500, 120);
		contentPanel.add(playAgainstAiButton);

		//Setup other features button
		puzzlesButton = new JButton("Puzzles");
		puzzlesButton.setRolloverEnabled(false);
		puzzlesButton.setFocusPainted(false);
		puzzlesButton.setBackground(BUTTON_BACKGROUND_COLOR);
		puzzlesButton.setForeground(TEXT_COLOR);
		puzzlesButton.setFont(new Font("Century Gothic", Font.PLAIN, 35));
		puzzlesButton.setBounds(100, 480, 500, 120);
		contentPanel.add(puzzlesButton);
	}

	//this method adds action listeners to GUI elements
	private void addActionListeners() {

		chessLessonsButton.addActionListener(this);
		playAgainstAiButton.addActionListener(this);
		puzzlesButton.addActionListener(this);
	}

	//detects actions performed on GUI elements and performs appropriate actions
	@Override
	public void actionPerformed(ActionEvent event) {

		//if chess lessons button was clicked
		if (event.getSource() == chessLessonsButton) {

			//close current window
			setVisible(false);
			dispose();

			//open new chess lessons window
			new ChessTutorLessonScreenGUI();

		} else if (event.getSource() == playAgainstAiButton) { //if chess puzzles button was clicked

			//close current window
			setVisible(false);
			dispose();

			//open new play against ai window
			new ChessTutorPlayAgainstAiScreenGUI();

		} else if (event.getSource() == puzzlesButton) { //if other features button was clicked

			//close current window
			setVisible(false);
			dispose();

			//open new play against ai window
			new ChessTutorPuzzleScreenGUI();
		}
	}
}
