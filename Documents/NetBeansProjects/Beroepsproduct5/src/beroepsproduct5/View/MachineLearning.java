/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beroepsproduct5.View;


import Beroepsproduct5.Model.OmgevingsTemp;
import Beroepsproduct5.Model.Product;
import beroepsproduct5.DbConnector;
import java.sql.ResultSet;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author Jandri
 */
public class MachineLearning extends GridPane {
    
    final private Text txtChooseTable;          //tekst voor het selecteren tafel
    final private Text txtCurrentTempDrinks;    //tekst voor overzicht temperatuur
    final private Text txtDrink1;               //tekst voor drank 1
    final private Text txtDrink2;               //tekst voor drank 2
    final private Text txtDrink3;               //tekst voor drank 3
    final private Text txtDrink4;               //tekst voor drank 4
    final private Text txtAverageTemp;          //tekst voor gemmiddelde temperatuur dranken
    final private Text txtExpectedTemp;         //tekst voor verwachtte temperatuur dranken
    final private Text txtOutsideTemp;          //tekst voor buitentemperatuur
    
    private int drink1Current;     //integer om temperatuur drank 1 in op te slaan
    private int drink2Current;     //integer om temperatuur drank 2 in op te slaan
    private int drink3Current;     //integer om temperatuur drank 3 in op te slaan
    private int drink4Current;     //integer om temperatuur drank 4 in op te slaan
    
    private int drink1Av;          //integer om gemiddelde dranktemperatuur in op te slaan
    private int drink2Av;          //integer om gemiddelde dranktemperatuur in op te slaan
    private int drink3Av;          //integer om gemiddelde dranktemperatuur in op te slaan
    private int drink4Av;          //integer om gemiddelde dranktemperatuur in op te slaan
    
    final private ComboBox cbChooseTable;               //dropdown om tafel te selecteren
    final private Button button;
    private LineChart<Number,Number> tableLineChart;    //linechart om temperaturen dranken te visualiseren
     
    DbConnector dbConnector;
    Thread temperatureThread;
    Product product;
    OmgevingsTemp omgevingstemp;
    
    public MachineLearning(Pane p){
        temperatureThread = new Thread();
        product = new Product();
        omgevingstemp = new OmgevingsTemp();
        
        txtChooseTable = new Text("Selecteer tafel");               
        txtCurrentTempDrinks = new Text("Huidige temperaturen");
        txtDrink1 = new Text ("Drank 1: " + drink1Current +" °C");
        txtDrink2 = new Text("Drank 2: "+ drink2Current +" °C");
        txtDrink3 = new Text("Drank 3: "+ drink3Current +" °C");
        txtDrink4 = new Text("Drank 4: "+drink4Current+ " °C");
        txtAverageTemp = new Text("Gemiddelde temperatuur: ");
        txtExpectedTemp = new Text("Verwachtte tijdstip voor drank koud");
        txtOutsideTemp = new Text("Huidige buitentemperatuur: "+omgevingstemp.getTemp());

        cbChooseTable = new ComboBox();
        button = new Button(" Zoek ");
        dbConnector = new DbConnector();
        
        initiateNewTable();
        fillComboBox();
        
        

        add(txtChooseTable,0,1);                //positioneren van items binnen gridpane
        add(cbChooseTable,1,1);
        add(button,2,1);
        add(txtCurrentTempDrinks,0,2);
        add(txtDrink1,1,2);
        add(txtDrink2,1,3);
        add(txtDrink3,1,4);
        add(txtDrink4,1,5);
        add(tableLineChart,2,6);
        
        button.setOnAction(event->{
            ResultSet result = null;
            try{
                String strSQLPlek1 = "select prodinbestel.plek, producten.naam, min(producttemp.temperatuur) from prodinbestel join producten on prodinbestel.producten_productId = producten.productId join producttemp on prodinbestel.id = producttemp.prodinbestel_id join bestellingen on prodinbestel.bestellingen_bestelNr = bestellingen.bestelNr where bestellingen.tafels_tafelNr = '"+cbChooseTable.getValue()+"'  and prodinbestel.plek = 1";
                
               result = dbConnector.getData(strSQLPlek1);
                while(result.next()){
                    
                    String drink1Current = result.getString("plek");
                    String temp = result.getString("min(producttemp.temperatuur)");
                    System.out.println(drink1Current);
                    System.out.println(temp);
                    drink1Current = temp;
                }
                
            }catch (Exception e){
                System.out.println(e);
            }
      
        });
        
        p.getChildren().addAll(this);                                           //koppelen van items aan de gridpane
        
            setPadding(new Insets(10, 10, 10, 10));
		setVgap(10);
		setHgap(10);
        
    }

    private void initiateNewTable() {
         
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Tijd in minuten");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Temperatuur in C°");
        tableLineChart = new LineChart<Number,Number>(xAxis,yAxis);
        
        tableLineChart.setTitle("Overzicht temperatuurverhoudingen");
        XYChart.Series drank1 = new XYChart.Series();
        drank1.setName("Plek 1");
        drank1.getData().add(new XYChart.Data(1,80));
        drank1.getData().add(new XYChart.Data(1, 23));
        drank1.getData().add(new XYChart.Data(2, 14));
        drank1.getData().add(new XYChart.Data(3, 15));
        drank1.getData().add(new XYChart.Data(4, 24));
        drank1.getData().add(new XYChart.Data(5, 34));
        drank1.getData().add(new XYChart.Data(6, 36));
        drank1.getData().add(new XYChart.Data(7, 22));                          //to be filled with real time data
        drank1.getData().add(new XYChart.Data(8, 45));
        drank1.getData().add(new XYChart.Data(9, 43));
        drank1.getData().add(new XYChart.Data(10, 17));
        drank1.getData().add(new XYChart.Data(11, 29));
        drank1.getData().add(new XYChart.Data(12, 25));
        
        XYChart.Series drank2 = new XYChart.Series();
        drank2.setName("Plek 2");
        drank2.getData().add(new XYChart.Data(1, 95));
        drank2.getData().add(new XYChart.Data(2, 92));
        drank2.getData().add(new XYChart.Data(3, 87));
        drank2.getData().add(new XYChart.Data(4, 80));
        drank2.getData().add(new XYChart.Data(5, 75));
        drank2.getData().add(new XYChart.Data(6, 72));
        drank2.getData().add(new XYChart.Data(7, 70));
        drank2.getData().add(new XYChart.Data(8, 65));                          //to be filled with real time data
        drank2.getData().add(new XYChart.Data(9, 60));
        drank2.getData().add(new XYChart.Data(10, 58));
        drank2.getData().add(new XYChart.Data(11, 55));
        drank2.getData().add(new XYChart.Data(12, 42));
        drank2.getData().add(new XYChart.Data(13,35));
        drank2.getData().add(new XYChart.Data(14,28));
        drank2.getData().add(new XYChart.Data(15,22));
        
        XYChart.Series drank3 = new XYChart.Series();
        drank3.setName("Plek 3");
        drank3.getData().add(new XYChart.Data(0,100));
        drank3.getData().add(new XYChart.Data(1,100));
        drank3.getData().add(new XYChart.Data(2,100));
        drank3.getData().add(new XYChart.Data(3,99));
        drank3.getData().add(new XYChart.Data(4,98));
        drank3.getData().add(new XYChart.Data(5,97));
        drank3.getData().add(new XYChart.Data(6,96));
        drank3.getData().add(new XYChart.Data(7,95));
        drank3.getData().add(new XYChart.Data(8,94));                           //to be filled with real time data
        drank3.getData().add(new XYChart.Data(9,90));
        drank3.getData().add(new XYChart.Data(10,88));
        drank3.getData().add(new XYChart.Data(11,82));
        drank3.getData().add(new XYChart.Data(12,75));
        drank3.getData().add(new XYChart.Data(13,65));
        drank3.getData().add(new XYChart.Data(14,50));
        drank3.getData().add(new XYChart.Data(15,45));
        drank3.getData().add(new XYChart.Data(16,22));
        
        XYChart.Series drank4 = new XYChart.Series();
        drank4.setName("Plek 4");
        drank4.getData().add(new XYChart.Data(0,100));
        drank4.getData().add(new XYChart.Data(1,100));
        drank4.getData().add(new XYChart.Data(2,100));
        drank4.getData().add(new XYChart.Data(3,99));
        drank4.getData().add(new XYChart.Data(4,98));
        drank4.getData().add(new XYChart.Data(5,97));
        drank4.getData().add(new XYChart.Data(6,96));
        drank4.getData().add(new XYChart.Data(7,95));
        drank4.getData().add(new XYChart.Data(8,94));                           //to be filled with real time data
        drank4.getData().add(new XYChart.Data(9,82));
        drank4.getData().add(new XYChart.Data(10,75));
        drank4.getData().add(new XYChart.Data(11,72));
        drank4.getData().add(new XYChart.Data(12,70));
        drank4.getData().add(new XYChart.Data(13,60));
        drank4.getData().add(new XYChart.Data(14,55));
        drank4.getData().add(new XYChart.Data(15,50));
        drank4.getData().add(new XYChart.Data(16,37));
        
        
        
        tableLineChart.getData().add(drank1);
        tableLineChart.getData().add(drank2);
        tableLineChart.getData().add(drank3);
        tableLineChart.getData().add(drank4);
    }

    private void fillComboBox() {
            ResultSet result = null;
            try{
                String strSQL = "SELECT * FROM TAFELS";
                result = dbConnector.getData(strSQL);
                while(result.next()){
                    String tafelnummer = result.getString("Tafelnr");
                    cbChooseTable.getItems().add(tafelnummer);
                }
            }catch (Exception e){
                System.out.println(e);
            }
            
    }

  



    
}