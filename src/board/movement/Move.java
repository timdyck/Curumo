package board.movement;

import board.PieceType;

public class Move {
    PieceType type;

    int x1;
    int y1;
    int x2;
    int y2;

    boolean capture = false;
    PieceType promote;

    public Move(PieceType type, int x1, int y1, int x2, int y2) {
        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Move(PieceType type, int x1, int y1, int x2, int y2, boolean capture) {
        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        this.capture = capture;
    }

    public Move(PieceType type, int x1, int y1, int x2, int y2, PieceType promote) {
        assert (type.equals(PieceType.WP) || type.equals(PieceType.BP)) : "Can't promote a piece that is not a pawn!";

        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        this.promote = promote;
    }

    public Move(PieceType type, int x1, int y1, int x2, int y2, boolean capture, PieceType promote) {
        assert (type.equals(PieceType.WP) || type.equals(PieceType.BP)) : "Can't promote a piece that is not a pawn!";

        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

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
        return move.x1 == -1 && move.y1 == -1 && move.x2 == -1 && move.y2 == -1 && move.type.equals(PieceType.BB);
    }

    public void printMove() {
        String printStr = type.name() + " - (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")";
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
        result = prime * result + y1;
        result = prime * result + y2;
        result = prime * result + ((promote == null) ? 0 : promote.hashCode());
        result = prime * result + x1;
        result = prime * result + x2;
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
        if (y1 != other.y1)
            return false;
        if (y2 != other.y2)
            return false;
        if (promote != other.promote)
            return false;
        if (x1 != other.x1)
            return false;
        if (x2 != other.x2)
            return false;
        if (type != other.type)
            return false;
        return true;
    }

}
