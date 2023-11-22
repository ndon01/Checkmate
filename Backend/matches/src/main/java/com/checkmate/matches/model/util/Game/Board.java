package com.checkmate.matches.model.util.Game;
import com.checkmate.matches.model.util.Pieces.*;
import java.util.ArrayList;

public class Board
{
    protected static Square[][] myBoard = new Square[8][8];


    public ArrayList<Move> moves = new ArrayList<Move>();
    private String player1, player2;
    private String winnerName;
    protected boolean turn; // true for white, false for black
    private boolean Winner = false;

    public Piece getPiece(int row, int col) {
        return myBoard[row][col].getOccupant();
    }

    public Square[][] getBoard()
    {
        return myBoard;
    }


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

    public static void instantiator()
    {
        boolean lever = true;
        for(int i = 0; i < myBoard.length; i++)
        {
            for (int j = 0; j < myBoard[i].length; j++)
            {
                if(lever)
                {
                    myBoard[i][j] = new Square(true);
                    lever = false;
                }
                else
                {
                    myBoard[i][j] = new Square(false);
                    lever = true;
                }
            }
        }
    }

    public static Square[][] boardMaker(String myString)
    {

        int j = 0; //rowws
        int k = 0; //columns
        String testString;
        instantiator();

        for(int i = 0; i < myString.length(); i++)
        {


            if(myString.substring(i, i+1).equalsIgnoreCase("r"))
            {
                if(Character.isUpperCase(myString.charAt(i)))
                {
                    myBoard[j][k].setOccupant(new Rook(false));
                    k++;
                }
                else
                {
                    myBoard[j][k].setOccupant(new Rook(true));
                    k++;
                }

            }
            else if(myString.substring(i, i+1).equalsIgnoreCase("n"))
            {
                if(Character.isUpperCase(myString.charAt(i)))
                {
                    myBoard[j][k].setOccupant(new Knight(false));
                    k++;
                }
                else
                {
                    myBoard[j][k].setOccupant(new Knight(true));
                    k++;
                }

            }
            else if(myString.substring(i, i+1).equalsIgnoreCase("b"))
            {
                if(Character.isUpperCase(myString.charAt(i)))
                {
                    myBoard[j][k].setOccupant(new Bishop(false));
                    k++;
                }
                else
                {
                    myBoard[j][k].setOccupant(new Bishop(true));
                    k++;
                }
            }
            else if(myString.substring(i, i+1).equalsIgnoreCase("q"))
            {
                if(Character.isUpperCase(myString.charAt(i)))
                {
                    myBoard[j][k].setOccupant(new Queen(false));
                    k++;
                }
                else
                {
                    myBoard[j][k].setOccupant(new Queen(true));
                    k++;
                }
            }
            else if(myString.substring(i, i+1).equalsIgnoreCase("k"))
            {
                if(Character.isUpperCase(myString.charAt(i)))
                {
                    myBoard[j][k].setOccupant(new King(false));
                    k++;
                }
                else
                {
                    myBoard[j][k].setOccupant(new King(true));
                    k++;
                }
            }
            else if(myString.substring(i, i+1).equalsIgnoreCase("p"))
            {
                if(Character.isUpperCase(myString.charAt(i)))
                {
                    myBoard[j][k].setOccupant(new Pawn(false));
                    k++;
                }
                else
                {
                    myBoard[j][k].setOccupant(new Pawn(true));
                    k++;
                }
            }
            else if(myString.substring(i, i+1).equalsIgnoreCase("/"))
            {
                j++;
                k = 0;
            }
            else if(Character.isDigit(myString.charAt(i)))
            {
                int counter = Integer.parseInt(myString.substring(i,i+1));
                for(int l = 0; l < counter; l++)
                {
                    k++;
                }
            }

        }
        return myBoard;

    }

    public boolean move(Move m) {
        Square dst = myBoard[m.getRow1()][m.getCol1()];
        if(dst.getOccupant() == null) {
            return move(m, false);
        }
        else
        {
            return move(m, true);
        }
    }

    public boolean move(Move move, boolean isCapture) { // makes a move, returns true if successful, false otherwise.
        Square src = myBoard[move.getRow0()][move.getCol0()], dst = myBoard[move.getRow1()][move.getCol1()];
        if (isCapture && dst.getOccupant() == null) return false;
        if (!isCapture && dst.getOccupant() != null) return false;

        if (src.getOccupant() == null || //empty src square
                !myBoard[move.getRow0()][move.getCol0()].getOccupant().isLegal(move, this))//illegal move
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
                if(move.getMovedPieceIsWhite())
                {
                    winnerName = player1;
                }
                else
                {
                    winnerName = player2;
                }
                System.out.printf("%s has won the util.game\n", move.getMovedPiece().isWhite() ? getPlayer1() : getPlayer2());
            }
        }

        return true;
    }

    public void undoLastMove() {
        if (moves.size() == 0) return;
        Move move = moves.get(moves.size() - 1);

        Square src = myBoard[move.getRow0()][move.getCol0()], dst = myBoard[move.getRow1()][move.getCol1()];

        src.setOccupant(move.wasPawn() ? new Pawn(dst.getOccupant().isWhite()) : dst.getOccupant());

        if (move.didCapturePiece()) {
            dst.setOccupant(move.getCapturedPiece());
        } else {
            dst.setOccupant(null);
        }

        moves.remove(move);

    }


}