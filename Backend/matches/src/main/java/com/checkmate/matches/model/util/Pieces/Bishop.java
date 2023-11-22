package com.checkmate.matches.model.util.Pieces;
import com.checkmate.matches.model.util.Game.*;
public class Bishop extends Piece
{

    public Bishop(boolean isWhite) {
        super(isWhite, "Bishop");
    }
    public String toString() {
        return super.toString() + " " + pieceType;
    }
}