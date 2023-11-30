package com.checkmate.matches;

import com.checkmate.matches.model.util.Game.Game;
import com.checkmate.matches.model.util.Game.Move;

class MatchApplicationTests {

    // Test
    public static void main(String[] args) {
        Game myGame = new Game("rnbqkbnr/7p/8/8/8/8/PPPPPPPP/RNBQKBNR", false);
        Move myMove = new Move("h7h6");

        myGame.printStatus();

        boolean moveWorked = myGame.move(myMove);

        System.out.println(moveWorked);

        myGame.printStatus();

        System.out.println(myGame.getBoardNotation());
    }

}
