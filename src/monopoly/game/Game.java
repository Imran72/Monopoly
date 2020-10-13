package monopoly.game;

import monopoly.bank.Bank;
import monopoly.field.Field;
import monopoly.player.Player;

public class Game {
    int width;
    int height;
    double playerMoney;
    double creditCoeff;
    double debtCoeff;
    double penaltyCoeff;
    boolean playerState;
    boolean botState;
    int cells;
    Field field;


    public Game(int width, int height, double money) {
        this.height = height;
        this.width = width;
        this.cells = this.width * 2 + this.height * 2 - 4;
        this.playerMoney = money;
        creditCoeff = Math.random() * (0.2 - 0.002) + 0.002;
        debtCoeff = Math.random() * (3.0 - 1.0) + 1.0;
        penaltyCoeff = Math.random() * (0.1 - 0.01) + 0.01;
        field = new Field(width, height, penaltyCoeff);
        Bank.debtCoeff = debtCoeff;
        Bank.creditCoeff = creditCoeff;
        Player player = new Player(money, cells);
        System.out.println("Let the game begin! \nData generated:");
        System.out.printf("creditCoeff = %.2f\ndebtCoeff = %.2f\npenaltyCoeff = %.2f\n",
                creditCoeff, debtCoeff, penaltyCoeff);
        while (true) {
            if (!PlayerMove(player)) {
                playerState = false;
                break;
            }
        }

    }

    public boolean PlayerMove(Player player) {
        int move = (int) (Math.random() * 7);
        player.addPosition(move);
        boolean state = field.getMap(player.getPosition()).Call(player);
        return state;
    }

}
