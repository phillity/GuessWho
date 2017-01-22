
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ExplinationUI extends JFrame implements ActionListener{
	//member variables
	int gamePlayNumberOfGuesses;
	
	//create jframe objects
	//play button
	JButton btnPlay = new JButton("Play");
	String text = "<html><h1>Welcome to Guess Who Game!</h1><br/><br/>"
		+ "<h2>Instructions</h2><br>There are 24 people, each with their own image.  The computer "
		+ "will randomly select one person.  It's up to the player to guess the name of the person that "
		+ " the computer has selected.  The player can eliminate people and their images by guessing names or traits."
		+ "  Trait guesses will eliminate every person that does not have the same trait as the randomly selected person."
		+ "  Name guesses will either eliminate an incorret guess or win the game."
		+ "  The player decides how many guesses they are allotted by setting the difficulty."
		+ "  When the player has one guess left, they are required to make a name guess.<html>";
	JLabel lblExplain = new JLabel("<html><div style=\"text-align: center;\">" + text + "</html>");
	
	//Difficulty radio buttons
	JRadioButton rdoEasy = new JRadioButton("Easy = 7 Guesses");
	JRadioButton rdoMedium = new JRadioButton("Medium = 5 Guesses");
	JRadioButton rdoHard = new JRadioButton("Hard = 3 Guesses");
	JRadioButton rdoPsychic = new JRadioButton("Psychic = 1 Guess");
	ButtonGroup grpDifficulty = new ButtonGroup();
		
	//add south panel to window
	JPanel pnlSouth = new JPanel();
	//add center panels to window
	JPanel pnlCenter1 = new JPanel();
	JPanel pnlCenter2 = new JPanel();
	
	public ExplinationUI(){
		//window title
		super("Guess Who Instructions");
		//create jframe window p with border layout
		Container p = this.getContentPane();
		p.setLayout(new BorderLayout());
				
		//jpanel pnlCenter created with gird layout
		pnlCenter1.setLayout(new BorderLayout());

		pnlCenter1.add(lblExplain, BorderLayout.CENTER);
		
		//add pnlCenter to the center of p
		p.add(pnlCenter1, BorderLayout.NORTH);
		
		pnlCenter2.setLayout(new FlowLayout());
		//radiobuttons to set difficulty
		//add radio buttons
		pnlCenter2.add(rdoEasy);
		pnlCenter2.add(rdoMedium);
		pnlCenter2.add(rdoHard);
		pnlCenter2.add(rdoPsychic);
		rdoEasy.setSelected(true);

		//add radio buttons to group
		grpDifficulty.add(rdoEasy);
		grpDifficulty.add(rdoMedium);
		grpDifficulty.add(rdoHard);
		grpDifficulty.add(rdoPsychic);
		
		//add pnlCenter to the center of p
		p.add(pnlCenter2, BorderLayout.CENTER);
		
		//jpanel pnlSouth created with gird layout
		pnlSouth.setLayout(new GridLayout(0,1));
		
		//add play button
		pnlSouth.add(btnPlay);
		btnPlay.addActionListener(this);
		
		//add pnlSouth to the south border of p
		p.add(pnlSouth, BorderLayout.SOUTH);
		
		this.pack();
		this.setSize(750,350);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e){
		if(rdoEasy.isSelected()){
			gamePlayNumberOfGuesses = 7;
		}
		else if(rdoMedium.isSelected()){
    		gamePlayNumberOfGuesses = 5;
    	}
		else if(rdoHard.isSelected()){
        	gamePlayNumberOfGuesses = 3;
        }
		else{
			gamePlayNumberOfGuesses = 1;
    	} // end if
		
		new GuessWhoUI(gamePlayNumberOfGuesses);
		this.dispose();
	}
}
