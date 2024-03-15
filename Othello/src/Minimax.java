public class Minimax implements IOthelloAI {
    int maxPlayer;

    @Override
    public Position decideMove(GameState state) {
        maxPlayer = state.getPlayerInTurn();
        MiniMaxReturnType max = maxValue(state, 0);
        return max.move;
    }

    private MiniMaxReturnType maxValue(GameState state, int gametreedepth) {
        if (state.isFinished() || state.legalMoves().isEmpty()) {
            return new MiniMaxReturnType(utility(state, maxPlayer), new Position(-1, -1));
        }
        int min = Integer.MIN_VALUE;
        Position bestMove = new Position(-1, -1);

        for (Position pos : state.legalMoves()) {
            MiniMaxReturnType minimum = minValue(result(state, pos), gametreedepth+1);
            if (minimum.miniMaxValue > min) {
                min = minimum.miniMaxValue;
                bestMove = pos;
            }
        }
        return new MiniMaxReturnType(min, bestMove);
    }

    private MiniMaxReturnType minValue(GameState state, int gametreedepth) {
        if (state.isFinished() || state.legalMoves().isEmpty()) {
            return new MiniMaxReturnType(utility(state, maxPlayer), new Position(-1, -1));
        }
        int max = Integer.MAX_VALUE;
        Position bestMove = new Position(-1, -1);

        for (Position pos : state.legalMoves()) {

            MiniMaxReturnType maximum = maxValue(result(state, pos), gametreedepth+1);
            if (maximum.miniMaxValue < max) {
                max = maximum.miniMaxValue;
                bestMove = pos;
            }
        }
        return new MiniMaxReturnType(max, bestMove);
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