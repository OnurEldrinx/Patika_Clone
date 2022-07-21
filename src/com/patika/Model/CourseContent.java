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
    private int quizId;

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
                temp.setQuizId(rs.getInt("quizID"));
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

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
