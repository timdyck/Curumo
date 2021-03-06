package com.tdyck.board.movement;

import java.util.ArrayList;
import java.util.List;

import com.tdyck.board.Board;
import com.tdyck.board.PieceType;

public class KnightMovement extends PieceMovement {

    private final long KNIGHT_RANGE = 0x0000000A1100110AL;
    private final int CENTRE = 18; // 18 Right shifts of KNIGHT_RANGE
                                   // puts the centre into the last bit

    public KnightMovement(Board board) {
        super(board);
        initializeMoves(PieceType.WN);
        initializeMoves(PieceType.BN);
    }

    private void initializeMoves(PieceType piece) {
        List<Move> moves = new ArrayList<Move>();
        long currentKnights = board.getBitBoard(piece);
        long allRangeBitBoard = 0L;

        for (int i : getIndices(currentKnights)) {
            long possibleMoves = i < CENTRE ? KNIGHT_RANGE >> (CENTRE - i) : KNIGHT_RANGE << (i - CENTRE);

            // Eliminate wrap around
            if (getX(i) > 5) {
                possibleMoves &= ~FILE_A & ~FILE_B;
            } else if (getX(i) < 2) {
                possibleMoves &= ~FILE_H & ~FILE_G;
            }
            long range = possibleMoves;

            long sameColorPieces = piece.isWhitePiece() ? whitePieces : blackPieces;
            possibleMoves &= ~sameColorPieces;

            moves.addAll(getMoves(possibleMoves, piece, getX(i), getY(i)));
            allRangeBitBoard |= range;
        }

        setMoves(piece, moves, allRangeBitBoard);
    }
}
