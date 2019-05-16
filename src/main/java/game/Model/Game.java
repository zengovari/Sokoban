package game.Model;

import game.View.GameView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Loads the core of the program and provides getters and setters for it.
 */

public class Game {

    /**
     *  Important final values for the game.
     */
    private final int WIDTH = 800,
                            HEIGHT = 600,
                            BUTTON_WIDTH = 150,
                            BUTTON_HEIGHT = 50,
                            VBOX_SIZE = 35;

    /**
     * The main root used to represent the main menu. {@link GameView#display()}
     */

    private final VBox MAIN_ROOT = new VBox(VBOX_SIZE);

    /**
     * The main stage used in the game.
     */

    private final Stage MAIN_STAGE = new Stage();

    /**
     * The main scene used in the game.
     */

    private final Scene MAIN_SCENE = new Scene(MAIN_ROOT, WIDTH, HEIGHT);

    /**
     * Locations of the images used in the game.
     */

    private final InputStream PLAYER_IMAGE_LOC =  Game.class.getClassLoader().getResourceAsStream("player.png"),
            WALL_IMAGE_LOC = Game.class.getClassLoader().getResourceAsStream("wall.png"),
            GOAL_IMAGE_LOC = Game.class.getClassLoader().getResourceAsStream("goal.png"),
            BOX_IMAGE_LOC = Game.class.getClassLoader().getResourceAsStream("box.png");

    /**
     * The images used in the game.
     */

    private final Image PLAYER_IMAGE = new Image(PLAYER_IMAGE_LOC),
            WALL_IMAGE = new Image(WALL_IMAGE_LOC),
            GOAL_IMAGE = new Image(GOAL_IMAGE_LOC),
            BOX_IMAGE = new Image(BOX_IMAGE_LOC);

    /**
     * The player name used druing the game and to save data.
     */

    private String player_name;

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }


    public int getButtonWidth() {
        return BUTTON_WIDTH;
    }

    public int getButtonHeight() {
        return BUTTON_HEIGHT;
    }

    public int getVboxSize() {
        return VBOX_SIZE;
    }

    public VBox getMain_root() {
        return MAIN_ROOT;
    }

    public Stage getMain_stage() {
        return MAIN_STAGE;
    }

    public Scene getMain_scene() {
        return MAIN_SCENE;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    public Image getPLAYER_IMAGE() {
        return PLAYER_IMAGE;
    }

    public Image getWALL_IMAGE() {
        return WALL_IMAGE;
    }

    public Image getGOAL_IMAGE() {
        return GOAL_IMAGE;
    }

    public Image getBOX_IMAGE() {
        return BOX_IMAGE;
    }


}
