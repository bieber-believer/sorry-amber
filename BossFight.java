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
		lailaps = new Lailaps;
		siren = new Siren;
		bats = new ArrayList<>();
		switchesActivated = 0;
		barriersBroken = false;
		exitSpawned = false;
    }

	public void displayBattle() {

	} 

	public void movePlayers() {

	}

}