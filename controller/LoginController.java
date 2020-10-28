
package javafx.mvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nayara
 */
public class LoginController implements Initializable {
    
    
    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnSair;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;


    @FXML
    void btnEntrarClick(ActionEvent event) {
        /// validar o login
        isAllowed = true;
        dialogStage.close();
    }

    @FXML
    void btnSairClick(ActionEvent event) {
        dialogStage.close();
    }
    
    private Stage dialogStage;
    private boolean isAllowed = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isIsAllowed() {
        return isAllowed;
    }
    
    
    
}
