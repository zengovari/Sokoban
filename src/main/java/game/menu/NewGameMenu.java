package game.menu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import game.Controller.GameController;
import game.Controller.MazeController;
import game.Model.FileHandler;
import game.Model.ScoreBoard;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This menu starts a new game with the name given by the user.
 *
 */

@Slf4j
public class NewGameMenu implements Menu{

    /**
     * A menu that asks for a player name, then creates a new data.json file using
     * {@link FileHandler#getDataPath()} and {@link FileHandler#writeToScores(List)}
     */


    public void display() {

        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);

        Button submit = new Button("Submit");
        submit.setAlignment(Pos.BOTTOM_CENTER);
        submit.setOnMouseClicked(actionEvent -> {
            if(!textField.getText().equals("")) {
                log.info("Player name is {}" , textField.getText());
                GameController.setPlayerName(textField.getText());

                ScoreBoard[] scoreBoardsArray = FileHandler.readScores();


                if (scoreBoardsArray != null ){
                    if(ScoreBoard.scoreBoardsContainsName(scoreBoardsArray ,GameController.getPlayerName())) {
                        log.error("Name already exists.");
                    } else {
                        List<ScoreBoard> scoreBoards = new ArrayList<>(Arrays.asList(scoreBoardsArray));


                        scoreBoards.add(new ScoreBoard(GameController.getPlayerName(), 0, 0));
                        FileHandler.writeToScores(scoreBoards);

                        MazeController.setLevelCount(1);
                        MazeController.setMapLoaded(false);
                        GameController.game();
                    }
                }
                else {

                    ScoreBoard scoreBoard = new ScoreBoard(GameController.getPlayerName(), 0, 0);
                    ArrayList<ScoreBoard> scoreBoards = new ArrayList<>(Arrays.asList(scoreBoard));
                    FileHandler.writeToScores(scoreBoards);

                    GameController.game();
                }
            } else {
                log.error("Name is null");
            }
        });

        HBox root = new HBox(textField);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(submit);

        Scene options_scene = new Scene(root, GameController.getWidth(), GameController.getHeight());
        GameController.getMain_stage().setScene(options_scene);
        GameController.getMain_stage().show();


    }
}