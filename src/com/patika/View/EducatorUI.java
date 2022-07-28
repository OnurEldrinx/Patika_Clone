package com.patika.View;

import com.patika.Model.Course;
import com.patika.Model.CourseContent;
import com.patika.Model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Locale;

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
    private JTextField searchTitleInput;
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

        Object[] contentTableColumns = {"ID","TITLE","INFO","YOUTUBE LINK","COURSE"};
        contentTableModel.setColumnIdentifiers(contentTableColumns);
        contentsTable.getTableHeader().setReorderingAllowed(false);
        contentsTable.setModel(contentTableModel);
        tempContentRow = new Object[contentTableColumns.length];
        contentsTable.getColumnModel().getColumn(0).setMaxWidth(50);



        //loadContentsTable((Course) Objects.requireNonNull(coursesComboBox.getSelectedItem()));


        // LOG OUT
        logOutButton.addActionListener(e -> {

            dispose();
            LoginGUI loginGUI = new LoginGUI();

        });

        //

        //  ADD CONTENT
        addContentButton.addActionListener(e -> {

            Course selected = (Course)coursesComboBox.getSelectedItem();

            AddContentGUI addContentGUI = new AddContentGUI(selected);

            addContentGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadContentsTable(selected);
                }
            });


        });
        showContentButton.addActionListener(e -> {

            Course selected = (Course)coursesComboBox.getSelectedItem();
            assert selected != null;
            loadContentsTable(selected);

        });

        searchContentButton.addActionListener(e -> {

            String s = searchTitleInput.getText().trim().toLowerCase();

            loadContentsTable(CourseContent.search(s));


        });
    }


    private void loadContentsTable(ArrayList<CourseContent> list){

        DefaultTableModel clearModel = (DefaultTableModel) contentsTable.getModel();
        clearModel.setRowCount(0);

        for (CourseContent temp:list){

            tempContentRow[0] = temp.getId();
            tempContentRow[1] = temp.getTitle();
            tempContentRow[2] = temp.getInfo();
            tempContentRow[3] = temp.getYoutubeLink();
            tempContentRow[4] = Course.fetchCourse(temp.getCourseId()).getName();

            contentTableModel.addRow(tempContentRow);

        }

    }

    private void loadContentsTable(Course course) {

        DefaultTableModel clearModel = (DefaultTableModel) contentsTable.getModel();
        clearModel.setRowCount(0);

        for (CourseContent temp:CourseContent.getContent(course.getId())){

            tempContentRow[0] = temp.getId();
            tempContentRow[1] = temp.getTitle();
            tempContentRow[2] = temp.getInfo();
            tempContentRow[3] = temp.getYoutubeLink();
            if(course.getId() == temp.getCourseId()){

                tempContentRow[4] = course.getName();

            }
            contentTableModel.addRow(tempContentRow);

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
