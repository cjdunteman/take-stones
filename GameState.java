import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GameState {
    private int size; // The number of stones
    private boolean[] stones; // Game state: true for available stones, false for taken ones
    private int lastMove; // The last move

    /**
     * Class constructor specifying the number of stones.
     */
    GameState(int size) {

        this.size = size;

        // For convenience, we use 1-based index, and set 0 to be unavailable
        this.stones = new boolean[this.size + 1];
        this.stones[0] = false;

        // Set default state of stones to available
        for (int i = 1; i <= this.size; ++i) {
            this.stones[i] = true;
        }

        // Set the last move be -1
        this.lastMove = -1;
    }

    /**
     * Copy constructor
     */
    private GameState(GameState other) {
        this.size = other.size;
        this.stones = Arrays.copyOf(other.stones, other.stones.length);
        this.lastMove = other.lastMove;
    }

    /**
     * This method is used to compute a list of legal moves
     *
     * @return This is the list of state's moves
     */
    List<Integer> getMoves() {
        List<Integer> movesList = new ArrayList<>();

        // Check if this is first move
        if (this.lastMove == -1) {
            double moveCeiling = (double) this.size / 2;
            for (Integer i = 1; i <= this.size; i++) {
                // Check if i is odd and less than half of total stones
                if ((i % 2 != 0) && (i < moveCeiling)) {
                    movesList.add(i);
                }
            }
        } else {
            for (Integer i = 1; i <= this.size; i++) {
                // Check if stone is multiple or factor of the last move and available
                if (((this.lastMove % i == 0) || (i % this.lastMove == 0)) && this.stones[i]) {
                    movesList.add(i);
                }
            }
        }
        return movesList;
    }

    boolean maxTurn(int turns) {
        return turns % 2 == 0;
    }

    int getTurns() {
        int turns = 0;
        for (int i = 1; i <= this.size; i++) {
            if (!this.stones[i]) {
                turns += 1;
            }
        }
        return turns;
    }

    /**
     * This method is used to generate a list of successors using the getMoves()
     * method
     *
     * @return This is the list of state's successors
     */
    List<GameState> getSuccessors() {
        return this.getMoves().stream().map(move -> {
            var state = new GameState(this);
            state.removeStone(move);
            return state;
        }).collect(Collectors.toList());
    }

    /**
     * This method is used to evaluate a game state based on the given heuristic
     * function
     *
     * @return int This is the static score of given state
     */
    double evaluate(boolean maxTurn) {
        double score;
        List<Integer> succList = this.getMoves();

        // If first move
        if (this.lastMove == -1) {
            score = Constants.FIRST_TURN;
            return score;
        } else {
            if (succList.size() == 0) {
                // No moves for maximizer
                score = Constants.END_STATE;
            } else if (this.lastMove == 1) {
                // If even
                if (succList.size() % 2 == 0) {
                    score = Constants.LAST_MOVE_ONE_EVEN;
                } else {
                    score = Constants.LAST_MOVE_ONE_ODD;
                }
            } else if (Helper.isPrime(this.lastMove)) {
                if (Helper.getMultiplesCount(this.lastMove, succList) % 2 == 0) {
                    score = Constants.LAST_MOVE_PRIME_EVEN;
                } else {
                    score = Constants.LAST_MOVE_PRIME_ODD;
                }
            } else {
                int largestPrimeFactor = Helper.getLargestPrimeFactor(this.lastMove);
                // If even
                if (Helper.getMultiplesCount(largestPrimeFactor, succList) % 2 == 0) {
                    score = Constants.LAST_MOVE_COMPOSITE_EVEN;
                } else {
                    score = Constants.LAST_MOVE_COMPOSITE_ODD;
                }
            }
        }

        if (maxTurn) {
            return score;
        } else {
            return score * -1;
        }
    }

    /**
     * Get the last move
     */
    int getLastMove() {
        return this.lastMove;
    }

    /**
     * This method is used to take a stone out
     *
     * @param idx Index of the taken stone
     */
    void removeStone(int idx) {
        this.stones[idx] = false;
        this.lastMove = idx;
    }

}
