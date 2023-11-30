// Nicholas Donahue 2023
package com.checkmate.matches.model.util.Game;

import com.checkmate.matches.model.util.Pieces.*;
import com.checkmate.matches.model.util.PrintableTable.PrintableTable;

import java.io.PrintStream;
import java.util.ArrayList;

public class Game {
    private String player1, player2;
    private ArrayList<Move> moves;
    private Square[][] board;

    public Piece getPiece(int row, int col) {
        return board[row][col].getOccupant();
    }

    private boolean Winner = false;

    public boolean getWinnerStatus() {
        return Winner;
    }

    public boolean isWhiteTurn() {//specifies whose turn it is: white/player1 or black/player2
        return moves.size() % 2 == 0;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public Game(String currentBoard, boolean isWhiteMove) {
        this.player1 = "";
        this.player2 = "";
        moves = new ArrayList<>();
        board = new Square[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean white = (row + col) % 2 == 0;
                if (row == 1)
                    board[row][col] = new Square(white, new Pawn(false));
                else if (row == 6)
                    board[row][col] = new Square(white, new Pawn(true));
                else if (row == 7 || row == 0) {
                    if (col == 0 || col == 7)
                        board[row][col] = new Square(white, new Rook(row == 7));
                    else if (col == 1 || col == 6)
                        board[row][col] = new Square(white, new Knight(row == 7));
                    else if (col == 2 || col == 5)
                        board[row][col] = new Square(white, new Bishop(row == 7));
                    else if (col == 3)
                        board[row][col] = new Square(white, new Queen(row == 7));
                    else
                        board[row][col] = new Square(white, new King(row == 7));
                } else
                    board[row][col] = new Square(white, null);
            }
        }
    }

    public Game(String player1, String player2) {//sets up the pieces on the board, initializes player's names.
        this.player1 = player1;
        this.player2 = player2;
        moves = new ArrayList<>();
        board = new Square[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean white = (row + col) % 2 == 0;
                if (row == 1)
                    board[row][col] = new Square(white, new Pawn(false));
                else if (row == 6)
                    board[row][col] = new Square(white, new Pawn(true));
                else if (row == 7 || row == 0) {
                    if (col == 0 || col == 7)
                        board[row][col] = new Square(white, new Rook(row == 7));
                    else if (col == 1 || col == 6)
                        board[row][col] = new Square(white, new Knight(row == 7));
                    else if (col == 2 || col == 5)
                        board[row][col] = new Square(white, new Bishop(row == 7));
                    else if (col == 3)
                        board[row][col] = new Square(white, new Queen(row == 7));
                    else
                        board[row][col] = new Square(white, new King(row == 7));
                } else
                    board[row][col] = new Square(white, null);
            }
        }
    }

    public boolean move(Move m) {
        return move(m, false);
    }

    public boolean move(Move move, boolean isCapture) { // makes a move, returns true if successful, false otherwise.
        Square src = board[move.getRow0()][move.getCol0()], dst = board[move.getRow1()][move.getCol1()];
        if (isCapture && dst.getOccupant() == null) return false;
        if (!isCapture && dst.getOccupant() != null) return false;

        if (src.getOccupant() == null || //empty src square
                !board[move.getRow0()][move.getCol0()].getOccupant().isLegal(move,this))//illegal move
            return false;

        boolean isWhite = src.getOccupant().isWhite();

        // change the pawn to a queen if the pawn reaches the opposite side of the board
        if ((src.getOccupant().getPieceType().equals("Pawn")) && ((isWhite && move.getRow1() == 0) || (!isWhite && move.getRow1() == 7))) {
            move.setWasPawn();
            src.setOccupant(new Queen(isWhite));
        }

        // setting data for the undo method
        move.setMovedPiece(src.getOccupant());


        if (dst.getOccupant() != null) {
            move.setCapturedPiece(dst.getOccupant());
        }

        moves.add(move);


        dst.setOccupant(src.getOccupant());
        src.setOccupant(null);


        if (move.getCapturedPiece() != null) {
            if (move.getCapturedPiece().getPieceType().equals("King")) {
                Winner = true;
                System.out.printf("%s has won the util.game\n", move.getMovedPiece().isWhite() ? getPlayer1() : getPlayer2());
            }
        }

        return true;
    }

    public void undoLastMove() {
        if (moves.size() == 0) return;
        Move move = moves.get(moves.size() - 1);

        Square src = board[move.getRow0()][move.getCol0()], dst = board[move.getRow1()][move.getCol1()];

        src.setOccupant(move.wasPawn() ? new Pawn(dst.getOccupant().isWhite()) : dst.getOccupant());

        if (move.didCapturePiece()) {
            dst.setOccupant(move.getCapturedPiece());
        } else {
            dst.setOccupant(null);
        }

        moves.remove(move);

    }

    public void reverse(int movesN) { // method to undo multiple steps *not required*
        movesN = Math.min(movesN, moves.size());

        for (int i = 0; i < movesN; i++) {
            undoLastMove();
        }

    }

    @Override
    public String toString() {
        return moves.toString();
    }

    /*
    public void showBoard(PrintStream stream) {
        stream.println(player2 + "\n______________________________________\n\ta\tb\tc\td\te\tf\tg\th");
        for (int row = 0; row < 8; row++) {
            for (int col = -1; col < 8; col++) {
                if (col < 0)
                    stream.print(8 - row);
                else if (getPiece(row, col) != null)
                    stream.print(getPiece(row, col));
                else if (getPiece(row, col) == null)
                    stream.print("  ");
                stream.print(col == -1 ? "  " : col != 7 ? "\t" : row != 7 ? "\n\n\n" : "\n");
            }
        }
        stream.println("______________________________________\n" + player1);
    }
    */

    private String abcz = "\ta\tb\tc\td\te\tf\tg\th";
    private String getTextInCenter(String Text, String Comparer) {
        String newString = "";
        int length = Comparer.length() - Text.length();
        for (int i = 0; i < length/2; i++) {
            newString += " ";
        }
        newString += Text;
        return newString;
    }

    // Show's the util.game board
    public void showBoard(PrintStream stream) {
        showBoard(stream, true);
    }
    public void showBoard(PrintStream stream, boolean printPlayer) {
        if (getWinnerStatus()) {
            printPlayer = false;
        }
        String dshes = "______________________________________";
        stream.println(dshes + "\n"+ getTextInCenter(player2, dshes) + "\n"+ dshes +"\n" + abcz); // prints the players name in the middle of the dashes for formatting
        for (int row = 0; row < 8; row++) {
            for (int col = -1; col < 8; col++) {
                if (col < 0)
                    stream.print(8 - row);
                else if (getPiece(row, col) != null)
                    stream.print(getPiece(row, col));
                else if (getPiece(row, col) == null)
                    stream.print("  ");
                stream.print(col == -1 ? "  " : col != 7 ? "\t" : row != 7 ? "\t" + (8 - row) + "\n\n" : "\t" + (8 - row) + "\n");
            }
        }
        stream.println(abcz);
        stream.println(dshes + "\n" + getTextInCenter(player1, dshes) + "\n" + dshes);

        if (printPlayer) {
            System.out.print("\n"+ (isWhiteTurn() ? getPlayer1() : getPlayer2()) + "'s Turn:\n");
        }
    }

    public void printStatus() {
        showBoard(System.out, false);

        PrintableTable ptable = new PrintableTable();
        ptable.createHeaders("#", "Player", "Piece Type", "From", "To", "Captured");

        for (int i = 0; i < moves.size(); i ++) {
            Move thisMove = moves.get(i);
            ptable.createRow(
                    "" + (i + 1),
                    thisMove.getMovedPiece().isWhite() ? getPlayer1() : getPlayer2(),
                    thisMove.getMovedPiece().getPieceType(),
                    thisMove.toString().substring(0, 2),
                    thisMove.toString().substring(2),
                    thisMove.getCapturedPiece() == null ? "NONE" : thisMove.getCapturedPiece().getPieceType()
            );
        }

        ptable.print();
        System.out.print("\n\n"+ (isWhiteTurn() ? getPlayer1() : getPlayer2()) + "'s Turn:\n");
    }

}
