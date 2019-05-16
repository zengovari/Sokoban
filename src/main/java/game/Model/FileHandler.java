package game.Model;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  This class handles the tasks regarding to the data.json file.
 */

@Slf4j
public class FileHandler {

    private static Gson gson = new Gson();

    /**
     * Reads the given file then saves it's content into a {@link Maze} list.
     *
     * @param fileName the name of the file we want to read
     * @return the created {@link Maze} list.
     */

    public static Maze[] readMazes(String fileName) {

        InputStream inputStream = FileHandler.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream != null) {
            log.info("{} successfully read.", fileName);
        } else {
            log.error("{} not found", fileName);
            log.info("Closing program..");
            System.exit(1);
        }

        Reader reader = new InputStreamReader(inputStream);

        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("Level");

        Maze[] mazes = gson.fromJson(jsonArray, Maze[].class);

        if (mazes != null) {
            log.info("Mazes list successfully created");
        } else {
            log.error("ERROR while creating the list of mazes.");
            return null;
        }

        return mazes;
    }

    /**
     * Creates a new 'data' folder (if it not existed yet), then a data.json file inside it (if it not existed yet)
     * @return the path of the data.json file
     */

    public static String getDataPath() {
        String path = null;


        try {
            Path workingDirectory = FileSystems.getDefault().getPath("").toAbsolutePath();
            path = workingDirectory.toString() + File.separator + "data" + File.separator;
        } catch (Exception e) {
            log.error("Couldn't find working directory");
        }

        File directory = new File(path);
        if (directory.mkdir()) {
            log.info("Directory {} created", path);
        }

        try {
            path = path + "data.json";
            Path dest = Paths.get(path);
            if (Files.notExists(dest)) {
                Files.createFile(Paths.get(path));
                log.info("File {} created", path);
            }
        } catch (Exception e) {
            log.error("Something went wrong while creating the data.json file..");
        }

        return path;
    }


    /**
     * Saves the content of the data.json file (the path is given by {@link FileHandler#getDataPath()}
     * into a {@link ScoreBoard} list, then returns this list.
     * @return the {@link ScoreBoard} list
     */

    public static ScoreBoard[] readScores() {

        BufferedReader br = null;
        ScoreBoard[] scoreBoards = null;
        try {
            br = new BufferedReader(new FileReader(getDataPath()));
            JsonObject jsonObject = gson.fromJson(br, JsonObject.class);
            try {
                JsonArray jsonArray = jsonObject.getAsJsonArray("Scoreboard");
                scoreBoards = gson.fromJson(jsonArray, ScoreBoard[].class);
            } catch (Exception e) {
                log.info("JsonArray could not be created");
            }


        } catch (FileNotFoundException e) {
            log.error("Data.json file not found.");
        }

        return scoreBoards;
    }


    /**
     * Writes a {@link ScoreBoard} list to the scores.json while using {@link JsonWriter}
     * @param scoreBoards the {@link ScoreBoard} list we want to write into our score.json file.
     */
    public static void writeToScores(List<ScoreBoard> scoreBoards) {
        try {
            JsonWriter jsonWriter = new JsonWriter(new FileWriter(getDataPath()));
            jsonWriter.setIndent("  ");

            jsonWriter.beginObject();
            jsonWriter.name("Scoreboard");
            jsonWriter.beginArray();

            for (ScoreBoard scoreBoard : scoreBoards) {
                jsonWriter.beginObject();

                jsonWriter.name("name").value(scoreBoard.getPlayerName());
                jsonWriter.name("level").value(scoreBoard.getLevel());
                jsonWriter.name("steps").value(scoreBoard.getSteps());

                jsonWriter.endObject();

            }
            jsonWriter.endArray();
            jsonWriter.endObject();
            jsonWriter.close();

        } catch (Exception e) {
            log.error("Something went wrong while writing to scores.json");
        }
    }


    /**
     * Reads the data.json list (that is created by the {@link FileHandler#getDataPath()})
     * then searches for the highest level completed by the player.
     * @param name the player name we want to find
     * @return the highest level completed by the given player
     */

    public static int lastCompletedLevelByPlayer(String name) {
        List<ScoreBoard> scoreBoards = new ArrayList<>(Arrays.asList(readScores()));
        scoreBoards = scoreBoards.stream()
                                     .sorted(Comparator.comparingInt(ScoreBoard::getLevel)
                                     .reversed())
                                     .collect(Collectors.toList());

        for(ScoreBoard scoreBoard: scoreBoards) {
            if(scoreBoard.getPlayerName().equals(name)) {
                return scoreBoard.getLevel();
            }
        }

        return -1;

    }


}