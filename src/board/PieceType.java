package board;

public enum PieceType {
    WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK;

    public enum Colour {
        WHITE, BLACK;
    }

    public Colour getColour() {
        if (this.equals(WP) || this.equals(WR) || this.equals(WN) || this.equals(WB) || this.equals(WQ) || this.equals(WK)) {
            return Colour.WHITE;
        } else {
            return Colour.BLACK;
        }
    }

    public boolean isWhitePiece() {
        return getColour().equals(Colour.WHITE);
    }

    public boolean isBlackPiece() {
        return getColour().equals(Colour.BLACK);
    }
}
