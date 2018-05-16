package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * contient les 3 modes de jeu 
 * @author Kaiqiang
 * @version 1.0
 */
public class ResearchGame {
	
	static final Logger logger = LogManager.getLogger();
	Random randomNum = new Random();
	private final String Welcome_Message = "Bienvenue dans le mode défenseur du Jeu de Recherche dans ce Jeu.\n Vous devez ici rentrer la combinaison et l'ordinateur va essayer de trouver la combinaison\n\n";

	
	GameUtilitaire gameUtil = new GameUtilitaire();
	int reponse,combinaison;
	
	public void researchGames(Scanner scan) {
		logger.info("Lancement Du Jeu de Recherche");
		Starter starter = new Starter();
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(new File("src/fr/kaiqiang/ressources/config.properties"));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		try {
			prop.load(input);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		int essai = Integer.parseInt(prop.getProperty("essai"));
		
		
		System.out.println("Bienvenue dans le Jeu de Recherche,"
				+ "\nIl y a dans ce Jeu 3 modes : "
				+ "\nJeu n°1 : Challenger où vous devez trouver la combinaison secrète de l'ordinateur"
				+ "\nJeu n°2 : Défenseur où c'est à l'ordinateur de trouver votre combinaison secrète"
				+ "\nJeu n°3 : Duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné"
				+ "\nEntrez votre choix en rentrant 1, 2 ou 3");
		
		
		game :while(true) {
			
			int game =scan.nextInt();
			
			switch(game) {
			/**
			 * Mode Challenger du jeu de recherche 
			 */
				
				case 1 :
					int indexMax = Integer.parseInt(prop.getProperty("case"));
					reponse = gameUtil.generateRandomNumber(indexMax);	
					int developer = Integer.parseInt(prop.getProperty("developerMod"));
					
				essai: while(essai != 0) {
					if (developer == 1) {
						System.out.println("\nLa Reponse Du Jeu est : " + reponse);
					}
					combinaison = gameUtil.inputUserValue(indexMax);
					
					ArrayList<Integer> combinaisonList = new ArrayList<Integer>();
					ArrayList<Integer> reponseList = new ArrayList<Integer>();
					for (int index = indexMax; index != 0; index--) {
						combinaisonList.add(0);
						reponseList.add(0);
						
					}
					int exponant = (int) Math.pow(10, indexMax-1);
					for (int index = 0; index < indexMax ; index++) {
						combinaisonList.set(index, combinaison / exponant % 10);
						reponseList.set(index, reponse/exponant % 10);
						exponant = exponant / 10;
					}
					
					System.out.print("Voici l'indice : ");
						for (int index = 0; index < indexMax ; index++) {
							
							try {
							int reponseBis = reponseList.get(index);
							int combinaisonBis = combinaisonList.get(index);
								if (combinaisonBis < reponseBis) {
									System.out.print("+");
								}else if ( combinaisonBis > reponseBis) {
									System.out.print("-");
								}else System.out.print("=");
							}		
							catch (Exception e) {
				//				e.printStackTrace();
								scan.nextLine();
							}
							
					
					}
				
					System.out.println("\n"+"Vous avez rentrer : " +combinaison);
					if (combinaison == reponse) {
				 System.out.println("Vous avez réussi !!!");break essai;
					}else {essai --;
						System.out.println("Il vous reste : "+ essai+" essai(s)");
					}
				}
					if(essai ==0)
					System.out.println("\nLa Solution est : "+ reponse);
						
					break game;
				
					
					/*
					 * mode défenseur du Jeu de recherche 
					 */
					
					
				case 2 :
					int saisieUtilisateurInt, responseProgram,
					responseProgramSplit,min,max;
					indexMax = Integer.parseInt(prop.getProperty("case")) ;
					System.out.println(Welcome_Message);
					
					saisieUtilisateurInt = gameUtil.inputUserValue(indexMax);
					
					responseProgram = gameUtil.generateRandomNumber(indexMax);
					
					
					
					ArrayList<Integer> maxList = new ArrayList<Integer>();
					ArrayList<Integer> minList = new ArrayList<Integer>();
					ArrayList<Integer> userValueList = new ArrayList<Integer>();
					ArrayList<Integer> responseProgramList = new ArrayList<Integer>();
					
					for(int indexNumber = 0; indexNumber < indexMax; indexNumber++) {
						minList.add(0);
						maxList.add(10);
						responseProgramList.add(0);
						userValueList.add(0);
					}
					
					
					
					while(essai != 0) {
						System.out.println("Nombre d'essai restant : " + essai);
						
						if(responseProgram == saisieUtilisateurInt) {
							System.out.println("La Reponse de l'ordinateur : " + responseProgram  + "\nLa Solution : " + saisieUtilisateurInt);
							System.out.println("Vous avez perdu l'ordinateur a trouvé votre reponse!");
							break;
						}
						else if(essai < 1 ) {
							System.out.println("Vous avez gagner l'ordinateur n'a pas réussi a trouver votre reponse !!!");
							break;
						}
						else {
							System.out.println("La Reponse de l'ordinateur : " + responseProgram  + "\nLa Solution : " + saisieUtilisateurInt);
							essai--;
						}
						
						
						int exponant = (int) Math.pow(10, indexMax-1);
						for (int index = 0; index < indexMax ; index++) {
							responseProgramList.set(index, responseProgram / exponant % 10);
							userValueList.set(index, saisieUtilisateurInt/exponant % 10);
							exponant = exponant / 10;
						}
						
							
						for (int index = 0; index < indexMax ; index++) {
							responseProgramSplit = responseProgramList.get(index);
							int responseValueUser = userValueList.get(index);
							
							try {
								
								if(responseProgramSplit < responseValueUser) {
									minList.set(index, responseProgramSplit);
									
									min = minList.get(index);max = maxList.get(index) ;
									int changeValuePlus = gameUtil.generateRandomNumberBounds(min,max);
									responseProgramList.set(index, changeValuePlus)  ;
								}
								else if(responseProgramSplit > responseValueUser){
									maxList.set(index, responseProgramSplit);
									
									min = minList.get(index);max = maxList.get(index) ;
									int changeValueMinus = gameUtil.generateRandomNumberBounds(min,max);
									responseProgramList.set(index, changeValueMinus);
								}
								else if (responseValueUser == responseProgramSplit) {
									responseProgramList.set(index, responseProgramSplit);
								}
							}
							catch (Exception e) {
//							e.printStackTrace();
//								scan.nextLine();
							}
						}
						
						
						responseProgram = 0 ;
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
					}
				
					break game;
					
				/*
				 * mode duel du Jeu de Recherche
				 */
					
					
				case 3 : 
					essai = 10; int turn = 1, userValueInt;
					
					int developerDuel = Integer.parseInt(prop.getProperty("developerMod"));
					indexMax = Integer.parseInt(prop.getProperty("case"));
					reponse = gameUtil.generateRandomNumber(indexMax);			
					
					
					ArrayList<Integer> responseDuelList = new ArrayList<Integer>();
					ArrayList<Integer> maxListDuel = new ArrayList<Integer>();
					ArrayList<Integer> minListDuel = new ArrayList<Integer>();
					ArrayList<Integer> userValueDuelList = new ArrayList<Integer>();
					ArrayList<Integer> programValueDuelList = new ArrayList<Integer>();
					
					
					for (int index = 0; index < indexMax; index++) {
						
						responseDuelList.add(0);
						minListDuel.add(0);
						maxListDuel.add(10);
						userValueDuelList.add(0);
						programValueDuelList.add(0);
						
					}
					
					int exponant = (int) Math.pow(10, indexMax-1);
					
					for (int index = 0; index < indexMax ; index++) {
						
						responseDuelList.set(index, reponse / exponant % 10);
						exponant = exponant / 10;
						
					}
					int programValue = gameUtil.generateRandomNumber(indexMax);
					   exponant = (int) Math.pow(10, indexMax-1);
					   
					   
					for (int index = 0; index < indexMax ; index++) {
						
						programValueDuelList.set(index, programValue / exponant % 10);
						exponant = exponant / 10;
						
					}
					
				
					 while(essai != 0) {
						System.out.println("\nTour " + turn+ "\nNombre d'essai restant : " + essai);
						if (developerDuel == 1) {
							System.out.println(""
									+ "La Reponse Du Jeu est : " + reponse);
						}
						userValueInt = gameUtil.inputUserValue(indexMax);
						
						exponant = (int) Math.pow(10, indexMax-1);
						for (int index = 0; index < indexMax ; index++) {
							userValueDuelList.set(index, userValueInt / exponant % 10);
							exponant = exponant / 10;
						}
						
						System.out.print("Voici l'indice : ");
						for (int index = 0; index < indexMax; index++) {
								
								try {
								int reponseBis = responseDuelList.get(index);
								int userValueBis = userValueDuelList.get(index);
									if (userValueBis < reponseBis) {
										System.out.print("+");
									}else if ( userValueBis > reponseBis) {
										System.out.print("-");
									}else System.out.print("=");
								}		
								catch (Exception e) {
					//				e.printStackTrace();
									scan.nextLine();
								}
						
						}
						if (userValueDuelList.equals(responseDuelList)) {
							System.out.println("\nBravo vous avez gagner !!!");
							break game;
						}
						else {
							
							try {
								Thread.sleep(350);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							for (int index = 0; index < indexMax; index++ ) {
								responseProgram = programValueDuelList.get(index);
								int secretPasswordSplit = responseDuelList.get(index);
								try {
									
									if(responseProgram < secretPasswordSplit) {
										minListDuel.set(index, responseProgram);
										
										min = minListDuel.get(index);max = maxListDuel.get(index) ;
										int changeValuePlus = gameUtil.generateRandomNumberBounds(min,max);
										programValueDuelList.set(index, changeValuePlus) ;
									}
									else if(responseProgram > secretPasswordSplit){
										maxListDuel.set(index, responseProgram);
										
										min = minListDuel.get(index);max = maxListDuel.get(index) ;
										int changeValueMinus = gameUtil.generateRandomNumberBounds(min,max);
										programValueDuelList.set(index, changeValueMinus) ;
									}
									else if (secretPasswordSplit == responseProgram) {
										programValueDuelList.set(index, responseProgram) ;;
									}
								}
								catch (Exception e) {
								e.printStackTrace();
									scan.nextLine();
								}
							}
							int responseProgramDuel= 0;
							for(int index= 0; index < indexMax; index++) {
								int addition = (int) (programValueDuelList.get(index)*Math.pow(10, indexMax-1-index));
								responseProgramDuel =  responseProgramDuel + addition;
							}
							
							System.out.println("\nLa réponse de l'ordinateur est : " + responseProgramDuel);
							if(programValueDuelList.equals(responseDuelList)) {
								System.out.println("L'ordinateur a trouvé il remporte la partie !!!");
								break game;
							}
							else {
								essai--; turn++;
							}
							if( essai == 0 ) {
								System.out.println("Match Nul !!\nNi vous ni l'ordinateur n'a pu trouver la combinaison à temps");
								break game;
							}
						
						}
						
						
					}
						
			}
		}
		ending : while(true) {
			System.out.println("Vous voulez jouer au même jeu(1)? un autre Jeu(2) ?\n Ou bien quittez l'application ? (3)");
			switch(scan.nextInt()) {
				case 1 : researchGames(scan);
				case 2 : starter.start(scan);
				case 3 : System.out.println("Au Revoir !");
						 logger.info("Fin Du Jeu");
						 break ending;
				
			}
		}
	}
}
