package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import MastermindGame.Mastermind;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import researchGame.ResearchGame;

/*
 * cette classe sert a envoyer l'utilisateur sur les diff�rents Jeux
 */
public class Starter {
	static final Logger logger = LogManager.getLogger();
	Properties prop = new Properties();
	InputStream input = null;

	public void start(Scanner scan, int developer) {

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

		int developerMod = Integer.parseInt(prop.getProperty("developer"));
		if(developerMod == 1){
			developer = 1;
			logger.info("Mode Développeur Actif");
		}
		GameUtilitaire gameUtil = new GameUtilitaire();

		String welcome = "-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n";
		welcome += "***Bienvenue dans le Jeu***\n";

		System.out.println(welcome);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boucle: while (true) {
			String choice = "Choisissez votre jeu\n\n";
			choice += "1/Pour le Jeu de Recherche\n2/Pour le Masterming\n";
			System.out.println(choice);

			try {
				int game = gameUtil.inputChoice();

				switch (game) {
				case 1:
					ResearchGame research = new ResearchGame();
					research.researchGames(scan, developer);
					break boucle;
				case 2:
					Mastermind mastermind = new Mastermind();
					mastermind.mastermindGames(scan, developer);
					break boucle;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
