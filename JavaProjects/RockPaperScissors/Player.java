abstract class Player {
    protected String name;
    protected String choice;

    public Player(String name) {
        this.name = name;
    }

    public abstract void makeChoice();

    public String getChoice() {
        return choice;
    }
}