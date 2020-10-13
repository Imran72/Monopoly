/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */
package monopoly.bank;

import monopoly.base.ICall;
import monopoly.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bank implements ICall {
    public static Map<Player, Double> playerDictionary;
    public static double debtCoeff;
    public static double creditCoeff;

    public Bank() {
        playerDictionary = new HashMap<Player, Double>();
    }

    boolean TakeDebt(Player player) {
        if (player.getMoney() >= playerDictionary.get(player)) {
            player.setMoney(player.getMoney() - playerDictionary.get(player));
            playerDictionary.remove(player);
            return true;
        }
        return false;
    }

    boolean TakeCredit(double sum, Player player) {
        playerDictionary.put(player, sum * creditCoeff);
        player.addMoney(sum);
        return true;
    }

    @Override
    public boolean Call(Player player) {
        if (playerDictionary.containsKey(player)) {
            System.out.println("You are in the office of the bank to which you are the debtor." +
                    " Trying to collect your debt...");
            return TakeDebt(player);
        } else {
            String answer;
            double num = 0;
            Scanner in = new Scanner(System.in);
            System.out.println("You are in the bank office. Would you like to get a credit?" +
                    " Input how many you want to get or ’No’!");
            answer = in.next();
            while (!answer.equals("No") && (num = TryParse(answer)) == -1 && num > player.getExpenses() * creditCoeff) {
                System.out.printf("Something went wrong! Input how many you want to get or ’No’! " +
                        "The maximum amount of your credit can be %d.", creditCoeff * player.getExpenses());
                answer = in.next();
            }
            if (answer.equals("No"))
                return true;
            else
                TakeCredit(num, player);
        }
        return true;
    }

    @Override
    public void CallInfo(String pos) {
        String info = String.format("Dear player, you are currently in cell %s. Welcome to the %s.", pos, "BankOffice");
        System.out.println(info);
    }

    @Override
    public char Name(Player player) {
        return '$';
    }

    public static double TryParse(String answer) {
        double num;
        try {
            num = Double.parseDouble(answer);
            return num;
        } catch (Exception e) {
            return -1;
        }
    }
}
