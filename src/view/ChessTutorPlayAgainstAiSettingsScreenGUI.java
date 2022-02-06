/*
 * Author: Jack Song
 * 
 * This class is a JPanel class that creates a panel to display choices for the user to choose
 * the game properties such as the colour they will be playing as and the difficulty of the AI
 * 
 */

package view;

//imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

public class ChessTutorPlayAgainstAiSettingsScreenGUI extends JPanel implements ActionListener {

	//Fields
	//GUI
	private JLabel chooseColourLabel;
	private JButton whiteColourButton;
	private JButton blackColourButton;
	private JButton easyDifficultyButton;
	private JButton hardDifficultyButton;
	private JButton mediumDifficultyButton;
	private JLabel difficultyLabel;

	//user selection choice
	private int aiDifficulty = 0; //default 0 -- easy
	private boolean colourWhite = true; //default true

	//colour constants
	private final Color BACKGROUND_COLOR = new Color(34, 36, 38);
	private final Color TEXT_COLOR = new Color(245, 247, 250);
	private final Color BUTTON_BACKGROUND_COLOR = new Color(49, 45, 87);
	private final Color BUTTON_BACKGROUND_COLOR_2 = new Color(70, 65, 115);

	//constructor
	public ChessTutorPlayAgainstAiSettingsScreenGUI() {

		//sets up frame and its properties
		setUpPanel();

		//initialize all the GUI elements
		initElements();

		//add action listeners for elements
		addActionListeners();
	}

	//this method sets up the GUI screen's frame.
	private void setUpPanel() {

		//Set properties - minimum size, title, layout etc.
		setPreferredSize(new Dimension(500, 550));
		setLayout(null);
		setBackground(BACKGROUND_COLOR);

		//set it to visible
		setVisible(true);
	}

	//this method initializes all the GUI elements
	private void initElements() {

		//set properties for each button

		//setup white colour button
		whiteColourButton = new JButton("");
		whiteColourButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		whiteColourButton.setRolloverEnabled(false);
		whiteColourButton.setFocusPainted(false);
		whiteColourButton.setBackground(BUTTON_BACKGROUND_COLOR_2);
		whiteColourButton.setBounds(0, 60, 250, 250);
		whiteColourButton.setIcon(new ImageIcon("images/WhiteQueen.png"));
		add(whiteColourButton);

		//setup black colour button
		blackColourButton = new JButton("");
		blackColourButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		blackColourButton.setRolloverEnabled(false);
		blackColourButton.setFocusPainted(false);
		blackColourButton.setBackground(BUTTON_BACKGROUND_COLOR_2);
		blackColourButton.setBounds(250, 60, 250, 250);
		blackColourButton.setIcon(new ImageIcon("images/BlackQueen.png"));
		add(blackColourButton);

		//setup choose colour label
		chooseColourLabel = new JLabel("Player Colour: White");
		chooseColourLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 28));
		chooseColourLabel.setForeground(TEXT_COLOR);
		chooseColourLabel.setHorizontalAlignment(SwingConstants.CENTER);
		chooseColourLabel.setBounds(0, 10, 500, 53);
		add(chooseColourLabel);

		//setup difficulty label
		difficultyLabel = new JLabel("AI Difficulty: Easy");
		difficultyLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 28));
		difficultyLabel.setForeground(TEXT_COLOR);
		difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		difficultyLabel.setBounds(0, 335, 500, 60);
		add(difficultyLabel);

		//setup easy difficulty button
		easyDifficultyButton = new JButton("Easy");
		easyDifficultyButton.setForeground(Color.GREEN);
		easyDifficultyButton.setFont(new Font("Century Gothic", Font.BOLD, 16));
		easyDifficultyButton.setRolloverEnabled(false);
		easyDifficultyButton.setFocusPainted(false);
		easyDifficultyButton.setBackground(BUTTON_BACKGROUND_COLOR);
		easyDifficultyButton.setBounds(50, 405, 100, 100);
		add(easyDifficultyButton);

		//setup medium difficulty button
		mediumDifficultyButton = new JButton("Medium");
		mediumDifficultyButton.setForeground(Color.ORANGE);
		mediumDifficultyButton.setFont(new Font("Century Gothic", Font.BOLD, 16));
		mediumDifficultyButton.setRolloverEnabled(false);
		mediumDifficultyButton.setFocusPainted(false);
		mediumDifficultyButton.setBackground(BUTTON_BACKGROUND_COLOR);
		mediumDifficultyButton.setBounds(200, 405, 100, 100);
		add(mediumDifficultyButton);

		//setup hard difficulty button
		hardDifficultyButton = new JButton("Hard");
		hardDifficultyButton.setForeground(Color.RED);
		hardDifficultyButton.setFont(new Font("Century Gothic", Font.BOLD, 16));
		hardDifficultyButton.setRolloverEnabled(false);
		hardDifficultyButton.setFocusPainted(false);
		hardDifficultyButton.setBackground(BUTTON_BACKGROUND_COLOR);
		hardDifficultyButton.setBounds(350, 405, 100, 100);
		add(hardDifficultyButton);

	}

	//this method adds action listeners to GUI elements
	private void addActionListeners() {

		//add action listeners for all buttons
		whiteColourButton.addActionListener(this);
		blackColourButton.addActionListener(this);
		easyDifficultyButton.addActionListener(this);
		hardDifficultyButton.addActionListener(this);
		mediumDifficultyButton.addActionListener(this);

	}

	//detects actions performed on GUI elements and performs appropriate actions
	@Override
	public void actionPerformed(ActionEvent event) {

		//if white colour button is clicked
		if (event.getSource() == whiteColourButton) {

			//set colour
			colourWhite = true;

			//update label
			chooseColourLabel.setText("Player Colour: White");
		}

		//if black colour button is clicked
		else if (event.getSource() == blackColourButton) {

			//set colour
			colourWhite = false;

			//update label
			chooseColourLabel.setText("Player Colour: Black");
		}

		//if easy difficulty button is clicked
		if (event.getSource() == easyDifficultyButton) {

			//set difficulty
			aiDifficulty = 0;

			//update label
			difficultyLabel.setText("AI Difficulty: Easy");
		}

		//if medium difficulty button is clicked
		else if (event.getSource() == mediumDifficultyButton) {

			//set difficulty
			aiDifficulty = 1;

			//update label
			difficultyLabel.setText("AI Difficulty: Medium");
		}

		//if hard difficulty button is clicked
		else if (event.getSource() == hardDifficultyButton) {

			//set difficulty
			aiDifficulty = 2;

			//update label
			difficultyLabel.setText("AI Difficulty: Hard");
		}

	}

	//getters
	public int getAiDifficulty() {
		return aiDifficulty;
	}

	public boolean isColourWhite() {
		return colourWhite;
	}

}
