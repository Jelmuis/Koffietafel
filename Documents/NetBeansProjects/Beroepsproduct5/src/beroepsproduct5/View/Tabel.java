package beroepsproduct5.View;


import beroepsproduct5.DbConnector;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.text.Font;

/**
 *
 * @author Jandri
 */
public class Tabel extends GridPane {

    private DbConnector dbConnector = new DbConnector();
    private String plektemp[] = new String[4];
    private String plekdrank[] = new String[4];
    private int plek[] = new int[4];
    private ArrayList<Series> graph = new ArrayList<Series>();
    private double temp1, temp2, temp3, temp4;
    private String drank1, drank2, drank3, drank4;

    private boolean coffeeStartMeasureExecuted = false;
    private boolean cappStartMeasureExecuted = false;
    private boolean teaStartMeasureExecuted = false;

    private boolean coffeeStopMeasureExecuted = false;
    private boolean cappStopMeasureExecuted = false;
    private boolean teaStopMeasureExecuted = false;

    private double coffeeMinTemp = 30;
    private double teaMinTemp = 25;
    private double cappMinTemp = 30;

    private double coffeeMaxTemp = 60;
    private double teaMaxTemp = 80;
    private double cappMaxTemp = 60;

    private Date startMeasureCoffeeTime;
    private Date startMeasureCappTime;
    private Date startMeasureTeaTime;

    private Date stopMeasureCoffeeTime;
    private Date stopMeasureCappTime;
    private Date stopMeasureTeaTime;
    
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private LineChart lineChart;
    
    private Text filling; 
    private Text title;

    public Tabel(Pane p) {
        //adding the chart
        filling = new Text ("                                                ");
        title = new Text("                     Temperatuur overzicht");
        title.setFont(Font.font("Verdana",20));
        
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        xAxis.setLabel("Minuten");
        xAxis.setAnimated(true); //axis animations are removed
        yAxis.setLabel("Temperatuur");
        yAxis.setAnimated(true); //axis animations are removed

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Realtime JavaFx Charts");
        lineChart.setAnimated(true); //disable animations

        //defining a graphPlek1 to display data
        XYChart.Series<String, Number> graphPlek1 = new XYChart.Series<>();
        graphPlek1.setName("Plek 1");

        XYChart.Series<String, Number> graphPlek2 = new XYChart.Series<>();
        graphPlek2.setName("Plek 2");

        XYChart.Series<String, Number> graphPlek3 = new XYChart.Series<>();
        graphPlek3.setName("Plek 3");

        XYChart.Series<String, Number> graphPlek4 = new XYChart.Series<>();
        graphPlek4.setName("Plek 4");

        graph.add(graphPlek1);
        graph.add(graphPlek2);
        graph.add(graphPlek3);
        graph.add(graphPlek4);

        //add Series to chart
        lineChart.getData().add(graphPlek1);
        lineChart.getData().add(graphPlek2);
        lineChart.getData().add(graphPlek3);
        lineChart.getData().add(graphPlek4);

        // this is used to display time in HH:mm:ss format
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        // setup a scheduled executor to periodically put data into the chart
        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10

            getDataFromDatabaseAndCheckTemp();

            // Update the chart
            Platform.runLater(() -> {
                // get current time
                Date now = new Date();

                //Get data for update table later on.
                //temp is for linechart
                //drank(x) is for calculating mintemp
                temp1 = Double.parseDouble(plektemp[0]);
                drank1 = plekdrank[0];

                System.out.println("Plek 1: " + temp1);

                temp2 = Double.parseDouble(plektemp[1]);
                drank2 = plekdrank[1];
                System.out.println("Plek 2: " + plektemp[1]);

                temp3 = Double.parseDouble(plektemp[2]);
                drank3 = plekdrank[2];
                System.out.println("Plek 3: " + plektemp[2]);

                temp4 = Double.parseDouble(plektemp[3]);
                drank4 = plekdrank[3];
                System.out.println("Plek 4: " + plektemp[3]);

                System.out.println("/n");
                System.out.println("------------------------");

                //update table
                graphPlek1.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), temp1));
                graphPlek2.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), temp2));
                graphPlek3.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), temp3));
                graphPlek4.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), temp4));
            });
        }, 0, 1, TimeUnit.MINUTES);
            
            this.add(filling,0,1);
            this.add(title,1,0);
            this.add(lineChart,1,1);                                      
            setPadding(new Insets(10, 10, 10, 10));
            setVgap(10);
            setHgap(10);
            p.getChildren().addAll(this);  
    }

    private void getDataFromDatabaseAndCheckTemp() {
        ResultSet result = null;
        try {
            String strSQL
                    = "select prodinbestel.plek, producttemp.temperatuur, producten.naam\n"
                    + "from producttemp\n"
                    + "join prodinbestel on prodinbestel.id = producttemp.prodinbestel_id\n"
                    + "join bestellingen on bestellingen.bestelNr = prodinbestel.bestellingen_bestelNr\n"
                    + "join producten on prodinbestel.producten_productId = producten.productId\n"
                    + "where bestellingen.tafels_tafelNr = 1\n"
                    + "order by producttemp.tijd DESC, prodinbestel.plek asc\n"
                    + "limit 4";

            result = dbConnector.getData(strSQL);
            int i = 0;
            while (result.next()) {
                String temp = result.getString("temperatuur");
                String dranksoort = result.getString("naam");
                String plekNr = result.getString("plek");

                plektemp[i] = (temp);
                plekdrank[i] = (dranksoort);
                plek[i] = Integer.parseInt(plekNr);

                System.out.println(plekdrank[i]);

                if (plekdrank[i].equals("koffie")) {                                                                                                            //Wanneer dranktype = koffie
                    if (Double.parseDouble(temp) > coffeeMaxTemp) {                                                                                             //Kijk wanneer huidige temperatuur hoger is dan start temperatuur voor meting
                        if (!coffeeStartMeasureExecuted) {                                                                                                       //Wanneer deze boolean false is (nog niet uitgevoerd)
                            startMeasureCoffeeTime = new Date();                                                                                                //Maak een nieuwe date + time aan (voor tijd startmoment meting)
                            coffeeStartMeasureExecuted = true;                                                                                                  //Zet boolean op uitgevoerd(true)
                        } else if (Double.parseDouble(temp) < coffeeMinTemp) {                                                                                  //Blijf doormeten totdat de temperatuur zakt onder de minimale temperatuur. Wanneer dit zo is:
                            if (!coffeeStopMeasureExecuted) {                                                                                                   //Wanneer boolean false is (nog niet uitgevoerd)
                                stopMeasureCoffeeTime = new Date();                                                                                             //Maak een nieuwe date + time aan (voor tijd stopmoment meting)
                                long diffInMinutesCoffee = ((startMeasureCoffeeTime.getTime() - stopMeasureCoffeeTime.getTime() / 1000) / 60);                  //bereken verschil tusssen start en stop in minuten
                                System.out.println("Koffie op plek " + plek[i] + " is afgekoeld in: " + diffInMinutesCoffee + " minuten.");
                                coffeeStopMeasureExecuted = true;                                                                                               //Zet boolean op uitgevoerd (true)
                            }
                        }                                                                                                                                       //Stappen zijn voor alle dranken hetzelfde.
                    }

                } else if (plekdrank[i] == "cappuccino") {
                    if (Double.parseDouble(temp) > cappMaxTemp) {
                        if (!cappStartMeasureExecuted) {
                            startMeasureCappTime = new Date();
                            coffeeStartMeasureExecuted = true;
                        } else if (Double.parseDouble(temp) < cappMinTemp) {
                            if (!cappStopMeasureExecuted) {
                                stopMeasureCappTime = new Date();
                                long diffInMinutesCapp = ((startMeasureCappTime.getTime() - stopMeasureCappTime.getTime() / 1000) / 60);
                                System.out.println("Cappuccino op plek " + plek[i] + " is afgekoeld in: " + diffInMinutesCapp + " minuten.");
                                cappStopMeasureExecuted = true;
                            }
                        }
                    }
                } else if (plekdrank[i] == "thee") {
                    if (Double.parseDouble(temp) > teaMaxTemp) {
                        if (!teaStartMeasureExecuted) {
                            startMeasureTeaTime = new Date();
                            teaStartMeasureExecuted = true;
                        } else if (Double.parseDouble(temp) < teaMinTemp) {
                            if (!teaStopMeasureExecuted) {
                                stopMeasureTeaTime = new Date();
                                long diffInMinutesTea = ((startMeasureTeaTime.getTime() - stopMeasureTeaTime.getTime() / 1000) / 60);
                                System.out.println("Tea has cooled down in: " + diffInMinutesTea + " minutes.");
                                teaStopMeasureExecuted = true;
                            }
                        }
                    }

                }
                i++;
                if (i == 4) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    }