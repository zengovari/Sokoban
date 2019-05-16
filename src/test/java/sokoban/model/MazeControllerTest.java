package sokoban.model;

import game.Controller.InvalidMoveException;
import game.Controller.MazeController;
import game.Model.Direction;
import game.Model.Maze;
import game.Model.FileHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeControllerTest {

    @Test
    public void testOf() {

        Maze[] mazes = FileHandler.readMazes("maps.json");

        MazeController mazeController = new MazeController(mazes[0]);

        mazeController.setMaze(new String[]{"                   "});

        assertNull(mazeController.getPlayerLocation());
        assertEquals(0, mazeController.getGoalCount());
        assertFalse(mazeController.checkVictory());
        assertThrows(NullPointerException.class, () -> mazeController.move(Direction.UP));
        assertThrows(NullPointerException.class, () -> mazeController.move(Direction.RIGHT));
        assertThrows(NullPointerException.class, () -> mazeController.move(Direction.DOWN));
        assertThrows(NullPointerException.class, () -> mazeController.move(Direction.LEFT));


        mazeController.setMaze(new String[]{"                   ",
                                            "      #            ",
                                            "      @            ",
                                            "  .                "});

        mazeController.setHeight(4);
        mazeController.setWidth(19);

        assertArrayEquals(new Integer[]{2, 6}, mazeController.getPlayerLocation());
        assertEquals(1, mazeController.getGoalCount());
        assertFalse(mazeController.checkVictory());
        assertThrows(InvalidMoveException.class, () -> mazeController.move(Direction.UP));


        mazeController.setMaze(new String[]{"                   ",
                                            "      #            ",
                                            "      @            ",
                                            "      $            ",
                                            "  .                ",
                                            "                   ",
                                            "                   ",});

        assertEquals(1, mazeController.getGoalCount());
        assertDoesNotThrow(() -> mazeController.moveDown(mazeController.getPlayerLocation(), "&"));
        assertDoesNotThrow(() -> mazeController.moveRight(mazeController.getPlayerLocation(), "@"));
        assertDoesNotThrow(() -> mazeController.moveDown(mazeController.getPlayerLocation(), "@"));
        assertDoesNotThrow(() -> mazeController.moveLeft(mazeController.getPlayerLocation(), "#"));
        assertDoesNotThrow(() -> mazeController.moveLeft(mazeController.getPlayerLocation(), "@"));
        assertDoesNotThrow(() -> mazeController.moveLeft(mazeController.getPlayerLocation(), "@"));
        assertDoesNotThrow(() -> mazeController.moveLeft(mazeController.getPlayerLocation(), "@"));
        assertEquals(1, mazeController.getGoalCount());

        mazeController.setMaze(new String[]{"                   ",
                                            "                   ",
                                            "     ###           ",
                                            "      $            ",
                                            "  .    XX          ",
                                            "       @$          ",
                                            "        .          ",});

        assertDoesNotThrow(() -> mazeController.moveUp(mazeController.getPlayerLocation(), "@"));
        assertThrows(InvalidMoveException.class, () -> mazeController.moveUp(mazeController.getPlayerLocation(), "@"));
        assertDoesNotThrow(() -> mazeController.moveRight(mazeController.getPlayerLocation(), "&"));
        assertDoesNotThrow(() -> mazeController.moveDown(mazeController.getPlayerLocation(), "@"));



    }

}
