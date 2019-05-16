package game.menu;

import game.Controller.GameController;
import game.Controller.MazeController;
import game.Model.FileHandler;
import game.Model.ScoreBoard;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This menu is used to load saved games by the players.
 */

@Slf4j
public class LoadMenu implements Menu {
    private Label level;


    /**
     * Displays the loading menu.
     */

    @Override
    public void display() {

        ArrayList<ScoreBoard> scoreBoards = new ArrayList<>(Arrays.asList(FileHandler.readScores()));
        ArrayList<Label> levels = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        VBox root = new VBox(GameController.getVboxSize());
        root.setAlignment(Pos.CENTER);

        for(ScoreBoard scoreBoard: scoreBoards) {
            level = new Label(scoreBoard.getPlayerName() + "\t\t" + String.valueOf(FileHandler.lastCompletedLevelByPlayer(scoreBoard.getPlayerName())+1));
            if (!names.contains(scoreBoard.getPlayerName())) {
                level.setOnMouseClicked(mouseEvent ->  {
                    GameController.setPlayerName(scoreBoard.getPlayerName());
                    MazeController.setLevelCount(FileHandler.lastCompletedLevelByPlayer(scoreBoard.getPlayerName())+1);
                    log.info("Player name: {}", scoreBoard.getPlayerName());
                    log.info("Level to load: {}",  String.valueOf(FileHandler.lastCompletedLevelByPlayer(scoreBoard.getPlayerName())+1));
                    log.info("Clicked");

                    MazeController.setMapLoaded(false);
                    MazeController.setMoves(0);
                    MazeController.draw(MazeController.getLevelCount());

                });

                names.add(scoreBoard.getPlayerName());
                levels.add(level);
            }
        }

        for(Label l: levels) {
            root.getChildren().add(l);
        }

        Scene load_scene = new Scene(root, GameController.getWidth(), GameController.getHeight());
        GameController.getMain_stage().setScene(load_scene);
        GameController.getMain_stage().show();




    }
}
