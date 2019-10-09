/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beroepsproduct5;

import Beroepsproduct5.View.Overzicht;
import beroepsproduct5.View.Bestellen;
import beroepsproduct5.View.MachineLearning;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;



/**
 *
 * @author SebastiaanU
 */
public class BpMenu extends MenuBar{
    Menu overzicht = new Menu("Overzicht");
    Menu bestellen = new Menu ("Bestellen");
    Menu betalen = new Menu ("Machine learning");
    MenuItem overzichtItem = new MenuItem("Overzicht");
    MenuItem bestellenItem = new MenuItem ("Bestellen");
    MenuItem betalenItem = new MenuItem ("Selecteer");
    
    public BpMenu (Pane p){
        
        overzichtItem.setOnAction(e ->{
            p.getChildren().clear();
            try {
                new Overzicht(p);
            } catch (SQLException ex) {
                Logger.getLogger(BpMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
         bestellenItem.setOnAction(e ->{
            p.getChildren().clear();
            new Bestellen (p);
        }); 
         betalenItem.setOnAction(e ->{
            p.getChildren().clear();
            new MachineLearning(p);
        });
         
        overzicht.getItems().addAll(overzichtItem);
        bestellen.getItems().addAll(bestellenItem);
        betalen.getItems().addAll(betalenItem);
       
        this.getMenus().addAll(overzicht, bestellen, betalen);
        p.getChildren().addAll(this);
    }
}
