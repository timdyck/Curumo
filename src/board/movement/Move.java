package board.movement;

import board.PieceType;

public class Move {
    private PieceType piece;

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private MoveType type;
    private PieceType capturedPiece;
    private PieceType promotionPiece;

    /**
     * Use this constructor if the move type is {@link MoveType#TYPICAL}
     * 
     * @param piece
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param type
     */
    public Move(PieceType piece, int x1, int y1, int x2, int y2) {
        this.piece = piece;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.type = MoveType.TYPICAL;
    }

    /**
     * Use this constructor if the move type is NOT {@link MoveType#CAPTURE} or
     * NOT {@link MoveType#PROMOTION} or NOT
     * {@link MoveType#CAPTURE_AND_PROMOTION}
     * 
     * @param piece
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param type
     */
    public Move(PieceType piece, int x1, int y1, int x2, int y2, MoveType type) {
        assert !type.equals(MoveType.CAPTURE) && !type.equals(MoveType.PROMOTION) && !type.equals(MoveType.CAPTURE_AND_PROMOTION) : "With this contruction, MoveType must not be CAPTURE or PROMOTION or CAPTURE_AND_PROMOTION!";

        this.piece = piece;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.type = type;
    }

    /**
     * Use this constructor for move types {@link MoveType#CAPTURE} and
     * {@link MoveType#PROMOTION}
     * 
     * @param piece
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param type
     * @param captureOrPromote
     */
    public Move(PieceType piece, int x1, int y1, int x2, int y2, MoveType type, PieceType captureOrPromote) {
        assert type.equals(MoveType.CAPTURE) || type.equals(MoveType.PROMOTION) : "With this contruction, MoveType must be either CAPTURE or PROMOTION!";

        this.piece = piece;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.type = type;

        if (type.equals(MoveType.CAPTURE)) {
            this.capturedPiece = captureOrPromote;
        } else {
            assert (piece.equals(PieceType.WP) || piece.equals(PieceType.BP)) : "Can't promote a piece that is not a pawn!";
            this.promotionPiece = captureOrPromote;
        }
    }

    /**
     * Use this constructor for move type {@link MoveType#CAPTURE_AND_PROMOTION}
     * 
     * @param piece
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param type
     * @param captured
     * @param promotion
     */
    public Move(PieceType piece, int x1, int y1, int x2, int y2, MoveType type, PieceType captured, PieceType promotion) {
        assert type.equals(MoveType.CAPTURE_AND_PROMOTION) : "With this contruction, MoveType must be either CAPTURE_AND_PROMOTION!";
        assert (piece.equals(PieceType.WP) || piece.equals(PieceType.BP)) : "Can't promote a piece that is not a pawn!";

        this.piece = piece;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.type = type;

        this.capturedPiece = captured;
        this.promotionPiece = promotion;
    }

    public PieceType getPiece() {
        return piece;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public MoveType getType() {
        return type;
    }

    public PieceType getCapturedPiece() {
        return capturedPiece;
    }

    public PieceType getPromotionPiece() {
        return promotionPiece;
    }

    /**
     * @return representation for previous move when there was no previous move
     */
    public static Move getFirstPreviousMove() {
        return new Move(PieceType.BB, -1, -1, -1, -1, MoveType.TYPICAL);
    }

    /**
     * @param move
     * @return whether or not this move is the first move of the game
     */
    public static boolean isFirstMove(Move move) {
        return move.x1 == -1 && move.y1 == -1 && move.x2 == -1 && move.y2 == -1 && move.piece.equals(PieceType.BB);
    }

    /**
     * @return move in the UCI from, i.e. x1=4 y1=1 x2=4 y1=3 gives e2e4
     */
    public String toUciForm() {
        String uciForm = String.valueOf((char) (x1 + 'a')) + (y1 + 1) + String.valueOf((char) (x2 + 'a')) + (y2 + 1);

        // Append piece type if promotion
        if (type.equals(MoveType.PROMOTION) || type.equals(MoveType.CAPTURE_AND_PROMOTION)) {
            if (promotionPiece.equals(PieceType.WN) || promotionPiece.equals(PieceType.BN)) {
                uciForm += 'k';
            } else {
                uciForm += promotionPiece.name().substring(1).toLowerCase();
            }
        }

        return uciForm;
    }

    @Override
    public String toString() {
        String printStr = piece.name() + " - (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")";
        if (type.equals(MoveType.CAPTURE_AND_PROMOTION)) {
            printStr += " (capture " + capturedPiece + " and promote " + piece + " to " + promotionPiece + "!)";
        } else if (type.equals(MoveType.CAPTURE)) {
            printStr += " (captured " + capturedPiece + "!)";
        } else if (type.equals(MoveType.CAPTURE)) {
            printStr += " (promote " + piece + " to " + promotionPiece + "!)";
        } else {
            printStr += " (" + type + ")";
        }
        return printStr;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((capturedPiece == null) ? 0 : capturedPiece.hashCode());
        result = prime * result + ((piece == null) ? 0 : piece.hashCode());
        result = prime * result + ((promotionPiece == null) ? 0 : promotionPiece.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + x1;
        result = prime * result + x2;
        result = prime * result + y1;
        result = prime * result + y2;
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
        Move other = (Move) obj;
        if (capturedPiece != other.capturedPiece)
            return false;
        if (piece != other.piece)
            return false;
        if (promotionPiece != other.promotionPiece)
            return false;
        if (type != other.type)
            return false;
        if (x1 != other.x1)
            return false;
        if (x2 != other.x2)
            return false;
        if (y1 != other.y1)
            return false;
        if (y2 != other.y2)
            return false;
        return true;
    }

}
