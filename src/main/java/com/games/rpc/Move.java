package com.games.rpc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * All possible moves of the game.
 */
public enum Move {
    ROCK, PAPER, SCISSORS;

    private static Map<String, Move> commandMapping = new HashMap<>();
    private Move loosesTo;

    static {
        ROCK.loosesTo = PAPER;
        PAPER.loosesTo = SCISSORS;
        SCISSORS.loosesTo = ROCK;
        commandMapping.put("r", ROCK);
        commandMapping.put("p", PAPER);
        commandMapping.put("s", SCISSORS);
    }

    /**
     * Check if current Move instance looses to input instance.
     *
     * @return {@code true} if looses.
     */
    public boolean loosesTo(Move move) {
        return loosesTo == move;
    }

    /**
     * Returns Move instance corresponding to the console command. Case insensitive.
     *
     * @param command Console command.
     * @return Move instance.
     */
    public static Move fromCommand(String command) {
        return commandMapping.get(command.toLowerCase());
    }

    /**
     * Returns random Move instance.
     *
     * @return Move instance.
     */
    public static Move getRandom() {
        return Move.values()[ThreadLocalRandom.current().nextInt(Move.values().length)];
    }

    /**
     * Returns Move instance to which the current instance looses.
     *
     * @return Move instance.
     */
    public Move getLoosesTo() {
        return loosesTo;
    }
}
