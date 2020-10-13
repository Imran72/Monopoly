package monopoly.game;

import monopoly.bank.Bank;
import monopoly.field.Field;
import monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public static Random rnd = new Random();
    public static List<Player> playersList;
    int width;
    int height;
    int step;
    double playerMoney;
    double creditCoeff;
    double debtCoeff;
    double penaltyCoeff;
    boolean playerState;
    boolean botState;
    int cells;
    Field field;


    public Game(int width, int height, double money) {
        playersList = new ArrayList<Player>();
        this.height = height;
        this.width = width;
        this.cells = this.width * 2 + this.height * 2 - 4;
        this.step = (int) (Math.random() * 2);
        this.playerMoney = money;
        creditCoeff = rnd.nextDouble() * (0.2 - 0.002) + 0.002;
        debtCoeff = rnd.nextDouble() * (3.0 - 1.0) + 1.0;
        penaltyCoeff = rnd.nextDouble() * (0.1 - 0.01) + 0.01;
        field = new Field(width, height, penaltyCoeff);
        Bank.debtCoeff = debtCoeff;
        Bank.creditCoeff = creditCoeff;
        Player player = new Player(money, cells);
        playersList.add(player);
        System.out.println("Let the game begin!");
        System.out.println("Playing field:");
        field.GetConsoleField(null);
        System.out.printf("Data generated:\ncreditCoeff = %.2f\ndebtCoeff = %.2f\npenaltyCoeff = %.2f\n",
                creditCoeff, debtCoeff, penaltyCoeff);
        while (true) {
            if (!PlayerMove(player)) {
                playerState = false;
                break;
            }
        }

    }

    public boolean PlayerMove(Player player) {
        int move = (int) (Math.random() * 6) + 1;
        boolean state;
        player.addPosition(move);
        while (field.getMap(player.getPosition()).getClass().getSimpleName().equals("Taxi")) {
            field.getMap(player.getPosition()).CallInfo(field.GetCoordinates(player.getPosition()));
            state = field.getMap(player.getPosition()).Call(player);
            //field.GetConsoleField(player);
        }
        field.getMap(player.getPosition()).CallInfo(field.GetCoordinates(player.getPosition()));
        state = field.getMap(player.getPosition()).Call(player);
        return state;
    }

}
