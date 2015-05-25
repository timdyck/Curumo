package board;

import java.util.HashMap;
import java.util.Map;

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
                    System.out.println(piece);
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
