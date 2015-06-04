package board;

import java.util.HashMap;
import java.util.Map;

public class BoardUtilities {

    public static final long WP_INITIAL = 0x000000000000ff00L;
    public static final long WR_INITIAL = 0x0000000000000081L;
    public static final long WN_INITIAL = 0x0000000000000042L;
    public static final long WB_INITIAL = 0x0000000000000024L;
    public static final long WQ_INITIAL = 0x0000000000000010L;
    public static final long WK_INITIAL = 0x0000000000000008L;
    public static final long BP_INITIAL = 0x00ff000000000000L;
    public static final long BR_INITIAL = 0x8100000000000000L;
    public static final long BN_INITIAL = 0x4200000000000000L;
    public static final long BB_INITIAL = 0x2400000000000000L;
    public static final long BQ_INITIAL = 0x1000000000000000L;
    public static final long BK_INITIAL = 0x0800000000000000L;

    /**
     * Gets the board in a matrix representation
     * 
     * @return matrix representing the board
     */
    public static String[][] boardToArray(Board board) {
        String[][] boardArray = new String[Board.DIMENSION][Board.DIMENSION];

        for (PieceType type : PieceType.values()) {
            String[][] bitBoardArray = bitBoardToArray(board, type);
            boardArray = mergeArrays(bitBoardArray, boardArray);
        }

        return boardArray;
    }

    public static String[][] bitBoardToArray(Board board, PieceType type) {
        return bitBoardToArray(board.getBitBoards().get(type), type.name());
    }

    public static String[][] bitBoardToArray(long bitBoard, String piece) {
        String[][] boardArray = new String[Board.DIMENSION][Board.DIMENSION];
        long pos = 0x8000000000000000L; // Bit in first position

        for (int i = 0; i < Board.NUM_SQUARES; i++) {
            if ((bitBoard & pos) == pos) {
                boardArray[i / Board.DIMENSION][i % Board.DIMENSION] = piece;
            }
            pos = pos >>> 1;
        }

        return boardArray;
    }

    private static String[][] mergeArrays(String[][] arr1, String[][] arr2) {
        String[][] boardArray = new String[Board.DIMENSION][Board.DIMENSION];

        for (int i = 0; i < Board.DIMENSION; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
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
        for (int i = 0; i < Board.DIMENSION; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
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

    public static void printBoard(Board board) {
        printBoard(boardToArray(board));
    }

    private static void printBoard(String[][] boardArray) {
        System.out.println(" -------------------------");
        for (int i = 0; i < Board.DIMENSION; i++) {

            System.out.print((Board.DIMENSION - i) + "|");

            for (int j = 0; j < Board.DIMENSION; j++) {

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
}
