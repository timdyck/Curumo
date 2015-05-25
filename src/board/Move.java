package board;

public class Move {
    PieceType type;

    int rank1;
    int file1;
    int rank2;
    int file2;

    boolean capture = false;
    PieceType promote;

    public Move(PieceType type, int rank1, int file1, int rank2, int file2) {
        this.type = type;
        this.rank1 = rank1;
        this.file1 = file1;
        this.rank2 = rank2;
        this.file2 = file2;
    }

    public Move(PieceType type, int rank1, int file1, int rank2, int file2, boolean capture) {
        this.type = type;
        this.rank1 = rank1;
        this.file1 = file1;
        this.rank2 = rank2;
        this.file2 = file2;

        this.capture = capture;
    }

    public Move(PieceType type, int rank1, int file1, int rank2, int file2, PieceType promote) {
        assert (type.equals(PieceType.WP) || type.equals(PieceType.BP)) : "Can't promote a piece that is not a pawn!";

        this.type = type;
        this.rank1 = rank1;
        this.file1 = file1;
        this.rank2 = rank2;
        this.file2 = file2;

        this.promote = promote;
    }

    public Move(PieceType type, int rank1, int file1, int rank2, int file2, boolean capture, PieceType promote) {
        assert (type.equals(PieceType.WP) || type.equals(PieceType.BP)) : "Can't promote a piece that is not a pawn!";

        this.type = type;
        this.rank1 = rank1;
        this.file1 = file1;
        this.rank2 = rank2;
        this.file2 = file2;

        this.capture = capture;
        this.promote = promote;
    }

    public void printMove() {
        String printStr = type.name() + " - (" + rank1 + "," + file1 + ") to (" + rank2 + "," + file2 + ")";
        if (capture && promote != null) {
            printStr += " (capture and promote " + type.name() + " to " + promote.name() + "!)";
        } else if (capture) {
            printStr += " (capture!)";
        } else if (promote != null) {
            printStr += " (promote " + type.name() + " to " + promote.name() + "!)";
        }
        System.out.println(printStr);
    }
}
