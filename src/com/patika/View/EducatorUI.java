package com.patika.View;

import com.patika.Model.Course;
import com.patika.Model.CourseContent;
import com.patika.Model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class EducatorUI extends JFrame{
    private JPanel wrapper;
    private JPanel welcomePanel;
    private JButton logOutButton;
    private JTabbedPane educatorTabsPane;
    private JLabel welcomeText;
    private JPanel coursesPanel;
    private JScrollPane coursesScroll;
    private JTable coursesTable;
    private JTable contentsTable;
    private JComboBox<Object> coursesComboBox;
    private JButton addContentButton;
    private JButton showContentButton;
    private JPanel contentOperationsPanel;
    private JTextField textField1;
    private JButton searchContentButton;

    private DefaultTableModel coursesTableModel;
    private Object[] tempCourseRow;

    private DefaultTableModel contentTableModel;
    private Object[] tempContentRow;

    private final Educator educator;

    public EducatorUI(Educator educator){

        this.educator = educator;
        setContentPane(wrapper);
        this.setTitle("Patika.dev | Educator");
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        welcomeText.setText("Welcome, " + educator.getFullName().toUpperCase());

        // Create courses table
        coursesTableModel = new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) {

                if(column == 0){

                    return false;

                }

                return super.isCellEditable(row, column);
            }
        };

        Object[] coursesTableColumns = {"ID","Name","Patika","Programming Language"};
        coursesTableModel.setColumnIdentifiers(coursesTableColumns);
        coursesTable.getTableHeader().setReorderingAllowed(false);
        coursesTable.setModel(coursesTableModel);
        tempCourseRow = new Object[coursesTableColumns.length];
        coursesTable.getColumnModel().getColumn(0).setMaxWidth(50);
        loadCoursesTable();


        // CREATE CONTENT TABLE
        contentTableModel = new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) {

                if(column == 0){

                    return false;

                }

                return super.isCellEditable(row, column);
            }
        };

        Object[] contentTableColumns = {"ID","TITLE","INFO","YOUTUBE LINK","COURSE",""};
        contentTableModel.setColumnIdentifiers(contentTableColumns);
        contentsTable.getTableHeader().setReorderingAllowed(false);
        contentsTable.setModel(contentTableModel);
        tempContentRow = new Object[contentTableColumns.length];
        contentsTable.getColumnModel().getColumn(0).setMaxWidth(50);



        loadContentsTable((Course) Objects.requireNonNull(coursesComboBox.getSelectedItem()));


        // LOG OUT
        logOutButton.addActionListener(e -> {

            dispose();
            LoginGUI loginGUI = new LoginGUI();

        });

        //

        //  ADD CONTENT
        addContentButton.addActionListener(e -> {



        });
    }

    private void loadContentsTable(Course course) {

        DefaultTableModel clearModel = (DefaultTableModel) contentsTable.getModel();
        clearModel.setRowCount(0);

        for (CourseContent temp:CourseContent.getContent(course.getId())){

            tempCourseRow[0] = temp.getId();
            tempCourseRow[1] = temp.getTitle();
            tempCourseRow[2] = temp.getInfo();
            tempCourseRow[3] = temp.getYoutubeLink();
            if(course.getId() == temp.getCourseId()){

                tempCourseRow[4] = course.getName();

            }
            contentTableModel.addRow(tempCourseRow);

        }

    }

    private void loadCoursesTable(){

        // courseBox
        for (Course course:Course.getCourseList(educator)){

            coursesComboBox.addItem(course);

        }

        DefaultTableModel clearModel = (DefaultTableModel) coursesTable.getModel();
        clearModel.setRowCount(0);

        for (Course temp:Course.getCourseList(educator)){

            tempCourseRow[0] = temp.getId();
            tempCourseRow[1] = temp.getName();
            tempCourseRow[2] = temp.getPatika().getName();
            tempCourseRow[3] = temp.getLanguage();
            coursesTableModel.addRow(tempCourseRow);

        }

    }



    public Educator getEducator() {
        return educator;
    }
}
