package com.tdyck.board.movement;

/**
 * Keeps track of all the current castling rules
 */
public class CastleFlags {
    private boolean wKCastle;
    private boolean wQCastle;
    private boolean bKCastle;
    private boolean bQCastle;

    public CastleFlags() {
        this.wKCastle = true;
        this.wQCastle = true;
        this.bKCastle = true;
        this.bQCastle = true;
    }

    public CastleFlags(boolean wKCastle, boolean wQCastle, boolean bKCastle, boolean bQCastle) {
        this.wKCastle = wKCastle;
        this.wQCastle = wQCastle;
        this.bKCastle = bKCastle;
        this.bQCastle = bQCastle;
    }

    /**
     * Copy constructor
     * 
     * @param flags
     */
    public CastleFlags(CastleFlags flags) {
        this.wKCastle = flags.wKCastle;
        this.wQCastle = flags.wQCastle;
        this.bKCastle = flags.bKCastle;
        this.bQCastle = flags.bQCastle;
    }

    public boolean isWKCastle() {
        return wKCastle;
    }

    public void noWKCastle() {
        wKCastle = false;
    }

    public boolean isWQCastle() {
        return wQCastle;
    }

    public void noWQCastle() {
        wQCastle = false;
    }

    public boolean isBKCastle() {
        return bKCastle;
    }

    public void noBKCastle() {
        bKCastle = false;
    }

    public boolean isBQCastle() {
        return bQCastle;
    }

    public void noBQCastle() {
        bQCastle = false;
    }
}
