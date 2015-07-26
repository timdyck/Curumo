package search;

import gameplay.Gameplay;

import java.util.Random;

import board.Board;
import board.PieceType;
import board.PieceType.Colour;
import board.movement.CastleFlags;
import board.movement.Move;
import board.movement.PieceMovement;

public class Zobrist {

    private long pieceKeys[][] = new long[Board.NUM_BOARDS][Board.NUM_SQUARES];
    private long blackMoveKey;
    private long castlingKeys[] = new long[Board.DIMENSION / 2];
    private long enPassantKeys[] = new long[Board.DIMENSION];

    private final int DETERMINISTIC_SEED = 11;

    public Zobrist() {
        Random rand = new Random(DETERMINISTIC_SEED);

        // Fill all members with random longs!
        for (PieceType piece : PieceType.values()) {
            for (int i = 0; i < Board.NUM_SQUARES; i++) {
                pieceKeys[piece.ordinal()][i] = rand.nextLong();
            }
        }

        blackMoveKey = rand.nextLong();

        for (int i = 0; i < Board.DIMENSION / 2; i++) {
            castlingKeys[i] = rand.nextLong();
        }

        for (int i = 0; i < Board.DIMENSION; i++) {
            enPassantKeys[i] = rand.nextLong();
        }

    }

    public long genZobristKey(Gameplay game) {
        long key = 0L;

        key ^= genPiecesKey(game);
        key ^= genTurnKey(game);
        key ^= genCastlingRightsKey(game);
        key ^= genEnPassantsKey(game);

        return key;
    }

    private long genPiecesKey(Gameplay game) {
        long key = 0L;

        for (PieceType piece : PieceType.values()) {
            long bitBoard = game.getBoard().getBitBoard(piece);
            for (int i : PieceMovement.getIndices(bitBoard)) {
                key ^= pieceKeys[piece.ordinal()][i];
            }
        }

        return key;
    }

    private long genTurnKey(Gameplay game) {
        return game.getTurn().equals(Colour.BLACK) ? blackMoveKey : 0;
    }

    /**
     * In terms of the castling keys array, order is WK, WQ, BK, BQ.
     * 
     * @param game
     * @return
     */
    private long genCastlingRightsKey(Gameplay game) {
        long key = 0L;

        CastleFlags flags = game.getMovement().getKingMovement().getFlags();
        if (flags.isWKCastle()) {
            key ^= castlingKeys[0];
        }
        if (flags.isWQCastle()) {
            key ^= castlingKeys[1];
        }
        if (flags.isBKCastle()) {
            key ^= castlingKeys[2];
        }
        if (flags.isBQCastle()) {
            key ^= castlingKeys[3];
        }

        return key;
    }

    private long genEnPassantsKey(Gameplay game) {
        long key = 0L;

        Move previousMove = game.getPreviousMove();
        if ((previousMove.getPiece().equals(PieceType.WP) && previousMove.getY1() == 1 && previousMove.getY2() == 3)
                || (previousMove.getPiece().equals(PieceType.BP) && previousMove.getY1() == 6 && previousMove.getY2() == 4)) {
            key ^= enPassantKeys[previousMove.getX1()];
        }

        return key;
    }
}