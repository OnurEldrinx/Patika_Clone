package com.patika.Model;

import com.patika.Helpers.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseContent {

    private int id;
    private String title;
    private String info;
    private String youtubeLink;
    private int courseId;


    public static ArrayList<CourseContent> getContent(int courseId){

        ArrayList<CourseContent> list = new ArrayList<>();

        String query = "select * from content where courseID = ?";

        CourseContent temp;

        try {

            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                temp = new CourseContent();
                temp.setId(rs.getInt("id"));
                temp.setTitle(rs.getString("title"));
                temp.setInfo(rs.getString("info"));
                temp.setYoutubeLink(rs.getString("youtube_link"));
                temp.setCourseId(rs.getInt("courseID"));
                list.add(temp);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    public static boolean addContent(String title,String info,String link,Course course){

        String query = "INSERT INTO content(title,info,youtube_link,courseID) VALUES(?,?,?,?)";
        try {

            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,title);
            ps.setString(2,info);
            ps.setString(3,link);
            ps.setInt(4,course.getId());
            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    }

    public static ArrayList<CourseContent> search(String title){

        ArrayList<CourseContent> list = new ArrayList<>();
        CourseContent temp;

        String q = "SELECT * FROM content WHERE title LIKE '%{{title}}%'";
        q = q.replace("{{title}}",title);

        try {

            PreparedStatement ps = DBConnector.getInstance().prepareStatement(q);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                temp = new CourseContent();
                temp.setId(rs.getInt("id"));
                temp.setTitle(rs.getString("title"));
                temp.setInfo(rs.getString("info"));
                temp.setYoutubeLink(rs.getString("youtube_link"));
                temp.setCourseId(rs.getInt("courseID"));
                list.add(temp);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
