package game.Controller;

/**
 * An exception that is throwns if the player can't move in a certain direction. Example: {@link MazeController#moveLeft(Integer[], String)}
  *
 */

public class InvalidMoveException extends Exception {

    public InvalidMoveException(String message)
        {
            super(message);
        }

}
