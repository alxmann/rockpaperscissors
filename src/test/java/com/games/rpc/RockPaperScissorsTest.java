package com.games.rpc;

import com.games.rpc.ai.AI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RockPaperScissorsTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;
    private AI ai;

    @BeforeEach
    void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setErr(new PrintStream(testOut));

        // Creating mock AI which always returns Rock
        ai = mock(AI.class);
        when(ai.nextMove(any())).thenReturn(Move.ROCK);
    }

    @AfterEach
    void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setErr(systemOut);
    }

    @Test
    void consoleReturnsValidOutputForTieCondition() {
        provideInput("r");
        RockPaperScissors rpcGame = new RockPaperScissors(ai);
        rpcGame.play();

        assertThat(getOutput(), containsString("INFO: Tie!"));
    }

    @Test
    void consoleReturnsValidOutputForOpponentWinsCondition() {
        provideInput("p");
        RockPaperScissors rpcGame = new RockPaperScissors(ai);
        rpcGame.play();

        assertThat(getOutput(), containsString("INFO: You win!"));
    }

    @Test
    void consoleReturnsValidOutputForAIWinsCondition() {
        provideInput("s");
        RockPaperScissors rpcGame = new RockPaperScissors(ai);
        rpcGame.play();

        assertThat(getOutput(), containsString("INFO: You lose!"));
    }

    @Test
    void consoleReturnsValidOutputForInvalidInputCondition() {
        provideInput("invalid");
        RockPaperScissors rpcGame = new RockPaperScissors(ai);
        rpcGame.play();

        assertThat(getOutput(), containsString("WARNING: Unsupported command!"));
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }
}