package com.patika.View;

import com.patika.Model.Educator;
import com.patika.Model.Student;

import javax.swing.*;

public class StudentUI extends JFrame{
    private JPanel wrapper;

    private final Student student;

    public StudentUI(Student student){

        this.student = student;
        setContentPane(wrapper);
        this.setTitle("Patika.dev | Student");
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    public Student getStudent() {
        return student;
    }
}
