package com.games.rpc.ai;

import com.games.rpc.Move;

import java.util.Arrays;

/**
 * Markov chain model based implementation. It should provide about 70 % chance to win, which is good enough for test
 * task.
 */
public class MarkovChainAIImpl implements AI {

    // Matrix represents number of events passing from one move to another.
    private int[][] matrix;
    private boolean firstGame;
    private Move opponentLastMove = null;

    /**
     * Initializes matrix with 0 values.
     */
    public MarkovChainAIImpl() {
        int length = Move.values().length;
        matrix = new int[length][length];

        for (int[] row : matrix) {
            Arrays.fill(row, 0);
        }
        firstGame = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move nextMove(Move opponentMove) {
        Move nextMove = predictNext();

        if (opponentLastMove != null) {
            updateMatrix(opponentLastMove, opponentMove);
        }
        opponentLastMove = opponentMove;
        firstGame = false;
        return nextMove;
    }

    private Move predictNext() {

        // First game move is always random.
        if (firstGame) {
            return Move.getRandom();
        }

        // Looking for the move that our opponent usually takes after this type of move. It should be highest number
        // in the row.
        int nextIndex = 0;

        for (int i = 0; i < Move.values().length; i++) {
            int prevIndex = opponentLastMove.ordinal();

            if (matrix[prevIndex][i] > matrix[prevIndex][nextIndex]) {
                nextIndex = i;
            }
        }

        // This should be the most likely next move.
        Move predictedNext = Move.values()[nextIndex];
        return predictedNext.getLoosesTo();
    }

    private void updateMatrix(Move prev, Move next) {
        matrix[prev.ordinal()][next.ordinal()]++;
    }
}
