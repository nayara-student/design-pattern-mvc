
package javafx.mvc;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.mvc.controller.LoginController;
import javafx.mvc.controller.PrincipalController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Nayara
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PrincipalController.class.getResource(
                "/javafx/mvc/view/Principal.fxml"));
        Parent root = loader.load();        
        Scene scene = new Scene(root);
        scene.getStylesheets()
                .add(this.getClass().getResource("estilo1.css").toString());
  
        PrincipalController c = loader.getController();
        c.setStagePrincipal(primaryStage);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sistema xyz");
        primaryStage.show();
        
        /// Abre a tela para validar o Login
        Stage login = new Stage();
        FXMLLoader loaderLogin = new FXMLLoader();
        loaderLogin.setLocation(
                LoginController.class
                        .getResource("/javafx/mvc/view/Login.fxml")
        );
        AnchorPane pageLogin = (AnchorPane) loaderLogin.load();
        login.setTitle("login");
        Scene sceneLogin = new Scene(pageLogin);

        sceneLogin.getStylesheets()
                .add(this.getClass().getResource("estilo1.css").toString());
        
        login.setScene(sceneLogin);
        
        LoginController clogin = loaderLogin.getController();
        clogin.setDialogStage(login);
        
        login.initOwner(primaryStage);
        login.initModality(Modality.APPLICATION_MODAL);
        login.showAndWait();
        
        if (!clogin.isIsAllowed()) {
            primaryStage.close();
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
