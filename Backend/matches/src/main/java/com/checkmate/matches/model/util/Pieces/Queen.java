package com.checkmate.matches.model.util.Pieces;
import com.checkmate.matches.model.util.Game.*;
public class Queen extends Piece
{

    public Queen(boolean isWhite) {
        super(isWhite, "Queen");
    }

    @Override
    public String toString() {
        return super.toString() + " " + pieceType;
    }

}