package MastermindGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import util.GameUtilitaire;
import util.Starter;

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
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}

		try {
			prop.load(input);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		System.out.println(mastermindIntro);

		game: while (true) {
			int game = gameUtil.inputChoice();

			switch (game) {
			case 1:
				MastermindChallenger mastermindChallenger = new MastermindChallenger();
				mastermindChallenger.mastermindChallenger(developer);
				break game;

			case 2:
				MastermindDefense2 mastermindDefense = new MastermindDefense2();
				mastermindDefense.mastermindDefense();
				break game;

			case 3:
				MastermindDuel2 mastermindDuel = new MastermindDuel2();
				mastermindDuel.mastermindDuel(developer);
				break game;

			}
		}
		ending: while (true) {

			System.out.println(
					"Vous voulez jouer au même jeu(1)? un autre Jeu(2) ?\n Ou bien quittez l'application ? (3)");
			try {
				Scanner scanner = new Scanner(System.in);
				switch (scanner.nextInt()) {
				case 1:
					mastermindGames(scan, developer);
					scanner.close();
				case 2:
					starter.start(scan, developer);
					scanner.close();
				case 3:
					scanner.close();
					logger.info("Fin Du Jeu");
					break ending;
				}
			} catch (Exception e) {
				System.out.println("Astuce, ici il faut rentrer 1 2 ou 3 !");
				continue;

			}
		}
	}
}