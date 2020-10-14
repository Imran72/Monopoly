package monopoly.base;

import monopoly.player.Player;

/**
 * base interface for all object in monopoly map
 */
public interface ICall {
    /**
     * to do some special function of an object
     */
    boolean Call(Player player, String step);

    /**
     * prints info about object
     */
    void CallInfo(String pos, String name);

    /**
     * returns special sign of an object
     */
    char Name(Player player);
}
