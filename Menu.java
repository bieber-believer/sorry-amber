/**
 * Handles the menu screens.
 * 
 * Menu
 */

import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[38;2;33;33;255m";
    private static final String GREEN = "\u001B[38;2;0;180;50m";
    private static final String YELLOW = "\u001B[38;2;255;255;0m";
    private static final String RED = "\u001B[38;2;255;0;0m";
    private static final String CYAN = "\u001B[38;2;0;255;255m";

    /**
     * Creates a Menu object using the given scannerm which will 
     * be used for reading input
     * @param scanner
     */
    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Displayes the main menu screen, showing New Game or New Game+ 
     * depending if a player has played
     * 
     * @param hasPlayedBefore whether the player has played before
     */
    public void displayMenu(boolean hasPlayedBefore) {

        System.out.println(BLUE + "       ************************************************");
        System.out.println(BLUE + "       *" + CYAN + "             Yohane The Parhelion!            " + BLUE + "*");
        System.out.println(BLUE + "       *" + CYAN + "        The Siren in the Mirror World!        " + BLUE + "*");
        System.out.println(BLUE + "       ************************************************");
        System.out.println();

        if (hasPlayedBefore)
            System.out.println(GREEN + "                 [N]ew Game+");
        else
            System.out.println(GREEN + "                 [N]ew Game");

        System.out.println(YELLOW + "                 [S]tatus");
        System.out.println(RED + "                 [Q]uit");
        System.out.println(BLUE + "       ************************************************" + RESET);
        System.out.println();
    }

    /**
     * Displayes the menu and reads player's choice
     * 
     * @param hasPlayedBefore whether the player has played before
     * @return choice of player
     */
    public char getChoice(boolean hasPlayedBefore) {
        char choice;
        String errorMessage = null;

        while (true) {
            displayMenu(hasPlayedBefore);

            if (errorMessage != null)
                System.out.println(RED + errorMessage);

            System.out.print("Your choice: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.isEmpty()) {
                errorMessage = "Invalid input! Please enter your choice.";
                continue;
            }

            choice = input.charAt(0);

            switch (choice) {
                case 'n':
                case 's':
                case 'q':
                    return choice;

                default:
                    errorMessage = "Invalid input! Enter N, S, or Q.";
            }
        }
    }

    /**
     * Displays the overall status screen
     * 
     * @param stats overall stats to read from
     */
    public void displayStatus(OverallStats stats) {

        System.out.println(BLUE + "       ************************************************" + RESET);
        System.out.println(CYAN + "                       Overall Status" + RESET );
        System.out.println(BLUE + "       ************************************************" + RESET);
        System.out.println();

        for (Idol idol : stats.getAqours()) {
            System.out.printf("        Times %-10s was saved %10d times%n",
                    idol.getName(),
                    idol.getRescueCount());
        }

        System.out.println();

        System.out.printf("        Times Siren was defeated %10d times%n",
                stats.getNumSiren());

        System.out.printf("        No. of game overs %17d times%n",
                stats.getGamesLost());

        System.out.printf("        Total gold spent %18d gp%n",
                stats.getGoldSpent());

        System.out.println();
        System.out.println(BLUE + "       ************************************************" + RESET);

        System.out.print("Press Enter to return to the menu...");
        scanner.nextLine();
    }

    /**
     * Displays the end screen of a run, either a win and game over, and
     * asks player if they wanna play again, go back to menu, or quit.
     * 
     * @param won whether the run ended in a win
     * @param hasPlayedBefore whether the player has played before
     * @param deathCause cause of Yohane's death if player lost
     * 
     * @return players choice on what to do next after the game
     */
    public char displayEndScreen(boolean won, boolean hasPlayedBefore, String deathCause) {
        System.out.println();

        if (won) {
            System.out.println(BLUE + "       ************************************************");
            System.out.println(BLUE + "       *" + GREEN + "                 DUNGEON COMPLETED            " + BLUE + "*");
            System.out.println(BLUE + "       *" + GREEN + "                 Chika is Rescued!            " + BLUE + "*");
            System.out.println(BLUE + "       ************************************************" + RESET);
        } else {
            System.out.println(BLUE + "       ************************************************");
            System.out.println(BLUE + "       *" + RED + "                 GAME OVER                " + BLUE + "*");

            String deathMessage = "You got killed by " + deathCause + "!";
            System.out.printf(BLUE + "       * " + RED + "%-44s" + BLUE + " *%n" + RESET, deathMessage);
            System.out.println(BLUE + "       ************************************************" + RESET);
        }

        System.out.println();

        if (hasPlayedBefore)
            System.out.println(GREEN + "                 [N]ew Game+" + RESET);
        else
            System.out.println(GREEN + "                 [N]ew Game" + RESET);

        System.out.println(YELLOW + "                 [M]ain Menu" + RESET);
        System.out.println(RED + "                 [Q]uit" + RESET);
        System.out.println();

        while (true) {
            System.out.print("Your choice: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.isEmpty()) {
                continue;
            }

            char choice = input.charAt(0);
            switch (choice) {
                case 'n':
                case 'm':
                case 'q':
                    return choice;
            }

            System.out.println(RED + "                 Invalid input! Please enter N, M, or Q." + RESET);
        }
    }

    /**
     * Displays the dungeon selection screen and read player input
     * 
     * @return the number of chosen dungeon
     */
    public int chooseDungeon() {
        while (true) {
            System.out.println(BLUE + "       ************************************************" + RESET);
            System.out.println(CYAN + "            Lailaps: Where are we heading, Yohane?" + RESET);
            System.out.println(BLUE + "       ************************************************" + RESET);
            System.out.println();

            System.out.println(GREEN + "                 [1] Izu-Mito Sea Paradise" + RESET);
            System.out.println(YELLOW + "                 [2] Under Construction" + RESET);
            System.out.println(RED + "                 [3] Under Construction" + RESET);
            System.out.println();

            System.out.print("Your choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    return 1;

                case "2":
                    return 2;

                case "3":
                    return 3;

                default:
                    System.out.println("Invalid input!");
            }
        }
    }
}
