/*
 * Author: Jack Song
 * 
 * This class is a JPanel class that creates a panel to display choices for the user to choose which piece to promote to.
 * 
 * Created with the help of WindowBuilder.
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

public class ChessTutorPawnPromotionScreenGUI extends JPanel implements ActionListener {

	//Fields
	//GUI - JButtons
	private JButton promotionPieceButtons[] = new JButton[4];
	
	//user selection choice
	private int selectionNumber=0;		//default is 0 -- queen
	
	//colour constants
	private final Color BACKGROUND_COLOR = new Color(34, 36, 38);
	private final Color TEXT_COLOR = new Color(245, 247, 250);

	//constructor
	public ChessTutorPawnPromotionScreenGUI() {
		
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
		setPreferredSize(new Dimension(500,500));
		setBackground(BACKGROUND_COLOR);
		setLayout(null);
		
		//set it to visible
		setVisible(true);
	}

	//this method initializes all the GUI elements
	private void initElements() {
		
		//set general properties for each button
		for(int buttonIndex=0; buttonIndex<promotionPieceButtons.length; buttonIndex++) {
			promotionPieceButtons[buttonIndex] = new JButton();
			promotionPieceButtons[buttonIndex].setBackground(BACKGROUND_COLOR);
			promotionPieceButtons[buttonIndex].setBorder(new LineBorder(TEXT_COLOR));
			promotionPieceButtons[buttonIndex].setFocusPainted(false);
			promotionPieceButtons[buttonIndex].setRolloverEnabled(false);
			add(promotionPieceButtons[buttonIndex]);
		}
		
		//set specific properties
		//setbounds
		promotionPieceButtons[0].setBounds(0, 0, 250, 250);
		promotionPieceButtons[1].setBounds(250, 0, 250, 250);
		promotionPieceButtons[2].setBounds(0, 250, 250, 250);
		promotionPieceButtons[3].setBounds(250, 250, 250, 250);
		
		//seticon
		promotionPieceButtons[0].setIcon(new ImageIcon("images/WhiteQueen.png"));
		promotionPieceButtons[1].setIcon(new ImageIcon("images/WhiteRook.png"));
		promotionPieceButtons[2].setIcon(new ImageIcon("images/WhiteKnight.png"));
		promotionPieceButtons[3].setIcon(new ImageIcon("images/WhiteBishop.png"));
	}
		

	//this method adds action listeners to GUI elements
	private void addActionListeners() {

		//add actionlisteners for all buttons
		for(JButton currentButton: promotionPieceButtons) 
			currentButton.addActionListener(this);
		
	}

	//detects actions performed on GUI elements and performs appropriate actions
	@Override
	public void actionPerformed(ActionEvent event) {

		//check which button is clicked
		for(int buttonIndex=0; buttonIndex<promotionPieceButtons.length; buttonIndex++) {
			if(event.getSource()==promotionPieceButtons[buttonIndex]) {
				
				//set button number
				selectionNumber=buttonIndex;
				
				break;
			}
		}
			
	}

	//getter to get which button the user clicked
	public int getSelectionNumber() {
		
		return selectionNumber;
	}
	
	
}
