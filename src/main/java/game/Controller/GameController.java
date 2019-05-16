package game.Controller;

import game.Model.Game;
import game.View.GameView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import game.Model.FileHandler;

/**
 * A controller that provides getters and setters for the {@link Game} model's objects.
 */

public class GameController {

    private static Game game = new Game();
    private static boolean continue_game;

    /**
     * Launches the game by loading the current level, which was saved by {@link MazeController}
     */

    public static void game() {

        MazeController.draw(MazeController.getLevelCount());
    }


    public static int getButtonWidth() {
        return game.getButtonWidth();
    }

    public static int getButtonHeight() {
        return game.getButtonHeight();
    }

    public static int getVboxSize() {
        return game.getVboxSize();
    }

    public static VBox getMain_root() {
        return game.getMain_root();
    }

    public static Stage getMain_stage() {
        return game.getMain_stage();
    }

    public static Scene getMain_scene() {
        return game.getMain_scene();
    }

    public static int getHeight() {
        return game.getHeight();
    }

    public static int getWidth() {
        return game.getWidth();
    }

    public static Image getPlayer_Image() {
        return game.getPLAYER_IMAGE();
    }

    public static Image getWall_Image() {
        return game.getWALL_IMAGE();
    }

    public static Image getGoal_Image() {
        return game.getGOAL_IMAGE();
    }

    public static Image getBox_Image() {
        return game.getBOX_IMAGE();
    }

    public static String getPlayerName() {
        return game.getPlayer_name();
    }

    /**
     * Checks if the data.json exists or not, used to hide the 'Highscore' button in the main menu. {@link GameView#display()}
     * @return true if it exists, otherwise false.
     */

    public static boolean isHighscore_exists() {
        if(FileHandler.readScores() == null) {
            return false;
        }
        return true;
    }


    public static void setPlayerName(String playerName) {
        game.setPlayer_name(playerName);
    }

    public static boolean isContinue_game() {
        return continue_game;
    }

    public static void setContinue_game(boolean continue_) {
        continue_game = continue_;
    }


}
