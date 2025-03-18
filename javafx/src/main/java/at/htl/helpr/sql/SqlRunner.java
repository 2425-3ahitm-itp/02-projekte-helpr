package at.htl.helpr.sql;

import at.htl.helpr.controller.Database;
import org.apache.ibatis.jdbc.ScriptRunner;
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

import static java.nio.file.Files.readAllBytes;

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
//        writeImageFromUser(1, "john.jpg");
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
            byte[] imageData = readAllBytes(Paths.get(filePath));
            stmt.setBytes(1, imageData);

            stmt.execute();

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeImageFromUser(long userId, String toWritePath) {
        String sql = """
                SELECT u_user.profile_picture
                from u_user
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

        System.out.println("Inserting images");

        var imagesArray = Arrays.asList(
                "dana.jpg",
                "john.jpg",
                "rhonda.jpg",
                "ricardo.jpg",
                "joel.jpg"
        );

        for (int i = 1; i <= imagesArray.size(); i++) {
            System.out.println("adding image " + imagesArray.get(i - 1));
            setImageForUser(i, Objects.requireNonNull(
                    SqlRunner.class.getResource("/img/profiles/" + imagesArray.get(i - 1))).getPath());
        }
    }
}
