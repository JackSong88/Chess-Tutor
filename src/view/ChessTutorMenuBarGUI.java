/* Author: Jack Song
 * 
 * This class is a GUI MenuBar class. The menubar can be added to any screen and has various menus and menuitems that 
 * can perform certain functions
 * 
 * Created with the help of WindowBuilder.
 */

package view;

//imports
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.LineBorder;

public class ChessTutorMenuBarGUI extends JMenuBar implements ActionListener{
	
	//Fields
	
	//Create menus
	private JMenu fileMenu = new JMenu("File");
	private JMenu helpMenu = new JMenu("Help");
	
	//create menu items
	private JMenuItem exitMenuItem = new JMenuItem("Exit");
	private JMenuItem homeMenuItem = new JMenuItem("Home");
	private JMenuItem userHelpMenuItem = new JMenuItem("User Help");
	
	//create colours
	private final Color BACKGROUND_COLOR = new Color(34, 36, 38);
	private final Color BORDER_COLOR = new Color(110, 110, 110);
	
	//constructor
	public ChessTutorMenuBarGUI() {
		
		//remove menu border
		setBorder(null);
		
		//Set Colours
		//menubar background
		setBackground(BACKGROUND_COLOR);
		
		//menubar border
		setBorder(new LineBorder(BORDER_COLOR));
		
		//menus foregrounds
		fileMenu.setForeground(Color.WHITE);
		helpMenu.setForeground(Color.WHITE);
		
		//menuitems background and foreground
		exitMenuItem.setBackground(BACKGROUND_COLOR);
		homeMenuItem.setBackground(BACKGROUND_COLOR);
		userHelpMenuItem.setBackground(BACKGROUND_COLOR);
		
		exitMenuItem.setForeground(Color.WHITE);
		homeMenuItem.setForeground(Color.WHITE);
		userHelpMenuItem.setForeground(Color.WHITE);
		
		//add Action listeners
		exitMenuItem.addActionListener(this);
		homeMenuItem.addActionListener(this);
		userHelpMenuItem.addActionListener(this);
		
		//add menu items to their respective JMenus
		fileMenu.add(homeMenuItem);
		fileMenu.add(exitMenuItem);
		helpMenu.add(userHelpMenuItem);
		
		//add the JMenus to the menu Bar
		add(fileMenu);
		add(helpMenu);
	}
	
	//action performed method, detects any action
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//If user clicks exit menu item, close the program
		if(event.getSource()==exitMenuItem) {
			
			System.exit(0);
		
		//if user clicks sign out menu item, bring user to sign in screen.
		} else if(event.getSource()==homeMenuItem) {
			
			//close the frame the menubar is added to
			getTopLevelAncestor().setVisible(false);
			((Window) getTopLevelAncestor()).dispose();
			
			//new login screen
			new ChessTutorHomeScreenGUI();
		
		//If user clicks user help menu item, open help screen 
		} else if(event.getSource()==userHelpMenuItem) {
			
			//new Help Screen
			new ChessTutorHelpScreenGUI();
		}
	}
	
}
