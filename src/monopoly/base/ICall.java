package monopoly.base;

import monopoly.field.Field;
import monopoly.player.Player;

public interface ICall {
    boolean Call(Player player);
    void CallInfo(String pos);
    char Name(Player player);
}
