package org.crabcraft.Commandler;

import java.io.File;
import java.sql.*;

public class FrameworkDB {

    static Connection connectionSetup() {
        // Establish the database connection and return the connection variable
        try {
            Class.forName("org.sqlite.JDBC");
        } 
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:cmdframework.db");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    static void firstTimeSetup() {
        // Check if cmdframework.db exists, if not, create it.
        if (new File("cmdframework.db").exists()) {
            System.out.println("FrameworkDB exists, no need for further action.");
        }
        else {
            Connection conn = connectionSetup();
            try{ conn.close(); } catch(SQLException e) { e.printStackTrace(); }
            System.out.println("FrameworkDB did not exist and was created");
        }
    }

    static void serverFirstTimeSetup(String serverPrefix, String serverId) {
        // When a server is joined, this is called to add it to the database table.
        Connection conn = connectionSetup();

        try {
            // Create the table if it doesn't exist
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS serverconf"
                            + "(serverId TEXT NOT NULL PRIMARY KEY,"
                            + " prefix TEXT NOT NULL)");
            System.out.println("serverConf initialised");
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // Add the server data into the table

            String sql = ("INSERT INTO serverconf VALUES(?,?)");

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, serverId);
            stmt.setString(2, serverPrefix);
            stmt.executeUpdate();
            stmt.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            conn.close();
        }
        catch(SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }

    public static String getServerPrefix(String serverId) {
        // Get the prefix for the given server from the FrameworkDB
        Connection conn = connectionSetup();
        String prefix = null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT prefix FROM serverconf WHERE serverId = " + serverId);
            prefix = results.getString("prefix");

            stmt.close();
            conn.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return prefix;
    }

    // TODO: Add public method for setting server prefix
}