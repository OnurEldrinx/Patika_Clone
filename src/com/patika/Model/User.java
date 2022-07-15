package com.patika.Model;

import com.patika.Helpers.DBConnector;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private int id;
    private String fullName;
    private String username;
    private String password;
    private String userType;

    public User() { }

    public User(int id, String fullName, String username, String password, String userType) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public static boolean isUsernameTaken(String username){

        boolean result = false;

        String query = "select * from user where username = ?";

        try {
            PreparedStatement statement = DBConnector.getInstance().prepareStatement(query);
            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){

                result = true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }


    public static boolean addUser(String fullName,String username,String password,String userType){

        String query = "INSERT INTO user(name,username,password,user_type) VALUES (?,?,?,?)";

        boolean result = true;

        try {

            if(!isUsernameTaken(username)){

                PreparedStatement statement = DBConnector.getInstance().prepareStatement(query);

                statement.setString(1,fullName);
                statement.setString(2,username);
                statement.setString(3,password);
                statement.setString(4,userType);
                result = statement.executeUpdate() != -1;

            }else{

                JOptionPane.showMessageDialog(null,"Username is taken.","Error",JOptionPane.ERROR_MESSAGE);
                result = false;

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return result;

    }

    public static boolean deleteUser(int id){

        String query = "delete from user where id = ?";
        boolean result = true;

        try {

            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            result = pr.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
