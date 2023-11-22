package com.checkmate.matches.model.util.Pieces;
import com.checkmate.matches.model.util.Game.*;
public class Rook extends Piece
{

    public Rook(boolean isWhite) {
        super(isWhite, "Rook");
    }
    public String toString() {
        return super.toString() + " " + pieceType;
    }
}