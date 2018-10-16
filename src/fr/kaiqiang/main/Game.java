package fr.kaiqiang.main;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import util.Starter;

/*
 * Jeu comprenant un Jeu de Recherche de combinaison et un jeu de Mastermind 
 * Chaque Jeu contient 3 modes Challenger/Defenseur/Duel
 * @Kaiqiang
 * @version 2.0
 */
public class Game {
	static final Logger logger = LogManager.getLogger();

	//
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		Starter starter = new Starter();
		int developer;
		logger.info("Lancement Du Jeu");

		if (args.length == 0) {
			logger.info("Mode Joueur Actif");
			developer = 0;
			starter.start(scan, developer);
		}

		else if (args[0].equals("developer")) {
			developer = 1;
			logger.info("Mode Developpeur Actif");
			starter.start(scan, developer);
		} else {
			logger.info("Mode Joueur Actif");
			developer = 0;
			starter.start(scan, developer);
		}

	}
}
