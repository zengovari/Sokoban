package game.menu;

import game.Controller.GameController;
import game.Controller.MazeController;
import game.Model.Maze;
import game.View.GameView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import game.View.MazeView;


@Slf4j
public class GameMenu implements Menu{

    private static Button reset_button, quit_button, yes_quit_button, no_quit_button, continue_button, back_button;
    private static Scene gamemenu_scene, quit_scene;
    private static VBox root, quit_root;
    private static Stage gamemenu_stage, quit_stage;
    private static Label quit_label;

    /**
     * Displays the menu.
     *
     * CONITNUE - Continues the game
     * RESET - Resets the map
     * QUIT - Quits the game
     */

    public void display() {

        gamemenu_stage = new Stage();
        gamemenu_stage.initModality(Modality.APPLICATION_MODAL);

        root = new VBox(GameController.getVboxSize());
        root.setAlignment(Pos.CENTER);

        continue_button = new Button("CONTINUE");
        continue_button.setMinSize(GameController.getButtonWidth() / 2, GameController.getButtonHeight() / 2);

        continue_button.setOnMouseClicked(event -> {
            gamemenu_stage.close();
        });


        reset_button = new Button("RESET");
        reset_button.setMinSize(GameController.getButtonWidth() / 2, GameController.getButtonHeight() / 2);

        reset_button.setOnMouseClicked(event -> {

            gamemenu_stage.close();
            MazeView.setMazeController(new MazeController(new Maze(MazeController.getMazes()[MazeController.getLevelCount() - 1])));
            MazeController.setMoves(0);
            MazeController.draw(MazeController.getLevelCount());

            log.info("Map reseted.");
        });


        quit_button = new Button("QUIT");
        quit_button.setMinSize(GameController.getButtonWidth() / 2, GameController.getButtonHeight() / 2);

        quit_button.setOnMouseClicked(event -> {
            log.info("Quit button pressed.");
            quit_stage = new Stage();
            quit_stage.initModality(Modality.APPLICATION_MODAL);

            quit_root = new VBox(GameController.getVboxSize());
            quit_root.setAlignment(Pos.BOTTOM_CENTER);

            quit_scene = new Scene(quit_root, 300, 200);

            quit_label = new Label("Are you sure you want to quit?");

            yes_quit_button = new Button("Yes");
            no_quit_button = new Button("No");
            no_quit_button.setOnMouseClicked(event2 -> {
                log.info("Answer: NO");
                quit_stage.close();
            });

            yes_quit_button.setOnMouseClicked(event2 -> {
                log.info("Closing game");
                GameController.getMain_stage().close();
                gamemenu_stage.close();
                quit_stage.close();
            });

            quit_root.getChildren().addAll(quit_label, yes_quit_button, no_quit_button);

            quit_stage.setScene(quit_scene);
            quit_stage.show();
        });

        back_button = new Button("BACK");
        back_button.setMinSize(GameController.getButtonWidth() / 2, GameController.getButtonHeight() / 2);

        back_button.setOnMouseClicked(event -> {
            GameController.setContinue_game(true);
            gamemenu_stage.close();
            GameView.display();

        });

        root.getChildren().addAll(continue_button, reset_button, back_button, quit_button);

        gamemenu_scene = new Scene(root, 200, 400);
        gamemenu_stage.setScene(gamemenu_scene);
        gamemenu_stage.show();
    }
}