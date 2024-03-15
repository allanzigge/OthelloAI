public class Minimax implements IOthelloAI {
    int maxPlayer;

    @Override
    public Position decideMove(GameState state) {
        maxPlayer = state.getPlayerInTurn();
        MiniMaxReturnType max = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return max.move;
    }

    private MiniMaxReturnType maxValue(GameState state, int alpha, int beta) {
        if (state.isFinished() || state.legalMoves().isEmpty()) {
            return new MiniMaxReturnType(utility(state, maxPlayer), new Position(-1, -1));
        }
        int value = Integer.MIN_VALUE;
        Position bestMove = new Position(-1, -1);

        for (Position pos : state.legalMoves()) {
            MiniMaxReturnType minimum = minValue(result(state, pos), alpha, beta);
            if (minimum.miniMaxValue > value) {
                value = minimum.miniMaxValue;
                bestMove = pos;
                alpha = Math.max(alpha, value);
            }
            if(value >= beta) return new MiniMaxReturnType(value, bestMove);
        }
        return new MiniMaxReturnType(value, bestMove);
    }

    private MiniMaxReturnType minValue(GameState state, int alpha, int beta) {
        if (state.isFinished() || state.legalMoves().isEmpty()) {
            return new MiniMaxReturnType(utility(state, maxPlayer), new Position(-1, -1));
        }
        int value = Integer.MAX_VALUE;
        Position bestMove = new Position(-1, -1);

        for (Position pos : state.legalMoves()) {

            MiniMaxReturnType maximum = maxValue(result(state, pos), alpha, beta);
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
        GameState stateCopy = new GameState(state.getBoard(), state.getPlayerInTurn()); // black 1, white
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