/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */

import monopoly.game.Game;

public class Monopoly {
    public static void main(String[] args) {
        try {
            int width = 6;//Integer.parseInt(args[0]);
            int height = 6;//Integer.parseInt(args[1]);
            int money = 1500;//Integer.parseInt(args[2]);
            if (!CheckNumbers(width, height, money))
                throw new IllegalArgumentException();
            new Game(width, height, money);
        } catch (NumberFormatException e) {
            System.out.println("Values should be integers!");
        } catch (IllegalArgumentException e) {
            System.out.println("Values are not in the correct range!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You did not pass the required values!");
        }
    }

    public static boolean CheckNumbers(int width, int height, int money) {
        if (width < 6 | width > 30)
            return false;
        if (height < 6 | height > 30)
            return false;
        if (money < 500 | money > 15000)
            return false;
        return true;
    }
}
