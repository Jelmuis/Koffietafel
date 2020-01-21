/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beroepsproduct5.View;

import beroepsproduct5.DbConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author SebastiaanU
 */
public class PlekView extends StackPane {
    private final ScheduledExecutorService scheduler
            = Executors.newSingleThreadScheduledExecutor();
    private DbConnector db = new DbConnector();

    private Text temperatuur;
    private Color circleColor;
    private Circle circle = createCircle();
    private String liveTemp = "test";
    private static int R = 100;
    private static final Color lineColor = Color.GREY;

    final Line l1 = createLine(lineColor, 0, R - 0.5, 2 * R, R - 0.5);
    final Line l2 = createLine(lineColor, R - 0.5, 0, R - 0.5, 2 * R);

    public PlekView(Pane p) {
        temperatuur = new Text(liveTemp);
        temperatuur.setBoundsType(TextBoundsType.VISUAL);
        Group group = new Group(circle, l1, l2);
        this.getChildren().addAll(group, temperatuur);
        p.getChildren().addAll(this);
        temperatuur.setText(liveTemp);
        centerText(temperatuur);

    }

    public void setCircleColor(Color color){
        circle.setStroke(color);
        circle.setFill(color);
    }

    private Circle createCircle() {
        final Circle circle = new Circle(R);

        circle.setStroke(circleColor);
        circle.setStrokeWidth(10);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setFill(circleColor);
        circle.relocate(0, 1);

        return circle;
    }

    private void centerText(Text text) {
        double W = text.getBoundsInLocal().getWidth();
        double H = text.getBoundsInLocal().getHeight();
        text.relocate(R - W / 2, R - H / 2);
    }

    private Line createLine(Color lineColor, double x1, double y1, double x2, double y2) {
        Line l1 = new Line(x1, y1, x2, y2);

        l1.setStroke(lineColor);
        l1.setStrokeWidth(1);

        return l1;
    }
    

}
