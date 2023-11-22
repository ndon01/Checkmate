package com.checkmate.matches.model.util.Pieces;
import com.checkmate.matches.model.util.Game.*;
public class Piece
{
    public boolean isWhite;
    public String pieceType;

    public Piece(boolean isWhite, String pieceType)
    {
        this.isWhite = isWhite;
        this.pieceType = pieceType;
    }

    public void setPieceColor(boolean isWhite)
    {
        this.isWhite = isWhite;
    }

    public String getPieceType()
    {
        return pieceType;
    }

    @Override
    public String toString() {
        return String.valueOf(isWhite);
    }

    public boolean isWhite() {
        return isWhite;
    }

    public static Piece createPiece(String Type, boolean White) {
        switch (Type) {
            // Creates and returns an instance of the specified piece type
            case ("Pawn"):
                return new Pawn(White);
            case ("Rook"):
                return new Rook(White);
            case ("Bishop"):
                return new Bishop(White);
            case ("Knight"):
                return new Knight(White);
            case ("King"):
                return new King(White);
            case ("Queen"):
                return new Queen(White);
            default:
                return null; // Returns null if the type is not recognized
        }
    }

    public boolean isLegal(Move move, Board game) {
        // Checks if the piece's color matches the current turn in the game
        if (isWhite != game.isWhiteTurn())
            return false; // Move is illegal if it's not this piece's turn

        // Checks if the piece at the destination is of the same color
        Piece captured = game.getPiece(move.getRow1(), move.getCol1());
        if (captured != null && captured.isWhite == this.isWhite)
            return false; // Move is illegal if it tries to capture a piece of the same color

        return true; // If none of the above conditions are met, the move is considered legal
    }

}