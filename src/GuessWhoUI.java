//GuessWhoUI
//UI that contains 24 images, one button, two combo boxes, and one label  

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.util.Random;


public class GuessWhoUI extends JFrame implements ActionListener{
	//member variables
	int selectedPerson;
	//guesses remaining
	int guessesRemaining;
	//difficulty level
	int gamePlayNumberOfGuesses;
	//cboTrait's options
	String[] traits = new String[] {"Wearing Hat?", "Wearing Glasses?", "Is Male?", "Has Facial Hair?", 
			"Is Bald?", "Has Brown Hair?", "Has Black Hair?", "Has Blonde Hair?", "Has Red Hair?", 
			"Has White Hair?"};

	//create array of 24 person objects called people
	Person[] people = new Person[24];
	
	//create jframe objects
	
	//guess button
	JButton btnGuess = new JButton("Guess");
	
	//combo boxes for guessing
	//combo box containing the name guess options
	JComboBox<String> cboName = new JComboBox<>();
	//combo box containing the trait guess options
	JComboBox<String> cboTrait = new JComboBox<>();
	
	//label displaying available guesses
	JLabel lblStatus = new JLabel("Guesses Left: " + guessesRemaining);
	
	//south panel to window
	JPanel pnlSouth = new JPanel();
	//center panel to window
	JPanel pnlCenter = new JPanel();
	
	
	//main
	public static void main(String[] args) {
		//call constructor
		new ExplinationUI();	
	} // end main
	
	
	public GuessWhoUI(int numberOfGuesses){
		//window title
		super("Guess Who");
		
		gamePlayNumberOfGuesses = numberOfGuesses;
		
		//read GuessWho.txt
		//parse text file with scanner
		FileInputStream fIn;
		try {
			//try to read in file
			fIn = new FileInputStream("GuessWho.txt");
			//parse file with scanner
			Scanner scanner = new Scanner(fIn);
			int i = 0;
		    while(scanner.hasNext()){
		    	//scanner reads in lines and splits them where there are ','s
		    	String[] attributes = scanner.nextLine().split(",");
		    	//each person is sent strings as the are read in
		    	people[i++] = new Person(attributes);
		    }
		    scanner.close();
		} 
		catch (FileNotFoundException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
		
		//create jframe window p with border layout
		Container p = this.getContentPane();
		p.setLayout(new BorderLayout());
		
		//jpanel pnlCenter created with gird layout
		pnlCenter.setLayout(new GridLayout(0,6));
		for (int i = 0; i < people.length; i++) {
			//24 jlabels are created and given an image
			JLabel lbl = new JLabel("");
			lbl.setName(people[i].getName());
			pnlCenter.add(lbl);
			Image img = new ImageIcon(this.getClass().getResource(people[i].getName() + ".png")).getImage();
			lbl.setIcon(new ImageIcon(img));
		}
		//add pnlCenter to the center of p
		p.add(pnlCenter, BorderLayout.CENTER);
		
		//jpanel pnlSouth created with gird layout
		pnlSouth.setLayout(new GridLayout(0,4));
		//add button, combo boxes, and status label to pnlSouth
		pnlSouth.add(btnGuess);
		btnGuess.addActionListener(this);
		pnlSouth.add(cboName);
		pnlSouth.add(cboTrait);
		pnlSouth.add(lblStatus);
		
		startNewGame();
		
		//add pnlSouth to the south border of p
		p.add(pnlSouth, BorderLayout.SOUTH);
		
		this.pack();
		this.setSize(750,700);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//establish random person player will try to guess
		chooseSelectedPerson();
	}
	
	private void startNewGame(){
		//reset number of guesses remaining
		guessesRemaining = gamePlayNumberOfGuesses; 
		lblStatus.setText("Guesses Left: " + guessesRemaining);
		
		//new random correct person
		chooseSelectedPerson();
		
		//set all images to visible
        for (Component child : pnlCenter.getComponents()){
            if (child instanceof JLabel){
                JLabel label = (JLabel)child;
                for(int i = 0; i < people.length; i++){
                	//if the name associated with an image is equal to name sent to function,
                	//set image to invisible
                	label.setVisible(true);
                }
            }
        }
		
        
		//populate combo boxes
		//clears cboName's contents
		cboName.removeAllItems();
		//adds blank option to cboName
		cboName.addItem("");
		for (int i = 0; i < people.length; i++) {
			//24 combo box options are created
			cboName.addItem(String.valueOf(people[i].getName()));
		}
		cboTrait.setVisible(true);
		//clears cboTraits's contents
		cboTrait.removeAllItems();
		//adds blank option to cboTrait
		cboTrait.addItem("");
		for (int i = 0; i < traits.length; i++) {
			//24 combo box options are created
			cboTrait.addItem(String.valueOf(traits[i]));
		}
		
		if(guessesRemaining == 1){
			cboTrait.setVisible(false);
		}
	}
	
	private void updateGuessesRemaining(){
		guessesRemaining--;
		lblStatus.setText("Guesses Left: " + guessesRemaining);
		if(guessesRemaining == 1){
			cboTrait.setVisible(false);
		}
		else if(guessesRemaining == 0){
			int reply = JOptionPane.showConfirmDialog(null,"You ran out of guesses. "
					+ "Sorry, you lose. The right person was " 
					+ people[selectedPerson].getName() +  ". Would you like to play again?",
					"YOU LOSE",JOptionPane.YES_NO_OPTION);
			
			if (reply == JOptionPane.YES_OPTION){
				//user desires to play again
				startNewGame();
            }
			else{
				//user wants to quit
                System.exit(0);
            }
		}	
	}
	
	
	private void removeNameGuess(String name){
		//function to remove options in cboName
		for(int i = 0; i < cboName.getItemCount(); i++){
			if(name.equals(cboName.getItemAt(i))){
				//for each name in cboName: if name in cboName is equal to name sent to function,
				//remove specific name option from cboName 
				cboName.removeItemAt(i);
				cboName.setSelectedIndex(0);
				return;
			}
		}
	
	}
	
	private void removeTraitGuess(String trait){
		//function to remove options in cboTrait
		for(int i = 0; i < cboTrait.getItemCount(); i++){
			if(trait.equals(cboTrait.getItemAt(i))){
				//for each string in cboTrait: if string in cboName is equal to trait sent to function,
				//remove specific trait option from cboTrait 
				cboTrait.removeItemAt(i);
				cboTrait.setSelectedIndex(0);
				return;
			}
		}
	
	}
	
	
	private void makeInvisible(String personName){
		//function to set eliminated images to invisible
        for (Component child : pnlCenter.getComponents()){
            if (child instanceof JLabel){
                JLabel label = (JLabel)child;
                if (label.getName().equals(personName)){
                	//if the name associated with an image is equal to name sent to function,
                	//set image to invisible
                	label.setVisible(false);
                	return;
                }
            }
        }
	}
	
	
	public void chooseSelectedPerson(){
		//at beginning of game, randomly select an int between 0 and 23
		//int will be used to identify a person in people array
         Random random = new Random();
         selectedPerson = random.nextInt(people.length - 1);
	}
	
	
	public void actionPerformed(ActionEvent e){
		//if button is pressed:
		if(("" == (String)cboName.getSelectedItem()) && ("" == (String)cboTrait.getSelectedItem())){
			//if nothing is selected in cboName or cboTrait
			JOptionPane.showMessageDialog(null,"Please select EITHER a Trait or a Name to guess."
					+ "","GUESS ERROR",JOptionPane.WARNING_MESSAGE);
		}
		else if("" == (String)cboName.getSelectedItem()){
			//if nothing is selected in cboName
			String traitGuess = (String)cboTrait.getSelectedItem();
			//traitguess = string selected in cboTrait
			if("Wearing Hat?" == traitGuess){
				//if Wearing Hat? is guessed:
				Boolean removePeopleWearingHats;
				//create bool to use in comparisons
				removeTraitGuess(traitGuess);
				//remove Wearing Hat? option
				if(people[selectedPerson].getHat().equals(true)){
					//if selected person is wearing a hat
					removePeopleWearingHats = false;
					//set removePeopleWearingHats to false
				}
				else{
					//if selected person is not wearing a hat
					removePeopleWearingHats = true;
					//set removePeopleWearingHats to true
				}
				for(int i = 0; i < people.length; i++){
					if(people[i].getHat().equals(removePeopleWearingHats)){
						//if person objects in people array have same getHat bool as removePeopleWearingHats
						makeInvisible(people[i].getName());
						removeNameGuess(people[i].getName());
						//make person objects' images invisible and remove their cboName option
					}
				}
			}
			else if("Wearing Glasses?" == traitGuess){
				//if Wearing Glasses? is guessed:
				Boolean removePeopleWearingGlasses;
				//create bool to use in comparisons
				removeTraitGuess(traitGuess);
				//remove Wearing Glasses? option
				if(people[selectedPerson].getGlasses().equals(true)){
					//if selected person is wearing glasses
					removePeopleWearingGlasses = false;
					//set removePeopleWearingGlasses to false
				}
				else{
					//if selected person is not wearing glasses
					removePeopleWearingGlasses = true;
					//set removePeopleWearingGlasses to true
				}
				for(int i = 0; i < people.length; i++){
					if(people[i].getGlasses().equals(removePeopleWearingGlasses)){
						//if person objects in people array have same getGlasses bool as removePeopleWearingGlasses
						makeInvisible(people[i].getName());
						removeNameGuess(people[i].getName());
						//make person objects' images invisible and remove their cboName option
					}
				}
			}
			else if("Is Male?" == traitGuess){
				//if Is Male? is guessed:
				Boolean removeMales;
				//create bool to use in comparisons
				removeTraitGuess(traitGuess);
				//remove Is Male? option
				if(people[selectedPerson].getGender().equals(true)){
					//if selected person is male
					removeMales = false;
					//set removeMales to false
				}
				else{
					//if selected person is female
					removeMales = true;
					//set removeMales to true
				}
				for(int i = 0; i < people.length; i++){
					if(people[i].getGender().equals(removeMales)){
						//if person objects in people array have same getGender bool as removeMales
						makeInvisible(people[i].getName());
						removeNameGuess(people[i].getName());
						//make person objects' image invisible and remove their cboName option
					}
				}
			}
			else if("Has Facial Hair?" == traitGuess){
				//if Has Facial Hair? is guessed:
				Boolean removePeopleWithFacialHair;
				//create bool to use in comparisons
				removeTraitGuess(traitGuess);
				//remove Has Facial Hair? option
				if(people[selectedPerson].getFacialHair().equals(true)){
					//if selected person has facial hair
					removePeopleWithFacialHair = false;
					//set removePeopleWithFacialHair to false
				}
				else{
					//if selected person does not have facial hair
					removePeopleWithFacialHair = true;
					//set removePeopleWithFacialHair to true
				}
				for(int i = 0; i < people.length; i++){
					if(people[i].getFacialHair().equals(removePeopleWithFacialHair)){
						//if person objects in people array have same getFacialHair bool as removePeopleWithFacialHair
						makeInvisible(people[i].getName());
						removeNameGuess(people[i].getName());
						//make person objects' image invisible and remove their cboName option
					}
				}
			}
			else if("Is Bald?" == traitGuess){
				//if Is Bald? is guessed:
				Boolean removeBaldPeople;
				//create bool to use in comparisons
				removeTraitGuess(traitGuess);
				//remove Is Bald? option
				if(people[selectedPerson].getBald().equals(true)){
					//if selected person is bald
					removeBaldPeople = false;
					//set removeBaldPeople to false
				}
				else{
					//user choose glasses, but selected person is not bald
					removeBaldPeople = true;
					//set removeBaldPeople to true
				}
				for(int i = 0; i < people.length; i++){
					if(people[i].getBald().equals(removeBaldPeople)){
						//if person objects in people array have same getBald bool as removeBaldPeople
						makeInvisible(people[i].getName());
						removeNameGuess(people[i].getName());
						//make person objects' image invisible and remove their cboName option
					}
				}
			}
			else if("Has Brown Hair?" == traitGuess){
				//if Has Brown Hair? is guessed:
				Boolean removePeopleWithBrownHair;
				//create bool to use in comparisons
				removeTraitGuess(traitGuess);
				//remove Has Brown Hair? option
				if(people[selectedPerson].getHairColor().toUpperCase().equals("BROWN HAIR")){
					//if selected person has brown hair
					removePeopleWithBrownHair = false;
					//set removePeopleWithBrownHair to false
				}
				else{
					//if selected person does not have brown hair
					removePeopleWithBrownHair = true;
					//set removePeopleWithBrownHair to true
				}
				for(int i = 0; i < people.length; i++){
					if(removePeopleWithBrownHair == people[i].getHairColor().toUpperCase().equals("BROWN HAIR")){
						//if person objects in people array have same getHairColor bool as removePeopleWithBrownHair
						makeInvisible(people[i].getName());
						removeNameGuess(people[i].getName());
						//make person objects' image invisible and remove their cboName option
					}
				}
			}
			else if("Has Black Hair?" == traitGuess){
				//if Has Black Hair? is guessed:
				Boolean removePeopleWithBlackHair;
				//create bool to use in comparisons
				removeTraitGuess(traitGuess);
				//remove Has Black Hair? option
				if(people[selectedPerson].getHairColor().toUpperCase().equals("BLACK HAIR")){
					//if selected person has black hair
					removePeopleWithBlackHair = false;
					//set removePeopleWithBlackHair to false
				}
				else{
					//if selected person does not have black hair
					removePeopleWithBlackHair = true;
					//set removePeopleWithBlackHair to true
				}
				for(int i = 0; i < people.length; i++){
					if(removePeopleWithBlackHair == people[i].getHairColor().toUpperCase().equals("BLACK HAIR")){
						//if person objects in people array have same getHairColor bool as removePeopleWithBlackHair
						makeInvisible(people[i].getName());
						removeNameGuess(people[i].getName());
						//make person objects' image invisible and remove their cboName option
					}
				}
			}
			else if("Has Blonde Hair?" == traitGuess){
				//if Has Blonde Hair? is guessed:
				Boolean removePeopleWithBlondeHair;
				//create bool to use in comparisons
				removeTraitGuess(traitGuess);
				//remove Has Blonde Hair? option
				if(people[selectedPerson].getHairColor().toUpperCase().equals("BLONDE HAIR")){
					//if selected person has blonde hair
					removePeopleWithBlondeHair = false;
					//set removePeopleWithBlondeHair to false
				}
				else{
					//if selected person has blonde hair
					removePeopleWithBlondeHair = true;
					//set removePeopleWithBlondeHair to true
				}
				for(int i = 0; i < people.length; i++){
					if(removePeopleWithBlondeHair == people[i].getHairColor().toUpperCase().equals("BLONDE HAIR")){
						//if person objects in people array have same getHairColor bool as removePeopleWithBlondeHair
						makeInvisible(people[i].getName());
						removeNameGuess(people[i].getName());
						//make person objects' image invisible and remove their cboName option
					}
				}
			}
			else if("Has Red Hair?" == traitGuess){
				//if Has Red Hair? is guessed:
				Boolean removePeopleWithRedHair;
				//create bool to use in comparisons
				removeTraitGuess(traitGuess);
				//remove Has Red Hair? option
				if(people[selectedPerson].getHairColor().toUpperCase().equals("RED HAIR")){
					//if selected person has red hair
					removePeopleWithRedHair = false;
					//set removePeopleWithRedHair to false
				}
				else{
					//if selected person has red hair
					removePeopleWithRedHair = true;
					//set removePeopleWithRedHair to true
				}
				for(int i = 0; i < people.length; i++){
					if(removePeopleWithRedHair == people[i].getHairColor().toUpperCase().equals("RED HAIR")){
						//if person objects in people array have same getHairColor bool as removePeopleWithRedHair
						makeInvisible(people[i].getName());
						removeNameGuess(people[i].getName());
						//make person objects' image invisible and remove their cboName option
					}
				}
			}
			else{
				//Has White Hair?
				//if Has White Hair? is guessed:
				Boolean removePeopleWithWhiteHair;
				//create bool to use in comparisons
				removeTraitGuess(traitGuess);
				//remove Has White Hair? option
				if(people[selectedPerson].getHairColor().toUpperCase().equals("WHITE HAIR")){
					//if selected person has white hair
					removePeopleWithWhiteHair = false;
					//set removePeopleWithWhiteHair to false
				}
				else{
					//if selected person has white hair
					removePeopleWithWhiteHair = true;
					//set removePeopleWithWhiteHair to true
				}
				for(int i = 0; i < people.length; i++){
					if(removePeopleWithWhiteHair == people[i].getHairColor().toUpperCase().equals("WHITE HAIR")){
						//if person objects in people array have same bool as removePeopleWithWhiteHair
						makeInvisible(people[i].getName());
						removeNameGuess(people[i].getName());
						//make person objects' image invisible and remove their cboName option
					}
				}
			}
			//-1 from remaining guesses
			updateGuessesRemaining();
		}
		else if("" == (String)cboTrait.getSelectedItem()){
			//if nothing is selected in cboTrait
			String nameGuess = (String)cboName.getSelectedItem();
			//nameGuess = string selected in cboTrait
			if(people[selectedPerson].getName().equals(nameGuess)){
				//if selected person has the selected name
				int reply = JOptionPane.showConfirmDialog(null,
						nameGuess + " is the correct person. Congratulations, YOU WIN! "
								+ "Would you like to play again?","YOU WIN",
						JOptionPane.YES_NO_OPTION);
				//player wins game
				if (reply == JOptionPane.YES_OPTION){
					//user desires to play again
					startNewGame();
	            }
				else{
					//user wants to quit
	                System.exit(0);
	            }
			}
			else{
				//if selected person does not have the selected name
				removeNameGuess(nameGuess);
				makeInvisible(nameGuess);
				//make person objects' image invisible and remove their cboName option
				//-1 from remaining guesses
				updateGuessesRemaining();
			}
		}
		else{
			//both a cboTrait option and a cboName option is selected
			JOptionPane.showMessageDialog(null,"Please select EITHER a Trait or a Name to guess.",
					"GUESS ERROR",JOptionPane.WARNING_MESSAGE);
		}
	}
}


