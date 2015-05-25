package board;

public class Move {
    PieceType type;

    int row1;
    int col1;
    int row2;
    int col2;

    boolean capture = false;
    PieceType promote;

    public Move(PieceType type, int row1, int col1, int row2, int col2) {
        this.type = type;
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;
    }

    public Move(PieceType type, int row1, int col1, int row2, int col2, boolean capture) {
        this.type = type;
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;

        this.capture = capture;
    }

    public Move(PieceType type, int row1, int col1, int row2, int col2, PieceType promote) {
        assert (type.equals(PieceType.WP) || type.equals(PieceType.BP)) : "Can't promote a piece that is not a pawn!";

        this.type = type;
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;

        this.promote = promote;
    }

    public Move(PieceType type, int row1, int col1, int row2, int col2, boolean capture, PieceType promote) {
        assert (type.equals(PieceType.WP) || type.equals(PieceType.BP)) : "Can't promote a piece that is not a pawn!";

        this.type = type;
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;

        this.capture = capture;
        this.promote = promote;
    }

    /**
     * @return representation for previous move when there was no previous move
     */
    public static Move getFirstPreviousMove() {
        return new Move(PieceType.BB, -1, -1, -1, -1);
    }

    /**
     * @param move
     * @return whether or not this move is the first move of the game
     */
    public static boolean isFirstMove(Move move) {
        return move.row1 == -1 && move.col1 == -1 && move.row2 == -1 && move.col2 == -1 && move.type.equals(PieceType.BB);
    }

    public void printMove() {
        String printStr = type.name() + " - (" + row1 + "," + col1 + ") to (" + row2 + "," + col2 + ")";
        if (capture && promote != null) {
            printStr += " (capture and promote " + type.name() + " to " + promote.name() + "!)";
        } else if (capture) {
            printStr += " (capture!)";
        } else if (promote != null) {
            printStr += " (promote " + type.name() + " to " + promote.name() + "!)";
        }
        System.out.println(printStr);
    }
}
