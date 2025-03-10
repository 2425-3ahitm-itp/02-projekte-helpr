package at.htl.helpr.sql;

import at.htl.helpr.controller.Database;
import org.apache.ibatis.jdbc.ScriptRunner;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class SqlRunner {

    private static final String SCHEMA_FILE_PATH = Objects.requireNonNull(
            SqlRunner.class.getResource("/database/schema.sql")
    ).getPath();

    public static void main(String[] args) {

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

    public static void createIfNotExists() {
        runScript(SCHEMA_FILE_PATH);
    }
}
