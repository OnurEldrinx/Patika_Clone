package com.patika.View;

import com.patika.Model.Educator;

import javax.swing.*;

public class EducatorUI extends JFrame{
    private JPanel wrapper;

    private final Educator educator;

    public EducatorUI(Educator educator){

        this.educator = educator;
        setContentPane(wrapper);
        this.setTitle("Patika.dev | Educator");
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    public Educator getEducator() {
        return educator;
    }
}
