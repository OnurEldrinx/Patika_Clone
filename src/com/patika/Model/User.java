package com.patika.Model;

import com.patika.Helpers.DBConnector;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    public static User isUsernameTaken(String username){

        User result = null;

        String query = "select * from user where username = ?";

        try {
            PreparedStatement statement = DBConnector.getInstance().prepareStatement(query);
            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){

                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullName(rs.getString("name"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setUserType(rs.getString("user_type"));
                result = u;
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

            if(isUsernameTaken(username) == null){

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

    public static boolean updateUser(int id,String fullName,String username,String password,String userType){

        boolean result = true;
        String query = "update user set name = ?,username = ?,password = ?,user_type = ? where id = ?";

        if (isUsernameTaken(username) != null && isUsernameTaken(username).getId() != id){

            JOptionPane.showMessageDialog(null,"Username is taken.","Error",JOptionPane.ERROR_MESSAGE);
            result = false;

        }else {

            try {

                PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
                ps.setString(1, fullName);
                ps.setString(2, username);
                ps.setString(3, password);
                ps.setString(4, userType);
                ps.setInt(5, id);
                result = ps.executeUpdate() != 0;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }




        return result;
    }

    public static ArrayList<User> searchUserList(String query){

        ArrayList<User> userList = new ArrayList<>();

        User temp;

        try {

            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){

                temp = new User();
                temp.setId(rs.getInt("id"));
                temp.setFullName(rs.getString("name"));
                temp.setUsername(rs.getString("username"));
                temp.setPassword(rs.getString("password"));
                temp.setUserType(rs.getString("user_type"));
                userList.add(temp);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(query);

        return userList;


    }

    public static String createQueryForSearch(String name,String username,String userType){

        String query = "SELECT * FROM user WHERE name LIKE '%{{name}}%' AND username LIKE '%{{username}}%'";

        query = query.replace("{{name}}",name);
        query = query.replace("{{username}}",username);

        if (!userType.isEmpty() && !userType.equals("Choose a type...")){

            query += " AND user_type LIKE '{{userType}}'";
            query = query.replace("{{userType}}",userType);

        }

        return query;

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
