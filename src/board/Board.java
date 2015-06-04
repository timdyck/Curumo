package board;

import java.util.HashMap;
import java.util.Map;

import board.movement.Move;
import board.movement.MoveType;

public class Board {

    public final static int DIMENSION = 8;
    public final static int NUM_SQUARES = DIMENSION * DIMENSION;
    public final static int NUM_BOARDS = 12;

    private Map<PieceType, Long> bitBoards;

    private Board(Map<PieceType, Long> bitBoards) {
        this.bitBoards = bitBoards;
    }

    public static Board StandardBoard() {
        Map<PieceType, Long> bitBoards = new HashMap<PieceType, Long>();
        bitBoards.put(PieceType.WP, 0x000000000000ff00L);
        bitBoards.put(PieceType.WR, 0x0000000000000081L);
        bitBoards.put(PieceType.WN, 0x0000000000000042L);
        bitBoards.put(PieceType.WB, 0x0000000000000024L);
        bitBoards.put(PieceType.WQ, 0x0000000000000010L);
        bitBoards.put(PieceType.WK, 0x0000000000000008L);
        bitBoards.put(PieceType.BP, 0x00ff000000000000L);
        bitBoards.put(PieceType.BR, 0x8100000000000000L);
        bitBoards.put(PieceType.BN, 0x4200000000000000L);
        bitBoards.put(PieceType.BB, 0x2400000000000000L);
        bitBoards.put(PieceType.BQ, 0x1000000000000000L);
        bitBoards.put(PieceType.BK, 0x0800000000000000L);
        return new Board(bitBoards);
    }

    public Map<PieceType, Long> getBitBoards() {
        return bitBoards;
    }

    public long getBitBoard(PieceType type) {
        return bitBoards.get(type);
    }

    public void setBitBoard(PieceType piece, long newBitBoard) {
        bitBoards.put(piece, newBitBoard);
    }

    /**
     * Updates the board after the given move is executed.
     * 
     * @param move
     */
    public void updateBoard(Move move) {
        PieceType piece = move.getPiece();
        long pieceBitBoard = getBitBoard(piece);
        long fromBitBoard = getBitBoard(move.getX1(), move.getY1());
        long toBitBoard = getBitBoard(move.getX2(), move.getY2());

        // Remove piece from old position
        pieceBitBoard &= ~fromBitBoard;

        if (move.getType().equals(MoveType.TYPICAL)) {
            // Move piece to new location
            pieceBitBoard |= toBitBoard;
        } else if (move.getType().equals(MoveType.CAPTURE)) {
            // Remove taken piece
            PieceType newPiece = move.getCapturedPiece();
            long newPieceBitBoard = getBitBoard(newPiece);
            newPieceBitBoard &= ~toBitBoard;
            setBitBoard(newPiece, newPieceBitBoard);

            // Move piece to new location
            pieceBitBoard |= toBitBoard;
        } else if (move.getType().equals(MoveType.PROMOTION)) {
            // Add new piece
            PieceType newPiece = move.getPromotionPiece();
            long newPieceBitBoard = getBitBoard(newPiece);
            newPieceBitBoard |= toBitBoard;
            setBitBoard(newPiece, newPieceBitBoard);
        } else if (move.getType().equals(MoveType.CAPTURE_AND_PROMOTION)) {
            // Remove taken piece
            PieceType capturedPiece = move.getCapturedPiece();
            long capturedPieceBitBoard = getBitBoard(capturedPiece);
            capturedPieceBitBoard &= ~toBitBoard;
            setBitBoard(capturedPiece, capturedPieceBitBoard);

            // Add new piece
            PieceType promotionPiece = move.getPromotionPiece();
            long promotionPieceBitBoard = getBitBoard(promotionPiece);
            promotionPieceBitBoard |= toBitBoard;
            setBitBoard(promotionPiece, promotionPieceBitBoard);
        } else if (move.getType().equals(MoveType.EN_PASSANT)) {
            PieceType capturedPiece;
            if (move.getPiece().isWhitePiece()) {
                capturedPiece = PieceType.BP;
            } else {
                capturedPiece = PieceType.WP;
            }

            // Remove taken piece
            long capturedPieceBitBoard = getBitBoard(capturedPiece);
            long capturedPieceLocation = getBitBoard(move.getX2(), move.getY1());
            capturedPieceBitBoard &= ~capturedPieceLocation;
            setBitBoard(capturedPiece, capturedPieceBitBoard);

            // Move piece to new location
            pieceBitBoard |= toBitBoard;
        } else if (move.getType().equals(MoveType.CASTLE)) {
            PieceType castledRook;
            long rookOldLocation;
            long rookNewLocation;

            // Get appropriate rook
            if (move.getPiece().isWhitePiece()) {
                castledRook = PieceType.WR;
            } else {
                castledRook = PieceType.BR;
            }

            // Get rook start and end positions
            if (move.getX2() - move.getX1() > 0) {
                // King-side Castle
                rookOldLocation = getBitBoard(move.getX1() + 3, move.getY1());
                rookNewLocation = getBitBoard(move.getX1() + 1, move.getY1());
            } else {
                // Queen-side Castle
                rookOldLocation = getBitBoard(move.getX1() - 4, move.getY1());
                rookNewLocation = getBitBoard(move.getX1() - 1, move.getY1());
            }

            // Move rook to new location
            long rookBitBoard = getBitBoard(castledRook);
            rookBitBoard |= rookNewLocation;

            // Remove rook's old position
            rookBitBoard &= ~rookOldLocation;
            setBitBoard(castledRook, rookBitBoard);

            // Move king to new location
            pieceBitBoard |= toBitBoard;
        }

        setBitBoard(piece, pieceBitBoard);
    }

    /**
     * Gets the board in a matrix representation
     * 
     * @return matrix representing the board
     */
    public String[][] boardToArray() {
        String[][] boardArray = new String[DIMENSION][DIMENSION];

        for (PieceType type : PieceType.values()) {
            String[][] bitBoardArray = bitBoardToArray(type);
            boardArray = mergeArrays(bitBoardArray, boardArray);
        }

        return boardArray;
    }

    public String[][] bitBoardToArray(PieceType type) {
        return bitBoardToArray(bitBoards.get(type), type.name());
    }

    public static String[][] bitBoardToArray(long bitBoard, String piece) {
        String[][] boardArray = new String[DIMENSION][DIMENSION];
        long pos = 0x8000000000000000L; // Bit in first position

        for (int i = 0; i < NUM_SQUARES; i++) {
            if ((bitBoard & pos) == pos) {
                boardArray[i / DIMENSION][i % DIMENSION] = piece;
            }
            pos = pos >>> 1;
        }

        return boardArray;
    }

    private String[][] mergeArrays(String[][] arr1, String[][] arr2) {
        String[][] boardArray = new String[DIMENSION][DIMENSION];

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (arr1[i][j] == arr2[i][j]) {
                    boardArray[i][j] = arr1[i][j];
                } else if (arr1[i][j] == null) {
                    boardArray[i][j] = arr2[i][j];
                } else if (arr2[i][j] == null) {
                    boardArray[i][j] = arr1[i][j];
                } else {
                    boardArray[i][j] = "XX";
                }
            }
        }

        return boardArray;
    }

    /**
     * Converts 8x8 matrix representation to a Board.
     * 
     * Expects {@link PieceType} on the board
     * 
     * @param matrix
     * @return Board
     */
    public static Board arrayToBoard(String[][] matrix) {
        Map<PieceType, Long> bitBoards = new HashMap<PieceType, Long>();
        bitBoards.put(PieceType.WP, 0L);
        bitBoards.put(PieceType.WR, 0L);
        bitBoards.put(PieceType.WN, 0L);
        bitBoards.put(PieceType.WB, 0L);
        bitBoards.put(PieceType.WQ, 0L);
        bitBoards.put(PieceType.WK, 0L);
        bitBoards.put(PieceType.BP, 0L);
        bitBoards.put(PieceType.BR, 0L);
        bitBoards.put(PieceType.BN, 0L);
        bitBoards.put(PieceType.BB, 0L);
        bitBoards.put(PieceType.BQ, 0L);
        bitBoards.put(PieceType.BK, 0L);

        long pos = 0x8000000000000000L;
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                String content = matrix[i][j];
                PieceType piece;

                try {
                    piece = PieceType.valueOf(content);
                } catch (IllegalArgumentException e) {
                    pos = pos >>> 1;
                    continue;
                }

                bitBoards.put(piece, bitBoards.get(piece) | pos);
                pos = pos >>> 1;
            }
        }

        return new Board(bitBoards);
    }

    public void printBoard() {
        printBoard(boardToArray());
    }

    private static void printBoard(String[][] boardArray) {
        System.out.println(" -------------------------");
        for (int i = 0; i < DIMENSION; i++) {

            System.out.print((DIMENSION - i) + "|");

            for (int j = 0; j < DIMENSION; j++) {

                if (boardArray[i][j] == null) {
                    System.out.print("  ");
                } else {
                    System.out.print(boardArray[i][j]);
                }

                System.out.print("|");
            }

            System.out.println();
            System.out.println(" -------------------------");
        }

        System.out.println("  A  B  C  D  E  F  G  H  ");
    }

    public static void printBitBoard(long bitBoard, String piece) {
        String[][] bitBoardArray = bitBoardToArray(bitBoard, piece);
        printBoard(bitBoardArray);
    }

    /**
     * Bottom left=0, bottom right=7, top left=56, top right= 63
     * 
     * @param x
     * @param y
     * @return a bitboard with a 1 at position (x,y)
     */
    private long getBitBoard(int x, int y) {
        int s = (Board.DIMENSION * y) + (DIMENSION - 1 - x);
        return 1L << s;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bitBoards == null) ? 0 : bitBoards.hashCode());
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
        Board other = (Board) obj;
        if (bitBoards == null) {
            if (other.bitBoards != null)
                return false;
        } else if (!bitBoards.equals(other.bitBoards))
            return false;
        return true;
    }

}
