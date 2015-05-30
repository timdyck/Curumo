package board;

public enum PieceType {
    WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK;

    public boolean isWhitePiece() {
        return this.equals(WP) || this.equals(WR) || this.equals(WN) || this.equals(WB) || this.equals(WQ) || this.equals(WK);
    }

    public boolean isBlackPiece() {
        return !isWhitePiece();
    }
}
