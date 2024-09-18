import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {
    public static void main(String[] args) {
        int count = 1;
        int p1 = 0;
        int p2 = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
            
        while(count < 4){
            User player = new User(playerName);
            CPU computerPlayer = new CPU();

            player.makeChoice();
            computerPlayer.makeChoice();

            System.out.println(playerName + " chooses: " + player.getChoice());
            System.out.println("Computer chooses: " + computerPlayer.getChoice());

            String playerChoice = player.getChoice();
            String computerChoice = computerPlayer.getChoice();

            if (!playerChoice.equals("rock") && !playerChoice.equals("scissors") && !playerChoice.equals("paper")){
                System.out.println("Try Again with a valid choice, match case. Either: rock, paper, or scissors");
                break;
            }
            else if (playerChoice.equals(computerChoice)) {
                System.out.println("It's a tie!");
            } else if ((playerChoice.equals("rock") && computerChoice.equals("scissors")) ||
                    (playerChoice.equals("paper") && computerChoice.equals("rock")) ||
                       (playerChoice.equals("scissors") && computerChoice.equals("paper"))) {
                System.out.println(playerName + " wins the round!");
                p1++;
            } else {
                System.out.println("The computer wins the round!");
                p2++;
            }
            count++;
        }

        System.out.println("Game Over");
        System.out.println(playerName + "'s score: " + p1);
        System.out.println("Computer's score: " + p2);

        if(p1 < p2){
            System.out.println("You " + playerName + " won the game!!!");
        }
        else if(p2 > p1){
            System.out.println("You lost against the Computer (CPU)");
        }
        else{
            System.out.println("The game is a draw");
        }

        scanner.close();
    }
}