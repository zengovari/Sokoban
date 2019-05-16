package game.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Used by {@link FileHandler#writeToScores(List)} to save user data to the data.json file
 */

public class ScoreBoard {

    /**
     * The name of the player.
     */
    @SerializedName("name")
    @Expose
    private String playerName;

    /**
     * The level the player finished last.
     */
    @SerializedName("level")
    private Integer level;


    /**
     * The amount of steps required to finish the level.
     */
    @SerializedName("steps")
    private Integer steps;

    public ScoreBoard(String playerName, Integer level, Integer steps) {
        this.playerName = playerName;
        this.level = level;
        this.steps = steps;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    /**
     * Checks if the gives name exists in the data.json file , or not.
     * @param scoreBoards the list of {@link ScoreBoard}
     * @param name name of the player we want to check
     * @return true if it exists, otherwise false
     */

    public static boolean scoreBoardsContainsName(ScoreBoard[] scoreBoards, String name) {
        for(int i = 0; i < scoreBoards.length; i++) {
            if(scoreBoards[i] != null)
                if (scoreBoards[i].getPlayerName().equals(name))
                    return true;
        }

        return false;
    }

}