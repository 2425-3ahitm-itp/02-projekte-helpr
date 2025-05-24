package at.htl.helpr.sql;

import at.htl.helpr.controller.Database;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.apache.ibatis.jdbc.ScriptRunner;

public class SqlRunner {

    private static final String SCHEMA_FILE_PATH = "/database/schema.sql";
    private static final String INSERTS_FILE_PATH = "/database/insert.sql";

    public static void main(String... args) {
        runSchema();
        runInserts();

        // setImageForUser(1, Objects.requireNonNull(
        // SqlRunner.class.getResource("/img/profiles/john.jpg")).getPath());
    }

    private static void runScript(String filePath) {
        try (Connection conn = Database.getConnection();
                var reader = new BufferedReader(new InputStreamReader(
                        Objects.requireNonNull(SqlRunner.class.getResourceAsStream(filePath))))) {

            ScriptRunner scriptRunner = new ScriptRunner(conn);
            scriptRunner.setLogWriter(null);

            scriptRunner.runScript(reader);

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runString(String sql) {
        try (Connection conn = Database.getConnection()) {

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

        try (Connection conn = Database.getConnection(); var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(2, userId);

            System.out.println("Settings image for user " + userId + " to " + filePath);

            // binary image data from filePath
            byte[] imageData;

            // read image at store/profile/filePath
            try (var fis = new FileReader("store/profiles/" + filePath)) {
                StringBuilder sb = new StringBuilder();
                int ch;
                while ((ch = fis.read()) != -1) {
                    sb.append((char) ch);
                }
                imageData = sb.toString().getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            stmt.setBytes(1, imageData);

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addImageForTaskWithOrder(long taskId, int order, String imageFilePath) {
        String sql = """
                INSERT INTO helpr.image
                (task_id, path, "order")
                values (?, ?, ?)
                """;

        try (Connection conn = Database.getConnection(); var stmt = conn.prepareStatement(sql)) {

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

        try (Connection conn = Database.getConnection(); var stmt = conn.prepareStatement(sql)) {

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

    public static void dropEverything() {
        String sql = """
                DROP SCHEMA IF EXISTS helpr CASCADE;
                CREATE SCHEMA helpr;
                """;

        runString(sql);
    }

    public static void runInserts() {
        runScript(INSERTS_FILE_PATH);

        List<String> profilesArray = new LinkedList<>();

        File folder = new File("store/profiles");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".jpg")) {
                    try {
                        String fileName = file.getCanonicalFile().getName();
                        profilesArray.add(fileName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        for (int i = 1; i <= profilesArray.size(); i++) {
            setImageForUser(i, profilesArray.get(i - 1));
        }

        List<String> imagesList = new ArrayList<>();

        // get all filenames in root/store/task_images
        folder = new File("store/task_images");
        listOfFiles = folder.listFiles();

        System.out.println("--------------------------------------------------");
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    try {
                        System.out.println("Adding image " + file.getCanonicalFile().getName());
                        imagesList.add(file.getCanonicalFile().getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        System.out.println("========================");

        for (String fileName : imagesList) {

            var parts = fileName.split("_");
            if (parts.length < 2) {
                continue;
            }

            long taskId = Long.parseLong(parts[0]);
            int order = Integer.parseInt(parts[1]);

            addImageForTaskWithOrder(taskId, order, fileName);
        }

    }
}
