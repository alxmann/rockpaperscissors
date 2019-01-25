package com.games.rpc;

import com.games.rpc.ai.AI;
import com.games.rpc.ai.AIFactory;

import java.util.Scanner;
import java.util.logging.*;

/**
 * Rock Paper Scissors game application.
 */
public class RockPaperScissors {
    private final Logger LOG = Logger.getLogger(RockPaperScissors.class.getName());
    private AI ai;
    private int aiScore;
    private int opponentScore;

    // Setting up simple message format.
    {
        LOG.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        SimpleFormatter formatter = new SimpleFormatter() {
            @Override
            public String format(final LogRecord record) {
                return String.format("%1$s: %2$s\n", record.getLevel().getName(), record.getMessage());
            }
        };
        handler.setFormatter(formatter);
        LOG.addHandler(handler);
    }

    /**
     * Initializes the game with a given AI. Random AI is used if no AI supplied.
     *
     * @param ai game AI
     */
    public RockPaperScissors(AI ai) {
        if (ai == null) {
            ai = (move) -> Move.getRandom();
            LOG.log(Level.WARNING, "Using random AI.");
        }
        this.ai = ai;
    }

    /**
     * Rock Paper Scissors game setting.
     *
     * @param args not used.
     */
    public static void main(String[] args) {
        AI ai = AIFactory.getAI("markov");
        RockPaperScissors rpsGame = new RockPaperScissors(ai);
        rpsGame.play();
    }

    /**
     * Starts the game in console. Uses standard {@link Scanner} class for simplicity.
     */
    public void play() {
        LOG.log(Level.INFO, "Welcome to Rock Paper Scissors game!");
        printOptions();
        Scanner scanner = new Scanner(System.in);
        LOG.log(Level.INFO, "Make your move: ");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if ("e".equalsIgnoreCase(input)) break;

            Move opponentMove = Move.fromCommand(input.toUpperCase());

            if (opponentMove == null) {
                LOG.log(Level.WARNING, "Unsupported command!");
                printOptions();
                continue;
            }

            Move aiMove = ai.nextMove(opponentMove);

            LOG.log(Level.INFO, "AI choice : " + aiMove);

            if (aiMove.equals(opponentMove)) {
                LOG.log(Level.INFO, "Tie!");
            } else if (aiMove.loosesTo(opponentMove)) {
                LOG.log(Level.INFO, "You win!");
                opponentScore++;
            } else {
                LOG.log(Level.INFO, "You lose!");
                aiScore++;
            }
            LOG.log(Level.INFO, "You (" + opponentScore + " : " + aiScore + ") AI\n");
            LOG.log(Level.INFO, "Make your move: ");
        }

        scanner.close();
    }

    private void printOptions() {
        LOG.log(Level.INFO, "Command line arguments:" +
                "\n[r]  For Rock." +
                "\n[p]  For Paper." +
                "\n[s]  For Scissors." +
                "\n[e]  To exit."
        );
    }
}
