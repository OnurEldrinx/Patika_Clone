package com.patika.View;

import com.patika.Model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePatikaGUI extends JFrame{
    private JPanel wrapper;
    private JTextField patikaUpdateInput;
    private JButton updateButton;
    private final Patika patika;

    public UpdatePatikaGUI(Patika patika){

        this.patika = patika;
        setContentPane(wrapper);
        setSize(300,150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Update");
        setResizable(false);
        setVisible(true);

        patikaUpdateInput.setText(patika.getName());


        updateButton.addActionListener(e -> {

            if(patikaUpdateInput.getText().isEmpty()){

                JOptionPane.showMessageDialog(null,"Please fill all fields.","Error",JOptionPane.ERROR_MESSAGE);

            }else{

                if(Patika.updatePatika(patika.getId(),patikaUpdateInput.getText())){

                    JOptionPane.showMessageDialog(null,"Patika is updated successfully.","Patika Update",JOptionPane.INFORMATION_MESSAGE);

                }

                dispose();
            }

        });


    }

    public Patika getPatika() {
        return patika;
    }
}
