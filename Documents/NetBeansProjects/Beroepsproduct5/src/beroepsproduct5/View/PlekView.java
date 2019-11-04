/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beroepsproduct5.View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author SebastiaanU
 */
public class PlekView extends Circle {

    private Text temperatuur;

    public PlekView() {
        temperatuur = new Text("");
        this.setRadius(10);
        this.setFill(Color.RED);
    }

}
