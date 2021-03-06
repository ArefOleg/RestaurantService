package com.example.rest_demo.config;

import com.example.rest_demo.RestDemoApplication;
import com.example.rest_demo.service.RestaurantServiceTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        RestDemoApplication.class, HSQLDBTestProfileConfig.class
})
@ActiveProfiles("test")
public class TestConnection {
    @Autowired
    DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceTest.class);

    @Test
    public void getHSQLDBInfo() throws SQLException {
        LOGGER.info("\nDriver name: " + dataSource.getConnection().getMetaData().getDriverName()
                + "\n URL: " + dataSource.getConnection().getMetaData().getURL()
                + "\n Catalog: " + dataSource.getConnection().getCatalog()
                + "\n Connection: " + dataSource.getConnection().getMetaData().getConnection()
                + "\n Database: " + dataSource.getConnection().getMetaData().getDatabaseProductName()

        );
    }

    @Test
    public void getSchemas() throws SQLException {
        var scheme = dataSource.getConnection().getMetaData().getSchemas();
        while (scheme.next()) {
            LOGGER.info("\n Scheme: " + scheme.getString(1));
        }
    }

    @Test
    public void createTable() throws SQLException {
        ResultSet rs = dataSource.getConnection().createStatement().
                executeQuery("CREATE SEQUENCE PUBLIC.GLOBAL_SEQ AS INTEGER START WITH 100000; ");
        rs = dataSource.getConnection().createStatement().executeQuery(
                        "CREATE TABLE PUBLIC.RESTAURANT(name VARCHAR(255) NOT NULL, id INTEGER GENERATED BY DEFAULT AS SEQUENCE PUBLIC.GLOBAL_SEQ PRIMARY KEY);");
        //???????????? ????????????
        rs = dataSource.getConnection().createStatement().executeQuery("INSERT INTO PUBLIC.RESTAURANT (NAME) VALUES ('Mirazur'), ('Gaggan');");
        //??????????????????, ?????? ???????????? ??????????????????????
        rs = dataSource.getConnection().createStatement().executeQuery("SELECT id, name FROM PUBLIC.RESTAURANT;");
        while(rs.next()) {
            LOGGER.info("\n Id: " + rs.getInt(1) + " name: " + rs.getString(2));
        }
    }

    @Sql(scripts = "classpath:sql/initDBHibernate.sql", config = @SqlConfig(encoding = "UTF-8"))
    @Test
    public void testInitDBSQL() throws SQLException{
        var rs = dataSource.getConnection().createStatement().executeQuery("SELECT id, name FROM PUBLIC.RESTAURANT;");
        while(rs.next()) {
            LOGGER.info("\n Id: " + rs.getInt(1) + " name: " + rs.getString(2));
        }
    }

    @Test
    public void getTables() throws SQLException {
        var tables = dataSource.getConnection().getMetaData().getTables(null, "%", "%", null);
        while (tables.next()) {
            LOGGER.info("\n Scheme: " + tables.getString(2) + " Table: " + tables.getString(3));
        }
    }

}
