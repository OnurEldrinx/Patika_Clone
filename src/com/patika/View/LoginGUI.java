package com.patika.View;

import com.patika.Helpers.Helper;
import com.patika.Model.Educator;
import com.patika.Model.Operator;
import com.patika.Model.Student;
import com.patika.Model.User;
import javax.swing.*;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel topWrapper;
    private JPanel bottomWrapper;
    private JTextField usernameLoginInput;
    private JPasswordField passwordLoginInput;
    private JButton loginButton;


    public LoginGUI(){

        setContentPane(wrapper);
        this.setTitle("Patika.dev | Login");
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        loginButton.addActionListener(e -> {

            if(usernameLoginInput.getText().isEmpty() || passwordLoginInput.getPassword().length == 0){

                JOptionPane.showMessageDialog(null,"Please fill the empty fields!","Error",JOptionPane.ERROR_MESSAGE);

            }else{

                User u = User.getUser(usernameLoginInput.getText(), String.valueOf(passwordLoginInput.getPassword()));
                if(u == null){

                    JOptionPane.showMessageDialog(null,"User is not found!","Error",JOptionPane.ERROR_MESSAGE);
                    usernameLoginInput.setText(null);
                    passwordLoginInput.setText(null);

                }else{

                    switch (u.getUserType()){

                        case "operator":
                            Operator currentOperator = new Operator();
                            currentOperator.setId(u.getId());
                            currentOperator.setFullName(u.getFullName());
                            currentOperator.setUsername(u.getUsername());
                            currentOperator.setPassword(u.getPassword());
                            currentOperator.setUserType(u.getUserType());
                            OperatorUI operatorUI = new OperatorUI(currentOperator);
                            break;
                        case "educator":
                            Educator currentEducator = new Educator();
                            currentEducator.setId(u.getId());
                            currentEducator.setFullName(u.getFullName());
                            currentEducator.setUsername(u.getUsername());
                            currentEducator.setPassword(u.getPassword());
                            currentEducator.setUserType(u.getUserType());
                            EducatorUI educatorUI = new EducatorUI(currentEducator);
                            break;
                        case "student":
                            Student currentStudent = new Student();
                            currentStudent.setId(u.getId());
                            currentStudent.setFullName(u.getFullName());
                            currentStudent.setUsername(u.getUsername());
                            currentStudent.setPassword(u.getPassword());
                            currentStudent.setUserType(u.getUserType());
                            StudentUI studentUI = new StudentUI(currentStudent);
                            break;


                    }

                    dispose();

                }

            }

        });
    }

    public static void main(String[] args) {

        Helper.setLayout("Nimbus");
        LoginGUI loginGUI = new LoginGUI();

    }

}
