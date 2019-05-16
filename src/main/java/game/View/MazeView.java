package game.View;

import game.Controller.*;
import game.Model.Maze;
import game.menu.GameMenu;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 *  The view of the mazes, handles the visualisation of the levels.
 */

@Data
@Slf4j
public class MazeView {

    private static final int OBJECT_SIZE = 35;


    private static final Canvas canvas = new Canvas(800, 600);
    private static final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private static Label move_label;
    private static Label level_label;

    private static GameMenu gameMenu = new GameMenu();
    private static MazeController mazeController;

    private boolean mapLoaded = false;


    public static void setMazeController(MazeController mazeController) {
        MazeView.mazeController = mazeController;
    }


    /**
     * Read through the maze, then draw it on the screen according to the current symbol
     *
     * Symbol meanings:
     * # - wall
     * $ - rock
     * . - finish
     * kukac - player
     * and - goal with player on it
     * X - goal with box on it
     *
     * @param level the level we want to draw
     */

    public void draw(int level)  {

        if (level < 1 || level > 60) {
            try {
                throw new InvalidLevelException("");
            } catch (InvalidLevelException e) {
                log.error("Invalid Level Exception");
                log.error("Closing program..");
                System.exit(1);
            }
        }


        if (!mapLoaded) {
            for (int i = 0; i < GameController.getWidth(); i++) {
                for (int j = 0; j < GameController.getHeight(); j++) {
                    graphicsContext.setFill(Color.WHITE);
                    graphicsContext.fillRect(i, j, OBJECT_SIZE, OBJECT_SIZE);
                }
            }

            mazeController = new MazeController(new Maze(MazeController.getMazes()[level - 1]));
            mapLoaded = true;
        }





        int y_position = GameController.getHeight()/ 2 - (mazeController.getHeight() * OBJECT_SIZE) / 2 ;
        int x_position;

        for(String maze_row: mazeController.getMaze()) {
            x_position = GameController.getWidth() / 2 - (mazeController.getWidth() * OBJECT_SIZE) / 2;
            y_position += OBJECT_SIZE;


            for (char maze_piece : maze_row.toCharArray()) {
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillRect(x_position, y_position, OBJECT_SIZE, OBJECT_SIZE);

                if (maze_piece == '#') {
                    graphicsContext.drawImage(GameController.getWall_Image(), x_position, y_position, OBJECT_SIZE, OBJECT_SIZE);

                } else if (maze_piece == '$') {
                    graphicsContext.drawImage(GameController.getBox_Image(), x_position, y_position, OBJECT_SIZE, OBJECT_SIZE);

                } else if (maze_piece == '.') {
                    graphicsContext.drawImage(GameController.getGoal_Image(), x_position, y_position, OBJECT_SIZE, OBJECT_SIZE);

                } else if (maze_piece == '@') {
                    graphicsContext.drawImage(GameController.getPlayer_Image(), x_position, y_position, OBJECT_SIZE, OBJECT_SIZE);

                } else if (maze_piece == '&') {
                    graphicsContext.drawImage(GameController.getGoal_Image(), x_position, y_position, OBJECT_SIZE, OBJECT_SIZE);
                    graphicsContext.drawImage(GameController.getPlayer_Image(), x_position, y_position, OBJECT_SIZE, OBJECT_SIZE);

                } else if (maze_piece == 'X') {
                    graphicsContext.drawImage(GameController.getGoal_Image(), x_position, y_position, OBJECT_SIZE, OBJECT_SIZE);
                    graphicsContext.drawImage(GameController.getBox_Image(), x_position, y_position, OBJECT_SIZE, OBJECT_SIZE);
                }

                x_position += OBJECT_SIZE;
            }
        }

        Pane pane = new Pane(canvas);


        move_label = new Label("Steps: " + mazeController.getMoves());
        move_label.layoutXProperty().bind(pane.widthProperty().subtract(780));
        move_label.setMinSize(40, 40);


        level_label = new Label("Level: " + level);
        level_label.layoutXProperty().bind(pane.widthProperty().subtract(100));
        level_label.setMinSize(40, 40);


        pane.getChildren().addAll(move_label, level_label);
        Scene scene = new Scene(pane);

        mazeController.handleMovement(scene);



        GameController.getMain_stage().setScene(scene);


    }


    /**
     * Checks if the map has been completed, if not, draws the changed map.
     */

    public void mazeChanged() {


        if (mazeController.checkVictory()) {


            for (int i = 0; i < GameController.getWidth(); i++) {
                for (int j = 0; j < GameController.getHeight(); j++) {
                    graphicsContext.setFill(Color.WHITE);
                    graphicsContext.fillRect(i, j, OBJECT_SIZE, OBJECT_SIZE);
                }
            }

            mapLoaded = false;
            mazeController.setMazeChanged(false);
            draw(MazeController.getLevelCount());

        }

        if(mazeController.isMazeChanged()) {
            mazeController.setMazeChanged(false);
            draw(MazeController.getLevelCount());
        }


    }

}
