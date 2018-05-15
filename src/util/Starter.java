package util;

import java.util.Scanner;
/*
 * cette classe sert a envoyer l'utilisateur sur les différents Jeux
 */
public class Starter {
		
		//Scanner scan = new Scanner(System.in);
		ResearchGame research = new ResearchGame();
		Mastermind mastermind = new Mastermind();
		public void start(Scanner scan) {
			String welcome = "-------------------------------------\n";
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
					int game = scan.nextInt();
					
					switch (game) {
						case 1:
							research.researchGames(scan);
							break boucle;
						case 2:
							mastermind.mastermindGames(scan);
							break boucle;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					scan.nextLine();
				}
			}
			
//			System.out.print("Voulez vous recommencer ? (O/n) ");
//			if (!scan.next().equals("n")) start(scan);
//			else System.out.println("Au revoir");
		}
	

}
