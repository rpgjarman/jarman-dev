import java.util.Scanner;
import java.util.Random;

class User extends Player {
    public User(String name) {
        super(name);
    }

    public void makeChoice() {
        Scanner sc = new Scanner(System.in);
        System.out.print(name + ", enter your choice (rock, paper, or scissors): ");
        choice = sc.nextLine().toLowerCase();
    }
}