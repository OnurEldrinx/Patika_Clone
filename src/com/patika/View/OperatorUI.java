package com.patika.View;

import com.patika.Helpers.DataManager;
import com.patika.Helpers.Helper;
import com.patika.Model.Operator;
import com.patika.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private DefaultTableModel userListTableModel;
    private Object[] tempRow;

    public OperatorUI(Operator operator) {


        this.operator = operator;
        this.setContentPane(wrapper);
        this.setTitle("Patika.dev | Operator");
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        welcomeLabel.setText("WELCOME, " + operator.getFullName().toUpperCase());
        logOutButton.setFocusPainted(false);
        usersTab.setFocusable(false);


        //Table Operations

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
        refreshTable();

        userListTable.setModel(userListTableModel);
        userListTable.getTableHeader().setReorderingAllowed(false);


        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(fullNameInputForm.getText().isEmpty() || usernameInputForm.getText().isEmpty() || passwordInputForm.getPassword().length == 0){

                    JOptionPane.showMessageDialog(null,"Please fill all fields.","Error",JOptionPane.ERROR_MESSAGE);

                }else{

                   if(User.addUser(fullNameInputForm.getText(),usernameInputForm.getText(), String.valueOf(passwordInputForm.getPassword()), Objects.requireNonNull(usertypesComboBox.getSelectedItem()).toString())){

                       JOptionPane.showMessageDialog(null,"User is added successfully.","Information",JOptionPane.INFORMATION_MESSAGE);
                       fullNameInputForm.setText(null);
                       usernameInputForm.setText(null);
                       passwordInputForm.setText(null);
                       usertypesComboBox.setSelectedIndex(0);
                       refreshTable();


                   }else{

                       JOptionPane.showMessageDialog(null,"Operation is not completed. Error occurred.","Error",JOptionPane.ERROR_MESSAGE);

                   }

                }



            }
        });


        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(userIDInputForm.getText().isEmpty()){

                    JOptionPane.showMessageDialog(null,"Please fill all fields.","Error",JOptionPane.ERROR_MESSAGE);

                }else{

                    int parsedID = 0;

                    if(Helper.isParsable(userIDInputForm.getText().trim())){

                        parsedID = Integer.parseInt(userIDInputForm.getText().trim());

                    }

                    if (Integer.TYPE.isInstance(parsedID) && User.deleteUser(parsedID)){

                        JOptionPane.showMessageDialog(null,"User is deleted successfully.","Information",JOptionPane.INFORMATION_MESSAGE);
                        refreshTable();

                    }else{

                        JOptionPane.showMessageDialog(null,"User is not found.","Error",JOptionPane.ERROR_MESSAGE);

                    }

                    /*if(User.deleteUser(parsedID)){

                        JOptionPane.showMessageDialog(null,"User is deleted successfully.","Information",JOptionPane.INFORMATION_MESSAGE);
                        refreshTable();

                    }else{

                        JOptionPane.showMessageDialog(null,"User is not found.","Error",JOptionPane.ERROR_MESSAGE);

                    }*/

                    userIDInputForm.setText(null);


                }

            }
        });



        this.setVisible(true);

    }

    public void refreshTable(){

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

    }


    public Operator getOperator() {
        return operator;
    }
}
