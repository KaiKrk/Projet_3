package researchGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import util.GameUtilitaire;

/*
 * mode duel du Jeu de Recherche
 */
public class ResearchGameDuel {
	public void reseachDuel(int developer) {
		int turn = 1, userValueInt, indexMax, reponse, responseProgram, min, max, userResponseToFind, programValue,
				exponant, responseProgramDuel, secretPasswordSplit, changeValuePlus, changeValueMinus, index, addition;

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
		// developer = Integer.parseInt(prop.getProperty("developerMod"));
		indexMax = Integer.parseInt(prop.getProperty("case"));
		int essai = Integer.parseInt(prop.getProperty("essai"));
		reponse = gameUtil.generateRandomNumber(indexMax);

		ArrayList<Integer> responseDuelList = new ArrayList<Integer>();
		ArrayList<Integer> maxListDuel = new ArrayList<Integer>();
		ArrayList<Integer> minListDuel = new ArrayList<Integer>();
		ArrayList<Integer> userValueDuelList = new ArrayList<Integer>();
		ArrayList<Integer> userResponseToFindList = new ArrayList<Integer>();
		ArrayList<Integer> programValueDuelList = new ArrayList<Integer>();

		for (index = 0; index < indexMax; index++) {

			responseDuelList.add(0);
			minListDuel.add(0);
			maxListDuel.add(10);
			userValueDuelList.add(0);
			programValueDuelList.add(0);
			userResponseToFindList.add(0);

		}
		System.out.println("Tout d'abord entrez la combinaison que l'ordinateur doit trouver");
		userResponseToFind = gameUtil.inputUserValue(indexMax);
		programValue = gameUtil.generateRandomNumber(indexMax);
		exponant = (int) Math.pow(10, indexMax - 1);

		for (index = 0; index < indexMax; index++) {

			userResponseToFindList.set(index, userResponseToFind / exponant % 10);
			responseDuelList.set(index, reponse / exponant % 10);
			programValueDuelList.set(index, programValue / exponant % 10);
			exponant = exponant / 10;

		}

		Duel: while (essai != 0) {
			System.out.println("\nTour " + turn + "\nNombre d'essai restant : " + essai);
			if (developer == 1) {
				System.out.println(" La Reponse Du Jeu est : " + reponse);
			}
			userValueInt = gameUtil.inputUserValue(indexMax);

			exponant = (int) Math.pow(10, indexMax - 1);
			for (index = 0; index < indexMax; index++) {
				userValueDuelList.set(index, userValueInt / exponant % 10);
				exponant = exponant / 10;
			}

			System.out.print("Voici l'indice : ");
			for (index = 0; index < indexMax; index++) {

				int reponseBis = responseDuelList.get(index);
				int userValueBis = userValueDuelList.get(index);
				if (userValueBis < reponseBis) {
					System.out.print("+");
				} else if (userValueBis > reponseBis) {
					System.out.print("-");
				} else
					System.out.print("=");
			}

			if (userValueDuelList.equals(responseDuelList)) {
				System.out.println("\nBravo vous avez gagner !!!");
				break Duel;
			} else {

				try {
					Thread.sleep(350);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (index = 0; index < indexMax; index++) {
					responseProgram = programValueDuelList.get(index);
					secretPasswordSplit = userResponseToFindList.get(index);

					if (responseProgram < secretPasswordSplit) {
						minListDuel.set(index, responseProgram);

						min = minListDuel.get(index);
						max = maxListDuel.get(index);
						changeValuePlus = gameUtil.generateRandomNumberBounds(min, max);
						programValueDuelList.set(index, changeValuePlus);
					} else if (responseProgram > secretPasswordSplit) {
						maxListDuel.set(index, responseProgram);

						min = minListDuel.get(index);
						max = maxListDuel.get(index);
						changeValueMinus = gameUtil.generateRandomNumberBounds(min, max);
						programValueDuelList.set(index, changeValueMinus);
					} else if (secretPasswordSplit == responseProgram) {
						programValueDuelList.set(index, responseProgram);
						;
					}
				}
			}
			responseProgramDuel = 0;
			for (index = 0; index < indexMax; index++) {
				addition = (int) (programValueDuelList.get(index) * Math.pow(10, indexMax - 1 - index));
				responseProgramDuel = responseProgramDuel + addition;
			}

			System.out.println("\n\nLa r�ponse de l'ordinateur est : " + responseProgramDuel);
			if (programValueDuelList.equals(userResponseToFindList)) {
				System.out.println("L'ordinateur a trouv� il remporte la partie !!!");
				break Duel;
			} else {
				essai--;
				turn++;
			}
			if (essai == 0) {
				System.out.println("Match Nul !!\nNi vous ni l'ordinateur n'a pu trouver la combinaison � temps");
				break Duel;
			}
		}
	}
}