/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beroepsproduct5.View;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author SebastiaanU
 */
public class TafelView extends BorderPane {

    private PlekView plekNoord, plekOost, plekZuid, plekWest;
    private Rectangle filler;

    public TafelView(Pane p) {

        this.setWidth(100);
        this.setHeight(100);
        this.setBorder(Border.EMPTY);

        plekNoord = new PlekView();
        plekOost = new PlekView();
        plekZuid = new PlekView();
        plekWest = new PlekView();
        filler = new Rectangle(30,30, Color.BLACK);
        
        this.setTop(plekNoord);
        this.setRight(plekOost);
        this.setBottom(plekZuid);
        this.setLeft(plekWest);
        //this.setCenter(filler);
        

        p.getChildren().addAll(this);

    }


}
