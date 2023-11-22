package com.checkmate.matches.model.util.Pieces;
import com.checkmate.matches.model.util.Game.*;
public class Square
{
    private boolean lightColor; // Boolean flag to indicate if the square is light-colored
    private Piece occupant; // Variable to hold the Piece occupying this square, if any

    // Constructor for the Square class
    public Square(boolean lightColor, Piece occupant) {
        this.lightColor = lightColor; // Sets the color of the square
        this.occupant = occupant; // Sets the occupant of the square
    }

    public Square(boolean lightColor)
    {
        this.lightColor = lightColor;
    }

    // Getter method to check if the square is light-colored
    public boolean isLightColor() {
        return lightColor;
    }

    // Getter method to get the Piece that occupies the square
    public Piece getOccupant() {
        return occupant;
    }

    // Setter method to set or change the occupant of the square
    public void setOccupant(Piece occupant) {
        this.occupant = occupant;
    }

    public String toString()
    {
        if(occupant == null) {
            return  null;
        }
        else
        {
            return " " + occupant.toString() + " ";
        }
    }
}
