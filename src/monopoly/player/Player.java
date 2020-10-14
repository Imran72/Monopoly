/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */

package monopoly.player;

/**
 * A class of an abstart player
 * There are:
 * - money
 * - position
 * - expenses
 * it all can be changed
 */
public class Player {
    int money; // initial money of player
    int expenses; // expenses player during a game
    int position; // player's position
    int mod; // number cells in map

    /**
     * returns position
     */
    public int getPosition() {
        return position;
    }

    /**
     * add incoming value to position
     */
    public void addPosition(int value) {
        position = (position + value) % mod;
    }

    /**
     * returns amount of money
     */
    public int getMoney() {
        return money;
    }

    /**
     * add value to money
     */
    public void addMoney(int value) {
        money += value;
    }

    /**
     * returns player's expenses
     */
    public int getExpenses() {
        return expenses;
    }

    /**
     * add value to expenses
     */
    public void addExpenses(int value) {
        expenses += value;
    }

    /**
     * Constructor
     */
    public Player(int money, int mod) {
        this.money = money;
        this.mod = mod;
        this.position = 0;
    }
}