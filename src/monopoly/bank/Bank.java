/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */
package monopoly.bank;

import monopoly.base.ICall;
import monopoly.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Special class for realization abstract 'bank'
 * Implements interface 'ICall'
 * The player can take a credit at a bank office and pay off the debt if he again ends up in a bank office
 */
public class Bank implements ICall {
    public static Map<Player, Integer> playerDictionary; // list of deptors
    public static double debtCoeff;
    public static double creditCoeff;

    /**
     * Constructor
     */
    public Bank() {
        playerDictionary = new HashMap<Player, Integer>();
    }

    /**
     * Method is meant to check an players' opportunity to pay off the debt
     * In case he has this opportunity --> he pays off and bank removes him from 'list of deptors'. Returns TRUE
     * In case he has no this opportunity method returns FALSE
     */
    boolean TakeDebt(Player player) {
        if (player.getMoney() >= playerDictionary.get(player)) {
            player.addMoney(-playerDictionary.get(player));
            playerDictionary.remove(player);
            return true;
        }
        return false;
    }

    /**
     * Method is meant to give a player a credit and then add him to 'list of deptors'
     */
    boolean TakeCredit(int sum, Player player) {
        playerDictionary.put(player, (int) Math.round(sum * creditCoeff));
        player.addMoney(sum);
        return true;
    }

    /**
     * implementaion of interface. Support the interaction between player andd bank office
     * There are three cases
     * - The player is a bot. Returns TRUE. END
     * - If player is in 'list of deptors' it runs the method 'TakeDept' to take a dept. TRUE/FALSE depends on the method
     * - If player is not in 'list of deptors', bank offers to take credit or go away. Returns TRUE anyway
     */
    @Override
    public boolean Call(Player player, String step) {
        if (step.equals("bot"))
            return true;
        if (playerDictionary.containsKey(player)) {
            System.out.println("You are in the office of the bank to which you are the debtor." +
                    " Trying to collect your debt...");
            return TakeDebt(player);
        } else {
            String answer;
            int num = 0;
            Scanner in = new Scanner(System.in);
            System.out.println("You are in the bank office. Would you like to get a credit?" +
                    "Input how many you want to get or ’No’!");
            answer = in.next();
            while (!answer.equals("No") && ((num = TryParse(answer)) < 1
                    || num > (int) Math.round(player.getExpenses() * creditCoeff))) {
                answer = String.format("Something went wrong! Input how many you want to get or ’No’! " +
                                "The maximum amount of your credit can be %d.\n" +
                                "Input how many you want to get or ’No’!",
                        (int) Math.round(creditCoeff * player.getExpenses()));
                System.out.println(answer);
                answer = in.next();
            }
            if (answer.equals("No"))
                return true;
            else
                TakeCredit(num, player);
        }
        return true;
    }

    /**
     * implementaion of interface. Notification to player about his position.
     */
    @Override
    public void CallInfo(String pos, String name) {
        String info = String.format("Dear %s, you are currently in cell %s. Welcome to the %s.", name, pos, "BankOffice");
        System.out.println(info);
    }

    /**
     * implementaion of interface. Returns a sign of bank office
     */
    @Override
    public char Name(Player player) {
        return '$';
    }

    /**
     * Method is meant to try convert a String to Integer
     */
    public static int TryParse(String answer) {
        int num;
        try {
            num = Integer.parseInt(answer);
            return num;
        } catch (Exception e) {
            return -1;
        }
    }
}
