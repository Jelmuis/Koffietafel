/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beroepsproduct5.View;

import beroepsproduct5.DbConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author SebastiaanU
 */
public class Overzicht extends GridPane {

    private TafelView tafelV1, tafelV2, tafelV3, tafelV4;

    public Overzicht(Pane p) {
        createTables(p);

        this.setHgap(5);
        this.setVgap(5);
        this.setPadding(new Insets(10,10,10,10));
        p.getChildren().addAll(this);
    }

    private void createTables(Pane p) {
        tafelV1 = new TafelView(p);
//        tafelV2 = new TafelView(p);
//        tafelV3 = new TafelView(p);
//        tafelV4 = new TafelView(p);
//    
       
        
        this.add(tafelV1,0,0);
//        this.add(tafelV2,2,0);
//        this.add(tafelV3,0,2);
//        this.add(tafelV4,2,2);
    }

}

