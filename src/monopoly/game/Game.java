package monopoly.game;

import monopoly.bank.Bank;
import monopoly.field.Field;
import monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class of an abstract Game. It imitates real monopoly game,
 * support interaction between game and players
 */
public class Game {
    public static Random rnd = new Random(); // to generate random numbers
    public static List<Player> playersList; // list of current players
    int width; // width of playing field
    int height; // height of playing field
    int step; // turn in the game
    int cells; // number of cells in field
    int playerMoney; // money of real player
    double creditCoeff;
    double debtCoeff;
    double penaltyCoeff;
    boolean playerState; // True = player is alive, has money > 0
    boolean botState; // True = bot is alive, has money > 0
    Field field; // object of field


    /**
     * Constructor
     * Makes game be alive till someone lose
     */
    public Game(int width, int height, int money) {
        // full data
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
        Player bot = new Player(money, cells);
        playersList.add(player);
        // Messages to players
        System.out.println("Let the game begin!");
        System.out.println("Playing field:");
        field.GetConsoleField(null);
        System.out.printf("Data generated:\ncreditCoeff = %.2f\ndebtCoeff = %.2f\npenaltyCoeff = %.2f\n",
                creditCoeff, debtCoeff, penaltyCoeff);
        // Turn
        if (step == 0)
            System.out.println("You go first!");
        else
            System.out.println("Bot goes first!");
        // Until someone loses this will happen
        while (true) {
            if (!PlayerMove(step == 0 ? player : bot, step)) {
                if (step == 0)
                    playerState = false;
                else
                    botState = false;
                break;
            }
            // display information about current situation
            field.GetConsoleField(step == 0 ? player : bot);
            String Info = String.format("Player:\nYour balance: %d\nYou are in the cell %s",
                    player.getMoney(), field.GetCoordinates(player.getPosition()));
            System.out.println(Info);
            Info = String.format("Bot:\nBot's balance: %d\nBot is in the cell %s",
                    bot.getMoney(), field.GetCoordinates(bot.getPosition()));
            System.out.println(Info);
            step = (step + 1) % 2; //  change turn
        }
        // depending on bot/player state, it will be chosen a winner
        if (playerState)
            System.out.println("You won! The game is over.");
        else
            System.out.println("You have lost and the bot has won! The game is over.");
    }

    /**
     * Method is meant to imitate a move like in real Monopoly
     * Firstly, generates renadom number [1, 6]
     * Then player/bot moves X cells forward
     * And depending on cells display some info about cell
     * and run interaction between player and cell
     */
    public boolean PlayerMove(Player player, int step) {
        int move = (int) (Math.random() * 6 + 1) + (int) (Math.random() * 6 + 1); // generates a sum of two random numbers
        boolean state; // state of method. is FALSE if something goes wrong, otherwise TRUE
        player.addPosition(move); // add number to position
        // In case of TAXI turn can repeat
        while (field.getMap(player.getPosition()).getClass().getSimpleName().equals("Taxi")) {
            field.getMap(player.getPosition()).CallInfo(field.GetCoordinates(player.getPosition()),
                    step == 0 ? "player" : "bot");
            field.getMap(player.getPosition()).Call(player, step == 0 ? "player" : "bot");
        }
        // display info about cell
        field.getMap(player.getPosition()).CallInfo(field.GetCoordinates(player.getPosition()),
                step == 0 ? "player" : "bot");
        // interaction between player and cell
        state = field.getMap(player.getPosition()).Call(player, step == 0 ? "player" : "bot");
        return state;
    }

}
