package ch.makery.adress;

import java.io.File;
import java.io.IOException;
import ch.makery.adress.model.Person;
import ch.makery.adress.view.PersonEditDialogController;
import ch.makery.adress.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Application d'Enregistrement d'Adresse");

        initRootLayout();
        showPersonOverview();
    }

    // Initialisation du menu principal
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Affichage de la scène contenant le menu principal
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de RootLayout.fxml.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Erreur: rootLayout est null. Vérifiez le fichier FXML.");
            e.printStackTrace();
        }
    }

    // Afficher l'interface person details dans le principal
    private void showPersonOverview() {
        try {
        	FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
        	AnchorPane personOverview = (AnchorPane) loader.load();

            // Vérifiez si le chargement a réussi
            if (personOverview != null) {
                System.out.println("PersonOverview.fxml chargé avec succès.");
                rootLayout.setCenter(personOverview);
            } else {
                System.err.println("Le chargement de PersonOverview.fxml a échoué.");
            }

            // Vérifiez si le contrôleur est bien récupéré
            PersonOverviewController controller = loader.getController();
            if (controller != null) {
                controller.setMainApp(this);
            } else {
                System.err.println("Le contrôleur est null. Vérifiez le fichier FXML.");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de PersonOverview.fxml.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("NullPointerException : " + e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     * Ouvrir laboite de dialogue pour saisir les info de person. 
     * Si l'utilisateur valider alors les donne=ées sont conservées
     * et true est retournet.
     *
     * @param person la methode person a edite
     * @return true s'il lvalidetr(ok) .
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            //chargement du fichier fxml et créaction du stage pour la boite de dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creaction de la boite de dialog.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // relier person au controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // afficher la boite de dialog et attend qu'il le ferme
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    /**
     * Renvoie la scène principale.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
