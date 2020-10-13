package monopoly.penalty;

import monopoly.base.ICall;
import monopoly.player.Player;

public class Penalty implements ICall {
    double penaltySum;

    public Penalty(double sum) {
        this.penaltySum = sum;
    }

    public boolean TakePenalty(Player player) {
        if (player.getMoney() >= penaltySum) {
            player.setMoney(player.getMoney() + penaltySum);
            return true;
        }
        return false;
    }

    @Override
    public boolean Call(Player player) {
        System.out.printf("You are in a penalty cell. You have been fined %d." +
                " Attempt to charge a fine...", penaltySum);
        return TakePenalty(player);
    }
}
