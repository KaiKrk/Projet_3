package MastermindGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import util.GameUtilitaire;

/*
 * mode defenseur du Jeu de Mastermind
 */
public class MastermindDefense extends Mastermind {
	public void mastermindDefense() {
		GameUtilitaire gameUtil = new GameUtilitaire();
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}

		try {
			prop.load(input);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		logger.info("Mastermind Mode Defense");

		int saisieUtilisateurInt, responseProgram, responseProgramSplit, min, max, index, indexMax, essai,
				numberAllowed, indexNumber, addition, changeValuePlus, responseValueUser, changeValueMinus;
		numberAllowed = Integer.parseInt(prop.getProperty("numberAllowed"));
		indexMax = Integer.parseInt(prop.getProperty("case"));

		saisieUtilisateurInt = gameUtil.inputUserValueMastermind(indexMax, numberAllowed);

		responseProgram = gameUtil.generateRandomNumberMastermind(indexMax, numberAllowed);

		essai = Integer.parseInt(prop.getProperty("essai"));

		ArrayList<Integer> maxList = new ArrayList<Integer>();
		ArrayList<Integer> minList = new ArrayList<Integer>();
		ArrayList<Integer> userValueToFindList = new ArrayList<Integer>();
		ArrayList<Integer> userValueToFindCheckList = new ArrayList<Integer>();
		ArrayList<Integer> responseProgramList = new ArrayList<Integer>();
		ArrayList<Integer> responseProgramCheckList = new ArrayList<Integer>();

		for (indexNumber = 0; indexNumber < indexMax; indexNumber++) {
			minList.add(0);
			maxList.add(10);
			userValueToFindList.add(0);
			userValueToFindCheckList.add(0);
			responseProgramList.add(0);
			responseProgramCheckList.add(0);
		}

		MDefense: while (essai != 0) {

			int RNRP = 0, RNWP = 0;
			// RNRP = Right Number Right Place / RNWP = Right Number Wrong Place
			int exponant = (int) Math.pow(10, indexMax - 1);
			for (index = 0; index < indexMax; index++) {
				responseProgramList.set(index, responseProgram / exponant % 10);
				userValueToFindList.set(index, saisieUtilisateurInt / exponant % 10);
				exponant = exponant / 10;
			}

			for (index = 0; index < indexMax; index++) {

				responseProgramSplit = responseProgramList.get(index);
				responseValueUser = userValueToFindList.get(index);

				try {

					if (responseProgramSplit < responseValueUser) {
						minList.set(index, responseProgramSplit);

						min = minList.get(index);
						max = maxList.get(index);
						changeValuePlus = gameUtil.generateRandomNumberBounds(min, max);
						responseProgramList.set(index, changeValuePlus);
					} else if (responseProgramSplit > responseValueUser) {
						maxList.set(index, responseProgramSplit);

						min = minList.get(index);
						max = maxList.get(index);
						changeValueMinus = gameUtil.generateRandomNumberBounds(min, max);
						responseProgramList.set(index, changeValueMinus);
					} else if (responseValueUser == responseProgramSplit) {
						responseProgramList.set(index, responseProgramSplit);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

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
					RNWP++;
				}
			}

			responseProgram = 0;
			for (index = 0; index < indexMax; index++) {
				addition = (int) (responseProgramList.get(index) * Math.pow(10, indexMax - 1 - index));
				responseProgram = responseProgram + addition;
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				continue;
			}

			System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + "\nNombre d'essai restant : " + essai);

			if (RNRP == indexMax) {
				System.out.println("La Reponse de l'ordinateur : " + responseProgram + "\nLa Solution : "
						+ saisieUtilisateurInt + "\nBien placé : " + RNRP
						+ " !!!! \nVous avez perdu L'Ordinateur a trouvé la réponse!!!");
				break MDefense;
			} else {
				System.out.println("La Reponse de l'ordinateur : " + responseProgram + "\nLa Solution : "
						+ saisieUtilisateurInt + "\nBien placé : " + RNRP + "\nBien Présent : " + RNWP);
				essai--;
			}
		}

	}
}
