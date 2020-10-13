package monopoly.empty;

import monopoly.base.ICall;
import monopoly.player.Player;

public class Empty implements ICall {

    @Override
    public boolean Call(Player player) {
        System.out.println("Just relax there");
        return true;
    }
}
