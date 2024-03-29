package de.hype.perms.utils;

import de.hype.perms.HypePermsSpigot;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL {

    public static Connection connection;
    public static String host, username, password, database;

    public MySQL(String host, String username, String password, String database) {
        MySQL.host = host;
        MySQL.username = username;
        MySQL.password = password;
        MySQL.database = database;
    }

    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true&useSSL=false", username, password);
            Bukkit.getConsoleSender().sendMessage(HypePermsSpigot.getInstance().getPrefix() + "MySQL Connection Successfully");
        } catch (SQLException ignored) {
            Bukkit.getConsoleSender().sendMessage(HypePermsSpigot.getInstance().getPrefix() + "MySQL Connection Error");
        }
    }

    public static ResultSet getResult(String qry) {
        if (isConnected()) {
            try {
                return connection.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isConnected() {
        return connection != null;
    }

    public static void update(String qry) {
        if (isConnected()) {
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(qry);
                st.close();
            } catch (SQLException e) {
                connect();
                e.printStackTrace();
            }
        }
    }

    public static PreparedStatement getStatement(String qry) {
        if (isConnected()) {
            PreparedStatement ps;
            try {
                ps = connection.prepareStatement(qry);
                return ps;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

}
