package MastermindGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import util.GameUtilitaire;

public class MastermindDefense2 extends Mastermind {
	public void mastermindDefense() {
		Random random = new Random();
		GameUtilitaire gameUtil = new GameUtilitaire();
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream("config.properties");
		}
		catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		try {
			prop.load(input);
		}
		catch (IOException e2) {
			e2.printStackTrace();
		}
		logger.info("Mastermind Mode Defense");
		
		int saisieUtilisateurInt, responseProgram, responseProgramSplit, min, max,index,indexMax,essai,numberAllowed,indexNumber,
		addition,changeValuePlus,responseValueUser,changeValueMinus,exponant,indexCheck;
		numberAllowed = Integer.parseInt(prop.getProperty("numberAllowed"));
		indexMax = Integer.parseInt(prop.getProperty("case"));
		
		saisieUtilisateurInt = gameUtil.inputUserValueMastermind(indexMax, numberAllowed);

//		responseProgram = gameUtil.generateRandomNumberMastermind(indexMax,numberAllowed);
		responseProgram = 1234;

		essai = Integer.parseInt(prop.getProperty("essai"));

		ArrayList<Integer> userValueToFindList = new ArrayList<Integer>();
		ArrayList<Integer> userValueToFindCheckList = new ArrayList<Integer>();
		
		ArrayList<Integer> responseProgramList = new ArrayList<Integer>();
		ArrayList<Integer> responseProgramCheckList = new ArrayList<Integer>();

		ArrayList<Integer> guessResponse = new ArrayList<Integer>();
		ArrayList<Integer> allPossibilities = new ArrayList<Integer>();
		ArrayList<Integer> allPossibilitiesCheck = new ArrayList<Integer>();
		
		
		int smallestValue = gameUtil.startAllChoice(indexMax);
		int biggestValue = gameUtil.endAllChoice(indexMax, numberAllowed);
		for (index = smallestValue; index < biggestValue; index++ ) {
			allPossibilities.add(index);
		}
//		for (index = smallestValue; index < biggestValue-smallestValue; index++ ) {
//			System.out.println(allPossibilities.get(index));
//		}
		for (indexNumber = 0; indexNumber < indexMax; indexNumber++) {
			allPossibilitiesCheck.add(0);
			userValueToFindList.add(0);
			userValueToFindCheckList.add(0);
			responseProgramList.add(0);
			responseProgramCheckList.add(0);
		}

		MDefense : while (essai != 0) {
			
			System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + "\nNombre d'essai restant : " + essai);
			int RNRP = 0, RNWP = 0,CRNRP,CRNWP;
			// RNRP = Right Number Right Place / RNWP = Right Number Wrong Place / CRNRP = Check Right Number Right Place / CRNWP = Check Right Number Wrong Place
			exponant = (int) Math.pow(10, indexMax-1);
			for (index = 0; index < indexMax ; index++) {
				responseProgramList.set(index, responseProgram / exponant % 10);
				userValueToFindList.set(index, saisieUtilisateurInt/exponant % 10);
				exponant = exponant / 10;
			}

			for (index = 0; index < indexMax; index++) {
				
				responseProgramCheckList.set(index, responseProgramList.get(index));
				userValueToFindCheckList.set(index, userValueToFindList.get(index));
			}
			
			for (index = 0; index < indexMax; index++) {
				if (responseProgramCheckList.get(index) == userValueToFindCheckList.get(index)) {
					RNRP++;
					responseProgramCheckList.set(index, -1);
					userValueToFindCheckList.set(index, -2);
				}
			}
			
			for (index = 0; index < indexMax; index++) {
				if (responseProgramCheckList.contains(userValueToFindCheckList.get(index))) {
					userValueToFindCheckList.set(index, -2);
					RNWP++;
				}
			}
			
			if (RNRP == indexMax) {
				System.out.println("La Reponse de l'ordinateur : " + responseProgram + "\nLa Solution : "
						+ saisieUtilisateurInt + "\nBien placé : " + RNRP
						+ " !!!! \nVous avez perdu L'Ordinateur a trouvé la réponse!!!");
				break MDefense;
			} else {
				System.out.println("La Reponse de l'ordinateur : " + responseProgram + "\nLa Solution : "
						+ saisieUtilisateurInt + "\nBien placé : " + RNRP + "\nBien Présent : " + RNWP);
			
				for(indexCheck = 0; indexCheck < allPossibilities.size();indexCheck++) {
					
					for (index = 0; index < indexMax; index++) {
						
						userValueToFindCheckList.set(index, userValueToFindList.get(index));
						
					}
					
					CRNRP = 0; CRNWP=0;
					for (index = 0; index < indexMax;index++) {
						
					}
					int allPossibilityListValue = allPossibilities.get(indexCheck);
					
					exponant = (int) Math.pow(10, indexMax-1);
					for (index = 0; index < indexMax ; index++) {
						allPossibilitiesCheck.set(index, allPossibilityListValue / exponant % 10);
						responseProgramList.set(index, responseProgram / exponant % 10);
						exponant = exponant / 10;
					}
					
					for (index = 0; index < indexMax; index++) {
						if (allPossibilitiesCheck.get(index) == responseProgramList.get(index)) {
							CRNRP++;
							allPossibilitiesCheck.set(index, -1);
							responseProgramList.set(index, -2);
						}
					}
					
					for (index = 0; index < indexMax; index++) {
						if (allPossibilitiesCheck.contains(responseProgramList.get(index))) {
							CRNWP++;
							responseProgramList.set(index, -2);
						}
					}
					if (CRNWP == RNWP && CRNRP == RNRP) {
						guessResponse.add(allPossibilityListValue);
					}
				}
				if(guessResponse.isEmpty()) {
					System.out.println("cest vide ");
				}else {
					
					responseProgram = guessResponse.get(0);
				}

				allPossibilities.clear();
				for (index = 0; index < guessResponse.size();index++) {
					allPossibilities.add(0);
					allPossibilities.set(index, guessResponse.get(index));
				}
				guessResponse.clear();
			}
				

			essai--;

		}

	}
}
