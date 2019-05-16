package game.View;

import game.Controller.GameController;
import game.Controller.MenuController;
import game.menu.LoadMenu;
import game.menu.NewGameMenu;
import game.menu.ScoreMenu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides the main menu for the game, everything can be reached from here.
 */

@Slf4j
public class GameView extends Application {

    private static ScoreMenu scoreMenu = new ScoreMenu();
    private static NewGameMenu newGame = new NewGameMenu();
    private static LoadMenu loadMenu = new LoadMenu();


    /**
     * Displays the main menu of the game.
     */

    public static void display() {

        GameController.getMain_stage().setResizable(false);
        GameController.getMain_root().setAlignment(Pos.CENTER);
        Button continue_button = new Button("CONTINUE");
        continue_button.setMinSize(GameController.getButtonWidth(), GameController.getButtonHeight());
        continue_button.setDisable(true);

        if(GameController.isContinue_game()){
            continue_button.setDisable(false);
            continue_button.setOnMouseClicked(actionEvent -> {
                GameController.getMain_root().getChildren().clear();
                GameController.game();


            });
        }

        Button newgame_button = new Button("NEW GAME");
        newgame_button.setMinSize(GameController.getButtonWidth(), GameController.getButtonHeight());
        newgame_button.setOnMouseClicked(actionEvent -> {
            GameController.getMain_root().getChildren().clear();
            MenuController.display(newGame);
        });

        Button load_button = new Button("LOAD");
        load_button.setMinSize(GameController.getButtonWidth(), GameController.getButtonHeight());
        load_button.setDisable(true);
        if (GameController.isHighscore_exists()) {
            load_button.setDisable(false);
            load_button.setOnMouseClicked(actionEvent -> {
                GameController.getMain_root().getChildren().clear();
                MenuController.display(loadMenu);
            });
        }

        Button score_button = new Button("HIGHSCORE");
        score_button.setMinSize(GameController.getButtonWidth(), GameController.getButtonHeight());
        score_button.setDisable(true);
        if (GameController.isHighscore_exists()) {
            score_button.setDisable(false);
            score_button.setMinSize(GameController.getButtonWidth(), GameController.getButtonHeight());
            score_button.setOnMouseClicked(actionEvent -> {
                GameController.getMain_root().getChildren().clear();
                MenuController.display(scoreMenu);
            });
        }

        Button exit_button = new Button("EXIT");
        exit_button.setMinSize(GameController.getButtonWidth(), GameController.getButtonHeight());
        exit_button.setOnMouseClicked(actionEvent -> {
            System.exit(1);
        });


        GameController.getMain_root().getChildren().addAll(continue_button, newgame_button,load_button , score_button, exit_button);


        GameController.getMain_stage().setScene(GameController.getMain_scene());
        GameController.getMain_stage().show();

    }



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        display();
    }
}
