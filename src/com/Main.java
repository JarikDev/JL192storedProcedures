package com;

import javax.imageio.ImageIO;
import javax.swing.plaf.nimbus.State;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        String userName = "root";
        String password = "sql123";
        String connectionUrl = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
      //  Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password);
             Statement stat = conn.createStatement()) {

            stat.execute ("drop table IF EXISTS Books");
            stat.executeUpdate("CREATE TABLE Books(id MEDIUMINT NOT NULL AUTO_INCREMENT,name CHAR(30) NOT NULL,dt DATE, PRIMARY KEY(id))");
            stat.executeUpdate("INSERT INTO Books(name)VALUES ('Inferno')");
            stat.executeUpdate("INSERT INTO Books(name)VALUES ('Solomon Kein')");
            stat.executeUpdate("INSERT INTO Books(name)VALUES ('Ivan Govnov')");
            stat.executeUpdate("INSERT INTO Books(name)VALUES ('Moja borba')");
            stat.executeUpdate("INSERT INTO Books(name)VALUES ('Voina i mir')");
            stat.executeUpdate("INSERT INTO Books(name)VALUES ('KZOT')");

            CallableStatement cS=conn.prepareCall("{call BooksCount(?)}");
            cS.registerOutParameter(1,Types.INTEGER);
            cS.execute();
            ResultSet rS=cS.getResultSet();
            System.out.println(cS.getInt(1));
            System.out.println("------------------");

            CallableStatement cS2=conn.prepareCall("{call getBooks(?)}");
            cS2.setInt(1,1);
            if(cS2.execute()){
                ResultSet rS2=cS2.getResultSet();
                while(rS2.next()){
                    System.out.println(rS2.getInt("id"));
                    System.out.println(rS2.getString("name"));
                }
            }
        }
    }
}


// CREATE TABLE IF NOT EXISTS Book (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id))























