package at.htl.helpr.sql;

import at.htl.helpr.controller.Database;
import org.apache.ibatis.jdbc.ScriptRunner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Objects;

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
    }

    private static void runScript( String filePath ) {
        try {
            Connection conn = Database.getConnection();
            ScriptRunner scriptRunner = new ScriptRunner( conn );
            scriptRunner.setLogWriter( null );

            BufferedReader reader = new BufferedReader(
                    new FileReader( filePath )
            );

            scriptRunner.runScript( reader );

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void runSchema() {
        runScript(SCHEMA_FILE_PATH);
    }

    public static void runInserts() {
        runScript(INSERTS_FILE_PATH);
    }
}
