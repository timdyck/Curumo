package board;

import java.util.HashMap;
import java.util.Map;

public class Board {

    public final static int DIMENSION = 8;
    public final static int NUM_BOARDS = 12;

    private static Map<BoardType, Long> bitBoards;

    public Board() {
        initializeBitBoards();
    }

    public static void initializeBitBoards() {
        bitBoards = new HashMap<BoardType, Long>();
        bitBoards.put(BoardType.WP, 0x000000000000ff00L);
        bitBoards.put(BoardType.WR, 0x0000000000000081L);
        bitBoards.put(BoardType.WN, 0x0000000000000042L);
        bitBoards.put(BoardType.WB, 0x0000000000000024L);
        bitBoards.put(BoardType.WQ, 0x0000000000000010L);
        bitBoards.put(BoardType.WK, 0x0000000000000008L);
        bitBoards.put(BoardType.BP, 0x00ff000000000000L);
        bitBoards.put(BoardType.BR, 0x8100000000000000L);
        bitBoards.put(BoardType.BN, 0x4200000000000000L);
        bitBoards.put(BoardType.BB, 0x2400000000000000L);
        bitBoards.put(BoardType.BQ, 0x1000000000000000L);
        bitBoards.put(BoardType.BK, 0x0800000000000000L);
    }

    public Map<BoardType, Long> getBitBoards() {
        return bitBoards;
    }

    public long getBitBoard(BoardType type) {
        return bitBoards.get(type);
    }

    /**
     * Gets the board in a matrix representation
     * 
     * @return matrix representing the board
     */
    public String[][] getBoardArray() {
        String[][] boardArray = new String[DIMENSION][DIMENSION];

        for (BoardType type : BoardType.values()) {
            String[][] bitBoardArray = getBitBoardArray(type);
            boardArray = mergeArrays(bitBoardArray, boardArray);
        }

        return boardArray;
    }

    public String[][] getBitBoardArray(BoardType type) {
        return getBitBoardArray(bitBoards.get(type), type.name());
    }

    public static String[][] getBitBoardArray(long bitBoard, String piece) {
        String[][] boardArray = new String[DIMENSION][DIMENSION];
        long pos = 0x8000000000000000L; // Bit in first position

        for (int i = 0; i < DIMENSION * DIMENSION; i++) {
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

    public void printBoard() {
        printBoard(getBoardArray());
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
        String[][] bitBoardArray = getBitBoardArray(bitBoard, piece);
        printBoard(bitBoardArray);
    }

    private String longToHex(long n) {
        return String.format("0x%8s", Long.toHexString(n)).replace(' ', '0');
    }

}
