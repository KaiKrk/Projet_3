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

		int saisieUtilisateurInt, responseProgram,index,indexMax,essai,numberAllowed,indexNumber,
				exponant,indexCheck, possibleValue;
		numberAllowed = Integer.parseInt(prop.getProperty("numberAllowed"));
		indexMax = Integer.parseInt(prop.getProperty("case"));

		saisieUtilisateurInt = gameUtil.inputUserValueMastermind(indexMax, numberAllowed);

		responseProgram = gameUtil.generateRandomNumberMastermind(indexMax,numberAllowed);

		essai = Integer.parseInt(prop.getProperty("essai"));

		ArrayList<Integer> userValueToFindList = new ArrayList<Integer>();
		ArrayList<Integer> userValueToFindCheckList = new ArrayList<Integer>();

		ArrayList<Integer> responseProgramList = new ArrayList<Integer>();
		ArrayList<Integer> responseProgramCheckList = new ArrayList<Integer>();

		ArrayList<Integer> guessResponse = new ArrayList<Integer>();
		ArrayList<Integer> allPossibilities = new ArrayList<Integer>();
		ArrayList<Integer> allPossibilitiesCheck = new ArrayList<Integer>();


// initialise toutes les list en ajoutant un 0 pour
		for (indexNumber = 0; indexNumber < indexMax; indexNumber++) {
			allPossibilitiesCheck.add(0);
			userValueToFindList.add(0);
			userValueToFindCheckList.add(0);
			responseProgramList.add(0);
			responseProgramCheckList.add(0);
//			isValueAllowed.add(0);
		}

		int smallestValue = gameUtil.startAllChoice(indexMax);
		int biggestValue = gameUtil.endAllChoice(indexMax, numberAllowed);

		for (index = smallestValue; index < biggestValue; index++ ) {
			allPossibilities.add(index);
		}
		/*
		boucle qui va supprimer des list de valeurs probables celles qui sont exclus du jeu par exemple dans un
		cas de numberAllowed = 5, supprime toutes les valeurs contenant un chiffre superieur a 5
		 */
		if (indexMax > 5){
			System.out.println("Le Programme reflechi patience patience...");
		}
		for (index =0; index < allPossibilities.size(); index++){


//
			possibleValue = allPossibilities.get(index);

			for (int i = numberAllowed +1; i < 10; i++){
				if (String.valueOf(possibleValue).contains(String.valueOf(i))){
					allPossibilities.remove(index); index--;break;
				}
			}

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
				//Grande boucle pour ajouter chaque reponse probable a la list guessResponse
				for(indexCheck = 0; indexCheck < allPossibilities.size();indexCheck++) {

					CRNRP = 0; CRNWP=0;

					int allPossibilityListValue = allPossibilities.get(indexCheck);

					exponant = (int) Math.pow(10, indexMax-1);
					for (index = 0; index < indexMax ; index++) {
						allPossibilitiesCheck.set(index, allPossibilityListValue / exponant % 10);
						responseProgramList.set(index, responseProgram / exponant % 10);
						exponant = exponant / 10;
					}
					//analyse si il y a des bien placé
					for (index = 0; index < indexMax; index++) {
						if (allPossibilitiesCheck.get(index) == responseProgramList.get(index)) {
							CRNRP++;
							allPossibilitiesCheck.set(index, -1);
							responseProgramList.set(index, -2);
						}
					}
					//analyse si il y a des bien present
					for (index = 0; index < indexMax; index++) {
						if (responseProgramList.contains(allPossibilitiesCheck.get(index))) {
							CRNWP++;
							allPossibilitiesCheck.set(index, -3);


						}
					}
					// si bien placé et bien présent sont identiques aux deux on ajoute a la list
					if (CRNRP == RNRP && CRNWP == RNWP) {
						guessResponse.add(allPossibilityListValue);
					}
				}
				responseProgram = guessResponse.get(random.nextInt(guessResponse.size()));
// reduit la liste de tout les possibles a la liste des reponses probables
				allPossibilities.clear();
				for (index = 0; index < guessResponse.size();index++) {
					allPossibilities.add(0);
				}
				for(index = 0; index < allPossibilities.size();index++) {
					allPossibilities.set(index, guessResponse.get(index));

				}
				guessResponse.clear();
			}


			essai--;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				continue;
			}
		}

	}
}