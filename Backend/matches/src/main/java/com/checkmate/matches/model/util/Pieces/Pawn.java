package com.checkmate.matches.model.util.Pieces;
import com.checkmate.matches.model.util.Game.*;
public class Pawn extends Piece
{
    public Pawn(boolean isWhite) {
        super(isWhite, "Pawn");
    }
    public String toString() {
        return super.toString() + " " + pieceType;
    }

    public String getPieceType()
    {
        return pieceType;
    }

    public boolean isLegal(Move move, Board game) {
        // First check the legality using the superclass's method
        if (!super.isLegal(move, game))
            return false;

        // Pawn-specific move logic
        int rowDiff = move.getRow1() - move.getRow0(); // Difference in rows
        int colDiff = move.getCol1() - move.getCol0(); // Difference in columns

        // Check if pawn is moving backward, which is illegal
        if ((rowDiff < 0 && isWhite) || (rowDiff > 0 && !isWhite))
            return false;

        // Capture logic and forward movement check
        Piece captured = game.getPiece(move.getRow1(), move.getCol1());
        if (captured != null && Math.abs(rowDiff) == 1 && colDiff == 0)
            return false;
        if ((Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 1) && (captured == null || (captured != null && captured.isWhite == isWhite())))
            return false;

				/* attempted to implement "en passant" but I didn't fully understand the chess rules yet, so I removed it
		if (
				rowDiff == 0 && Math.abs(colDiff) == 1 &&
				(captured == null || (captured != null && captured.white == white) ||
				(captured != null && ((captured.white && (move.getRow1() + 2) != 6) || (!captured.white && (move.getRow1() - 2) != 1)) )
				)
		) return false; */

        // Check for illegal moves: moving more than two spaces or moving two spaces not from the starting position
        if ((rowDiff < -2 && isWhite) || (rowDiff > 2 && !isWhite))
            return false;
        if ((isWhite && rowDiff == -2 && move.getRow0() != 6) || (!isWhite && rowDiff == 2 && move.getRow0() != 1))
            return false;

        // Additional rules can be added here

        return true; // Return true if none of the illegal conditions are met
    }


}