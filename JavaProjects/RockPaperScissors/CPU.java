import java.util.Scanner;
import java.util.Random;

class CPU extends Player {
    private Random r;

    public CPU() {
        super("Computer");
        r = new Random();
    }

    public void makeChoice() {
        String[] choices = {"rock", "paper", "scissors"};
        int randomIndex = r.nextInt(choices.length);
        choice = choices[randomIndex];
    }
}