package com.games.rpc.ai;

import com.games.rpc.Move;

/**
 * Encapsulates Rock Paper Scissors AI algorithm. Implementations must provide more than 50% chance to win.
 */
public interface AI {

    /**
     * Provides next move of AI.
     *
     * @param opponentMove opponent's current move.
     * @return AI next move.
     */
    Move nextMove(Move opponentMove);
}
