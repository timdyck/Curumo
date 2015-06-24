package board.movement;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.BoardUtils;
import board.PieceType;

public class KingMovement extends PieceMovement {

    private final long KING_RANGE = 0x0000000000070507L;
    private final int CENTRE = 9; // 9 Right shifts of KING_RANGE
                                  // puts the centre into the last bit

    private final long DEFAULT_UNSAFE_MOVES = 0x0L;

    // Castling flags and locations
    private CastleFlags flags;

    private final long wKCastleFull = 0x000000000000000fL;
    private final long wKCastleSpan = 0x0000000000000006L;
    private final long wKCastlePos = 0x0000000000000002L;
    private final long wKRookPos = 0x0000000000000001L;

    private final long wQCastleFull = 0x00000000000000f8L;
    private final long wQCastleSpan = 0x0000000000000030L;
    private final long wQCastlePos = 0x0000000000000020L;
    private final long wQRookPos = 0x0000000000000080L;

    private final long bKCastleFull = 0x0f00000000000000L;
    private final long bKCastleSpan = 0x0600000000000000L;
    private final long bKCastlePos = 0x0200000000000000L;
    private final long bKRookPos = 0x0100000000000000L;

    private final long bQCastleFull = 0xf800000000000000L;
    private final long bQCastleSpan = 0x3000000000000000L;
    private final long bQCastlePos = 0x2000000000000000L;
    private final long bQRookPos = 0x8000000000000000L;

    public KingMovement(Board board) {
        super(board);
        constructKingMovement(board, DEFAULT_UNSAFE_MOVES, DEFAULT_UNSAFE_MOVES, new CastleFlags());
    }

    public KingMovement(Board board, CastleFlags flags) {
        super(board);
        constructKingMovement(board, DEFAULT_UNSAFE_MOVES, DEFAULT_UNSAFE_MOVES, flags);
    }

    public KingMovement(Board board, long unsafeForWhite, long unsafeForBlack) {
        super(board);
        constructKingMovement(board, unsafeForWhite, unsafeForBlack, new CastleFlags());
    }

    private void constructKingMovement(Board board, long unsafeForWhite, long unsafeForBlack, CastleFlags flags) {
        this.flags = flags;
        updateCastleFlags();
        initializeMoves(PieceType.WK, unsafeForWhite);
        initializeMoves(PieceType.BK, unsafeForBlack);
    }

    public void initializeMoves(PieceType piece, long unsafeMoves) {
        List<Move> moves = new ArrayList<Move>();
        long currentKing = board.getBitBoard(piece);
        int i = initialIndex(currentKing);

        long possibleMoves = i < CENTRE ? KING_RANGE >> (CENTRE - i) : KING_RANGE << (i - CENTRE);
        // Eliminate wrap around
        if (getX(i) == 7) {
            possibleMoves &= ~FILE_A;
        } else if (getX(i) == 0) {
            possibleMoves &= ~FILE_H;
        }
        long range = possibleMoves;

        // Eliminate moves putting king in check
        possibleMoves &= ~unsafeMoves;

        // Eliminate same colour 'captures'
        long sameColorPieces = piece.isWhitePiece() ? whitePieces : blackPieces;
        possibleMoves &= ~sameColorPieces;

        int x = getX(i);
        int y = getY(i);

        // Check for castling (Castling still available && no piece between rook
        // and king && these spots not in check)
        long castleBoard = currentKing | ~occupied;
        boolean inCheck = (currentKing & unsafeMoves) == currentKing;
        if (piece.isWhitePiece() && !inCheck) {
            castleBoard |= board.getBitBoard(PieceType.WR);

            if (flags.isWKCastle() && ((wKCastleFull & castleBoard) == wKCastleFull && (wKCastleSpan & unsafeMoves) == 0)
                    && ((wKCastlePos & ~unsafeMoves) == wKCastlePos)) {
                // White king castle
                possibleMoves |= wKCastlePos;
            }

            if (flags.isWQCastle() && ((wQCastleFull & castleBoard) == wQCastleFull && (wQCastleSpan & unsafeMoves) == 0)
                    && ((wQCastlePos & ~unsafeMoves) == wQCastlePos)) {
                // White queen castle
                possibleMoves |= wQCastlePos;
            }
        } else if (piece.isBlackPiece() && !inCheck) {
            castleBoard |= board.getBitBoard(PieceType.BR);

            if (flags.isBKCastle() && ((bKCastleFull & castleBoard) == bKCastleFull && (bKCastleSpan & unsafeMoves) == 0)
                    && ((bKCastlePos & ~unsafeMoves) == bKCastlePos)) {
                // Black king castle
                possibleMoves |= bKCastlePos;
            }

            if (flags.isBQCastle() && ((bQCastleFull & castleBoard) == bQCastleFull && (bQCastleSpan & unsafeMoves) == 0)
                    && ((bQCastlePos & ~unsafeMoves) == bQCastlePos)) {
                // Black queen castle
                possibleMoves |= bQCastlePos;
            }
        }

        moves.addAll(getMoves(possibleMoves, piece, x, y));
        setMoves(piece, moves, range);
    }

    public void updateUnsafeMoves(long unsafeForWhite, long unsafeForBlack) {
        initializeMoves(PieceType.WK, unsafeForWhite);
        initializeMoves(PieceType.BK, unsafeForBlack);
    }

    public void updateCastleFlags() {
        if (flags.isWKCastle() || flags.isWQCastle()) {
            // Check if king has moved
            if (board.getBitBoard(PieceType.WK) != BoardUtils.WK_INITIAL) {
                flags.noWKCastle();
                flags.noWQCastle();
            }

            // Check if either rook has moved
            long rookBoard = board.getBitBoard(PieceType.WR);
            if ((rookBoard & wKRookPos) != wKRookPos) {
                flags.noWKCastle();
            }
            if ((rookBoard & wQRookPos) != wQRookPos) {
                flags.noWQCastle();
            }
        }

        if (flags.isBKCastle() || flags.isBQCastle()) {
            // Check if king has moved
            if (board.getBitBoard(PieceType.BK) != BoardUtils.BK_INITIAL) {
                flags.noBKCastle();
                flags.noBQCastle();
            }

            // Check if either rook has moved
            long rookBoard = board.getBitBoard(PieceType.BR);
            if ((rookBoard & bKRookPos) != bKRookPos) {
                flags.noBKCastle();
            }
            if ((rookBoard & bQRookPos) != bQRookPos) {
                flags.noBQCastle();
            }
        }
    }

    public CastleFlags getFlags() {
        return flags;
    }

}
