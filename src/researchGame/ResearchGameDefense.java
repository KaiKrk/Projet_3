package researchGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import util.GameUtilitaire;

public class ResearchGameDefense {
	private final String Welcome_Message = "Bienvenue dans le mode défenseur du Jeu de Recherche dans ce Jeu.\n Vous devez ici rentrer la combinaison et l'ordinateur va essayer de trouver la combinaison\n\n";
	public void reseachDefense() {
		int saisieUtilisateurInt, responseProgram,responseProgramSplit,min,max,indexMax,exponant,index,addition,
		changeValuePlus,changeValueMinus,responseValueUser;
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
		int essai = Integer.parseInt(prop.getProperty("essai")); 
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
		
		
		
		RDefense : while(essai != 0) {
			System.out.println("Nombre d'essai restant : " + essai);
			
			if(responseProgram == saisieUtilisateurInt) {
				System.out.println("La Reponse de l'ordinateur : " + responseProgram  + "\nLa Solution : " + saisieUtilisateurInt);
				System.out.println("Vous avez perdu l'ordinateur a trouvé votre reponse!");
				break;
			}
			else if(essai == 0 ) {
				System.out.println("Vous avez gagner l'ordinateur n'a pas réussi a trouver votre reponse !!!");
				break;
			}
			else {
				System.out.println("La Reponse de l'ordinateur : " + responseProgram  + "\nLa Solution : " + saisieUtilisateurInt);
				essai--;
			}
			
			
			exponant = (int) Math.pow(10, indexMax-1);
			for (index = 0; index < indexMax ; index++) {
				responseProgramList.set(index, responseProgram / exponant % 10);
				userValueList.set(index, saisieUtilisateurInt/exponant % 10);
				exponant = exponant / 10;
			}
			
				
			for (index = 0; index < indexMax ; index++) {
				responseProgramSplit = responseProgramList.get(index);
				responseValueUser = userValueList.get(index);
				
					
					if(responseProgramSplit < responseValueUser) {
						minList.set(index, responseProgramSplit);
						
						min = minList.get(index);max = maxList.get(index) ;
						changeValuePlus = gameUtil.generateRandomNumberBounds(min,max);
						responseProgramList.set(index, changeValuePlus)  ;
					}
					else if(responseProgramSplit > responseValueUser){
						maxList.set(index, responseProgramSplit);
						
						min = minList.get(index);max = maxList.get(index) ;
						changeValueMinus = gameUtil.generateRandomNumberBounds(min,max);
						responseProgramList.set(index, changeValueMinus);
					}
					else if (responseValueUser == responseProgramSplit) {
						responseProgramList.set(index, responseProgramSplit);
					}
				
			}
			
			
			responseProgram = 0 ;
			for(index= 0; index < indexMax; index++) {
				addition = (int) (responseProgramList.get(index)*Math.pow(10, indexMax-1-index));
				responseProgram =  responseProgram + addition;
			}
				
			
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					continue;
				}
		}
	
	}
}
