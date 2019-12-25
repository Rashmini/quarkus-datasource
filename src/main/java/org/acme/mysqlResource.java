package org.acme;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Path("/mysqlResource")
public class mysqlResource {

    @Inject
    @DataSource("users")
    AgroalDataSource dataSource;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Connection conn = null;
        Statement stmt = null;
        try {

            //STEP 1: Open a connection
            System.out.println("Connecting to database...");
            conn = dataSource.getConnection();
            stmt = conn.createStatement();

            //STEP 2: Execute queries
            String sql1 =  "CREATE TABLE IF NOT EXISTS REGISTRATION " +
                    "(id INTEGER not NULL, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            String sql2 =  "INSERT INTO REGISTRATION SELECT * FROM (SELECT 100, 'Rash', 'Aaa', 22) AS tmp WHERE NOT EXISTS ( SELECT id FROM REGISTRATION WHERE id = 100 )";
            String sql3 = "select * from REGISTRATION";

            /*String sql= "DROP TABLE IF EXISTS REGISTRATION";
            stmt.executeUpdate(sql);*/

            System.out.println("Creating table in given database...");
            stmt.executeUpdate(sql1);

            System.out.println("Inserting records into the table...");
            stmt.executeUpdate(sql2);

            System.out.println("Accessing data in table REGISTRATION...");
            ResultSet rs=stmt.executeQuery(sql3);
            while(rs.next())
                System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4));

            // STEP 3: Clean-up environment
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        System.out.println("Goodbye!");
        return "Executed queries";
    }
}