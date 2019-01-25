package com.games.rpc.ai;

/**
 * Factory for different AI types.
 */
public class AIFactory {

    /**
     * Returns AI implementation corresponding input. Current options: [markov].
     *
     * @param aiType Type of AI.
     * @return AI instance or {@code null}.
     */
    public static AI getAI(String aiType) {
        if (aiType == null) return null;

        switch (aiType.toLowerCase()) {
            case "markov":
                return new MarkovChainAIImpl();
            default:
                return null;
        }
    }
}
