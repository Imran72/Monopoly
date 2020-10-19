/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */

import monopoly.game.Game;

/**
 * Class with main
 * It is a start of a Game
 */
public class Monopoly {
    /**
     * It is a MAIN
     * here it must get values from player
     */
    public static void main(String[] args) {
        try {
            int width = Integer.parseInt(args[0]); // width of a field
            int height = Integer.parseInt(args[1]); // height of a field
            int money = Integer.parseInt(args[2]); // amount of money
            // if data is not match it throws Exception
            if (!CheckNumbers(width, height, money))
                throw new IllegalArgumentException();
            new Game(width, height, money);
        } catch (NumberFormatException e) {
            System.out.println("Values should be integers!");
        } catch (IllegalArgumentException e) {
            System.out.println("Values are not in the correct range!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You did not pass the required values!");
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }

    /**
     * This method is meant to check Data is correct or not
     */
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
