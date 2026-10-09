package com.kina.reclutamiento.config;

import com.kina.reclutamiento.exception.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
   private static final String URL = 
           "jdbc:mysql://localhost:3306/reclutamiento_db_in4bm";
   private static final String USER = "";
   private static final String PASSWORD = "";
   
   public Connection getConnection(){
       try{
           return DriverManager.getConnection(URL,USER,PASSWORD);
       }catch(SQLException e){
           throw new DataAccessException("no se pudo conectar a la base de datos " + e.getMessage());
       }
   }
}
