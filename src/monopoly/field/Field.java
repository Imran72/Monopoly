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

public class Field {
    int width;
    int height;
    double penalty;
    ICall[] monopolyMap;

    public ICall getMap(int index) {
        return monopolyMap[index];
    }

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
        return String.format("(%d, %d)", x, y);
    }


    public Field(int width, int height, double penalty) {
        this.width = width;
        this.height = height;
        this.penalty = penalty;
        this.GenerateField();
    }

    public void GetConsoleField(Player player) {
        String st = "";
        String buffer = "";
        for (int i = 0; i < (width - 2) * 2 + 1; i++)
            buffer += " ";
        ICall[] top = Arrays.copyOfRange(monopolyMap, 0, width);
        ICall[] right = Arrays.copyOfRange(monopolyMap, width, width + height - 2);
        ICall[] bottom = Arrays.copyOfRange(monopolyMap, width + height - 2, width * 2 + height - 2);
        ICall[] left = Arrays.copyOfRange(monopolyMap, width * 2 + height - 2, width * 2 + height * 2 - 4);
        for (int i = 0; i < top.length; i++)
            st += top[i].Name(player) + " ";
        st += "\n";
        for (int i = 0; i < right.length; i++)
            st += left[left.length - 1 - i].Name(player) + buffer + right[i].Name(player) + "\n";
        for (int i = bottom.length - 1; i > -1; i--)
            st += bottom[i].Name(player) + " ";
        System.out.println(st);
    }

    public void GenerateField() {
        int last = 0;
        monopolyMap = new ICall[width * 2 + height * 2 - 4];
        char[] arr;
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0)
                arr = new char[width - 2];
            else
                arr = new char[height - 2];
            int cells = arr.length;
            int len = arr.length;
            int bankIndex = (int) (Math.random() * len);
            while (!(arr[bankIndex] == 0))
                bankIndex = (int) (Math.random() * len);
            arr[bankIndex] = '$';
            cells--;
            int taxi = (int) (Math.random() * (Math.min(2, cells) + 1));
            for (int j = 0; j < taxi; j++) {
                int taxiIndex = (int) (Math.random() * len);
                while (!(arr[taxiIndex] == 0))
                    taxiIndex = (int) (Math.random() * len);
                arr[taxiIndex] = 'T';
            }
            cells -= taxi;
            int penalty = (int) (Math.random() * (Math.min(2, cells) + 1));
            for (int j = 0; j < penalty; j++) {
                int penaltyIndex = (int) (Math.random() * len);
                while (!(arr[penaltyIndex] == 0))
                    penaltyIndex = (int) (Math.random() * len);
                arr[penaltyIndex] = '%';
            }
            cells -= penalty;
            for (int j = 0; j < cells; j++) {
                int shopIndex = (int) (Math.random() * len);
                while (!(arr[shopIndex] == 0))
                    shopIndex = (int) (Math.random() * len);
                arr[shopIndex] = 'S';
            }
            monopolyMap[last] = new Empty();
            last++;
            for (int j = 0; j < arr.length; j++) {
                monopolyMap[last] = GetObject(arr[j]);
                last++;
            }
        }
    }

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

