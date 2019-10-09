/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beroepsproduct5.View;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author Jandri
 */
public class MachineLearning extends GridPane{
    private Text    txtChooseDrink;     //text for combobox.
    private Text    txtDrinkPlaceInfo;  //text for place of the drink
    private Text    txtDrinkMl;         //text for machinelearning about drink
    private ComboBox cbChooseDrink;     //combobox for selecting a drink
    
    public MachineLearning(Pane p){
        txtChooseDrink = new Text("Selecteer de specifieke drank");
        txtDrinkPlaceInfo = new Text("Specifieke plaats van de drank");
        txtDrinkMl = new Text("Machine learning");
        cbChooseDrink = new ComboBox();
        
        add(txtChooseDrink,0,0);
        add(txtDrinkPlaceInfo,0,1);
        add(txtDrinkMl,0,2);
        add(cbChooseDrink,0,3);
        
        p.getChildren().addAll(this);
		
        
        
        
        setPadding(new Insets(10, 10, 10, 10));
		setVgap(5);
		setHgap(5);
                
        
    }
    
}