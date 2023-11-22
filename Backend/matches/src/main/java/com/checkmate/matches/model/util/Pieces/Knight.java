package com.checkmate.matches.model.util.Pieces;
import com.checkmate.matches.model.util.Game.*;
public class Knight extends Piece {


    public Knight(boolean isWhite) {
        super(isWhite, "Knight");
    }
    public String toString() {
        return super.toString() + " " + pieceType;
    }

}
