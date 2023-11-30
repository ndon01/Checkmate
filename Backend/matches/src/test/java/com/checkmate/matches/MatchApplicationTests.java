package com.checkmate.matches;

import com.checkmate.matches.model.util.Game.Game;
import com.checkmate.matches.model.util.Game.Move;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class MatchApplicationTests {

    // Test
    public static void main(String[] args) {
        Game myBoard = new Game("Alice", "Bob");

        Move myMove = new Move("h6h5");


        boolean moveWorked = myBoard.move(myMove);

        System.out.println(moveWorked);
    }

}
