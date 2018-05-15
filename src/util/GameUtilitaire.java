package util;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameUtilitaire {
	Scanner scan = new Scanner (System.in);
	int saisieUtilisateur, randomNumber, max, min;
	String saisieUtilisateurStr;
	Random generateRandomNumber = new Random();
	/*
	 * genere la combinaison a trouver pour tes les jeux
	 * @param indexMax
	 */
	public int generateRandomNumber(int indexMax) {
		ArrayList<Integer> randomNumberCheck = new ArrayList<Integer>();
		while(true) {
			int maxBounds = (int) Math.pow(10, indexMax);
			int minBounds = (int) Math.pow(10, indexMax-1)-1;
			randomNumber = generateRandomNumber.nextInt(maxBounds - minBounds) + minBounds;
			
			
			if (randomNumber > minBounds) {
				
				for(int index = 0; index < indexMax; index++) {
					randomNumberCheck.add(0);
				}
				
				int exponant = (int) Math.pow(10, indexMax-1);
				for (int index = 0; index < indexMax ; index++) {
					randomNumberCheck.set(index, randomNumber / exponant % 10);
					exponant = exponant / 10;
				}
				for (int index = 0; index < 4; index++) {
					if(randomNumberCheck.get(index) == 0) {
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
	 * @param min, max
	 */
	public  int generateRandomNumberBounds(int min,int max) {
	
		randomNumber = generateRandomNumber.nextInt(max - min)+ min;
		if(randomNumber == max) {
			generateRandomNumberBounds(min,max);
		}
		else if(randomNumber == min) {
			generateRandomNumberBounds(min,max);
		}
		
		return randomNumber;
	}
	
	/*
	 * demande a l'utilisateur de rentrer sa combinaison de valeur
	 * @param indexMax
	 */
	
	public int inputUserValue(int indexMax) {
		
		while(true) {
			System.out.println("Veuillez rentrer votre solution à " +indexMax+ " chiffres : ");
			saisieUtilisateurStr = scan.nextLine();
			
			try {
				if (saisieUtilisateurStr.length() == indexMax) { 
					saisieUtilisateur = Integer.parseInt(saisieUtilisateurStr);
					break;
				}
			}
			
			catch (Exception e) {
//				e.printStackTrace();
				scan.nextLine();
			}
		}
		return saisieUtilisateur;
	}
	
	
	
}