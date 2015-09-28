package com.tdyck.board;

import java.util.ArrayList;
import java.util.List;

public enum PieceType {
    WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK;

    public enum Colour {
        WHITE, BLACK;

        public Colour getOppositeColour() {
            return this.equals(BLACK) ? WHITE : BLACK;
        }
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

    public static List<PieceType> getWhitePieceTypes() {
        List<PieceType> pieces = new ArrayList<PieceType>();

        pieces.add(WP);
        pieces.add(WR);
        pieces.add(WN);
        pieces.add(WB);
        pieces.add(WQ);
        pieces.add(WK);

        return pieces;
    }

    public static List<PieceType> getBlackPieceTypes() {
        List<PieceType> pieces = new ArrayList<PieceType>();

        pieces.add(BP);
        pieces.add(BR);
        pieces.add(BN);
        pieces.add(BB);
        pieces.add(BQ);
        pieces.add(BK);

        return pieces;
    }
}
