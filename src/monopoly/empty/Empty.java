package monopoly.empty;

import monopoly.base.ICall;
import monopoly.player.Player;

public class Empty implements ICall {

    @Override
    public boolean Call(Player player) {
        System.out.println("Just relax there.");
        return true;
    }

    @Override
    public void CallInfo(String pos) {
        String info = String.format("Dear player, you are currently in cell %s. Welcome to the %s.", pos, "EmptyCell");
        System.out.println(info);
    }

    @Override
    public char Name(Player player) {
        return 'E';
    }

}
