/**
 * @author <a href="mailto:iatimkanov@edu.hse.ru"> Imran Timkanov</a>
 */
package monopoly.field;

import monopoly.bank.Bank;
import monopoly.base.ICall;
import monopoly.empty.Empty;
import monopoly.penalty.Penalty;
import monopoly.player.Player;
import monopoly.shop.Shop;
import monopoly.taxi.Taxi;

import java.util.Arrays;

/**
 * A class of an abstract Field. It imitates real Monopoly Field/Map
 * Supports some methods to generate random field/map for game
 * Has method to display Field/Map to console
 */
public class Field {
    int width; // width of field
    int height; // height of field
    double penalty; // penalty to transfer to Penalty object when it creates
    ICall[] monopolyMap; // array of object on field

    /**
     * Returns an object indexed
     */
    public ICall getMap(int index) {
        return monopolyMap[index];
    }

    /**
     * Player saves his position in special sum of x and y coordinate
     * To make special format to format (X, Y) there is this method
     */
    public String GetCoordinates(int index) {
        int x = 0;
        int y = 0;
        if (index <= width + height - 2) {
            x = Math.min(5, index);
            y = Math.max(0, index - x);
        } else if (index > width + height - 2) {
            index %= (width + height - 2);
            x = width - Math.min(index, width - 1) - 1;
            y = height - (index - (width - x - 1)) - 1;
        }
        return String.format("(%d, %d)", x, y); // nesessary format
    }


    /**
     * Constructor
     */
    public Field(int width, int height, double penalty) {
        this.width = width;
        this.height = height;
        this.penalty = penalty;
        this.GenerateField(); // Generates a field
    }

    /**
     * Method is meant to display a field with object to console
     * Because of the special way of storing, it must be a special method to display field correctly
     * It separate map on 4 parts of field (top, right, left and bottom)
     * After creates a String going along every part
     */
    public void GetConsoleField(Player player) {
        String st = ""; // variable for storing a map
        String buffer = ""; // buffer of spaces for field in console
        for (int i = 0; i < (width - 2) * 2 + 1; i++) // makes nesessary number of spaces
            buffer += " ";
        ICall[] top = Arrays.copyOfRange(monopolyMap, 0, width); // top part of a field
        ICall[] right = Arrays.copyOfRange(monopolyMap, width, width + height - 2); // right part of a field
        ICall[] bottom = Arrays.copyOfRange(monopolyMap,
                width + height - 2, width * 2 + height - 2); // bottom part of a field
        ICall[] left = Arrays.copyOfRange(monopolyMap,
                width * 2 + height - 2, width * 2 + height * 2 - 4); // left part of a field
        for (int i = 0; i < top.length; i++)
            st += top[i].Name(player) + " ";
        st += "\n";
        for (int i = 0; i < right.length; i++)
            st += left[left.length - 1 - i].Name(player) + buffer + right[i].Name(player) + "\n";
        for (int i = bottom.length - 1; i > -1; i--)
            st += bottom[i].Name(player) + " ";
        System.out.println(st); // display a field
    }

    /**
     * Method is meant to generate a random field
     * based on rules from task and giving width and height parameters
     * Generates a files in four steps
     * Conditionally separate a field on 4 parts (top, right, left, bottom)
     * After in order make a bank office in random place
     * After x numbers of taxis
     * After x numbers of penalty station
     * After shops
     * First of all generates a String with sign of object
     * But then depending on this String makes real MAP[]
     */
    public void GenerateField() {
        int last = 0;
        monopolyMap = new ICall[width * 2 + height * 2 - 4]; // map with size
        char[] arr;
        // 4 parts
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0)
                arr = new char[width - 2];
            else
                arr = new char[height - 2];
            int cells = arr.length; // how many cells in any part of field
            int len = arr.length; // how many cells in any part of field
            int bankIndex = (int) (Math.random() * len); // generate index for bank office
            while (!(arr[bankIndex] == 0))
                bankIndex = (int) (Math.random() * len);
            arr[bankIndex] = '$'; // bank sign was added
            cells--; // number -1
            int taxi = (int) (Math.random() * (Math.min(2, cells) + 1)); // number of taxis
            for (int j = 0; j < taxi; j++) { // find place for taxis
                int taxiIndex = (int) (Math.random() * len);
                while (!(arr[taxiIndex] == 0))
                    taxiIndex = (int) (Math.random() * len);
                arr[taxiIndex] = 'T';
            }
            cells -= taxi; // number of cells minus number of taxis
            int penalty = (int) (Math.random() * (Math.min(2, cells) + 1)); // generates numbers of penalty stations
            for (int j = 0; j < penalty; j++) {
                int penaltyIndex = (int) (Math.random() * len);
                while (!(arr[penaltyIndex] == 0))
                    penaltyIndex = (int) (Math.random() * len);
                arr[penaltyIndex] = '%';
            }
            cells -= penalty;// number of cells minus number of penalty
            for (int j = 0; j < cells; j++) {
                int shopIndex = (int) (Math.random() * len);
                while (!(arr[shopIndex] == 0))
                    shopIndex = (int) (Math.random() * len);
                arr[shopIndex] = 'S';
            }
            monopolyMap[last] = new Empty();
            last++;
            // based on String with generated signs($,%,S,T,E)
            // it makes real Map
            for (int j = 0; j < arr.length; j++) {
                monopolyMap[last] = GetObject(arr[j]);
                last++;
            }
        }
    }

    /**
     * Depending on sign it returns right object
     */
    ICall GetObject(char s) {
        if (s == '$')
            return new Bank();
        if (s == 'S')
            return new Shop();
        if (s == 'T')
            return new Taxi();
        return new Penalty(this.penalty);
    }
}

