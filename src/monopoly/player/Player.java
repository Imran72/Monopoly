/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */

package monopoly.player;

public class Player {
    double money;
    double expenses;
    int position;
    int mod;

    public int getPosition() {
        return position;
    }

    public void setPosition(int value)
    {
        position = value;
    }

    public void addPosition(int value)
    {
        position = (position + value) % mod;
    }

    public void addMoney(double value)
    {
        money += value;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double value) {
        expenses = value;
    }

    public Player(double money, int mod) {
        this.money = money;
        this.mod = mod;
        position = 0;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double value) {
        money = value;
    }

    public Player(int money, int position) {
        this.money = money;
        this.position = position;
    }
}