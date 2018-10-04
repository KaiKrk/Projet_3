package MastermindGame;

import util.GameUtilitaire;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class MastermindDuel2 extends Mastermind{


    public void mastermindDuel(int developer) {
        int essai, indexMax, responseToFind, responseProgram, responseProgramSplit, min, max, turn, numberAllowed,
                indexNumber, programResponseToFind, RNRP, RNWP, userValue, exponant, index, changeValuePlus,
                changeValueMinus, addition;
        GameUtilitaire gameUtil = new GameUtilitaire();
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
        logger.info("Mastermind Mode Duel");
        turn = 1;
        essai = Integer.parseInt(prop.getProperty("essai"));
        numberAllowed = Integer.parseInt(prop.getProperty("numberAllowed"));

        ArrayList<Integer> maxListDuel = new ArrayList<Integer>();
        ArrayList<Integer> minListDuel = new ArrayList<Integer>();

        ArrayList<Integer> userValueListDuel = new ArrayList<Integer>();
        ArrayList<Integer> userValueCheckListDuel = new ArrayList<Integer>();

        ArrayList<Integer> userResponseToFindListDuel = new ArrayList<Integer>();
        ArrayList<Integer> userResponseToFindCheckListDuel = new ArrayList<Integer>();

        ArrayList<Integer> programResponseToFindListDuel = new ArrayList<Integer>();
        ArrayList<Integer> programResponseToFindCheckListDuel = new ArrayList<Integer>();

        ArrayList<Integer> responseProgramListDuel = new ArrayList<Integer>();
        ArrayList<Integer> responseProgramCheckListDuel = new ArrayList<Integer>();

        indexMax = Integer.parseInt(prop.getProperty("case"));
        responseToFind = gameUtil.generateRandomNumberMastermind(indexMax, numberAllowed);
        responseProgram = gameUtil.generateRandomNumberMastermind(indexMax, numberAllowed);

        for (indexNumber = 0; indexNumber < indexMax; indexNumber++) {

            userValueListDuel.add(0);
            userValueCheckListDuel.add(0);

            userResponseToFindListDuel.add(0);
            userResponseToFindCheckListDuel.add(0);

            programResponseToFindListDuel.add(0);
            programResponseToFindCheckListDuel.add(0);

            maxListDuel.add(10);
            minListDuel.add(0);

            responseProgramListDuel.add(0);
            responseProgramCheckListDuel.add(0);
        }

        System.out.println("Tout d'abord il faut entrer la reponse que l'ordinateur doit trouver. ");
        programResponseToFind = gameUtil.inputUserValueMastermind(indexMax, numberAllowed);

        MDuel:
        while (essai != 0) {
            RNRP = 0;
            RNWP = 0;
            System.out.println("Tour n'" + turn + "\nNombre d'essai restant : " + essai);
            if (developer == 1) {
                System.out.println("La Reponse Du Jeu est : " + responseToFind);
//				gameUtil.inputUserValueMastermind(indexMax, numberAllowed);
            }

            userValue = gameUtil.inputUserValueMastermind(indexMax, numberAllowed);

            exponant = (int) Math.pow(10, indexMax - 1);

            // Rangement des r�ponses/solutions dans leurs List respective
            for (index = 0; index < indexMax; index++) {
                userValueListDuel.set(index, userValue / exponant % 10);
                userResponseToFindListDuel.set(index, responseToFind / exponant % 10);
                programResponseToFindListDuel.set(index, programResponseToFind / exponant % 10);
                exponant = exponant / 10;
            }

            // Creation des List Check identique aux List pr�c�dente
            for (index = 0; index < indexMax; index++) {
                userValueCheckListDuel.set(index, userValueListDuel.get(index));
                userResponseToFindCheckListDuel.set(index, userResponseToFindListDuel.get(index));
                programResponseToFindCheckListDuel.set(index, programResponseToFindListDuel.get(index));
            }
            // Verification si il y a des Bien Plac�s

            for (index = 0; index < indexMax; index++) {
                if (userResponseToFindCheckListDuel.get(index) == userValueCheckListDuel.get(index)) {
                    RNRP++;
                    userValueCheckListDuel.set(index, -1);
                    userResponseToFindCheckListDuel.set(index, -2);
                }
            }

            // Verification si il y a des Bien Pr�sent
            for (index = 0; index < indexMax; index++) {
                if (userResponseToFindCheckListDuel.contains(userValueCheckListDuel.get(index))) {
                    RNWP++;
                }
            }

            if (RNRP == indexMax) {
                System.out.println("Bien plac� : " + RNRP + " !!!! \nBravo vous avez r�ussi, vous avez gagner!!!");
                break MDuel;

            }
            else{

            }
        }
    }
}
