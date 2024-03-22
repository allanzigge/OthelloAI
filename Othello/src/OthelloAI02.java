import java.util.HashSet;
import java.util.Set;

public class OthelloAI02 implements IOthelloAI {
    int maxPlayer;

    /**
     * Decides on the best move to make in the current state of the game for the
     * current player.
     * 
     * @param state the current state of the game.
     * @return a position representing the best move to make for the player.
     */
    @Override
    public Position decideMove(GameState state) {
        maxPlayer = state.getPlayerInTurn();
        MiniMaxReturnType max = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE, 8);
        return max.move;
    }

    /**
     * Maximizes the utility for the player, in a given a state.
     * 
     * @param state the current state of the game.
     * @param alpha the maximum value available for the max player.
     * @param beta  the minimum value available for the min player.
     * @param depth the current depth in the game tree.
     * @return a MiniMaxReturnType object containing the minimax value and a move to
     *         make.
     */
    private MiniMaxReturnType maxValue(GameState state, int alpha, int beta, int depth) {
        if (depth == 0) {
            return new MiniMaxReturnType(utility(state, maxPlayer), new Position(-1, -1));
        }
        if (state.isFinished() || state.legalMoves().isEmpty()) {
            return new MiniMaxReturnType(utility(state, maxPlayer), new Position(-1, -1));
        }
        int value = Integer.MIN_VALUE;
        Position bestMove = new Position(-1, -1);

        Set<Position> moves = new HashSet<Position>();
        moves.addAll(state.legalMoves());

        for (Position pos : moves) {
            MiniMaxReturnType minimum = minValue(result(state, pos), alpha, beta, depth - 1);
            if (minimum.miniMaxValue > value) {
                value = minimum.miniMaxValue;
                bestMove = pos;
                alpha = Math.max(alpha, value);
            }
            if (value >= beta)
                return new MiniMaxReturnType(value, bestMove);
        }
        return new MiniMaxReturnType(value, bestMove);
    }

    /**
     * Minimizes the utility for the player, in a given a state.
     * 
     * @param state the current state of the game.
     * @param alpha the maximum value available for the max player.
     * @param beta  the minimum value available for the min player.
     * @param depth the current depth in the game tree.
     * @return a MiniMaxReturnType object containing the minimax value and a move to
     *         make.
     */
    private MiniMaxReturnType minValue(GameState state, int alpha, int beta, int depth) {
        if (depth == 0) {
            return new MiniMaxReturnType(utility(state, maxPlayer), new Position(-1, -1));
        }
        if (state.isFinished() || state.legalMoves().isEmpty()) {
            return new MiniMaxReturnType(utility(state, maxPlayer), new Position(-1, -1));
        }
        int value = Integer.MAX_VALUE;
        Position bestMove = new Position(-1, -1);

        Set<Position> moves = new HashSet<Position>();
        moves.addAll(state.legalMoves());

        for (Position pos : moves) {
            MiniMaxReturnType maximum = maxValue(result(state, pos), alpha, beta, depth - 1);
            if (maximum.miniMaxValue < value) {
                value = maximum.miniMaxValue;
                bestMove = pos;
                beta = Math.min(beta, value);
            }
            if (value <= alpha)
                return new MiniMaxReturnType(value, bestMove);
        }
        return new MiniMaxReturnType(value, bestMove);
    }

    /**
     * Generates a resulting game state from applying a specific action to an
     * initial game state.
     * 
     * @param state  the current state of the game.
     * @param action the action to take.
     * @return a GameState indicating the new game state.
     */
    private GameState result(GameState state, Position action) {
        GameState stateCopy = new GameState(state.getBoard(), state.getPlayerInTurn()); // black 1, white 2
        stateCopy.insertToken(action); // stateCopy.insertToken
        return stateCopy;
    }

    /**
     * Calculates the utility value of the current game state for the specified
     * player.
     * 
     * @param state  the current state of the game.
     * @param player the player for which the utility is calculated.
     * @return an integer representing the utility value, as based on tokens on the board.
     */
    private int utility(GameState state, int player) {
        player -= 1;
        return state.countTokens()[player];
    }
}

final class MiniMaxReturnType {
    int miniMaxValue;
    Position move;

    MiniMaxReturnType(int miniMaxValue, Position move) {
        this.miniMaxValue = miniMaxValue;
        this.move = move;
    }
}