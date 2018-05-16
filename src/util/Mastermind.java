package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mastermind {
	
	private final String mastermindIntro = "Bienvenue dans le Jeu de Mastermind,\n " + "Il y a dans ce Jeu 3 modes : "
			+ "\nJeu n°1 : Challenger où vous devez trouver la combinaison secrète de l'ordinateur"
			+ "\nJeu n°2 : Défenseur où c'est à l'ordinateur de trouver votre combinaison secrète"
			+ "\nJeu n°3 : Duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné"
			+ "Entrez votre choix en rentrant 1, 2 ou 3";
	
	GameUtilitaire gameUtil = new GameUtilitaire();
	static final Logger logger = LogManager.getLogger();
	
	public void mastermindGames(Scanner scan) {
		
		logger.info("Lancement Du Jeu Mastermind");
		Starter starter = new Starter();
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream("src/fr/kaiqiang/ressources/config.properties");
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
		
		System.out.println(mastermindIntro);

		game: while (true) {
			int game = scan.nextInt();

			switch (game) {
			case 1:

				System.out.println("Bienvenue dans le mode challenger du Mastermind\n"
						+ "Le but : découvrir la combinaison à x chiffres votre adversaire"
						+ "\nvotre adversaire indique pour chaque proposition "
						+ "le nombre de chiffre de la proposition qui apparaissent "
						+ "à la bonne place et à la mauvaise place dans la combinaison secrète.");
				
				int responseToFind;
				int essai = Integer.parseInt(prop.getProperty("essai"));
				int indexMax = Integer.parseInt(prop.getProperty("numberOfNumber"));
				int developer = Integer.parseInt(prop.getProperty("developerMod"));

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
				responseToFind = gameUtil.generateRandomNumber(indexMax);
				while (essai != 0) {
					int RNRP = 0, RNWP = 0;
					// RNRP = Right Number Right Place / RNWP = Right Number Wrong Place
					
					System.out.println("Nombre d'essai restant : " + essai);
					if (developer == 1) {
						System.out.println(""
								+ "La Reponse Du Jeu est : " + responseToFind);
					}
					int userValue = gameUtil.inputUserValue(indexMax);

					int exponant = (int) Math.pow(10, indexMax - 1);
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
						System.out.println(
								"Bien placé : " + RNRP + " !!!! \nBravo vous avez réussi, vous avez gagner!!!");
						break game;
					} else {
						System.out.println("Bien placé : " + RNRP + "\nBien Présent : " + RNWP);
					}
					essai--;
				}
				if (essai == 0) {
					System.out
							.println("Vous avez perdu!\nVous n'avez pas réussi a trouver la bonne combinaison à temps! "
									+ "La réponse est : " + responseToFind);
					break game;
				}

			case 2:

				int saisieUtilisateurInt, responseProgram, responseProgramSplit, min, max;
				indexMax = Integer.parseInt(prop.getProperty("numberOfNumber"));
				saisieUtilisateurInt = gameUtil.inputUserValue(indexMax);

				responseProgram = gameUtil.generateRandomNumber(indexMax);

				essai = Integer.parseInt(prop.getProperty("essai"));


				ArrayList<Integer> maxList = new ArrayList<Integer>();
				ArrayList<Integer> minList = new ArrayList<Integer>();
				ArrayList<Integer> userValueToFindList = new ArrayList<Integer>();
				ArrayList<Integer> userValueToFindCheckList = new ArrayList<Integer>();
				ArrayList<Integer> responseProgramList = new ArrayList<Integer>();
				ArrayList<Integer> responseProgramCheckList = new ArrayList<Integer>();

				for (int indexNumber = 0; indexNumber < indexMax; indexNumber++) {
					minList.add(0);
					maxList.add(10);
					userValueToFindList.add(0);
					userValueToFindCheckList.add(0);
					responseProgramList.add(0);
					responseProgramCheckList.add(0);
				}

				while (essai != 0) {
					
					int RNRP = 0, RNWP = 0;
					// RNRP = Right Number Right Place / RNWP = Right Number Wrong Place
					int exponant = (int) Math.pow(10, indexMax-1);
					for (int index = 0; index < indexMax ; index++) {
						responseProgramList.set(index, responseProgram / exponant % 10);
						userValueToFindList.set(index, saisieUtilisateurInt/exponant % 10);
						exponant = exponant / 10;
					}

					for (int index = 0; index < indexMax; index++) {
						
						responseProgramSplit = responseProgramList.get(index);
						int responseValueUser = userValueToFindList.get(index);
						
						try {

							if (responseProgramSplit < responseValueUser) {
								minList.set(index, responseProgramSplit);

								min = minList.get(index);
								max = maxList.get(index);
								int changeValuePlus = gameUtil.generateRandomNumberBounds(min, max);
								responseProgramList.set(index, changeValuePlus);
							} else if (responseProgramSplit > responseValueUser) {
								maxList.set(index, responseProgramSplit);

								min = minList.get(index);
								max = maxList.get(index);
								int changeValueMinus = gameUtil.generateRandomNumberBounds(min, max);
								responseProgramList.set(index, changeValueMinus);
							} else if (responseValueUser == responseProgramSplit) {
								responseProgramList.set(index, responseProgramSplit);
							}
						} catch (Exception e) {
							e.printStackTrace();
							scan.nextLine();
						}

						responseProgramCheckList.set(index, responseProgramList.get(index));
						userValueToFindCheckList.set(index, userValueToFindList.get(index));
					}
					for (int index = 0; index < indexMax; index++) {
						if (responseProgramCheckList.get(index) == userValueToFindCheckList.get(index)) {
							RNRP++;
							responseProgramCheckList.set(index, -1);
							userValueToFindCheckList.set(index, -2);
						}
					}

					for (int index = 0; index < indexMax; index++) {
						if (responseProgramCheckList.contains(userValueToFindCheckList.get(index))) {
							RNWP++;
						}
					}
					
					responseProgram = 0;
					for(int index= 0; index < indexMax; index++) {
						int addition = (int) (responseProgramList.get(index)*Math.pow(10, indexMax-1-index));
						responseProgram =  responseProgram + addition;
					}
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + "\nNombre d'essai restant : " + essai);

					if (RNRP == indexMax) {
						System.out.println("La Reponse de l'ordinateur : " + responseProgram + "\nLa Solution : "
								+ saisieUtilisateurInt + "\nBien placé : " + RNRP
								+ " !!!! \nVous avez perdu L'Ordinateur a trouvé la réponse!!!");
						break game;
					} else {
						System.out.println("La Reponse de l'ordinateur : " + responseProgram + "\nLa Solution : "
								+ saisieUtilisateurInt + "\nBien placé : " + RNRP + "\nBien Présent : " + RNWP);
						essai--;
					}
				}

			case 3:

				essai = Integer.parseInt(prop.getProperty("essai"));
				int turn = 1;
				int developerDuel = Integer.parseInt(prop.getProperty("developerMod"));

				ArrayList<Integer> maxListDuel = new ArrayList<Integer>();
				ArrayList<Integer> minListDuel = new ArrayList<Integer>();
				
				ArrayList<Integer> userValueListDuel = new ArrayList<Integer>();
				ArrayList<Integer> userValueCheckListDuel = new ArrayList<Integer>();

				ArrayList<Integer> responseToFindListDuel = new ArrayList<Integer>();
				ArrayList<Integer> responseToFindCheckListDuel = new ArrayList<Integer>();
				
				ArrayList<Integer> responseProgramListDuel = new ArrayList<Integer>();
				ArrayList<Integer> responseProgramCheckListDuel = new ArrayList<Integer>();

				indexMax = Integer.parseInt(prop.getProperty("numberOfNumber"));
				responseToFind = gameUtil.generateRandomNumber(indexMax);
				responseProgram = gameUtil.generateRandomNumber(indexMax);

				for (int indexNumber = 0; indexNumber < indexMax; indexNumber++) {
					
					userValueListDuel.add(0);
					userValueCheckListDuel.add(0);
					
					responseToFindListDuel.add(0);
					responseToFindCheckListDuel.add(0);
					
					maxListDuel.add(10);
					minListDuel.add(0);
					
					responseProgramListDuel.add(0);
					responseProgramCheckListDuel.add(0);
				}

				while (essai != 0) {
					int RNRP = 0, RNWP = 0;
					System.out.println("Tour n°" + turn + "\nNombre d'essai restant : " + essai);
					if (developerDuel == 1) {
						System.out.println("La Reponse Du Jeu est : " + responseToFind);
								
					}
					int userValue = gameUtil.inputUserValue(indexMax);

					int exponant = (int) Math.pow(10, indexMax-1);
					
					for (int index = 0; index < indexMax ; index++) {
						userValueListDuel.set(index, userValue / exponant % 10);
						responseToFindListDuel.set(index, responseToFind/exponant % 10);
						exponant = exponant / 10;
					} 

					
					for (int index = 0; index < indexMax; index++) {
						userValueCheckListDuel.set(index, userValueListDuel.get(index));
						responseToFindCheckListDuel.set(index, responseToFindListDuel.get(index));
					}

					
					for (int index = 0; index < indexMax; index++) {
						if (responseToFindCheckListDuel.get(index) == userValueCheckListDuel.get(index)) {
							RNRP++;
							userValueCheckListDuel.set(index, -1);
							responseToFindCheckListDuel.set(index, -2);
						}
					}
					
					
					for (int index = 0; index < indexMax; index++) {
						if (responseToFindCheckListDuel.contains(userValueCheckListDuel.get(index))) {
							RNWP++;
						}
					}

					if (RNRP == indexMax) {
						System.out.println(
								"Bien placé : " + RNRP + " !!!! \nBravo vous avez réussi, vous avez gagner!!!");
						break game;
						
					} 
					
					else {
						System.out.println("Bien placé : " + RNRP + "\nBien Présent : " + RNWP);
						RNRP = 0;
						RNWP = 0;

						exponant = (int) Math.pow(10, indexMax-1);
						for (int index = 0; index < indexMax ; index++) {
							responseProgramListDuel.set(index, responseProgram / exponant % 10);
							
							exponant = exponant / 10;
						}

						for (int index = 0; index < indexMax; index++) {
							responseProgramSplit = responseProgramListDuel.get(index);
							int responseValueUser = responseToFindListDuel.get(index);
							try {

								if (responseProgramSplit < responseValueUser) {
									minListDuel.set(index, responseProgramSplit);

									min = minListDuel.get(index);
									max = maxListDuel.get(index);
									int changeValuePlus = gameUtil.generateRandomNumberBounds(min, max);
									responseProgramListDuel.set(index, changeValuePlus);
								}
								
								else if (responseProgramSplit > responseValueUser) {
									
									maxListDuel.set(index, responseProgramSplit);

									min = minListDuel.get(index);
									max = maxListDuel.get(index);
									int changeValueMinus = gameUtil.generateRandomNumberBounds(min, max);
									responseProgramListDuel.set(index, changeValueMinus);
									
								} 
								
								else if (responseValueUser == responseProgramSplit) {
									
									responseProgramListDuel.set(index, responseProgramSplit);
									
								}
							} catch (Exception e) {
								e.printStackTrace();
								scan.nextLine();
							}

							responseProgramCheckListDuel.set(index, responseProgramListDuel.get(index));

						}
						
						responseProgram =0;
						
						for(int index= 0; index < indexMax; index++) {
							int addition = (int) (responseProgramListDuel.get(index)*Math.pow(10, indexMax-1-index));
							responseProgram =  responseProgram + addition;
						}
						
						
						for (int index = 0; index < indexMax; index++) {
							if (responseProgramCheckListDuel.get(index) == responseToFindListDuel.get(index)) {
								RNRP++;
								responseProgramCheckListDuel.set(index, 0);
							}
						}

						
						for (int index = 0; index < indexMax; index++) {
							if (responseProgramCheckListDuel.contains(responseToFindListDuel.get(index))) {
								RNWP++;
							}
						}

						
						if (RNRP == indexMax) {
							System.out.println("L'Ordinateur a saisi : " + responseProgram + "\nBien Placé : " + RNRP
									+ "\nL'Ordinateur a gagné, il remporte ce Duel !!!");
							break game;
						} 
						else {
							System.out.println("L'Ordinateur a saisi : " + responseProgram + "\nBien placé : " + RNRP
									+ "\nBien Présent : " + RNWP);
						}
					}

					essai--;
					turn++;
				}

			}
		}
		while(true) {
			
			System.out.println("Vous voulez jouer au même jeu(1)? un autre Jeu(2) ?\n Ou bien quittez l'application ? (3)");
			switch(scan.nextInt()) {
				case 1 : mastermindGames(scan);
				case 2 : starter.start(scan);
				case 3 : System.out.println("Au Revoir !");
					 logger.info("Fin Du Jeu");
			}
		}
	}
}