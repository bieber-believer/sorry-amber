public class FinalBattle {

    private Yohane yohane;
    private Lailaps lailaps;
    private Siren siren;

    private ArrayList<Bat> bats;

    private int switchesActivated;
    private int moveCounter;

    private boolean barriersBroken;
    private boolean exitSpawned;

	public FinalBattle(Yohane yohane) {
				this.yohane = yohane;

				lailaps = new Lailaps(startRow,startCol);
				siren = new Siren(startRow,startCol);

				bats = new ArrayList<>();

				switchesActivated = 0;
				moveCounter = 0;

				barriersBroken = false;
				exitSpawned = false;
 }

	public void displayBattle() {

	} 

/**
* Moves Yohane and Lailaps
*/
	public void movePlayers() {
			// read player input
			String input = scanner.nextLine().toUpperCase();

			if (input.isEmpty())
						return;

			char move = input.charAt(0);

			switch (move) {
        case 'W':
            movePlayer(-1, 0);
            moveLailaps(-1, 0);
            break;

        case 'S':
            movePlayer(1, 0);
            moveLailaps(1, 0);
            break;

        case 'A':
            movePlayer(0, -1);
            moveLailaps(0, -1);
            break;

        case 'D':
            movePlayer(0, 1);
            moveLailaps(0, 1);
            break;

        default:
            return;
			}
			// count player move
			moveCounter++;
}


/**
* Handles Lailaps' movement
*/
public void moveLailaps(int rowChange, int colChange) {

    int newRow = lailaps.getRow() + rowChange;
    int newCol = lailaps.getCol() + colChange;

    if (!isInsideMap(newRow, newCol))
        return;

    Tile target = map[newRow][newCol];

    if (!target.isPassable)
        return;

    lailaps.setPosition(newRow, newCol);
}
	}

private void checkSwitches() {
				// check if Yohane is on her switch
    boolean yohaneOnSwitch = isYohaneOnSwitch();

    // check if Lailaps is on his switch
    boolean lailapsOnSwitch = isLailapsOnSwitch();

    // both switches activated
    if (yohaneOnSwitch && lailapsOnSwitch) {
        switchesActivated++;
        // remove the barriers after the third activation
        if (switchesActivated == 3) {
            barriersBroken = true;
        }

        // otherwise generate another pair of switches
        else {
            spawnSwitches();
        }
}

}