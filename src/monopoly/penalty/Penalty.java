package monopoly.penalty;

import monopoly.base.ICall;
import monopoly.player.Player;

public class Penalty implements ICall {
    double penaltyCoeff;

    /**
     * Constructor
     */
    public Penalty(double coeff) {
        this.penaltyCoeff = coeff;
    }

    /**
     * Method is meant to take penalty from player in case he has enough money -- returns TRUE,
     * otherwise returns FALSE
     */
    public boolean TakePenalty(Player player) {
        if (player.getMoney() >= Math.round(penaltyCoeff * player.getMoney())) {
            player.addMoney((int) Math.round(-penaltyCoeff * player.getMoney()));
            return true;
        }
        return false;
    }

    /**
     * implementaion of interface.
     * Gives an information that player should pay penalty
     */
    @Override
    public boolean Call(Player player, String step) {
        step = step == "player" ? "You are" : "Bot is";
        String st = String.format("%s in a penalty cell. Have been fined %d$. Attempt" +
                " to charge a fine...", step, (int) Math.round(penaltyCoeff * player.getMoney()));
        System.out.println(st);
        return TakePenalty(player);
    }

    /**
     * implementaion of interface. Notification to player about his position.
     */
    @Override
    public void CallInfo(String pos, String name) {
        String info = String.format("Dear %s, you are currently in cell %s." +
                " Welcome to the %s.", name, pos, "PenaltyStation");
        System.out.println(info);
    }

    /**
     * implementaion of interface. Returns a sign of penalty station
     */
    @Override
    public char Name(Player player) {
        return '%';
    }
}
