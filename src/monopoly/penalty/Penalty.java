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
            player.addMoney(-penaltySum * player.getMoney());
            return true;
        }
        return false;
    }

    @Override
    public boolean Call(Player player) {
        String st = String.format("You are in a penalty cell. You have been fined %.2f. Attempt to charge a fine...", penaltySum * player.getMoney());
        System.out.println(st);
        return TakePenalty(player);
    }

    @Override
    public void CallInfo(String pos) {
        String info = String.format("Dear player, you are currently in cell %s. Welcome to the %s.", pos, "PenaltyStation");
        System.out.println(info);
    }

    @Override
    public char Name(Player player) {
        return '%';
    }
}
