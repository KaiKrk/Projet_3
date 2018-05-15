package fr.kaiqiang.main;

import java.util.Scanner;
//import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import util.Starter;

/*
 * Jeu comprenant un Jeu de Recherche de combinaison et un jeu de Mastermind 
 * Chaque Jeu contient 3 modes Challenger/Defenseur/Duel
 * @Kaiqiang
 * @version 1.0
 */
public class Game {
	static final Logger logger = LogManager.getLogger();
	public static void main(String[] args) {
		logger.info("Lancement Du Jeu");
//		logger.trace( "Test de logger.trace");
		Scanner scan = new Scanner(System.in);
		Starter starter = new Starter();
		starter.start(scan);
	}
}
