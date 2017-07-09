/**
 * Sample Skeleton for 'Artsmia.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Model;
import it.polito.tdp.artsmia.model.Mostra;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ChoiceBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldStudenti"
    private TextField txtFieldStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleCreaGrafo(ActionEvent event) {
    	
    	Integer year = boxAnno.getValue();
    	
    	if(year==null){
    		txtResult.setText("ERRORE: Selezionare un anno.\n");
    		return;
    	}
    	
    	// creazione grafo
    	
    	model.creaGrafo(year);
    	
    	// grafo fortemente connesso?
    	
    	boolean connesso = model.isStronglyConnected();
    	
    	if(connesso==true){
    		txtResult.appendText("Il grafo generato è fortemente connesso.\n");
    	}else{
    		txtResult.appendText("Il grafo generato non è fortemente connesso.\n");
    	}
    	
    	// ricerca mostra migliore
    	
    	Mostra max = model.getmostraMigliore();
    	    	
    	txtResult.appendText(String.format("\nLa mostra con più pezzi è: %s\ncon %d oggetti esposti.\n", max.getTitle(), max.getnObjects()));

    }

    @FXML
    void handleSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtFieldStudenti != null : "fx:id=\"txtFieldStudenti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {

		this.model = model;
		
		List<Integer> years = model.loadYears();
		
		boxAnno.getItems().addAll(years);
		
	}
}
