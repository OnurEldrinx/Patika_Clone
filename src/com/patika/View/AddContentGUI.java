package com.patika.View;

import com.patika.Model.Course;
import com.patika.Model.CourseContent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddContentGUI extends JFrame{
    private JPanel wrapper;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JTextField titleInput;
    private JTextField linkInput;
    private JTextField courseNameInput;
    private JTextArea infoInput;
    private JScrollPane infoScroll;
    private JButton applyButton;
    private JButton cancelButton;

    private final Course course;

    public AddContentGUI(Course course){

        this.course = course;
        setContentPane(wrapper);
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Add Content");
        //setResizable(false);
        setVisible(true);

        courseNameInput.setText(course.getName());
        courseNameInput.setEditable(false);

        cancelButton.addActionListener(e -> {


            titleInput.setText(null);
            infoInput.setText(null);
            linkInput.setText(null);
            dispose();


        });

        applyButton.addActionListener(e -> {

            if(titleInput.getText().isEmpty()){

                JOptionPane.showMessageDialog(null,"You should fill the title field at least.","Error",JOptionPane.ERROR_MESSAGE);


            }else {

                if(CourseContent.addContent(titleInput.getText(),infoInput.getText(),linkInput.getText(),course)){

                    JOptionPane.showMessageDialog(null,"The content is added successfully.",course.getName() + " Content",JOptionPane.INFORMATION_MESSAGE);


                }else{

                    JOptionPane.showMessageDialog(null,"Error occurred.","Error",JOptionPane.ERROR_MESSAGE);

                }

                titleInput.setText(null);
                infoInput.setText(null);
                linkInput.setText(null);


            }

        });
    }


    public static void main(String[] args) {

        Course course = new Course(1,1,1,"JAVA 102","JAVA");
        AddContentGUI addContentGUI = new AddContentGUI(course);

    }

    public Course getCourse() {
        return course;
    }
}
