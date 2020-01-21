/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beroepsproduct5.View;

import beroepsproduct5.DbConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author SebastiaanU
 */
public class TafelView extends BorderPane {

    private PlekView plekNoord, plekOost, plekZuid, plekWest;
    private final ScheduledExecutorService scheduler
            = Executors.newSingleThreadScheduledExecutor();

    private ArrayList<PlekView> plekList = new ArrayList<PlekView>();
    private ArrayList tempArray = new ArrayList<Double>();

    private DbConnector db = new DbConnector();

    public TafelView(Pane p) {
        this.setWidth(100);
        this.setHeight(100);
        this.setBorder(Border.EMPTY);

        plekNoord = new PlekView(p);
        plekOost = new PlekView(p);
        plekZuid = new PlekView(p);
        plekWest = new PlekView(p);

        plekList.add(plekNoord);
        plekList.add(plekOost);
        plekList.add(plekZuid);
        plekList.add(plekWest);

        executorService();
        this.setTop(plekNoord);
        this.setRight(plekOost);
        this.setBottom(plekZuid);
        this.setLeft(plekWest);

        p.getChildren().addAll(this);

    }

    public void executorService() {
        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                try {
                    getDbData();
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void getDbData() throws SQLException {
        ResultSet result = null;
        String strSQL = "select  producttemp.temperatuur\n"
                + "                    from producttemp\n"
                + "                    join prodinbestel on prodinbestel.id = producttemp.prodinbestel_id\n"
                + "                    join bestellingen on bestellingen.bestelNr = prodinbestel.bestellingen_bestelNr\n"
                + "                    join producten on prodinbestel.producten_productId = producten.productId\n"
                + "                    where bestellingen.tafels_tafelNr = 1\n"
                + "                     order by producttemp.tijd DESC, prodinbestel.plek asc\n"
                + "                     \n"
                + "                    limit 4\n";
        result = db.getData(strSQL);

        while (result.next()) {

            //int plek = Integer.parseInt(result.getString("plek"));
            double temperatuur = Double.parseDouble(result.getString("temperatuur"));
            tempArray.add(temperatuur);

        }

        for (int i = 0; i < plekList.size(); i++) {
            if (i == 0) {
                System.out.println(tempArray.get(i).toString());
                Color color = getTempcolor((double) tempArray.get(i));
                plekList.get(i).setCircleColor(color);
                this.setTop(plekList.get(i));
            } else if (i == 1) {
                System.out.println(tempArray.get(i).toString());
                Color color = getTempcolor((double) tempArray.get(i));
                plekList.get(i).setCircleColor(color);
                this.setRight(plekList.get(i));
            } else if (i == 2) {
                System.out.println(tempArray.get(i).toString());
                Color color = getTempcolor((double) tempArray.get(i));
                plekList.get(i).setCircleColor(color);
                this.setBottom(plekList.get(i));
            } else if (i == 3) {
                System.out.println(tempArray.get(i).toString());
                Color color = getTempcolor((double) tempArray.get(i));
                plekList.get(i).setCircleColor(color);
                this.setLeft(plekList.get(i));
            }
        }
        System.out.println("--------");
        tempArray.clear();
    }

    private Color getTempcolor(double temp) {
        Color color = null;

        if (temp > 65 && temp < 70) {
            color = Color.LIME;
            return color;
        } else if (temp > 60 && temp < 65) {
            color = Color.GREENYELLOW;
            return color;
        } else if (temp > 55 && temp < 60) {
            color = Color.YELLOWGREEN;
            return color;
        } else if (temp > 50 && temp < 55) {
            color = Color.YELLOW;
            return color;
        } else if (temp > 45 && temp < 50) {
            color = Color.GOLD;
            return color;
        } else if (temp > 40 && temp < 45) {
            color = Color.ORANGE;
            return color;
        } else if (temp > 26 && temp < 40) {
            color = Color.ORANGERED;
            return color;
        } else if (temp > 10) {
            color = Color.BLUE;
            return color;
        }
        return color;
    }
}
