package MastermindGame;

import util.GameUtilitaire;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class MastermindDuel2 extends Mastermind{


    public void mastermindDuel(int developer) {
        int essai, indexMax, responseToFind, responseProgram, turn, numberAllowed,
                indexNumber, programResponseToFind, RNRP, RNWP, userValue, exponant, index;
        Random random = new Random();
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

        ArrayList<Integer> guessResponse = new ArrayList<Integer>();
        ArrayList<Integer> allPossibilities = new ArrayList<Integer>();
        ArrayList<Integer> allPossibilitiesCheck = new ArrayList<Integer>();

        indexMax = Integer.parseInt(prop.getProperty("case"));

        // solution que  le joueur doit trouver
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

            allPossibilitiesCheck.add(0);
        }

        int indexCheck,possibleValue;

        int smallestValue = gameUtil.startAllChoice(indexMax);
        int biggestValue = gameUtil.endAllChoice(indexMax, numberAllowed);

        for (index = smallestValue; index < biggestValue; index++ ) {
            allPossibilities.add(index);

        }

        System.out.println("Tout d'abord il faut entrer la reponse que l'ordinateur doit trouver. ");
        programResponseToFind = gameUtil.inputUserValueMastermind(indexMax, numberAllowed);

        MDuel:
        while (essai != 0) {
            RNRP = 0;
            RNWP = 0;
            System.out.println("\nTour " + turn + "\nNombre d'essai restant : " + essai);
            if (developer == 1) {
                System.out.println("La Reponse Du Jeu est : " + responseToFind);
            }

            userValue = gameUtil.inputUserValueMastermind(indexMax, numberAllowed);

            exponant = (int) Math.pow(10, indexMax - 1);

            // Rangement des réponses/solutions dans leurs List respective
            for (index = 0; index < indexMax; index++) {
                userValueListDuel.set(index, userValue / exponant % 10);
                userResponseToFindListDuel.set(index, responseToFind / exponant % 10);
                programResponseToFindListDuel.set(index, programResponseToFind / exponant % 10);
                exponant = exponant / 10;
            }

            // Creation des List Check identique aux List précédente
            for (index = 0; index < indexMax; index++) {
                userValueCheckListDuel.set(index, userValueListDuel.get(index));
                userResponseToFindCheckListDuel.set(index, userResponseToFindListDuel.get(index));
                programResponseToFindCheckListDuel.set(index, programResponseToFindListDuel.get(index));
            }
            // Verification si il y a des Bien Placés

            for (index = 0; index < indexMax; index++) {
                if (userResponseToFindCheckListDuel.get(index) == userValueCheckListDuel.get(index)) {
                    RNRP++;
                    userValueCheckListDuel.set(index, -1);
                    userResponseToFindCheckListDuel.set(index, -2);
                }
            }

            // Verification si il y a des Bien Présent
            for (index = 0; index < indexMax; index++) {
                if (userResponseToFindCheckListDuel.contains(userValueCheckListDuel.get(index))) {
                    RNWP++;
                }
            }

            if (RNRP == indexMax) {
                System.out.println("Bien placé : " + RNRP + " !!!! \nBravo vous avez réussi, vous avez gagner!!!");
                break MDuel;

            }

            else{
                System.out.println("Bien placé : " + RNRP + "\nBien Présent : " + RNWP);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    continue;
                }




                if (indexMax > 5){
                    System.out.println("Le Programme reflechi patience patience...");
                }
                for (index =0; index < allPossibilities.size(); index++) {


                    possibleValue = allPossibilities.get(index);

                    for (int i = numberAllowed + 1; i < 10; i++) {
                        if (String.valueOf(possibleValue).contains(String.valueOf(i))) {
                            allPossibilities.remove(index);
                            index--;
                            break;
                        }
                    }
                }
                int CRNRP,CRNWP;
                RNRP = 0; RNWP = 0;
                // RNRP = Right Number Right Place / RNWP = Right Number Wrong Place / CRNRP = Check Right Number Right Place / CRNWP = Check Right Number Wrong Place
                exponant = (int) Math.pow(10, indexMax-1);
                for (index = 0; index < indexMax ; index++) {
                    responseProgramListDuel.set(index, responseProgram / exponant % 10);
                    programResponseToFindListDuel.set(index,  programResponseToFind/exponant % 10);
                    exponant = exponant / 10;
                }

                for (index = 0; index < indexMax; index++) {

                    responseProgramCheckListDuel.set(index, responseProgramListDuel.get(index));
                    programResponseToFindCheckListDuel.set(index, programResponseToFindListDuel.get(index));
                }

                for (index = 0; index < indexMax; index++) {
                    if (responseProgramCheckListDuel.get(index) == programResponseToFindCheckListDuel.get(index)) {
                        RNRP++;
                        responseProgramCheckListDuel.set(index, -1);
                        programResponseToFindCheckListDuel.set(index, -2);
                    }
                }

                for (index = 0; index < indexMax; index++) {
                    if (responseProgramCheckListDuel.contains(programResponseToFindCheckListDuel.get(index))) {
                        programResponseToFindCheckListDuel.set(index, -2);
                        RNWP++;
                    }
                }

                if (RNRP == indexMax) {
                    System.out.println("La Reponse de l'ordinateur : " + responseProgram + "\nLa Solution : "
                            + programResponseToFind + "\nBien placé : " + RNRP
                            + " !!!! \nVous avez perdu L'Ordinateur a trouvé la réponse!!!");
                    break MDuel;}

                else {


                    System.out.println("La Reponse de l'ordinateur : " + responseProgram + "\nLa Solution : "
                            + programResponseToFind + "\nBien placé : " + RNRP + "\nBien Présent : " + RNWP);

                    //Grande boucle pour ajouter chaque reponse probable a la list guessResponse
                    for(indexCheck = 0; indexCheck < allPossibilities.size();indexCheck++) {

                        CRNRP = 0;
                        CRNWP = 0;

                        int allPossibilityListValue = allPossibilities.get(indexCheck);

                        exponant = (int) Math.pow(10, indexMax - 1);
                        for (index = 0; index < indexMax; index++) {
                            allPossibilitiesCheck.set(index, allPossibilityListValue / exponant % 10);
                            responseProgramListDuel.set(index, responseProgram / exponant % 10);
                            exponant = exponant / 10;
                        }
                        for (index = 0; index < indexMax; index++) {
                            if (allPossibilitiesCheck.get(index) == responseProgramListDuel.get(index)) {
                                CRNRP++;
                                allPossibilitiesCheck.set(index, -1);
                                responseProgramListDuel.set(index, -2);
                            }
                        }
                        for (index = 0; index < indexMax; index++) {
                            if (responseProgramListDuel.contains(allPossibilitiesCheck.get(index))) {
                                CRNWP++;
                                allPossibilitiesCheck.set(index, -3);


                            }
                        }
                        if (CRNRP == RNRP && CRNWP == RNWP) {
                            guessResponse.add(allPossibilityListValue);
                        }
                    }
                        responseProgram = guessResponse.get(random.nextInt(guessResponse.size()));

// reduit la liste de tout les possibles a la liste des reponses probables
                        allPossibilities.clear();
                        for (index = 0; index < guessResponse.size();index++) {
                            allPossibilities.add(0);
                        }
                        for(index = 0; index < allPossibilities.size();index++) {
                            allPossibilities.set(index, guessResponse.get(index));

                        }
                        guessResponse.clear();




                    essai--;turn++;

                }


            }
        }
    }
}
