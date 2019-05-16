package game.Controller;

import game.Model.Direction;
import game.Model.Maze;
import game.Model.ScoreBoard;
import game.View.MazeView;
import game.menu.GameMenu;
import game.menu.ScoreMenu;
import javafx.scene.Scene;
import lombok.extern.slf4j.Slf4j;
import game.Model.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A controller for the mazes, handles user input and game logic.
 *
 * Symbol meanings:
 * # - wall
 * $ - rock
 * . - goal
 * &#064; - player
 * &amp; - goal with player on it
 * X - goal with box on it
 *
 */

@Slf4j
public class MazeController {
    private Maze maze;
    private static MazeView mazeView = new MazeView();
    private int goalCount;
    private static int levelCount = 1;
    private static int moves;
    private GameMenu gameMenu = new GameMenu();

    private boolean mazeChanged = false;


    private boolean mapLoaded = false;

    public void setHeight(int height) {
        maze.setHeight(height);
    }

    public void setWidth(int width) {
        maze.setWidth(width);
    }

    public MazeController (Maze maze) {
        this.maze = maze;
    }

    public String[] getMaze() {
        return maze.getMaze();
    }

    public void setMaze(String[] level) { maze.setMaze(level);}

    public int getHeight() {
        return maze.getHeight();
    }

    public int getWidth() {
        return maze.getWidth();
    }

    public static Maze[] getMazes() {
        return Maze.getMazes();
    }

    public String getLevel() {
        return maze.getLevel();
    }

    public static int getMoves() {
        return moves;
    }

    public static void setMoves(int moves_) {
        moves = moves_;
    }

    public boolean isMazeChanged() {
        return mazeChanged;
    }

    public void setMazeChanged(boolean mazeChanged) {
        this.mazeChanged = mazeChanged;
    }


    public static void draw(int level) {
       mazeView.draw(level);
    }

    public static int getLevelCount() {
        return levelCount;
    }

    public static void setLevelCount(int levelCount_) {
        levelCount = levelCount_;
    }

    public static boolean isMapLoaded() {
        return mazeView.isMapLoaded();
    }

    public static void setMapLoaded(boolean mapLoaded) {
        mazeView.setMapLoaded(mapLoaded);
    }


    /**
     * Moves the player from the gives location upwards according to the objects surrounding the player.
     *
     * @param location the player's position
     * @param playerSymbol the player symbol, which can be '&#064;' (player is standing on a 'blank' object) or '&amp;' (player is standing on a 'finish' object) depending on the player's position.
     * @return the maze after the move, null if the maze hasn't changed.
     * @throws InvalidMoveException if the player can't move in that direction
     */

    public String[] moveUp(Integer[] location, String playerSymbol) throws InvalidMoveException {

        if(getMaze()[location[0] - 1].toCharArray()[location[1]] == '#' ||
                (getMaze()[location[0] - 1].toCharArray()[location[1]] == '$' && getMaze()[location[0] - 2].toCharArray()[location[1]] == '#' ) ||
                (getMaze()[location[0] - 1].toCharArray()[location[1]] == 'X' && getMaze()[location[0] - 2].toCharArray()[location[1]] == '#' ) ||
                (getMaze()[location[0] - 1].toCharArray()[location[1]] == '$' && getMaze()[location[0] - 2].toCharArray()[location[1]] == '$' ) ||
                (getMaze()[location[0] - 1].toCharArray()[location[1]] == 'X' && getMaze()[location[0] - 2].toCharArray()[location[1]] == 'X' ) ||
                (getMaze()[location[0] - 1].toCharArray()[location[1]] == 'X' && getMaze()[location[0] - 2].toCharArray()[location[1]] == '$') ||
                (getMaze()[location[0] - 1].toCharArray()[location[1]] == '$' && getMaze()[location[0] - 2].toCharArray()[location[1]] == 'X')) {

            setMoves(getMoves() - 1);

            throw new InvalidMoveException("");

        }

        if(playerSymbol.equals("&")) {
            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1]) + '.' + getMaze()[location[0]].substring(location[1] + 1);
        } else {
            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1]) + ' ' + getMaze()[location[0]].substring(location[1] + 1);
        }

        if (getMaze()[location[0] - 1].toCharArray()[location[1]] == ' ') {

            getMaze()[location[0] - 1] = getMaze()[location[0] - 1].substring(0, location[1]) + '@' + getMaze()[location[0] - 1].substring(location[1] + 1);

            return getMaze();
        }

        if (getMaze()[location[0] - 1].toCharArray()[location[1]]  == '.') {

            getMaze()[location[0] - 1] = getMaze()[location[0] - 1].substring(0, location[1]) + '&' + getMaze()[location[0] - 1].substring(location[1] + 1);
        }

        if (getMaze()[location[0] - 1].toCharArray()[location[1]] == '$') {
            log.info("Pushing box upwards.");

            if(getMaze()[location[0] - 2].toCharArray()[location[1]] == ' ') {
                getMaze()[location[0] - 1] = getMaze()[location[0] - 1].substring(0, location[1]) + '@' + getMaze()[location[0] - 1].substring(location[1] + 1);
                getMaze()[location[0] - 2] = getMaze()[location[0] - 2].substring(0, location[1]) + '$' + getMaze()[location[0] - 2].substring(location[1] + 1);
                return getMaze();
            }

            if(getMaze()[location[0] - 2].toCharArray()[location[1]] == '.') {
                getMaze()[location[0] - 1] = getMaze()[location[0] - 1].substring(0, location[1]) + '@' + getMaze()[location[0] - 1].substring(location[1] + 1);
                getMaze()[location[0] - 2] = getMaze()[location[0] - 2].substring(0, location[1]) + 'X' + getMaze()[location[0] - 2].substring(location[1] + 1);
                return getMaze();
            }
        }

        if (getMaze()[location[0] - 1].toCharArray()[location[1]] == 'X') {
            log.info("Pushing box upwards");

            if(getMaze()[location[0] - 2].toCharArray()[location[1]] == ' ') {
                getMaze()[location[0] - 1] = getMaze()[location[0] - 1].substring(0, location[1]) + '&' + getMaze()[location[0] - 1].substring(location[1] + 1);
                getMaze()[location[0] - 2] = getMaze()[location[0] - 2].substring(0, location[1]) + '$' + getMaze()[location[0] - 2].substring(location[1] + 1);
                return getMaze();
            }

            if(getMaze()[location[0] - 2].toCharArray()[location[1]] == '.') {
                getMaze()[location[0] - 1] = getMaze()[location[0] - 1].substring(0, location[1]) + '&' + getMaze()[location[0] - 1].substring(location[1] + 1);
                getMaze()[location[0] - 2] = getMaze()[location[0] - 2].substring(0, location[1]) + 'X' + getMaze()[location[0] - 2].substring(location[1] + 1);
                return getMaze();
            }
        }

        return null;
    }

    /**
     * Moves the player from the gives location downwards according to the objects surrounding the player.
     *
     * @param location the player's position
     * @param playerSymbol the player symbol, which can be '&#064;' (player is standing on a 'blank' object) or '&amp;' (player is standing on a 'finish' object) depending on the player's position.
     * @return the maze after the move
     * @throws InvalidMoveException if the player can't move in that direction
     */

    public String[] moveDown(Integer[] location, String playerSymbol) throws  InvalidMoveException {
        if(getMaze()[location[0] + 1].toCharArray()[location[1]] == '#' ||
                (getMaze()[location[0] + 1].toCharArray()[location[1]]  == '$' && getMaze()[location[0] + 2].toCharArray()[location[1]] == '#') ||
                (getMaze()[location[0] + 1].toCharArray()[location[1]] == 'X' && getMaze()[location[0] + 2].toCharArray()[location[1]] == '#' ) ||
                (getMaze()[location[0] + 1].toCharArray()[location[1]] == '$' && getMaze()[location[0] + 2].toCharArray()[location[1]] == '$' ) ||
                (getMaze()[location[0] + 1].toCharArray()[location[1]] == 'X' && getMaze()[location[0] + 2].toCharArray()[location[1]] == 'X' ) ||
                (getMaze()[location[0] + 1].toCharArray()[location[1]] == 'X' && getMaze()[location[0] + 2].toCharArray()[location[1]] == '$') ||
                (getMaze()[location[0] + 1].toCharArray()[location[1]] == '$' && getMaze()[location[0] + 2].toCharArray()[location[1]] == 'X')) {


            setMoves(getMoves() - 1);
            throw new InvalidMoveException("");
        }

        if(playerSymbol.equals("&")) {
            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1]) + '.' + getMaze()[location[0]].substring(location[1] + 1);
        } else {
            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1]) + ' ' + getMaze()[location[0]].substring(location[1] + 1);
        }

        if (getMaze()[location[0] + 1].toCharArray()[location[1]] == ' ') {

            getMaze()[location[0] + 1] = getMaze()[location[0] + 1].substring(0, location[1]) + '@' + getMaze()[location[0] + 1].substring(location[1] + 1);

            return getMaze();
        }

        if (getMaze()[location[0] + 1].toCharArray()[location[1]]  == '.') {

            getMaze()[location[0] + 1] = getMaze()[location[0] + 1].substring(0, location[1]) + '&' + getMaze()[location[0] + 1].substring(location[1] + 1);

        }

        if (getMaze()[location[0] + 1].toCharArray()[location[1]] == '$') {
            log.info("Pushing box downwards");

            if (getMaze()[location[0] + 2].toCharArray()[location[1]] == ' ') {
                getMaze()[location[0] + 1] = getMaze()[location[0] + 1].substring(0, location[1]) + '@' + getMaze()[location[0] + 1].substring(location[1] + 1);
                getMaze()[location[0] + 2] = getMaze()[location[0] + 2].substring(0, location[1]) + '$' + getMaze()[location[0] + 2].substring(location[1] + 1);

            }

            if (getMaze()[location[0] + 2].toCharArray()[location[1]] == '.') {


                getMaze()[location[0] + 1] = getMaze()[location[0] + 1].substring(0, location[1]) + '@' + getMaze()[location[0] + 1].substring(location[1] + 1);
                getMaze()[location[0] + 2] = getMaze()[location[0] + 2].substring(0, location[1]) + 'X' + getMaze()[location[0] + 2].substring(location[1] + 1);

                return getMaze();

            }
        }

        if (getMaze()[location[0] + 1].toCharArray()[location[1]] == 'X') {
            log.info("Pushing box downwards");


            if(getMaze()[location[0] + 2].toCharArray()[location[1]] == '.') {
                getMaze()[location[0] + 1] = getMaze()[location[0] + 1].substring(0, location[1]) + '&' + getMaze()[location[0] + 1].substring(location[1] + 1);
                getMaze()[location[0] + 2] = getMaze()[location[0] + 2].substring(0, location[1]) + 'X' + getMaze()[location[0] + 2].substring(location[1] + 1);
            }

            if(getMaze()[location[0] + 2].toCharArray()[location[1]] == ' ') {
                getMaze()[location[0] + 1] = getMaze()[location[0] + 1].substring(0, location[1]) + '&' + getMaze()[location[0] + 1].substring(location[1] + 1);
                getMaze()[location[0] + 2] = getMaze()[location[0] + 2].substring(0, location[1]) + '$' + getMaze()[location[0] + 2].substring(location[1] + 1);
            }

        }

        return null;
    }

    /**
     * Moves the player from the gives location to the left according to the objects surrounding the player.
     *
     * @param location the player's position
     * @param playerSymbol the player symbol, which can be '&#064;' (player is standing on a 'blank' object) or '&amp;' (player is standing on a 'finish' object) depending on the player's position.
     * @return the maze after the move
     * @throws InvalidMoveException if the player can't move in that direction
     */

    public String[] moveLeft(Integer[] location, String playerSymbol) throws InvalidMoveException {
        if(getMaze()[location[0]].toCharArray()[location[1] - 1] == '#' ||
                (getMaze()[location[0]].toCharArray()[location[1] - 1] == '$' && getMaze()[location[0]].toCharArray()[location[1] - 2] == '#') ||
                (getMaze()[location[0]].toCharArray()[location[1] - 1] == 'X' && getMaze()[location[0]].toCharArray()[location[1] - 2] == '#') ||
                (getMaze()[location[0]].toCharArray()[location[1] - 1] == '$' && getMaze()[location[0]].toCharArray()[location[1] - 2] == '$') ||
                (getMaze()[location[0]].toCharArray()[location[1] - 1] == 'X' && getMaze()[location[0]].toCharArray()[location[1] - 2] == 'X') ||
                (getMaze()[location[0]].toCharArray()[location[1] - 1] == 'X' && getMaze()[location[0]].toCharArray()[location[1] - 2] == '$') ||
                (getMaze()[location[0]].toCharArray()[location[1] - 1] == '$' && getMaze()[location[0]].toCharArray()[location[1] - 2] == 'X')){


            setMoves(getMoves() - 1);
            throw new InvalidMoveException("");
        }

        if(playerSymbol.equals("&")) {
            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1]) + "." + getMaze()[location[0]].substring(location[1] + 1);
        } else {
            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1]) + " " + getMaze()[location[0]].substring(location[1] + 1);
        }

        if (getMaze()[location[0]].toCharArray()[location[1] - 1] == ' ') {

            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] - 1) + "@" + getMaze()[location[0]].substring(location[1]);

            return getMaze();
        }

        if (getMaze()[location[0]].toCharArray()[location[1] - 1]  == '.') {

            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] - 1) + "&" + getMaze()[location[0]].substring(location[1]);

            return getMaze();
        }

        if (getMaze()[location[0]].toCharArray()[location[1] - 1] == '$') {
            log.info("Pushing box to left");

            if (getMaze()[location[0]].toCharArray()[location[1] - 2] == ' '){

                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] - 1) + '@' + getMaze()[location[0]].substring(location[1]);
                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] - 2) + '$' + getMaze()[location[0]].substring(location[1] - 1);

                return getMaze();
            }

            if (getMaze()[location[0]].toCharArray()[location[1] - 2] == '.'){

                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] - 1) + '@' + getMaze()[location[0]].substring(location[1]);
                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] - 2) + 'X' + getMaze()[location[0]].substring(location[1] - 1);

                return getMaze();
            }
        }

        if (getMaze()[location[0]].toCharArray()[location[1] - 1] == 'X') {
            log.info("Pushing box left");


            if (getMaze()[location[0]].toCharArray()[location[1] - 2] == ' ') {

                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] - 1) + '&' + getMaze()[location[0]].substring(location[1]);
                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] - 2) + '$' + getMaze()[location[0]].substring(location[1] - 1);

                return getMaze();
            }

            if (getMaze()[location[0]].toCharArray()[location[1] - 2] == '.') {

                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] - 1) + '&' + getMaze()[location[0]].substring(location[1]);
                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] - 2) + 'X' + getMaze()[location[0]].substring(location[1] - 1);

                return getMaze();
            }
        }
        return null;
    }

    /**
     * Moves the player from the gives location to the right according to the objects surrounding the player.
     *
     * @param location the player's position
     * @param playerSymbol the player symbol, which can be '&#064;' (player is standing on a 'blank' object) or '&amp;' (player is standing on a 'finish' object) depending on the player's position.
     * @return the maze after the move
     * @throws InvalidMoveException if the player can't move in that direction
     */

    public String[] moveRight(Integer[] location, String playerSymbol) throws InvalidMoveException {
        if(getMaze()[location[0]].toCharArray()[location[1] + 1] == '#' ||
                (getMaze()[location[0]].toCharArray()[location[1] + 1] == '$' && getMaze()[location[0]].toCharArray()[location[1] + 2] == '#') ||
                (getMaze()[location[0]].toCharArray()[location[1] + 1] == 'X' && getMaze()[location[0]].toCharArray()[location[1] + 2] == '#') ||
                (getMaze()[location[0]].toCharArray()[location[1] + 1] == '$' && getMaze()[location[0]].toCharArray()[location[1] + 2] == '$') ||
                (getMaze()[location[0]].toCharArray()[location[1] + 1] == 'X' && getMaze()[location[0]].toCharArray()[location[1] + 2] == 'X') ||
                (getMaze()[location[0]].toCharArray()[location[1] + 1] == 'X' && getMaze()[location[0]].toCharArray()[location[1] + 2] == '$') ||
                (getMaze()[location[0]].toCharArray()[location[1] + 1] == '$' && getMaze()[location[0]].toCharArray()[location[1] + 2] == 'X')){

            setMoves(getMoves() - 1);
            throw new InvalidMoveException("");
        }

        if (playerSymbol.equals("&")) {
            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1]) + "." + getMaze()[location[0]].substring(location[1] + 1);
        } else {
            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1]) + " " + getMaze()[location[0]].substring(location[1] + 1);
        }

        if (getMaze()[location[0]].toCharArray()[location[1] + 1] == ' ' ) {

            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] + 1) + '@' + getMaze()[location[0]].substring(location[1] + 2);

            return getMaze();
        }

        if( getMaze()[location[0]].toCharArray()[location[1] + 1]  == '.') {

            getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] + 1) + "&" + getMaze()[location[0]].substring(location[1] + 2);
        }

        if (getMaze()[location[0]].toCharArray()[location[1] + 1] == '$') {
            log.info("Pushing box to right");

            if (getMaze()[location[0]].toCharArray()[location[1] + 2] == ' ') {

                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] + 1) + '@' + getMaze()[location[0]].substring(location[1] + 2);
                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] + 2) + '$' + getMaze()[location[0]].substring(location[1] + 3);

                return getMaze();
            }

            if (getMaze()[location[0]].toCharArray()[location[1] + 2] == '.') {

                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] + 1) + '@' + getMaze()[location[0]].substring(location[1] + 2);
                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] + 2) + 'X' + getMaze()[location[0]].substring(location[1] + 3);

                return getMaze();
            }
        }

        if (getMaze()[location[0]].toCharArray()[location[1] + 1] == 'X') {
            log.info("Pushing box to right");


            if (getMaze()[location[0]].toCharArray()[location[1] + 2] == ' ') {

                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] + 1) + '&' + getMaze()[location[0]].substring(location[1] + 2);
                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] + 2) + '$' + getMaze()[location[0]].substring(location[1] + 3);

                return getMaze();
            }

            if (getMaze()[location[0]].toCharArray()[location[1] + 2] == '.') {

                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] + 1) + '&' + getMaze()[location[0]].substring(location[1] + 2);
                getMaze()[location[0]] = getMaze()[location[0]].substring(0, location[1] + 2) + 'X' + getMaze()[location[0]].substring(location[1] + 3);

                return getMaze();
            }
        }

        return null;
    }

    /**
     * Moves the player to the appropriate direction.
     *
     * @param dir the direction we want to move our player
     * @return the maze after the move
     * @throws InvalidMoveException if the player can't move in that direction
     */

    public String[] move(Direction dir) throws InvalidMoveException {
        if(goalCount <= 0)
            this.goalCount = getGoalCount();

        setMoves(getMoves() + 1);
        Integer[] location = getPlayerLocation();
        String playerSymbol = getMaze()[location[0]].substring(location[1], location[1] + 1);

        switch (dir) {

            case UP:

                log.info("Moving up");
                moveUp(location, playerSymbol);

                break;

            case DOWN:

                log.info("Moving down");
                moveDown(location, playerSymbol);

                break;

            case LEFT:

                log.info("Moving left");
                moveLeft(location, playerSymbol);

                break;

            case RIGHT:
                log.info("Moving right");

                moveRight(location, playerSymbol);


                break;
        }

        return getMaze();
    }

    /**
     * Changes the maze according to the appropriate direction
     * @param scene the scene we want to use to draw the maze
     */

    public void handleMovement(Scene scene) {
        scene.setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case UP:
                    try {
                        setMaze(move(Direction.UP));
                    } catch (InvalidMoveException e) {
                        log.info("Can't move up");
                    }
                    mazeChanged = true;
                    mazeView.mazeChanged();
                    break;
                case DOWN:
                    try {
                       setMaze(move(Direction.DOWN));
                    } catch (InvalidMoveException e) {
                        log.info("Can't move down");
                    }
                    mazeChanged = true;
                    mazeView.mazeChanged();
                    break;
                case LEFT:
                    try {
                        setMaze(move(Direction.LEFT));
                    } catch (InvalidMoveException e) {
                        log.info("Can't move left");
                    }
                    mazeChanged = true;
                    mazeView.mazeChanged();
                    break;
                case RIGHT:
                    try {
                        setMaze(move(Direction.RIGHT));
                    } catch (InvalidMoveException e) {
                        log.info("Can't move right");
                    }
                    mazeChanged = true;
                    mazeView.mazeChanged();
                    break;
                case ESCAPE:
                    MenuController.display(gameMenu);
                    break;
            }


        });
    }
    /**
     * Searches through the maze until it finds the '@' symbol  or 'and' symbol (player symbol)
     *
     * @return the player's position if the player is found, otherwise [-1, -1]
     */

    public Integer[] getPlayerLocation() {
        int i = 0, j = 0;
        for (String maze_row: getMaze()) {
            for(char maze_piece: maze_row.toCharArray()) {
                if (maze_piece == '@' || maze_piece == '&') {
                    return new Integer[] {i, j};
                }
                j += 1;
            }
            j = 0;
            i += 1;
        }

        return null;
    }

    /**
     * Searches through the maze and counts the '.' symbols (finish symbols)
     *
     * @return the amount of '.' symbols (finish symbol)
     */
    public int getGoalCount() {
        int goalCount = 0;
        for (String maze_row: getMaze()) {
            for(char maze_piece: maze_row.toCharArray()) {
                if (maze_piece == '.'){
                    goalCount++;
                }
            }
        }
        return goalCount;
    }

    /**
     * Checks if the amount of 'X' symbols (box on finish position) are equal with the amount of '.' symbols (goal symbols)
     * @return true if equals, otherwise false
     */

    public boolean checkVictory() {
        int count = 0;
        for (String maze_row: getMaze()) {
            for (char maze_piece : maze_row.toCharArray()) {
                if (maze_piece == 'X') {
                    count++;
                }
            }
        }
        if(goalCount == count & goalCount != 0) {

            addScore(GameController.getPlayerName(), getMoves(), getLevelCount());
            ScoreMenu.displayLevel(MazeController.getLevelCount());

            log.info("Map done.");
            log.info("Loading next level.");

            setMoves(0);
            setLevelCount(getLevelCount() + 1);

            return true;
        }
        return false;
    }

    /**
     * Appends a new line to the scores.json file*
     * @param name the player name
     * @param newLevel the finished level
     * @param newSteps the steps it tooks to complete the level
     */

    private void addScore(String name, int newLevel, int newSteps) {
        ArrayList<ScoreBoard> scoreBoards = new ArrayList<>(Arrays.asList(FileHandler.readScores()));

        ScoreBoard to_be_removed = null;


        for(ScoreBoard scoreBoard: scoreBoards) {
            if (scoreBoard.getPlayerName().equals(name)) {
                to_be_removed = scoreBoard;
            }
        }

        scoreBoards.remove(to_be_removed);

        scoreBoards.add(new ScoreBoard(GameController.getPlayerName(), newSteps, newLevel));


        scoreBoards.add(new ScoreBoard(GameController.getPlayerName(), 0, 0));

        FileHandler.writeToScores(scoreBoards);


    }

}
