package kr.huni.signup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DebeziumConfig {

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    private final String mysqlHostname = "localhost";
    private final String mysqlPort = "3306";
    private final String databaseName = "testdb";
//
//    @Bean
//    public io.debezium.config.Configuration mysqlConnector() {
//        Map<String, String> configMap = new HashMap<>();
//
//        //This sets the name of the Debezium connector instance. It’s used for logging and metrics.
//        configMap.put("name", "signup-cdc-connector");
//        //This specifies the Java class for the connector. Debezium uses this to create the connector instance.
//        configMap.put("connector.class", "io.debezium.connector.mysql.MySqlConnector");
//
//        //This sets the Java class that Debezium uses to store the progress of the connector. (offest)
//        /* Not-working
//        In this case, it’s using a JDBC-based store, which means it will store the progress in a relational database.
//        configMap.put("offset.storage", "io.debezium.storage.jdbc.offset.JdbcOffsetBackingStore");
//        //This is the JDBC URL for the database where Debezium stores the connector offsets (progress).
//        */
//
//        File offsetStorageTempFile = new File("offsets_.dat");
//        configMap.put("database.history", "io.debezium.relational.history.FileDatabaseHistory");
//        configMap.put("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
//        configMap.put("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath());
//        configMap.put("offset.flush.interval.ms", "60000");
//
//        //Connect Debezium connector to the source DB
//        configMap.put("database.hostname", mysqlHostname);
//        configMap.put("database.port", mysqlPort);
//        configMap.put("database.user", datasourceUsername);
//        configMap.put("database.password", datasourcePassword);
//        configMap.put("database.dbname", "testdb");
//        configMap.put("database.server.name", "signup"); //Why is this used?
//        configMap.put("plugin.name", "mysql");
//
//        configMap.put("schema.include.list", "testdb");
//        configMap.put("table.include.list", "testdb.users"); //Include only the users table in the testdb schema
//
//        // Enable snapshot mode to capture existing data
//        configMap.put("snapshot.mode", "initial");
//
//        // Enable binlog reading to capture changes
//        configMap.put("database.history", "io.debezium.relational.history.FileDatabaseHistory");
//        configMap.put("database.history.file.filename", "dbhistory.dat");
//
//        configMap.put("topic.prefix", "cdc_"); //Used as prefix for Kafka topics
//        configMap.put("snapshot.mode", "initial");
//
//        configMap.put("database.server.id", "85744"); // Unique ID for the MySQL server instance
//
//        return io.debezium.config.Configuration.from(configMap);
//    }

    @Bean
    public io.debezium.config.Configuration customerConnector() throws IOException {
        File offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
        File dbHistoryTempFile = File.createTempFile("dbhistory_", ".dat");
        return io.debezium.config.Configuration.create()
                .with("name", "customer-mysql-connector")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())
                .with("offset.flush.interval.ms", "60000")
                .with("database.hostname", mysqlHostname)
                .with("database.port", mysqlPort)
                .with("database.user", datasourceUsername)
                .with("database.password", datasourcePassword)
                .with("database.dbname", "testdb")
                .with("database.include.list", "testdb")
                .with("table.include.list", "testdb.users")
                .with("include.schema.changes", "false")
                .with("database.allowPublicKeyRetrieval", "true")
                .with("database.server.id", "10181")
                .with("database.server.name", "customer-mysql-db-server")
                .with("schema.history.internal", "io.debezium.storage.kafka.history.KafkaSchemaHistory")
                .with("schema.history.internal.kafka.bootstrap.servers", "localhost:9092")
                .with("schema.history.internal.kafka.topic", "schema-changes.testdb")
                .with("topic.prefix", "cdc_")
                .build();
    }
}
