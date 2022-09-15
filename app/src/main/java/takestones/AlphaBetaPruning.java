package takestones;

import java.util.List;

class AlphaBetaPruning {
    private int maxDepth;
    private int maxDepthSearch;
    private int nodesVisited;
    private int nodesEvaluated;
    private int bestMove;
    private double bestMoveScore;

    AlphaBetaPruning(int maxDepthSearch) {
        this.maxDepth = 0;
        this.maxDepthSearch = maxDepthSearch;
        this.nodesVisited = 0;
        this.nodesEvaluated = 0;
    }

    /**
     * This function will print out the information to the terminal, as specified in
     * the homework description.
     */
    void printStats() {
        double averageBranchingFactor = ((double) this.nodesVisited - 1) / (this.nodesVisited - this.nodesEvaluated);
        System.out.println("Move: " + this.bestMove);
        System.out.println("Value: " + this.bestMoveScore);
        System.out.println("Number of Nodes Visited: " + this.nodesVisited);
        System.out.println("Number of Nodes Evaluated: " + this.nodesEvaluated);
        System.out.println("Max Depth Reached: " + this.maxDepth);
        System.out.println("Avg Effective Branching Factor: " + String.format("%.1f", averageBranchingFactor));
    }

    /**
     * This function will start the alpha-beta search
     * 
     * @param state This is the current game state
     * @param depth This is the specified search depth
     */
    void run(GameState state, int depth) {
        this.maxDepthSearch = depth;
        this.bestMoveScore = this.alphabeta(state, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                state.maxTurn(state.getTurns()));
    }

    /**
     * This method is used to implement alpha-beta pruning for both 2 players
     * 
     * @param state     This is the current game state
     * @param depth     Current depth of search
     * @param alpha     Current Alpha value
     * @param beta      Current Beta value
     * @param maxPlayer True if player is Max Player; Otherwise, false
     * @return int This is the number indicating score of the best next move
     */
    private double alphabeta(GameState state, int depth, double alpha, double beta, boolean maxPlayer) {
        this.nodesVisited += 1;
        List<GameState> succList = state.getSuccessors();
        this.maxDepth = Math.max(this.maxDepth, depth);

        // Returns false if no more successors or the depth limit has been reached
        if (succList.size() == 0 || depth == this.maxDepthSearch) {
            this.nodesEvaluated++;
            return state.evaluate(!maxPlayer);
        }

        if (maxPlayer) {
            double maxValue = alpha;
            for (GameState succState : succList) {
                double value = this.alphabeta(succState, depth + 1, alpha, beta, false);
                if (value > maxValue) {
                    maxValue = value;
                    this.bestMove = depth == 0 ? succState.getLastMove() : this.bestMove;
                }
                alpha = Math.max(maxValue, alpha);
                if (alpha >= beta) {
                    break;
                }
            }
            return maxValue;
        } else {
            double minValue = beta;
            for (GameState succState : succList) {
                double value = this.alphabeta(succState, depth + 1, alpha, beta, true);
                if (value < minValue) {
                    minValue = value;
                    this.bestMove = depth == 0 ? succState.getLastMove() : this.bestMove;
                }
                beta = Math.min(minValue, beta);
                if (beta <= alpha) {
                    break;
                }
            }
            return minValue;
        }
    }
}
