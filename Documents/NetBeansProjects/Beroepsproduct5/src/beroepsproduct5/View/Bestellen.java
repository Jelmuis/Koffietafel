/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beroepsproduct5.View;

import Beroepsproduct5.Model.Bestelling;
import Beroepsproduct5.Model.ProductInBestel;
import beroepsproduct5.DbConnector;
import java.sql.ResultSet;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author Jandri
 */
public class Bestellen extends GridPane {

    private Text lblDrank, lblTafel, lblPlek, lblBestelling, lblApproved; //
    private ComboBox<String> cbxDrank, cbxPlek, cbxTafel;//, cbxBestelling; //
//    private ChoiceBox<String> ;
    private Button btnOpslaan;//, btnNew;

    public Bestellen(Pane p) {
        // padding
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(5);
        setHgap(5);

        // text
        lblTafel = new Text("Tafel Nummer");
        lblDrank = new Text("Drank");
        lblPlek = new Text("Plek");
//                lblBestel = new Text ("Bestel Nummer");
        lblApproved = new Text("Uw bestelling is gemaakt");
        lblBestelling = new Text("Bestelnummer");

        lblApproved.setVisible(false);
        lblApproved.setFill(Color.RED);

        // Combobox
        cbxTafel = new ComboBox<String>();
        cbxTafel.getItems().addAll("1", "2", "3", "4");
        cbxDrank = new ComboBox<String>();
        cbxDrank.getItems().addAll();
        cbxPlek = new ComboBox<String>();
        cbxPlek.getItems().addAll(); //"1", "2", "3", "4"
        //cbxBestelling = new ComboBox<String>();
        //cbxBestelling.getItems().addAll();

        // Value van de boxes
        cbxTafel.setValue("Kies Tafel");
        cbxDrank.setValue("Kies Drank");
        cbxPlek.setValue("Kies Plek");
        //cbxBestelling.setValue("Kies Bestel #"); 

        // button & event
//        btnNew = new Button("Bestelling");
//        btnNew.setOnAction(event -> {
//            Bestelling nieuwBestelling = new Bestelling();
//            nieuwBestelling.setTafelnummer(Integer.parseInt(cbxTafel.getValue()));
//            
//             DbConnector db = new DbConnector();
//            String strSQL = "insert into bestellingen (tafels_tafelNr) VALUES ('"+  nieuwBestelling.getTafelnummer() +"')";
//            int result = db.executeDML(strSQL);
//        });
        btnOpslaan = new Button("Opslaan");
        btnOpslaan.setOnAction(event -> {
            Bestelling nieuwBestelling = new Bestelling();
            nieuwBestelling.setTafelnummer(Integer.parseInt(cbxTafel.getValue()));
            ProductInBestel nieuwProductInBestel = new ProductInBestel();
            //nieuwProductInBestel.setBestelId(Integer.parseInt(cbxBestelling.getValue()));
//            nieuwProductInBestel.setProductId(Integer.parseInt(cbxDrank.getValue()));
            nieuwProductInBestel.setPlek(Integer.parseInt(cbxPlek.getValue()));

//                        
            DbConnector db = new DbConnector();
                    String strSQL1 = "INSERT INTO bestellingen (tafels_tafelNr)values ('" + nieuwBestelling.getTafelnummer() + "')";
                    db.executeDML(strSQL1);
            ResultSet result = null;
            try {
                String strSQL2 = "select max(bestelNr) as maxBestel from bestellingen";
                result = db.getData(strSQL2);
                while (result.next()) {
                    String TIJDELIJKBESTEL = result.getString("maxBestel");
                    String strSQL3 = "insert into prodinbestel (bestellingen_bestelNr,producten_productId,plek,tijdPlaatsing) values (" + TIJDELIJKBESTEL + ",(select productId from producten where naam ='"  + cbxDrank.getValue()  +  "')," + cbxPlek.getValue() +  ",now())";
                            //+ "insert into prodinbestel (bestellingen_bestelNr,producten_productId,plek,tijdPlaatsing) values (" + TIJDELIJKBESTEL + "," +  cbxDrank.getValue() +  "," + cbxPlek.getValue() +  ",now())";

                    db.executeDML(strSQL3);
                }
            } catch (Exception e) {
                System.out.println(e);

            }

//			
            lblApproved.setVisible(true);
        });
        // gridpane layout

        // add(btnOpslaan, 1, 0);
        add(lblDrank, 0, 3);
        add(cbxDrank, 1, 3);
        add(lblPlek, 0, 2);
        add(cbxPlek, 1, 2);
        add(lblTafel, 0, 1);
        add(cbxTafel, 1, 1);
        //add(cbxBestelling, 1, 4);

        add(btnOpslaan, 1, 8);
        add(lblApproved, 0, 8);

        vuldeCombo1();
        vuldeCombo2();
        // vuldeCombo3();

        // mainPane = root
        p.getChildren().add(this);
    }

    //methode om de combobox te vullen
    public void vuldeCombo1() {
        DbConnector db = new DbConnector();
        ResultSet result = null;
        try {
            String strSQL = "select * from producten";
            result = db.getData(strSQL);
            while (result.next()) {
                String productId = result.getString("naam");
                cbxDrank.getItems().add(productId);
            }
        } catch (Exception e) {

        }
    }

    public void vuldeCombo2() {
        DbConnector db = new DbConnector();
        ResultSet result = null;
        try {
            String strSQL = "select plek from prodinbestel";
            result = db.getData(strSQL);
            while (result.next()) {
                String plek = result.getString("plek");
                cbxPlek.getItems().add(plek);
            }
        } catch (Exception e) {

        }
    }

//        public void vuldeCombo3() {
//		DbConnector db = new DbConnector();
//		ResultSet result = null;
//		try {
//			String strSQL = "select bestelNr from bestellingen";
//			result = db.getData(strSQL);
//			while (result.next()) {
//				String bestelNr = result.getString("bestelNr");
//				cbxBestelling.getItems().add(bestelNr);
//			}
//		} catch (Exception e) {
//
//		}
//	}
}
