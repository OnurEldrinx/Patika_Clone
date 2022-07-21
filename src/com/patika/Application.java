package com.patika;

import com.patika.Helpers.DBConnector;
import com.patika.Helpers.Helper;
import com.patika.Model.Operator;
import com.patika.View.LoginGUI;
import com.patika.View.OperatorUI;


public class Application {
    public static void main(String[] args) {

        DBConnector.getInstance();
        Helper.setLayout("Nimbus");
        LoginGUI loginGUI = new LoginGUI();



    }
}
