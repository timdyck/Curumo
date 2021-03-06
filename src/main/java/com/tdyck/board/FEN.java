package com.tdyck.board;

import java.util.HashMap;
import java.util.Map;

import com.tdyck.board.movement.CastleFlags;
import com.tdyck.board.movement.Move;
import com.tdyck.gameplay.Gameplay;

/**
 * Utility class to convert a Forsyth-Edwards Notation string into a
 * {@link Gameplay}
 */
public class FEN {

    /**
     * Return board description
     * @param fenString
     * @return board from fen string
     */
    public static Gameplay fromFenString(String fenString) {

        // Handle board block
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

        int index = 0;
        int boardPos = 63;

        while (index < fenString.length() && fenString.charAt(index) != ' ') {
            switch (fenString.charAt(index)) {
            case 'P':
                bitBoards.put(PieceType.WP, bitBoards.get(PieceType.WP) | 1L << boardPos--);
                break;
            case 'p':
                bitBoards.put(PieceType.BP, bitBoards.get(PieceType.BP) | 1L << boardPos--);
                break;
            case 'R':
                bitBoards.put(PieceType.WR, bitBoards.get(PieceType.WR) | 1L << boardPos--);
                break;
            case 'r':
                bitBoards.put(PieceType.BR, bitBoards.get(PieceType.BR) | 1L << boardPos--);
                break;
            case 'N':
                bitBoards.put(PieceType.WN, bitBoards.get(PieceType.WN) | 1L << boardPos--);
                break;
            case 'n':
                bitBoards.put(PieceType.BN, bitBoards.get(PieceType.BN) | 1L << boardPos--);
                break;
            case 'B':
                bitBoards.put(PieceType.WB, bitBoards.get(PieceType.WB) | 1L << boardPos--);
                break;
            case 'b':
                bitBoards.put(PieceType.BB, bitBoards.get(PieceType.BB) | 1L << boardPos--);
                break;
            case 'Q':
                bitBoards.put(PieceType.WQ, bitBoards.get(PieceType.WQ) | 1L << boardPos--);
                break;
            case 'q':
                bitBoards.put(PieceType.BQ, bitBoards.get(PieceType.BQ) | 1L << boardPos--);
                break;
            case 'K':
                bitBoards.put(PieceType.WK, bitBoards.get(PieceType.WK) | 1L << boardPos--);
                break;
            case 'k':
                bitBoards.put(PieceType.BK, bitBoards.get(PieceType.BK) | 1L << boardPos--);
                break;
            case '/':
                break;
            case '1':
                boardPos -= 1;
                break;
            case '2':
                boardPos -= 2;
                break;
            case '3':
                boardPos -= 3;
                break;
            case '4':
                boardPos -= 4;
                break;
            case '5':
                boardPos -= 5;
                break;
            case '6':
                boardPos -= 6;
                break;
            case '7':
                boardPos -= 7;
                break;
            case '8':
                boardPos -= 8;
                break;
            default:
                break;
            }

            index++;
        }

        // Handle turn colour
        PieceType.Colour turn = fenString.charAt(++index) == 'w' ? PieceType.Colour.WHITE : PieceType.Colour.BLACK;
        index += 2;

        // Handle castling flags
        boolean wKCastle = false;
        boolean wQCastle = false;
        boolean bKCastle = false;
        boolean bQCastle = false;

        while (fenString.charAt(index) != ' ') {
            switch (fenString.charAt(index)) {
            case '-':
                break;
            case 'K':
                wKCastle = true;
                break;
            case 'Q':
                wQCastle = true;
                break;
            case 'k':
                bKCastle = true;
                break;
            case 'q':
                bQCastle = true;
                break;
            default:
                break;
            }

            index++;
        }

        // Handle en passant
        index++;
        Move previousMove = Move.getFirstPreviousMove();
        if (fenString.charAt(index) != '-') {
            int x = fenString.charAt(index) - 'a';
            int y = Integer.parseInt(fenString.substring(++index, index + 1));

            if (y == 3) {
                previousMove = new Move(PieceType.WP, x, 1, x, 3);
            } else if (y == 6) {
                previousMove = new Move(PieceType.BP, x, 6, x, 4);
            }
        }

        Board board = new Board(bitBoards);
        CastleFlags flags = new CastleFlags(wKCastle, wQCastle, bKCastle, bQCastle);
        Gameplay game = new Gameplay(board, previousMove, turn, flags);

        return game;
    }
}
