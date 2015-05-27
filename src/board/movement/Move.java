package board.movement;

import board.PieceType;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (capture ? 1231 : 1237);
        result = prime * result + col1;
        result = prime * result + col2;
        result = prime * result + ((promote == null) ? 0 : promote.hashCode());
        result = prime * result + row1;
        result = prime * result + row2;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Move other = (Move) obj;
        if (capture != other.capture)
            return false;
        if (col1 != other.col1)
            return false;
        if (col2 != other.col2)
            return false;
        if (promote != other.promote)
            return false;
        if (row1 != other.row1)
            return false;
        if (row2 != other.row2)
            return false;
        if (type != other.type)
            return false;
        return true;
    }

}
