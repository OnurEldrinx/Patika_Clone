package com.patika.Model;

import com.patika.Helpers.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {

    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String language;

    private Patika patika;
    private User educator;



    public Course(int id, int user_id, int patika_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.language = language;
        this.patika = Patika.getPatikaByID(patika_id);
        this.educator = User.getUser(user_id);
    }


    public static Course fetchCourse(int id){

        Course course = null;

        String query = "SELECT * FROM course WHERE id = ?";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){

                course = new Course(resultSet.getInt("id"),resultSet.getInt("user_id"),resultSet.getInt("patika_id"),resultSet.getString("name"),resultSet.getString("language"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    public static ArrayList<Course> getCourseList(){

        ArrayList<Course> list = new ArrayList<>();

        Course temp;

        try {

            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course");

            while (resultSet.next()){

                temp = new Course(resultSet.getInt("id"),resultSet.getInt("user_id"),resultSet.getInt("patika_id"),resultSet.getString("name"),resultSet.getString("language"));
                list.add(temp);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    public static ArrayList<Course> getCourseList(Educator thisEducator){

        ArrayList<Course> list = new ArrayList<>();

        Course temp;

        try {

            String query = "select * from course where user_id = ?";
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,thisEducator.getId());
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){

                temp = new Course(resultSet.getInt("id"),resultSet.getInt("user_id"),resultSet.getInt("patika_id"),resultSet.getString("name"),resultSet.getString("language"));
                list.add(temp);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    public static boolean addCourse(int user_id,int patika_id,String name,String language){

        String query = "INSERT INTO course(user_id,patika_id,name,language) VALUES(?,?,?,?)";

        try {

            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,user_id);
            ps.setInt(2,patika_id);
            ps.setString(3,name);
            ps.setString(4,language);
            return (ps.executeUpdate() != -1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    @Override
    public String toString() {
        return name;
    }
}
