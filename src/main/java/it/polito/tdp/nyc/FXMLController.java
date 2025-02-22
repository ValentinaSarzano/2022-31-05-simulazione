/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.nyc.model.City;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.Vicino;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="cmbQuartiere"
    private ComboBox<City> cmbQuartiere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML // fx:id="clQuartiere"
    private TableColumn<Vicino, String> clQuartiere; // Value injected by FXMLLoader
 
    @FXML // fx:id="clDistanza"
    private TableColumn<Vicino, Double> clDistanza; // Value injected by FXMLLoader
    
    @FXML // fx:id="tblQuartieri"
    private TableView<Vicino> tblQuartieri; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String provider = cmbProvider.getValue();
    	if(provider == null) {
    		txtResult.appendText("ERRORE: Selezionare prima un provider dal menu a tendina!\n");
    	}
    	this.model.creaGrafo(provider);
    	cmbQuartiere.getItems().clear();
    	cmbQuartiere.getItems().addAll(this.model.getVertici());
        btnAdiacenti.setDisable(false);
        
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("#VERTICI: "+ this.model.nVertici()+"\n");
    	txtResult.appendText("#ARCHI: "+ this.model.nArchi());
		
    }

    @FXML
    void doQuartieriAdiacenti(ActionEvent event) {
    	txtResult.clear();
    	City quartiere = cmbQuartiere.getValue();
    	if(quartiere == null) {
    		txtResult.appendText("ERRORE: Selezionare prima un quartiere dal menu a tendina!\n");
    	}
    	btnCreaLista.setDisable(false);
    	List<Vicino> adiacenti = new ArrayList<>(this.model.getAdiacenti(quartiere));
    	//Creo un ObservableList
    	tblQuartieri.setItems(FXCollections.observableArrayList(adiacenti));
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	City quartiere = cmbQuartiere.getValue();
    	if(quartiere == null) {
    		txtResult.appendText("ERRORE: Selezionare prima un quartiere dal menu a tendina!\n");
    	}
    	int N = 0;
    	try {
    		N = Integer.parseInt(txtMemoria.getText());
    	}catch(NumberFormatException e) {
    		txtResult.appendText("ERRORE: Inserire un numero N intero!\n");
    	}
    	this.model.doSimula(N, quartiere);
    	List<Integer> revisionati = new ArrayList<>(this.model.getRevisionati());
    	for(Integer i: revisionati) {
    		txtResult.appendText("Tecnico "+ revisionati.indexOf(i) + " - #Hotspot revisionati: " + i + "\n");
    	}
    	txtResult.appendText("Durata totale del processo: "+ this.model.getDurataTot());

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbQuartiere != null : "fx:id=\"cmbQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clDistanza != null : "fx:id=\"clDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clQuartiere != null : "fx:id=\"clQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";

        //Tra virgolette vanno messi i nomi assegnati agli attributi della classe Vicino che mi interessa stampare sulla table
        clQuartiere.setCellValueFactory(new PropertyValueFactory<Vicino, String>("quartiere"));
		clDistanza.setCellValueFactory(new PropertyValueFactory<Vicino, Double>("distanza"));
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbProvider.getItems().addAll(this.model.getProviders());
        btnAdiacenti.setDisable(true);
        btnCreaLista.setDisable(true);
    }

}
