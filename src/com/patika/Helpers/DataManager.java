package com.patika.Helpers;

import com.patika.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataManager {

    public static ArrayList<User> getUserList(){

        ArrayList<User> userList = new ArrayList<>();
        User tempUser;
        String query = "select * from user";

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){

                tempUser = new User();
                tempUser.setId(resultSet.getInt("id"));
                tempUser.setFullName(resultSet.getString("name"));
                tempUser.setUsername(resultSet.getString("username"));
                tempUser.setPassword(resultSet.getString("password"));
                tempUser.setUserType(resultSet.getString("user_type"));

                userList.add(tempUser);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;

    }



}
