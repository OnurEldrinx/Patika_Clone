package com.patika.Model;

import com.patika.Helpers.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {

    private int id;
    private String name;


    public static boolean addPatika(String name){

        boolean r = true;

        String query = "INSERT INTO patika(name) VALUES (?)";
        try {

            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            r = (preparedStatement.executeUpdate() != -1);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return r;
    }

    public static ArrayList<Patika> getPatikaList(){

        ArrayList<Patika> list = new ArrayList<>();

        Patika temp;

        try {

            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM patika");

            while (resultSet.next()){

                temp = new Patika();
                temp.setId(resultSet.getInt("id"));
                temp.setName(resultSet.getString("name"));
                list.add(temp);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return list;

    }


    public static boolean updatePatika(int targetID,String newName){

        boolean r = true;

        String query = "UPDATE patika SET name = ? WHERE id = ?";

        try {

            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(2,targetID);
            ps.setString(1,newName);
            r = (ps.executeUpdate() != -1);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return r;
    }

    public static boolean deletePatika(int id){

        String query = "DELETE FROM patika WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }

    public static Patika getPatikaByID(int id){

        Patika result = null;

        String query = "SELECT * FROM patika WHERE id = ?";
        try {

            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){

                result = new Patika();
                result.setId(resultSet.getInt("id"));
                result.setName(resultSet.getString("name"));

            }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
