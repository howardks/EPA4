/**
 * File: csci1302/ch16/MileageCalculator.java
 * Package: ch16
 * @author Christopher Williams
 * Created on: Apr 12, 2017
 * Edited by: Kaylie Howard 
 * Last Modified: Nov 15, 2021
 * Description:  Modify MileageCalculatorNoConversion into PAssign09 and practice with github. 
 */
package ch16;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PAssign09 extends Application { // Task 1: Create a second branch and rename class to PAssign09
	// default values/strings
    private double txtWidth = 125.0;
    private String defaultCalc = String.format("%.2f", 0.00);
    private String defaultEntry = String.format("%.2f", 0.00);
    private String defaultMileage = "Miles";
    private String defaultCapacity = "Gallons";
    private String defaultResult = "MPG";
    private String altMileage = "Kilometers";
    private String altCapacity = "Liters";
    private String altResult = "L/100KM";
    
    // create UI components split by type
    private Button btnCalc = new Button("Calculate");
    private Button btnReset = new Button("Reset");
    
    private Label lblDistance = new Label(defaultMileage);
    private Label lblCapacity = new Label(defaultCapacity);
    private Label lblResult = new Label(defaultResult);
    private Label lblEffType = new Label("Efficiency Type");
    
    private TextField tfDistance = new TextField(defaultEntry);
    private TextField tfCapacity = new TextField(defaultEntry);
    private TextField tfResult = new TextField(defaultCalc);
    
     
    ObservableList<String> items = FXCollections.observableArrayList(defaultResult, altResult);
    
    private ComboBox<String> cmbType = new ComboBox<>(items);
    
    private GridPane mainPane = new GridPane();
    
    public void start(Stage primaryStage) {  
    	
        // set preferences for UI components
        tfDistance.setMaxWidth(txtWidth);
        tfCapacity.setMaxWidth(txtWidth);
        tfResult.setMaxWidth(txtWidth);
        tfResult.setEditable(false);
        
        // create a main grid pane to hold items
        mainPane.setPadding(new Insets(10.0));
        mainPane.setHgap(txtWidth/2.0);
        mainPane.setVgap(txtWidth/12.0);
        
        // add items to mainPane
        mainPane.add(lblEffType, 0, 0);
        // Task 1
        mainPane.add(cmbType, 1, 0);
        // The empty first option was annoying me. 
        cmbType.getSelectionModel().selectFirst();
        
        mainPane.add(lblDistance, 0, 2);
        mainPane.add(tfDistance, 1, 2);
        mainPane.add(lblCapacity, 0, 3);
        mainPane.add(tfCapacity, 1, 3);
        mainPane.add(lblResult, 0, 4);
        mainPane.add(tfResult, 1, 4);
        mainPane.add(btnReset, 0, 5);
        mainPane.add(btnCalc, 1, 5);
        
        // register action handlers
        btnCalc.setOnAction(e -> calcMileage());
        tfDistance.setOnAction(e -> calcMileage());
        tfCapacity.setOnAction(e -> calcMileage());
        tfResult.setOnAction(e -> calcMileage());   
        btnReset.setOnAction(e -> resetForm());
        
        cmbType.valueProperty().addListener(ov -> convert());
        cmbType.setOnAction(e -> changeLabels());
        
        // create a scene and place it in the stage
        Scene scene = new Scene(mainPane); 
        
        // set and show stage
        primaryStage.setTitle("Mileage Calculator"); 
        primaryStage.setScene(scene); 
        primaryStage.show();      
        
        // stick default focus in first field for usability
        tfDistance.requestFocus();
    }
    
    /**
     * Convert existing figures and recalculate
     * This needs to be separate to avoid converting when
     * the conversion is not necessary
     */
    private void changeLabels() {
    	// distinguish between L/100KM and MPG and update labels if necessary
    	if (cmbType.getValue().equals(altResult)) {
    		// Metric
    		lblCapacity.setText(altCapacity);
        	lblDistance.setText(altMileage);
        	lblResult.setText(altResult); 
    	} else {
    		// Imperial
    		lblCapacity.setText(defaultCapacity);
        	lblDistance.setText(defaultMileage);
        	lblResult.setText(defaultResult);
    	}
    }
    
    private void convert() {
    	double distance = 0.0, capacity = 0.0;
    	
    	if (tfCapacity.getText() != null && !tfCapacity.getText().isEmpty()
        		&& tfDistance.getText() != null && !tfDistance.getText().isEmpty()) {
        	distance = Double.parseDouble(tfDistance.getText());
            capacity = Double.parseDouble(tfCapacity.getText());
        }
    	
    	if (cmbType.getValue().equals(altResult)) {
    		// convert imperial to metric
    		distance *= 1.60934;
    		tfDistance.setText(String.format("%.2f", distance));
    		capacity *= 3.78541;
    		tfCapacity.setText(String.format("%.2f", capacity));
    		calcMileage();
    	} else {
    		// convert metric to imperial
    		distance *= 0.621371;
    		tfDistance.setText(String.format("%.2f", distance));
    		capacity *= 0.264172;
    		tfCapacity.setText(String.format("%.2f", capacity));
    		calcMileage();
    	}
    }
    
    /**
     * Calculate expenses based on entered figures
     */
    private void calcMileage() {       
    	// set default values
        double distance = 0.0, capacity = 0.0;
        
        // make sure to get numeric values only
        if (tfCapacity.getText() != null && !tfCapacity.getText().isEmpty()
        		&& tfDistance.getText() != null && !tfDistance.getText().isEmpty()) {
        	distance = Double.parseDouble(tfDistance.getText());
            capacity = Double.parseDouble(tfCapacity.getText());
        }

        // check for type of calculation
        double result = 0.0;
        
        if (cmbType.getValue().equals(altResult)) {
        	// Metric
        	result = (distance != 0) ? capacity/(distance/100.0) : 0;
        } else {
        	// Imperial
        	result = (capacity != 0) ? distance/capacity : 0; 
        }
    
	    // update calculation fields with currency formatting
        tfResult.setText(String.format("%.2f", result));
    }
    
    /**
     * Reset all values in the application
     */
    private void resetForm() {
        // reset all form fields
    	cmbType.getSelectionModel().selectFirst();
        tfDistance.setText(defaultEntry);
        tfCapacity.setText(defaultEntry);
        tfResult.setText(defaultCalc);
        lblCapacity.setText(defaultCapacity);
    	lblDistance.setText(defaultMileage);
    	lblResult.setText(defaultResult);
    }
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
