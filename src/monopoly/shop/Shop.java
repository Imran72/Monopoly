/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */
package monopoly.shop;

import monopoly.base.ICall;
import monopoly.player.Player;

import java.util.Random;
import java.util.Scanner;


public class Shop implements ICall {
    public static Random rnd = new Random();
    Player owner;
    double N;
    double K;
    double compensationCoeff;
    double improvementCoeff;

    public Shop() {
        N = rnd.nextDouble() * (500 - 50) + 50;
        K = rnd.nextDouble() * (0.9 * N - 0.5 * N) + 0.5 * N;
        compensationCoeff = rnd.nextDouble() * (1 - 0.1) + 0.1;
        improvementCoeff = rnd.nextDouble() * (2 - 0.1) + 0.1;
    }

    void UpgradeShop(Player player) {
        player.addMoney(-N);
        N += improvementCoeff * N;
        K += compensationCoeff * K;
    }

    void BecomeOwner(Player player) {
        owner = player;
        player.addMoney(-N);
    }

    @Override
    public boolean Call(Player player) {
        String offer;
        if (owner == null) {
            offer = String.format("This shop has no owner. Would you like to buy it for %.2f$?" +
                    " Input ‘Yes’ if you agree or ‘No’ otherwise.", N);
            System.out.println(offer);
            Scanner in = new Scanner(System.in);
            String ans = in.next();
            while (!ans.equals("Yes") && !ans.equals("No"))
            {
                System.out.println("Input ‘Yes’ if you agree or ‘No’ otherwise.");
                ans = in.next();
            }
            if (ans.equals("Yes"))
                this.BecomeOwner(player);

        } else if (player.equals(owner)) {
            offer = String.format("Would you like to upgrade it for %.2f$?" +
                    " Input ‘Yes’ if you agree or ‘No’ otherwise».", N);
            System.out.println(offer);
            Scanner in = new Scanner(System.in);
            String ans = in.next();
            while (!ans.equals("Yes") && !ans.equals("No"))
            {
                System.out.println("Input ‘Yes’ if you agree or ‘No’ otherwise.");
                ans = in.next();
            }
            if (ans.equals("Yes"))
                this.UpgradeShop(player);
        } else {
            offer = "You have entered your opponent's shop. You have to pay him compensation.";
            System.out.println(offer);
            if (player.getMoney() > this.K)
            {
                player.addMoney(-K);
                this.owner.addMoney(K);
            }
            else
                return false;
        }
        return true;
    }

    @Override
    public void CallInfo(String pos) {
        String info = String.format("Dear player, you are currently in cell %s. Welcome to the %s.", pos, "Shop");
        System.out.println(info);
    }

    @Override
    public char Name(Player player) {
        if (owner == null)
            return 'S';
        if (player == owner)
            return 'M';
        return 'O';
    }
}
