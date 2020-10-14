/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */
package monopoly.taxi;

import monopoly.base.ICall;
import monopoly.player.Player;

/**
 * Abstract class of Taxi for Monopoly
 * Realisation of extra transpoting by taxi
 */
public class Taxi implements ICall {
    /**
     * Generates random number [3,5]
     */
    public int GenerateCells() {
        return (int) (Math.random() * (5 - 3 + 1) + 3);
    }

    /**
     * implementaion of interface.
     * Give an info about transportaion by taxi
     */
    @Override
    public boolean Call(Player player, String step) {
        step = step == "player" ? "You are" : "Bot is";
        int move = GenerateCells();
        player.addPosition(move);
        System.out.println(String.format("%s shifted forward by %d cells.", step, move));
        return true;
    }

    /**
     * implementaion of interface. Notification to player about his position.
     */
    @Override
    public void CallInfo(String pos, String name) {
        String info = String.format("Dear %s, you are currently in cell %s. Welcome to the %s.", name, pos, "TaxiStation");
        System.out.println(info);
    }

    /**
     * implementaion of interface. Returns a sign of taxi station
     */
    @Override
    public char Name(Player player) {
        return 'T';
    }
}
