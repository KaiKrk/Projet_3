package util;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameUtilitaire {
	static final Logger logger = LogManager.getLogger();
	Scanner scan = new Scanner(System.in);
	int saisieUtilisateur, randomNumber, max, min;
	String saisieUtilisateurStr;
	Random generateRandomNumber = new Random();

	/*
	 * genere la combinaison a trouver pour tes les jeux
	 * 
	 * @param indexMax
	 */
	public int generateRandomNumber(int indexMax) {
		ArrayList<Integer> randomNumberCheck = new ArrayList<Integer>();
		while (true) {
			int maxBounds = (int) Math.pow(10, indexMax);
			int minBounds = (int) Math.pow(10, indexMax - 1) - 1;
			randomNumber = generateRandomNumber.nextInt(maxBounds - minBounds) + minBounds;

			if (randomNumber > minBounds) {

				for (int index = 0; index < indexMax; index++) {
					randomNumberCheck.add(0);
				}

				int exponant = (int) Math.pow(10, indexMax - 1);
				for (int index = 0; index < indexMax; index++) {
					randomNumberCheck.set(index, randomNumber / exponant % 10);
					exponant = exponant / 10;
				}
				for (int index = 0; index < 4; index++) {
					if (randomNumberCheck.get(index) == 0) {
						generateRandomNumber(indexMax);
					}
				}
				break;
			}
		}
		return randomNumber;
	}

	/*
	 * permet de nouvelles réponse en fonction des réponse précédente
	 * 
	 * @param min, max
	 */
	public int generateRandomNumberBounds(int min, int max) {

		randomNumber = generateRandomNumber.nextInt(max - min) + min;
		if (randomNumber == max) {
			generateRandomNumberBounds(min, max);
		} else if (randomNumber == min) {
			generateRandomNumberBounds(min, max);
		}

		return randomNumber;
	}

	/*
	 * demande a l'utilisateur de rentrer sa combinaison de valeur
	 * 
	 * @param indexMax
	 */

	public int inputUserValue(int indexMax) {
		while (indexMax > 10) {
			indexMax--;
		}
		while (true) {
			System.out.println("Veuillez rentrer votre solution à " + indexMax + " chiffres : ");
			saisieUtilisateurStr = scan.nextLine();

			try {
				if (saisieUtilisateurStr.length() == indexMax) {
					saisieUtilisateur = Integer.parseInt(saisieUtilisateurStr);
					break;
				}
			}

			catch (Exception e) {

				System.out.println("Ah petite erreur, relisez bien la consigne\n ");
				continue;
			}
		}
		return saisieUtilisateur;
	}

	/*
	 * 
	 */
	public int inputUserValueMastermind(int indexMax, int numberAllowed) {

		ArrayList<Integer> checkInputUser = new ArrayList<Integer>();

		int index;

		for (index = 0; index < indexMax; index++) {
			checkInputUser.add(0);
		}
		while (indexMax > 10) {
			indexMax--;
		}

		input: while (true) {
			System.out.println(
					"Veuillez rentrer votre solution à " + indexMax + " chiffres entre 1 et " + numberAllowed + ":");
			saisieUtilisateurStr = scan.next();

			try {
				if (saisieUtilisateurStr.length() == indexMax) {
					saisieUtilisateur = Integer.parseInt(saisieUtilisateurStr);

					int exponant = (int) Math.pow(10, indexMax - 1);
					for (index = 0; index < indexMax; index++) {
						checkInputUser.set(index, saisieUtilisateur / exponant % 10);
						exponant = exponant / 10;
					}

					for (index = 0; index < indexMax; index++) {
						if (checkInputUser.get(index) > numberAllowed) {
							continue input;
						}
					}

					break;
				}
			}

			catch (Exception e) {
				scan.close();

				inputUserValueMastermind(indexMax, numberAllowed);
			}
		}
		return saisieUtilisateur;
	}

	public int generateRandomNumberMastermind(int indexMax, int numberAllowed) {
		ArrayList<Integer> randomNumberList = new ArrayList<Integer>();
		while (true) {

			int numberAllowedMaxBounds = numberAllowed, index;
			int maxBounds = (int) Math.pow(10, indexMax);
			int minBounds = (int) Math.pow(10, indexMax - 1) - 1;
			randomNumber = generateRandomNumber.nextInt(maxBounds - minBounds) + minBounds;

			if (randomNumber > minBounds) {

				for (index = 0; index < indexMax; index++) {
					randomNumberList.add(0);
				}

				int exponant = (int) Math.pow(10, indexMax - 1);
				for (index = 0; index < indexMax; index++) {
					randomNumberList.set(index, randomNumber / exponant % 10);
					exponant = exponant / 10;
				}
				for (index = 0; index < indexMax; index++) {
					if (randomNumberList.get(index) > numberAllowed) {
						randomNumberList.set(index, generateRandomNumber.nextInt(numberAllowedMaxBounds - 1) + 1);

					}
				}
				randomNumber = 0;
				for (index = 0; index < indexMax; index++) {
					int addition = (int) (randomNumberList.get(index) * Math.pow(10, indexMax - 1 - index));
					randomNumber = randomNumber + addition;
				}
				break;
			}
		}
		return randomNumber;
	}

	public int inputChoice() {
		Scanner scan = new Scanner(System.in);
		String gameString;
		int game = 0;

		while (true) {
			try {
				System.out.println("Votre Choix ?");
				gameString = scan.nextLine();
				game = Integer.parseInt(gameString);
			} catch (Exception e) {
				System.out.println("Oops Petite erreur, je te donne encore un chance ! ");
			}

			if (game == 1 || game == 2 || game == 3) {
				break;
			} else {
				System.out.println("Astuce : Ici il faut rentrer un des chiffres proposés au dessus ! ");
				continue;
			}
		}
		return game;
	}

	public int startAllChoice(int indexMax) {
		int responseProgram = 0;
		for (int index = 0; index < indexMax; index++) {
			int addition = (int) (1 * Math.pow(10, indexMax - 1 - index));
			responseProgram = responseProgram + addition;
		}
//		System.out.println(responseProgram);

		return responseProgram;
	}

	public int endAllChoice(int indexMax, int numberAllowed) {

		int responseProgram = 0;
		for (int index = 0; index < indexMax; index++) {
			int addition = (int) (numberAllowed * Math.pow(10, indexMax - 1 - index));
			responseProgram = responseProgram + addition;
		}
//		System.out.println(responseProgram);
		return responseProgram;
	}

	// public int returnFirstMastermindValue(int indexMax) {
	// int firstValue =
	// return
	// }
	//
}