package at.htl.helpr.sql;

import at.htl.helpr.controller.Database;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import org.apache.ibatis.jdbc.ScriptRunner;

public class SqlRunner {

    private static final String SCHEMA_FILE_PATH = Objects.requireNonNull(
            SqlRunner.class.getResource("/database/schema.sql")
    ).getPath();

    private static final String INSERTS_FILE_PATH = Objects.requireNonNull(
            SqlRunner.class.getResource("/database/insert.sql")
    ).getPath();

    public static void main(String[] args) {
        runSchema();
        runInserts();

//        setImageForUser(1, Objects.requireNonNull(
//                SqlRunner.class.getResource("/img/profiles/john.jpg")).getPath());
    }

    private static void runScript(String filePath) {
        try (Connection conn = Database.getConnection()) {

            ScriptRunner scriptRunner = new ScriptRunner(conn);
            scriptRunner.setLogWriter(null);

            BufferedReader reader = new BufferedReader(
                    new FileReader(filePath)
            );

            scriptRunner.runScript(reader);

        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runString(String sql) {
        try (Connection conn = Database.getConnection()) {

            // sql is the string containing the sql statements

            ScriptRunner scriptRunner = new ScriptRunner(conn);
            scriptRunner.setLogWriter(null);

            scriptRunner.runScript(new BufferedReader(new StringReader(sql)));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setImageForUser(long userId, String filePath) {
        String sql = """
                UPDATE u_user
                SET profile_picture = ?
                WHERE user_id = ?
                """;

        try (Connection conn = Database.getConnection();
                var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(2, userId);

            // binary image data from filePath
            byte[] imageData;
            try (var inputStream = SqlRunner.class.getResourceAsStream(filePath)) {
                assert inputStream != null;
                imageData = inputStream.readAllBytes();
            }
            stmt.setBytes(1, imageData);

            stmt.execute();

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addImageForTaskWithOrder(long taskId, int order, String imageFilePath) {
        String sql = """
                INSERT INTO helpr.image
                (task_id, path, "order")
                values (?, ?, ?)
                """;

        try (Connection conn = Database.getConnection();
                var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, taskId);
            stmt.setInt(3, order);
            stmt.setString(2, imageFilePath);

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeImageFromUser(long userId, String toWritePath) {
        String sql = """
                SELECT u_user.profile_picture
                from helpr.u_user
                WHERE user_id = ?
                """;

        try (Connection conn = Database.getConnection();
                var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    byte[] imageData = rs.getBytes("profile_picture");

                    // write binary imageData to toWritePath
                    try (var fos = new FileOutputStream(toWritePath)) {
                        fos.write(imageData);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void runSchema() {
        runScript(SCHEMA_FILE_PATH);
    }

    public static void runInserts() {
        runScript(INSERTS_FILE_PATH);

        var imagesArray = Arrays.asList(
                "dana.jpg",
                "john.jpg",
                "rhonda.jpg",
                "ricardo.jpg",
                "joel.jpg"
        );

        for (int i = 1; i <= imagesArray.size(); i++) {
            setImageForUser(i, "/img/profiles/" + imagesArray.get(i - 1));
        }

        var taskImagesPath = Paths.get(Objects.requireNonNull(
                SqlRunner.class.getResource("/img/task_images/")).getPath());

        try (var imagesList = java.nio.file.Files.list(taskImagesPath)) {
            var taskImages = imagesList
                    .filter(path -> path.toString().endsWith(".png"))
                    .toList();

            for (java.nio.file.Path taskImage : taskImages) {

                var fileName = taskImage.getFileName().toString();
                var parts = fileName.split("_");
                if (parts.length < 2) {
                    continue;
                }
                long taskId = Long.parseLong(parts[0]);
                int order = Integer.parseInt(parts[1]);

                addImageForTaskWithOrder(taskId, order, "/img/task_images/" + fileName);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
