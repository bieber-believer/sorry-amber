import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    private static final Color blue = new Color(0x2121FF);
    private static final Color green = new Color(0x00B432);
    private static final Color yellow = new Color(0xFFFF00);
    private static final Color red = new Color(0xFF0000);
    private static final Color cyan = new Color(0x00FFFF);

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void displayMenu(boolean hasPlayedBefore) {
        clearScreen();

        System.out.println(blue + "       ************************************************");
        System.out.println(blue + "       *" + cyan + "             Yohane The Parhelion!            " + blue + "*");
        System.out.println(blue + "       *" + cyan + "        The Siren in the Mirror World!        " + blue + "*");
        System.out.println(blue + "       ************************************************");
        System.out.println();

        if (hasPlayedBefore)
            System.out.println(green + "                 [N]ew Game+");
        else
            System.out.println(green + "                 [N]ew Game");

        System.out.println(yellow + "                 [S]tatus");
        System.out.println(red + "                 [Q]uit");
        System.out.println(blue + "       ************************************************");
        System.out.println();
    }

    public char getChoice(boolean hasPlayedBefore) {
        char choice;
        String errorMessage = null;

        while (true) {
            displayMenu(hasPlayedBefore);

            if (errorMessage != null)
                System.out.println(red + errorMessage);

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

    public void displayStatus(OverallStats stats) {
        clearScreen();

        System.out.println(blue + "       ************************************************");
        System.out.println(cyan + "                    Overall Status");
        System.out.println(blue + "       ************************************************");
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
        System.out.println(blue + "       ************************************************");

        System.out.print("Press Enter to return to the menu...");
        scanner.nextLine();
    }
}
