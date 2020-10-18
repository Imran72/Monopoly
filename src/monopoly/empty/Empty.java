package monopoly.empty;

import monopoly.base.ICall;
import monopoly.player.Player;

public class Empty implements ICall {


    @Override
    public boolean Call(Player player, String step) {
        System.out.println("Just relax there.");
        return true;
    }

    /**
     * implementaion of interface. Notification to player about his position.
     */
    @Override
    public void CallInfo(String pos, String name) {
        String info = String.format("Dear %s, you are currently in cell %s. Welcome to the %s.", name, pos, "EmptyCell");
        System.out.println(info);
    }

    /**
     * implementaion of interface. Returns a sign of empty cell
     */
    @Override
    public char Name(Player player) {
        return 'E';
    }

}
