/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */
package monopoly.taxi;

import monopoly.base.ICall;
import monopoly.player.Player;

public class Taxi implements ICall {
    public int GenerateCells() {
        return (int) (Math.random() * (5 - 3) + 3);
    }

    @Override
    public boolean Call(Player player) {
        int move = GenerateCells();
        player.addPosition(move);
        System.out.printf("You are shifted forward by %d cells", move);
        return true;
    }
}
