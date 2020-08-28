package com.corba.corbam;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class ConnectionClass {

    String classs = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/corbacim";
    String un = "root";
    String password = "";




    public static Connection connectDatabase(){
        Connection con=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");

            con=DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/corbacim?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8","root","");
        } catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("basarili");
        return con;
    }

}
