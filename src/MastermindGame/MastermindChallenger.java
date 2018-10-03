package MastermindGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import util.GameUtilitaire;

public class MastermindChallenger extends Mastermind {
	/*
	 * mode challenger du Jeu de Mastermind
	 */
	public void mastermindChallenger(int developer) {
		GameUtilitaire gameUtil = new GameUtilitaire();
		Properties prop = new Properties();
		InputStream input = null;

		int userValue, RNRP, RNWP, exponant, responseToFind, essai, indexMax, numberAllowed;

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
		logger.info("Mastermind Mode Challenger");

		System.out.println("Bienvenue dans le mode challenger du Mastermind\n"
				+ "Le but : découvrir la combinaison à x chiffres votre adversaire"
				+ "\nvotre adversaire indique pour chaque proposition "
				+ "le nombre de chiffre de la proposition qui apparaissent "
				+ "à la bonne place et à la mauvaise place dans la combinaison secrète.");

		essai = Integer.parseInt(prop.getProperty("essai"));
		indexMax = Integer.parseInt(prop.getProperty("case"));
		numberAllowed = Integer.parseInt(prop.getProperty("numberAllowed"));

		ArrayList<Integer> userValueList = new ArrayList<Integer>();
		ArrayList<Integer> userValueCheckList = new ArrayList<Integer>();
		ArrayList<Integer> responseToFindList = new ArrayList<Integer>();
		ArrayList<Integer> responseToFindCheckList = new ArrayList<Integer>();

		for (int index = 0; index < indexMax; index++) {
			userValueList.add(0);
			responseToFindList.add(0);
			userValueCheckList.add(0);
			responseToFindCheckList.add(0);
		}
		responseToFind = gameUtil.generateRandomNumberMastermind(indexMax, numberAllowed);
		MChallenger: while (essai != 0) {
			RNRP = 0;
			RNWP = 0;
			// RNRP = Right Number Right Place / RNWP = Right Number Wrong Place

			System.out.println("Nombre d'essai restant : " + essai);
			if (developer == 1) {
				System.out.println("" + "La Reponse Du Jeu est : " + responseToFind);
			}
			userValue = gameUtil.inputUserValueMastermind(indexMax, numberAllowed);

			exponant = (int) Math.pow(10, indexMax - 1);
			for (int index = 0; index < indexMax; index++) {
				userValueList.set(index, userValue / exponant % 10);
				responseToFindList.set(index, responseToFind / exponant % 10);

				exponant = exponant / 10;
			}

			for (int index = 0; index < indexMax; index++) {
				userValueCheckList.set(index, userValueList.get(index));
				responseToFindCheckList.set(index, responseToFindList.get(index));
				if (responseToFindCheckList.get(index) == userValueCheckList.get(index)) {
					RNRP++;
					userValueCheckList.set(index, -1);
					responseToFindCheckList.set(index, -2);
				}
			}
			for (int index = 0; index < indexMax; index++) {
				if (responseToFindCheckList.contains(userValueCheckList.get(index))) {
					RNWP++;
				}

			}

			if (RNRP == indexMax) {
				System.out.println("Bien placé : " + RNRP + " !!!! \nBravo vous avez réussi, vous avez gagner!!!");
				break MChallenger;
			} else {
				System.out.println("Bien placé : " + RNRP + "\nBien Présent : " + RNWP);
			}
			essai--;
		}
		if (essai == 0) {
			System.out.println("Vous avez perdu!\nVous n'avez pas réussi a trouver la bonne combinaison à temps! "
					+ "La réponse est : " + responseToFind);

		}
	}
}
