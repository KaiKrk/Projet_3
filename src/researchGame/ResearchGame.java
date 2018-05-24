package researchGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import util.GameUtilitaire;
import util.Starter;

/**
 * contient les 3 modes de jeu 
 * @author Kaiqiang
 * @version 2.0
 */
public class ResearchGame {
	
	static final Logger logger = LogManager.getLogger();
	Random randomNum = new Random();

	GameUtilitaire gameUtil = new GameUtilitaire();
	int reponse,combinaison;
	
	public void researchGames(Scanner scan, int developer) {
		logger.info("Lancement Du Jeu de Recherche");
		Starter starter = new Starter();
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
		
		
		
		System.out.println("Bienvenue dans le Jeu de Recherche,"
				+ "\nIl y a dans ce Jeu 3 modes : "
				+ "\nJeu n°1 : Challenger où vous devez trouver la combinaison secrète de l'ordinateur"
				+ "\nJeu n°2 : Défenseur où c'est à l'ordinateur de trouver votre combinaison secrète"
				+ "\nJeu n°3 : Duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné"
				+ "\nEntrez votre choix en rentrant 1, 2 ou 3");
		
		
		game :while(true) {
			
			int game = gameUtil.inputChoice();
			switch(game) {
			
				
				case 1 :
					logger.info("Mode Challenger");
					ResearchGameChallenger researchGame = new ResearchGameChallenger();
					researchGame.researchChallenger(developer);
					break game;

					
					
					
				case 2 :
					logger.info("Mode Defenseur");
					ResearchGameDefense researchDefense = new ResearchGameDefense();
					researchDefense.reseachDefense();
					break game;
					
				
					
					
				case 3 : 
					logger.info("Mode Duel");
					ResearchGameDuel researchDuel = new ResearchGameDuel();
					researchDuel.reseachDuel(developer);
					break game;
						
						}
						
						
					}
						
		ending : while(true) {
			
			System.out.println("Vous voulez jouer au même jeu(1)? un autre Jeu(2) ?\n Ou bien quittez l'application ? (3)");
			try {
				Scanner scanner = new Scanner(System.in);
				switch(scanner.nextInt()) {
					case 1 : researchGames(scan,developer);
							 scanner.close();
					
					case 2 : starter.start(scan,developer);
							 scanner.close();
					
					case 3 : System.out.println("Au Revoir !");
							scanner.close();
						 	logger.info("Fin Du Jeu");
						 	break ending;
				}
			}
				catch (Exception e) {
					System.out.println("Astuce, ici il faut rentrer 1 2 ou 3 !");
					continue;
				}
		}

	}
}
