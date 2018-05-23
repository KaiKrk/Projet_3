package util;

import java.util.Scanner;
/*
 * cette classe sert a envoyer l'utilisateur sur les différents Jeux
 */
public class Starter {
		
		public void start(Scanner scan, int developer) {
			GameUtilitaire gameUtil = new GameUtilitaire();
			String welcome = "-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n";
			welcome += "***Bienvenue dans le Jeu***\n";
			
			System.out.println(welcome);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
			boucle : while(true) {
				String choice = "Choisissez votre jeu\n\n";
				choice += "1/Pour le Jeu de Recherche\n2/Pour le Masterming\n";
				System.out.println(choice);
				
				try {
					int game = gameUtil.inputChoice();
					
					switch (game) {
						case 1:
							ResearchGame research = new ResearchGame();
							research.researchGames(scan,developer);
							break boucle;
						case 2:
							Mastermind mastermind = new Mastermind();
							mastermind.mastermindGames(scan,developer);
							break boucle;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	

}
