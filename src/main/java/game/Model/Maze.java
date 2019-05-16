package game.Model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 *
 *
 */

@Data
public class Maze {

    /**
     * An array of every maze.
     */

    private static final Maze[] mazes = FileHandler.readMazes("maps.json");

    /**
     * The level represented in a '#level' format
     */

    @SerializedName("Id")
    private String level;

    /**
     * The width of the maze.
     */

    @SerializedName("Width")
    private int width;

    /**
     * The height of the maze.
     */

    @SerializedName("Height")
    private int height;

    /**
     * The maze stored in a {@link String} array.
     */

    @SerializedName("L")
    private String[] maze;



    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public static Maze[] getMazes() {
        return mazes;
    }

    public String[] getMaze() {
        return maze;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setMaze(String[] maze) {this.maze = maze;}

    public String getLevel() {
        return level;
    }


    /**
     * A copy constructor used to do not copy the original maze.
     * @param maze the maze to be copied.
     */

    public Maze(Maze maze) {
        this.height = maze.height;
        this.width = maze.width;
        this.maze = maze.maze.clone();
        this.level = maze.level;
    }



}
