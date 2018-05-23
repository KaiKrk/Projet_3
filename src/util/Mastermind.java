package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import MastermindGame.MastermindChallenger;
import MastermindGame.MastermindDefense;
import MastermindGame.MastermindDuel;

public class Mastermind {
	
	private final String mastermindIntro = "Bienvenue dans le Jeu de Mastermind,\n " + "Il y a dans ce Jeu 3 modes : "
			+ "\nJeu n°1 : Challenger où vous devez trouver la combinaison secrète de l'ordinateur"
			+ "\nJeu n°2 : Défenseur où c'est à l'ordinateur de trouver votre combinaison secrète"
			+ "\nJeu n°3 : Duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné"
			+ "Entrez votre choix en rentrant 1, 2 ou 3";
	
	GameUtilitaire gameUtil = new GameUtilitaire();
	public final Logger logger = LogManager.getLogger();
	
	public void mastermindGames(Scanner scan, int developer) {
		
		logger.info("Lancement Du Jeu Mastermind");
		Starter starter = new Starter();
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
		
		System.out.println(mastermindIntro);

		game: while (true) {
			int game = gameUtil.inputChoice(scan);

			switch (game) {
			case 1:
				MastermindChallenger mastermindChallenger = new MastermindChallenger();
				mastermindChallenger.mastermindChallenger(developer);
				break game;

			case 2:
				MastermindDefense mastermindDefense = new MastermindDefense();
				mastermindDefense.mastermindDefense();
				break game;
				
			case 3:
				MastermindDuel mastermindDuel = new MastermindDuel();
				mastermindDuel.mastermindDuel(developer);
				break game;

			}
		}
		ending : while(true) {
			
			System.out.println("Vous voulez jouer au même jeu(1)? un autre Jeu(2) ?\n Ou bien quittez l'application ? (3)");
			switch(scan.nextInt()) {
				case 1 : mastermindGames(scan,developer);
				case 2 : starter.start(scan,developer);
				case 3 : System.out.println("Au Revoir !");
					 logger.info("Fin Du Jeu");
					 break ending;
			}
		}
	}
}