package com.patika.View;

import com.patika.Model.Course;
import com.patika.Model.Patika;
import com.patika.Model.Student;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class StudentUI extends JFrame{
    private JPanel wrapper;
    private JLabel welcomeText;
    private JButton logOutButton;
    private JTabbedPane panes;
    private JScrollPane patikaListScroll;
    private JTree patikaTree;
    private DefaultTreeModel patikaTreeModel;
    DefaultMutableTreeNode top;
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

        welcomeText.setText("WELCOME, " + student.getFullName().toUpperCase());

        top = new DefaultMutableTreeNode(new Patika("Patikas"));
        patikaTreeModel = new DefaultTreeModel(top);
        patikaTree.setModel(patikaTreeModel);

        refreshPatikaTree();



    }

    public static void main(String[] args) {

        Student student = new Student();
        student.setFullName("Onur Öztop");
        StudentUI studentUI = new StudentUI(student);

    }

    public void refreshPatikaTree(){

        top = new DefaultMutableTreeNode(new Patika("Patikas"));
        DefaultTreeModel clear = new DefaultTreeModel(top);

        patikaTree.setModel(clear);

        for (Patika p:Patika.getPatikaList()){

            DefaultMutableTreeNode childPatika = new DefaultMutableTreeNode(p);

            for (Course course:Course.getCourseList(p.getId())){

                DefaultMutableTreeNode childCourse = new DefaultMutableTreeNode(course);
                childPatika.add(childCourse);

            }

            top.add(childPatika);

        }

    }

    public Student getStudent() {
        return student;
    }



}
