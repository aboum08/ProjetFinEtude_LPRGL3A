package ch.makery.adress.view;


import ch.makery.adress.MainApp;
import ch.makery.adress.model.Person;
import ch.makery.adress.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {

    @FXML
    private TableView<Person> personTable;
    
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TableColumn<Person, String> firstNameColumn;
    
    @FXML
    private Label birthdayLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label postalCodeLabel;

    @FXML
    private Label streetLabel;
    
    // Reference de main application
    private MainApp mainApp;
    
    /**
     * le constructeur
     * il est appele avant la methode d'initialisation
     * 
     */
    public PersonOverviewController() {
    	
    }
    
    /**
     * initialisation du controller class il sera auto;atiquement appelle
     * apres l'execution du fichier fxml 
     */
    @FXML
    private void initialize() {
        // Initialisation de la table des personnes avec ses colonnes
        firstNameColumn.setCellValueFactory(celleData -> celleData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(celleData -> celleData.getValue().lastNameProperty());

        // Vérifiez que les éléments FXML sont bien initialisés
        if (firstNameColumn == null) {
            System.err.println("firstNameColumn est null !");
        }
        if (lastNameColumn == null) {
            System.err.println("lastNameColumn est null !");
        }
        if (personTable == null) {
            System.err.println("personTable est null !");
        }
        
        // vider les champs de person details
        showPersonDetails(null);
        
        // ecouter pour selectionner les changements et afficher les detail quant ils changent
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    
    /**
     * Il est appelle par main application pour se referer a lui meme
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
    	this.mainApp=mainApp;
    	
    	// Ajouter observable list data a table
    	personTable.setItems(mainApp.getPersonData());
    }
    
    /**
     * remplir tous les champs de person pour montrer  les details d'une personn
     * Si ses données sont null, tous les champs se renitialise
     * 
     * @param person la person ou null
     */
    private void showPersonDetails(Person person) {
    	
    	if(person !=null) {
    		// Remplir les labels avec les info pour l'objet person
    		firstnameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
            
    	}else {
    		//si les données sont nulles, vider les champs
    		firstnameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
    	}
    }
    
    /**
     * Methode qui sera appele lorque l'utilisateur cliquera sur Delete
     * 
     */
    @FXML
    private void handleDeletePerson() {
    	int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
    	if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Aucune Selection !!");
            alert.setHeaderText("Il n'y a personne selectonnée");
            alert.setContentText("Veillez selectonner une personne dans la table.");

            alert.showAndWait();
        }
    }
    
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * methode appelé quand l'utilisateur clique sur Edit. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
}
