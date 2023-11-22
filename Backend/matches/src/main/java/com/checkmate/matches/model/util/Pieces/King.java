package com.checkmate.matches.model.util.Pieces;
import com.checkmate.matches.model.util.Game.*;
public class King extends Piece
{

    public King(boolean isWhite) {
        super(isWhite, "King");
    }
    public String toString() {
        return super.toString() + " " + pieceType;
    }
}