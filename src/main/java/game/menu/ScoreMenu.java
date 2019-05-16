package game.menu;

import game.Controller.GameController;
import game.Controller.MazeController;
import game.Model.ScoreBoard;
import game.View.GameView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import game.Model.FileHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This menu represents the scores reached by players.
 */

@Slf4j
public class ScoreMenu implements Menu {

    /**
     * Displays a scoreboard according to the moves that were necessary to complete the level.
     * @param level the scoreboard calculation happens according to this level
     */

    public static void displayLevel(int level) {

        log.info("Displaying level {}", level);

        ArrayList<ScoreBoard> scoreBoards = new ArrayList<>(Arrays.asList(FileHandler.readScores()));
        if(scoreBoards == null) {
            log.info("Scoreboards is empty");
        }
        List<ScoreBoard> scoreBoardsOrdered = scoreBoards.stream().sorted(Comparator.comparingInt(ScoreBoard::getLevel).reversed()).collect(Collectors.toList());


        VBox root = new VBox(GameController.getVboxSize());
        root.setAlignment(Pos.TOP_CENTER);

        Stage score_stage = new Stage();
        score_stage.initModality(Modality.APPLICATION_MODAL);
        score_stage.setTitle("Highscore");


        scoreBoardsOrdered = scoreBoardsOrdered.stream().sorted(Comparator.comparingInt(ScoreBoard::getSteps)).collect(Collectors.toList());

        ArrayList<Label> labels = new ArrayList<>();
        List<String> names = new ArrayList<>();


        Label label;
        if (scoreBoardsOrdered.size() < 10) {
            for(int i = 0; i < scoreBoardsOrdered.size(); i++) {
                if (!names.contains(scoreBoardsOrdered.get(i).getPlayerName())) {
                    if (scoreBoardsOrdered.get(i).getLevel() == level) {
                        label = new Label("Name: " + scoreBoardsOrdered.get(i).getPlayerName() + "\t\tLevel: " + scoreBoardsOrdered.get(i).getLevel() + "\t\tSteps: " + scoreBoardsOrdered.get(i).getSteps());
                        if (scoreBoardsOrdered.get(i).getPlayerName().equals(GameController.getPlayerName())) {
                            label.setFont(new Font("Cambria", 20));
                        }
                        labels.add(label);
                        names.add(scoreBoardsOrdered.get(i).getPlayerName());
                    }
                }
            }
        } else  {
            int i = 0;
            while (names.size() != 10 && i < scoreBoardsOrdered.size()) {
                if(scoreBoardsOrdered.get(i).getLevel() == level) {
                    label = new Label("Name: " + scoreBoardsOrdered.get(i).getPlayerName() + "\t\tLevel: " + scoreBoardsOrdered.get(i).getLevel() + "\t\tSteps: " + scoreBoardsOrdered.get(i).getSteps());
                    if (scoreBoardsOrdered.get(i).getPlayerName().equals(GameController.getPlayerName())) {
                        label.setFont(new Font("Cambria", 20));
                    }
                    labels.add(label);
                    names.add(scoreBoardsOrdered.get(i).getPlayerName());
                }
                i++;
            }
        }

        Label level_label = new Label("LEVEL " + MazeController.getLevelCount()  + " HIGHSCORE" );
        level_label.setFont(new Font("Cambira", 30));

        root.getChildren().add(level_label);

        for(Label l: labels) {
            root.getChildren().add(l);
        }



        Button continue_button = new Button("CONTINUE");
        continue_button.setMinSize(GameController.getButtonWidth() / 2, GameController.getButtonHeight() / 2);

        continue_button.setOnMouseClicked(event -> {
            score_stage.close();
        });


        root.getChildren().add(continue_button);


        Scene score_scene = new Scene(root, GameController.getWidth(), GameController.getHeight() + 100);
        score_stage.setScene(score_scene);
        score_stage.show();

    }

    /**
     * Displays a scoreboard according to the amount of level the players completed.
     */

    public void display() {

        ArrayList<ScoreBoard> scoreBoards = new ArrayList<>(Arrays.asList(FileHandler.readScores()));
        List<ScoreBoard> scoreBoardsOrdered = scoreBoards.stream().sorted(Comparator.comparingInt(ScoreBoard::getLevel).reversed()).collect(Collectors.toList());

        VBox root = new VBox(GameController.getVboxSize());
        root.setAlignment(Pos.TOP_CENTER);

        List<String> names = new ArrayList<>();
        ArrayList<Label> labels = new ArrayList<>();


        int i = 0;
        if (scoreBoardsOrdered.size() < 10) {
            while (i < scoreBoardsOrdered.size()) {
                    if(!names.contains(scoreBoardsOrdered.get(i).getPlayerName())) {
                        if( scoreBoardsOrdered.get(i).getLevel() != 0) {
                            labels.add(new Label("Name: " + scoreBoardsOrdered.get(i).getPlayerName() + "\t\tLevel: " + scoreBoardsOrdered.get(i).getLevel()));
                            names.add(scoreBoardsOrdered.get(i).getPlayerName());
                        }
                    }
                    i++;
            }
        } else  {

            while (names.size() != 10 && i < scoreBoardsOrdered.size()) {
                if (!names.contains(scoreBoardsOrdered.get(i).getPlayerName())) {
                    if( scoreBoardsOrdered.get(i).getLevel() != 0) {
                        labels.add(new Label("Name: " + scoreBoardsOrdered.get(i).getPlayerName() + "\t\tLevel: " + scoreBoardsOrdered.get(i).getLevel()));
                        names.add(scoreBoardsOrdered.get(i).getPlayerName());
                    }
                }
                i++;
            }
        }

        for(Label label: labels) {
            root.getChildren().add(label);
        }


        Button back_button = new Button("BACK");
        back_button.setMinSize(GameController.getButtonWidth() / 2, GameController.getButtonHeight() / 2);

        back_button.setOnMouseClicked(event -> {
            GameView.display();

        });
        root.getChildren().add(back_button);

        Scene options_scene = new Scene(root, GameController.getWidth(), GameController.getHeight());


        GameController.getMain_stage().setScene(options_scene);
        GameController.getMain_stage().show();



    }
}
