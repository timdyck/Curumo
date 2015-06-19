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

    private final long WKCastleReq = 0x000000000000000fL;
    private final long WKCastlePos = 0x0000000000000002L;
    private final long wKRookPos = 0x0000000000000001L;

    private final long WQCastleReq = 0x00000000000000f8L;
    private final long WQCastlePos = 0x0000000000000020L;
    private final long wQRookPos = 0x0000000000000080L;

    private final long BKCastleReq = 0x0f00000000000000L;
    private final long BKCastlePos = 0x0200000000000000L;
    private final long bKRookPos = 0x0100000000000000L;

    private final long BQCastleReq = 0xf800000000000000L;
    private final long BQCastlePos = 0x2000000000000000L;
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
        long currentKings = board.getBitBoard(piece);

        for (int i = initialIndex(currentKings); i < finalIndex(currentKings); i++) {
            if (((currentKings >> i) & 1) == 1) {
                long possibleMoves = i < CENTRE ? KING_RANGE >> (CENTRE - i) : KING_RANGE << (i - CENTRE);

                long sameColorPieces = piece.isWhitePiece() ? whitePieces : blackPieces;
                possibleMoves &= ~sameColorPieces;

                // Eliminate wrap around
                if (getX(i) == 7) {
                    possibleMoves &= ~FILE_A;
                } else if (getX(i) == 0) {
                    possibleMoves &= ~FILE_H;
                }

                // Eliminate moves putting king in check
                possibleMoves &= ~unsafeMoves;

                int x = getX(i);
                int y = getY(i);

                // Check for castling
                long castleBoard = currentKings | ~occupied;
                if (piece.isWhitePiece()) {
                    castleBoard |= board.getBitBoard(PieceType.WR);

                    if (flags.isWKCastle() && ((WKCastleReq & castleBoard) == WKCastleReq)
                            && ((WKCastlePos & ~unsafeMoves) == WKCastlePos)) {
                        // White king castle
                        possibleMoves |= WKCastlePos;
                    }
                    if (flags.isWQCastle() && ((WQCastleReq & castleBoard) == WQCastleReq)
                            && ((WQCastlePos & ~unsafeMoves) == WQCastlePos)) {
                        // White queen castle
                        possibleMoves |= WQCastlePos;
                    }
                } else if (piece.isBlackPiece()) {
                    castleBoard |= board.getBitBoard(PieceType.BR);

                    if (flags.isBKCastle() && ((BKCastleReq & castleBoard) == BKCastleReq)
                            && ((BKCastlePos & ~unsafeMoves) == BKCastlePos)) {
                        // Black king castle
                        possibleMoves |= BKCastlePos;
                    }
                    if (flags.isBQCastle() && ((BQCastleReq & castleBoard) == BQCastleReq)
                            && ((BQCastlePos & ~unsafeMoves) == BQCastlePos)) {
                        // Black queen castle
                        possibleMoves |= BQCastlePos;
                    }
                }

                moves.addAll(getMoves(possibleMoves, piece, x, y));
                setMoves(piece, moves, possibleMoves);
            }
        }
    }

    public void updateUnsafeMoves(long unsafeForWhite, long unsafeForBlack) {
        initializeMoves(PieceType.WK, DEFAULT_UNSAFE_MOVES);
        initializeMoves(PieceType.BK, DEFAULT_UNSAFE_MOVES);
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
