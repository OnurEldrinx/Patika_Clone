package com.patika;

import com.patika.Helpers.DBConnector;
import com.patika.Helpers.Helper;
import com.patika.Model.Operator;
import com.patika.Model.Patika;
import com.patika.View.OperatorUI;
import com.patika.View.UpdatePatikaGUI;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {

        DBConnector.getInstance();

        Helper.setLayout("Nimbus");
        Operator operator = new Operator();
        operator.setId(1);
        operator.setFullName("Onur Öztop");
        operator.setPassword("admin");
        operator.setUsername("eldrinx");
        operator.setUserType("operator");
        OperatorUI operatorUI = new OperatorUI(operator);



    }
}
