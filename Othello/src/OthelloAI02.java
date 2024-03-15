import java.util.HashSet;
import java.util.Set;

public class OthelloAI02 implements IOthelloAI {
    int maxPlayer;
    @Override
    public Position decideMove(GameState state) {
        maxPlayer = state.getPlayerInTurn();
        MiniMaxReturnType max = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE, 8);
        return max.move;
    }

    private MiniMaxReturnType maxValue(GameState state, int alpha, int beta, int depth) {
        if(depth == 0) {
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
            MiniMaxReturnType minimum = minValue(result(state, pos), alpha, beta, depth-1);
            if (minimum.miniMaxValue > value) {
                value = minimum.miniMaxValue;
                bestMove = pos;
                alpha = Math.max(alpha, value);
            }
            if(value >= beta) return new MiniMaxReturnType(value, bestMove);
        }
        return new MiniMaxReturnType(value, bestMove);
    }

    private MiniMaxReturnType minValue(GameState state, int alpha, int beta, int depth) {
        if(depth == 0) {
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
            MiniMaxReturnType maximum = maxValue(result(state, pos), alpha, beta, depth-1);
            if (maximum.miniMaxValue < value) {
                value = maximum.miniMaxValue;
                bestMove = pos;
                beta = Math.min(beta, value);
            }
            if(value <= alpha) return new MiniMaxReturnType(value, bestMove);
        }
        return new MiniMaxReturnType(value, bestMove);
    }

    private GameState result(GameState state, Position action) {
        GameState stateCopy = new GameState(state.getBoard(), state.getPlayerInTurn()); // black 1, white 2
        stateCopy.insertToken(action); // stateCopy.insertToken
        return stateCopy;
    }

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