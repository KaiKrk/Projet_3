package researchGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import util.GameUtilitaire;
/**
 * Mode Challenger du jeu de recherche 
 */
public class ResearchGameChallenger {
		
	public void researchChallenger(int developer) {
		
		GameUtilitaire gameUtil = new GameUtilitaire();
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
		int indexMax = Integer.parseInt(prop.getProperty("case"));
		int reponse = gameUtil.generateRandomNumber(indexMax);	
		int essai = Integer.parseInt(prop.getProperty("essai")); 
		int combinaison,exponant,index,reponseSplit,combinaisonSplit;
		
	essai: while(essai != 0) {
		if (developer == 1) {
			System.out.println("\nLa Reponse Du Jeu est : " + reponse);
		}
		combinaison = gameUtil.inputUserValue(indexMax);
		
		ArrayList<Integer> combinaisonList = new ArrayList<Integer>();
		ArrayList<Integer> reponseList = new ArrayList<Integer>();
		for (index = indexMax; index != 0; index--) {
			combinaisonList.add(0);
			reponseList.add(0);
			
		}
		exponant = (int) Math.pow(10, indexMax-1);
		for (index = 0; index < indexMax ; index++) {
			combinaisonList.set(index, combinaison / exponant % 10);
			reponseList.set(index, reponse/exponant % 10);
			exponant = exponant / 10;
		}
		
		System.out.print("Voici l'indice : ");
			for (index = 0; index < indexMax ; index++) {
				
				try {
				reponseSplit = reponseList.get(index);
				combinaisonSplit = combinaisonList.get(index);
					if (combinaisonSplit < reponseSplit) {
						System.out.print("+");
					}else if ( combinaisonSplit > reponseSplit) {
						System.out.print("-");
					}else System.out.print("=");
				}		
				catch (Exception e) {
					continue;
				}
				
		
		}
	
		System.out.println("\n"+"Vous avez rentrer : " +combinaison);
		if (combinaison == reponse) {
			 System.out.println("Vous avez réussi !!!");
			 break essai;
		}
		else {
			essai --;
			System.out.println("Il vous reste : "+ essai+" essai(s)");
		}
	}
		if(essai == 0)
		System.out.println("\nLa Solution est : "+ reponse);
		
	}
}
