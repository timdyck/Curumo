package board;

import java.util.HashMap;
import java.util.Map;

public class Board {

    public final static int DIMENSION = 8;
    public final static int NUM_BOARDS = 12;

    public final static String BLANK_STR = "OO";

    private static Map<BoardType, Long> bitBoards;

    public Board() {
        initializeBoards();
    }

    public static void initializeBoards() {
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

    public static void initializeRandomBoards() {

    }

    public Map<BoardType, Long> getBitBoards() {
        return bitBoards;
    }

    public String[][] getBoardArray() {
        String[][] boardArray = new String[DIMENSION][DIMENSION];

        // Bits to board
        for (BoardType type : BoardType.values()) {
            long board = bitBoards.get(type);
            long pos = 0x8000000000000000L; // Bit in first position

            for (int i = 0; i < DIMENSION * DIMENSION; i++) {
                if ((board & pos) == pos) {
                    boardArray[i / DIMENSION][i % DIMENSION] = type.name();
                }
                pos = pos >>> 1;
            }

        }

        return boardArray;
    }

    public void printBoard() {
        String[][] boardArray = getBoardArray();

        // Print out the board
        System.out.println("-------------------------");
        for (int i = 0; i < DIMENSION; i++) {
            System.out.print("|");
            for (int j = 0; j < DIMENSION; j++) {
                if (boardArray[i][j] == null) {
                    System.out.print(BLANK_STR);
                } else {
                    System.out.print(boardArray[i][j]);
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.println("-------------------------");
        }
    }

    private String longToHex(long n) {
        return String.format("0x%8s", Long.toHexString(n)).replace(' ', '0');
    }

}
