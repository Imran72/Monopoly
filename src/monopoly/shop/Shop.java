/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */
package monopoly.shop;

import monopoly.base.ICall;
import monopoly.player.Player;

import java.util.Random;
import java.util.Scanner;

/**
 * Special class for realization abstract 'shop'
 * Implements interface 'ICall'
 * Player is able to buy, upgrade a shop, pay compensation as well
 */
public class Shop implements ICall {
    public static Random rnd = new Random();
    Player owner; // owner of a shop
    int N; // shop's cost
    int K; // shop's compensation
    double compensationCoeff;
    double improvementCoeff;

    /**
     * Constructor
     */
    public Shop() {
        N = (int) Math.round(rnd.nextDouble() * (500 - 50) + 50);
        K = (int) Math.round(rnd.nextDouble() * (0.9 * N - 0.5 * N) + 0.5 * N);
        compensationCoeff = rnd.nextDouble() * (1 - 0.1) + 0.1;
        improvementCoeff = rnd.nextDouble() * (2 - 0.1) + 0.1;
    }

    /**
     * Method takes a player and upgrades shop using player's money
     */
    void UpgradeShop(Player player) {
        player.addMoney(-N);
        player.addExpenses(N);
        N += Math.round(improvementCoeff * N);
        K += Math.round(compensationCoeff * K);
    }

    /**
     * Method is meant to make a player as shop's owner after payment
     */
    void BecomeOwner(Player player) {
        owner = player;
        player.addMoney(-N);
        player.addExpenses(N);
    }

    /**
     * Special method for interaction between bot and shop
     */
    public boolean BotShop(Player player) {
        int num = rnd.nextInt(2);
        if (owner == null) {
            if (num == 1) {
                BecomeOwner(player);
                System.out.println("Bot became a shop's owner!");
            }
        } else if (player.equals(owner)) {
            if (num == 1) {
                UpgradeShop(player);
                System.out.println("Bot upgraded its shop!");
            }
        } else {
            if (player.getMoney() > this.K) {
                player.addMoney(-K);
                this.owner.addMoney(K);
                System.out.println("Bot paid compensation!");
            } else
                return false;
        }
        return true;
    }

    /**
     * implementaion of interface. Support the interaction between player andd shop
     * There are three possibility
     * - Buy no one's shop
     * - Upgrade your own shop
     * - Pay compensation to owner of the shop
     */
    @Override
    public boolean Call(Player player, String step) {
        if (step.equals("bot"))
            return BotShop(player);
        String offer; // offer to player
        if (owner == null) {
            offer = String.format("This shop has no owner. Would you like to buy it for %d$?" +
                    " Input ‘Yes’ if you agree or ‘No’ otherwise.", N);
            System.out.println(offer);
            Scanner in = new Scanner(System.in);
            String ans = in.next();
            while (!ans.equals("Yes") && !ans.equals("No")) {
                System.out.println("Input ‘Yes’ if you agree or ‘No’ otherwise.");
                ans = in.next();
            }
            if (ans.equals("Yes"))
                this.BecomeOwner(player);

        } else if (player.equals(owner)) {
            offer = String.format("Would you like to upgrade it for %d$?" +
                    " Input ‘Yes’ if you agree or ‘No’ otherwise».", N);
            System.out.println(offer);
            Scanner in = new Scanner(System.in);
            String ans = in.next();
            while (!ans.equals("Yes") && !ans.equals("No")) {
                System.out.println("Input ‘Yes’ if you agree or ‘No’ otherwise.");
                ans = in.next();
            }
            if (ans.equals("Yes"))
                this.UpgradeShop(player);
        } else {
            offer = "You have entered your opponent's shop. You have to pay him compensation.";
            System.out.println(offer);
            if (player.getMoney() > this.K) {
                player.addMoney(-K);
                this.owner.addMoney(K);
            } else
                return false;
        }
        return true;
    }

    /**
     * implementaion of interface. Notification to player about his position.
     */
    @Override
    public void CallInfo(String pos, String name) {
        String info = String.format("Dear %s, you are currently in cell %s. Welcome to the %s.", name, pos, "Shop");
        System.out.println(info);
    }

    /**
     * implementaion of interface. Returns a sign which depends on relation between player and current shop
     */
    @Override
    public char Name(Player player) {
        if (owner == null)
            return 'S';
        if (player == owner)
            return 'M';
        return 'O';
    }
}
