/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */
package monopoly.shop;

import monopoly.base.ICall;
import monopoly.player.Player;


public class Shop implements ICall {
    Player owner;
    int N;
    double K;
    double compensationCoeff;
    double improvementCoeff;

    public Shop() {
        N = (int) (Math.random() * (500 - 50) + 50);
        K = Math.random() * (0.9 * N - 0.5 * N) + 0.5 * N;
        compensationCoeff = Math.random() * (1 - 0.1) + 0.1;
        improvementCoeff = Math.random() * (2 - 0.1) + 0.1;
    }

    void UpgradeShop() {
        N += improvementCoeff * N;
        K += compensationCoeff * K;
    }

    void BecomeOwner(Player player) {
        owner = player;
    }

    @Override
    public boolean Call(Player player) {

        return true;
    }

    @Override
    public void CallInfo(String pos) {
        String info = String.format("Dear player, you are currently in cell %s. Welcome to the %s.", pos, "Shop");
        System.out.println(info);
    }

    @Override
    public char Name(Player player) {
        if (owner == null)
            return 'S';
        if (player == owner)
            return 'M';
        return 'O';
    }
}
