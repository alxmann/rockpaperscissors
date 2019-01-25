package com.games.rpc.ai;

import com.games.rpc.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

class MarkovChainAIImplTest {
    private AI ai;

    @BeforeEach
    void setUpOutput() {

        // Creating Markov chain based AI
        ai = AIFactory.getAI("markov");
    }

    @Test
    void aiBeatsThePatternMoreThan65PercentOfTheTime() {
        int aiWins = 0;
        int games = 0;

        // Creating repining pattern as AI opponent.
        for (int i = 0; i < 1000; i++) {
            if (aiWins(Move.ROCK)) aiWins++;
            games++;
            if (i % 2 == 0) {
                if (aiWins(Move.PAPER)) aiWins++;
                games++;
            }
            if (aiWins(Move.SCISSORS)) aiWins++;
            if (aiWins(Move.SCISSORS)) aiWins++;
            if (aiWins(Move.SCISSORS)) aiWins++;
            games += 3;
        }
        System.out.println(games);
        System.out.println(aiWins);
        assertThat((double) aiWins / games, greaterThan(0.65));
    }

    private boolean aiWins(Move opponentsMove) {
        Move aiMove = ai.nextMove(opponentsMove);
        return opponentsMove.loosesTo(aiMove);
    }
}