package com.patika.View;

import com.patika.Helpers.DataManager;
import com.patika.Helpers.Helper;
import com.patika.Helpers.Item;
import com.patika.Model.Course;
import com.patika.Model.Operator;
import com.patika.Model.Patika;
import com.patika.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class OperatorUI extends JFrame{

    private final Operator operator;

    private JPanel wrapper;
    private JTabbedPane usersTab;
    private JPanel welcomePanel;
    private JLabel welcomeLabel;
    private JButton logOutButton;
    private JPanel usersPanel;
    private JScrollPane userListScroll;
    private JTable userListTable;
    private JPanel userFormPanel;
    private JTextField fullNameInputForm;
    private JTextField usernameInputForm;
    private JPasswordField passwordInputForm;
    private JComboBox usertypesComboBox;
    private JButton addUserButton;
    private JTextField userIDInputForm;
    private JButton deleteUserButton;
    private JPanel searchPanel;
    private JTextField searchByNameInput;
    private JTextField searchByUsernameInput;
    private JComboBox searchByUserTypeComboBox;
    private JButton searchButton;
    private JPanel patikaPanel;
    private JScrollPane patikaListScroll;
    private JTable patikaListTable;
    private JPanel addPatikaPanel;
    private JTextField patikaNameInput;
    private JButton addPatikaButton;
    private JPanel coursePanel;
    private JScrollPane courseListScroll;
    private JTable courseListTable;
    private JPanel addCoursePanel;
    private JTextField courseNameInput;
    private JTextField languageInput;
    private JComboBox coursePatikaComboBox;
    private JComboBox courseEducatorComboBox;
    private JButton addCourseButton;
    private DefaultTableModel userListTableModel;
    private Object[] tempRow;

    private DefaultTableModel patikaListTableModel;
    private Object[] tempPatikaRow;

    private JPopupMenu patikaMenu;

    private DefaultTableModel courseListTableModel;
    private Object[] tempCourseRow;

    public OperatorUI(Operator operator) {


        this.operator = operator;
        this.setContentPane(wrapper);
        this.setTitle("Patika.dev | Operator");
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        welcomeLabel.setText("WELCOME, " + operator.getFullName().toUpperCase());
        logOutButton.setFocusPainted(false);
        usersTab.setFocusable(false);


        //Table Operations

        // User Table
        userListTableModel = new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) {

                if(column == 0){

                    return false;

                }

                return super.isCellEditable(row, column);
            }
        };
        Object[] userListTableColumns = {"ID","FULL NAME","USERNAME","PASSWORD","USER TYPE"};
        userListTableModel.setColumnIdentifiers(userListTableColumns);

        tempRow = new Object[userListTableColumns.length];

        userListTable.setModel(userListTableModel);
        userListTable.getTableHeader().setReorderingAllowed(false);
        loadUserTable();
        //

        // Patika Table

        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem deleteMenu = new JMenuItem("Delete");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {

            int selectedId = Integer.parseInt(patikaListTable.getValueAt(patikaListTable.getSelectedRow(),0).toString());
            UpdatePatikaGUI updatePatikaGUI = new UpdatePatikaGUI(Patika.getPatikaByID(selectedId));

            updatePatikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaTable();
                    loadCourseTable();
                }
            });

        });


        deleteMenu.addActionListener(e -> {

            int selectedId = Integer.parseInt(patikaListTable.getValueAt(patikaListTable.getSelectedRow(),0).toString());
            if(Patika.deletePatika(selectedId)){

                loadPatikaTable();
                loadCourseTable();
                JOptionPane.showMessageDialog(null,"Patika is deleted successfully.","Information",JOptionPane.INFORMATION_MESSAGE);

            }else{

                JOptionPane.showMessageDialog(null,"Operation is not completed.","Patika Update",JOptionPane.ERROR_MESSAGE);

            }


        });

        patikaListTableModel = new DefaultTableModel();
        Object[] patikaListTableColumns = {"ID","NAME"};
        patikaListTableModel.setColumnIdentifiers(patikaListTableColumns);

        tempPatikaRow = new Object[patikaListTableColumns.length];



        patikaListTable.setModel(patikaListTableModel);
        patikaListTable.setComponentPopupMenu(patikaMenu);
        patikaListTable.getTableHeader().setReorderingAllowed(false);
        patikaListTable.getColumnModel().getColumn(0).setMaxWidth(50);
        loadPatikaTable();
        //

        // Courses Table
        courseListTableModel = new DefaultTableModel();
        Object[] courseListTableColumns = {"ID", "NAME", "PROGRAMMING LANGUAGE", "PATIKA", "EDUCATOR"};
        courseListTableModel.setColumnIdentifiers(courseListTableColumns);
        tempCourseRow = new Object[courseListTableColumns.length];

        courseListTable.setModel(courseListTableModel);
        courseListTable.getColumnModel().getColumn(0).setMaxWidth(50);
        courseListTable.getTableHeader().setReorderingAllowed(false);

        loadCourseTable();
        //




        patikaListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = patikaListTable.rowAtPoint(point);
                patikaListTable.setRowSelectionInterval(selectedRow,selectedRow);

            }
        });


        userListTable.getModel().addTableModelListener(e ->{

            if(e.getType() == TableModelEvent.UPDATE){

                int userID = Integer.parseInt(userListTable.getValueAt(userListTable.getSelectedRow(),0).toString());
                String fullName = userListTable.getValueAt(userListTable.getSelectedRow(),1).toString();
                String username = userListTable.getValueAt(userListTable.getSelectedRow(),2).toString();
                String password = userListTable.getValueAt(userListTable.getSelectedRow(),3).toString();
                String usertype = userListTable.getValueAt(userListTable.getSelectedRow(),4).toString();

                if(User.updateUser(userID,fullName,username,password,usertype)){

                    JOptionPane.showMessageDialog(null,"User is updated successfully.","User Update",JOptionPane.INFORMATION_MESSAGE);
                    loadUserTable();

                }else{

                    JOptionPane.showMessageDialog(null,"Operation is not completed.","User Update",JOptionPane.ERROR_MESSAGE);
                    loadUserTable();
                }

            }



        });


        addUserButton.addActionListener(e -> {

            if(fullNameInputForm.getText().isEmpty() || usernameInputForm.getText().isEmpty() || passwordInputForm.getPassword().length == 0){

                JOptionPane.showMessageDialog(null,"Please fill all fields.","Error",JOptionPane.ERROR_MESSAGE);

            }else{

               if(User.addUser(fullNameInputForm.getText(),usernameInputForm.getText(), String.valueOf(passwordInputForm.getPassword()), Objects.requireNonNull(usertypesComboBox.getSelectedItem()).toString())){

                   JOptionPane.showMessageDialog(null,"User is added successfully.","Information",JOptionPane.INFORMATION_MESSAGE);
                   fullNameInputForm.setText(null);
                   usernameInputForm.setText(null);
                   passwordInputForm.setText(null);
                   usertypesComboBox.setSelectedIndex(0);
                   loadUserTable();


               }else{

                   JOptionPane.showMessageDialog(null,"Operation is not completed. Error occurred.","Error",JOptionPane.ERROR_MESSAGE);

               }

            }



        });


        deleteUserButton.addActionListener(e -> {

            if(userIDInputForm.getText().isEmpty()){

                JOptionPane.showMessageDialog(null,"Please fill all fields.","Error",JOptionPane.ERROR_MESSAGE);

            }else{

                int parsedID = 0;

                if(Helper.isParsable(userIDInputForm.getText().trim())){

                    parsedID = Integer.parseInt(userIDInputForm.getText().trim());

                }

                if (User.deleteUser(parsedID)){

                    JOptionPane.showMessageDialog(null,"User is deleted successfully.","Information",JOptionPane.INFORMATION_MESSAGE);
                    loadUserTable();
                    loadCourseTable();

                }else{

                    JOptionPane.showMessageDialog(null,"User is not found.","Error",JOptionPane.ERROR_MESSAGE);

                }

                userIDInputForm.setText(null);


            }

        });



        this.setVisible(true);

        searchButton.addActionListener(e -> {

            String full_name = searchByNameInput.getText();
            String user_name = searchByUsernameInput.getText();
            String user_type = Objects.requireNonNull(searchByUserTypeComboBox.getSelectedItem()).toString();

            if(user_type.equals("Choose a type...")){

                user_type = "";

            }

            String query = User.createQueryForSearch(full_name,user_name,user_type);
            ArrayList<User> searchResult = User.searchUserList(query);
            loadUserTable(searchResult);

            searchByNameInput.setText(null);
            searchByUsernameInput.setText(null);
            searchByUserTypeComboBox.setSelectedIndex(0);

        });


        logOutButton.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        }
        );


        addPatikaButton.addActionListener(e -> {

            if(patikaNameInput.getText().isEmpty()){

                JOptionPane.showMessageDialog(null,"Please fill all fields.","Error",JOptionPane.ERROR_MESSAGE);


            }else{

                if(Patika.addPatika(patikaNameInput.getText())){

                    JOptionPane.showMessageDialog(null,"Patika is added successfully.","Information",JOptionPane.INFORMATION_MESSAGE);
                    loadPatikaTable();
                    patikaNameInput.setText(null);
                }

            }


        });


        addCourseButton.addActionListener(e -> {

            Item patikaItem = (Item) coursePatikaComboBox.getSelectedItem();
            Item userItem = (Item) courseEducatorComboBox.getSelectedItem();

            if(courseNameInput.getText().isEmpty() || languageInput.getText().isEmpty()){

                JOptionPane.showMessageDialog(null,"Please fill all fields.","Error",JOptionPane.ERROR_MESSAGE);

            }else{

                assert userItem != null;
                assert patikaItem != null;
                if(Course.addCourse(userItem.getId(),patikaItem.getId(),courseNameInput.getText(),languageInput.getText())){

                    JOptionPane.showMessageDialog(null,"Course is added successfully.","Information",JOptionPane.INFORMATION_MESSAGE);
                    loadCourseTable();

                }else{

                    JOptionPane.showMessageDialog(null,"Operation is not completed. Error occurred.","Error",JOptionPane.ERROR_MESSAGE);

                }

            }

        });


    }

    private void loadCourseTable() {

        DefaultTableModel clearModel = (DefaultTableModel) courseListTable.getModel();
        clearModel.setRowCount(0);

        for (Course temp:Course.getCourseList()){

            tempCourseRow[0] = temp.getId();
            tempCourseRow[1] = temp.getName();
            tempCourseRow[2] = temp.getLanguage();
            if(temp.getPatika() !=null){

                tempCourseRow[3] = temp.getPatika().getName();

            }else{

                tempCourseRow[3] = "Unknown";

            }

            if(temp.getEducator() !=null){

                tempCourseRow[4] = temp.getEducator().getFullName();
            }else{

                tempCourseRow[4] = "Unknown";

            }

            courseListTableModel.addRow(tempCourseRow);

        }

        loadEducatorComboBox();
        loadPatikaComboBox();

    }

    public void loadPatikaComboBox(){

        coursePatikaComboBox.removeAllItems();
        for (Patika patika:Patika.getPatikaList()){

            coursePatikaComboBox.addItem(new Item(patika.getId(),patika.getName()));

        }


    }

    public void loadEducatorComboBox(){

        courseEducatorComboBox.removeAllItems();
        for(User user:DataManager.getUserList()){

            if(user.getUserType().equals("educator")){

                courseEducatorComboBox.addItem(new Item(user.getId(),user.getFullName()));

            }

        }


    }

    public void loadPatikaTable() {

        DefaultTableModel clearModel = (DefaultTableModel) patikaListTable.getModel();
        clearModel.setRowCount(0);

        for (Patika temp:Patika.getPatikaList()){

            tempPatikaRow[0] = temp.getId();
            tempPatikaRow[1] = temp.getName();
            patikaListTableModel.addRow(tempPatikaRow);

        }

        loadPatikaComboBox();
        loadEducatorComboBox();

    }

    public void loadUserTable(){

        DefaultTableModel clearModel = (DefaultTableModel) userListTable.getModel();
        clearModel.setRowCount(0);

        for(int i=0;i< DataManager.getUserList().size();i++){


            tempRow[0] = DataManager.getUserList().get(i).getId();
            tempRow[1] = DataManager.getUserList().get(i).getFullName();
            tempRow[2] = DataManager.getUserList().get(i).getUsername();
            tempRow[3] = DataManager.getUserList().get(i).getPassword();
            tempRow[4] = DataManager.getUserList().get(i).getUserType();

            userListTableModel.addRow(tempRow);

        }

        loadEducatorComboBox();
        loadPatikaComboBox();


    }

    public void loadUserTable(ArrayList<User> userList){

        DefaultTableModel clearModel = (DefaultTableModel) userListTable.getModel();
        clearModel.setRowCount(0);

        for(int i=0;i< userList.size();i++){


            tempRow[0] = userList.get(i).getId();
            tempRow[1] = userList.get(i).getFullName();
            tempRow[2] = userList.get(i).getUsername();
            tempRow[3] = userList.get(i).getPassword();
            tempRow[4] = userList.get(i).getUserType();

            userListTableModel.addRow(tempRow);

        }

        loadEducatorComboBox();
        loadPatikaComboBox();

    }


    public Operator getOperator() {
        return operator;
    }
}
