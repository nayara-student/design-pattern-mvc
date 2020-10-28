/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.mvc.dao.Criterios;
import javafx.mvc.dao.DaoCliente;
import javafx.mvc.model.Cliente;
import javafx.mvc.services.Conexao;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nayara
 */
public class ClienteListaController implements Initializable {

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnInserir;

    @FXML
    private Label lblCpf;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblSituacao;

    @FXML
    private TableView<Cliente> tableViewCliente;

    @FXML
    private TableColumn<?, ?> tableViewClienteCpf;

    @FXML
    private TableColumn<?, ?> tableViewClienteNome;

    @FXML
    private TextField ftNome;

    @FXML
    private TextField ftSituacao;

    @FXML
    private Button btnFiltrar;

    @FXML
    private void btnFiltrarClick(ActionEvent event) throws Exception {
        listarClientes();
    }

    @FXML
    void btnAlterarClick(ActionEvent event) throws Exception {
        Cliente cli
                = tableViewCliente.getSelectionModel()
                        .getSelectedItem();
        if (cli != null) {
            boolean okClicked = showDialog(cli);
            if (okClicked) {
                this.dc.salvar(cli);
                listarClientes();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Seleciona!!!");
            alert.show();
        }
    }
    
    private boolean showDialog(Cliente cli) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(
                ClienteEdicaoController.class
                .getResource("/javafx/mvc/view/ClienteEdicao.fxml")
        );
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edição de cliente");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        ClienteEdicaoController c = loader.getController();
        c.setDialogStage(dialogStage);
        c.setCliente(cli);
        //mostrar a tela
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();
        return c.isOkClicked();
    }
    

    @FXML
    void btnExcluirClick(ActionEvent event) throws Exception {
        Cliente cli
                = tableViewCliente.getSelectionModel()
                        .getSelectedItem();
        if (cli != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setContentText("Deseja Excluir?");
            Optional<ButtonType> opcao = confirm.showAndWait();
            if (
                    opcao.get()
                         .getButtonData()
                         .equals(ButtonBar.ButtonData.OK_DONE)
                ) {
                this.dc.remover(cli);
                listarClientes();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Seleciona!!!");
            alert.show();
        }
        
    }

    @FXML
    void btnInserirClick(ActionEvent event) throws Exception {
        Cliente cli = new Cliente();
        boolean okClicked = showDialog(cli);
        if (okClicked) {
            this.dc.salvar(cli);
            listarClientes();
        }
    }

    private DaoCliente dc;
    private List<Cliente> lista;
    private ObservableList<Cliente> listaObserver;

    private void listarClientes() throws Exception {
        tableViewClienteNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );
        tableViewClienteCpf.setCellValueFactory(
                new PropertyValueFactory<>("cnpj")
        );
        Criterios c = new Criterios("");
        if (!ftNome.getText().trim().isEmpty()) {
            String par = ftNome.getText();
            if (!par.matches("[^´~%';-_\\/\\*]+")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Caracteres inválidos!");
                alert.show();
                return;
            }
            c.setCriterio(" where nome like '%"
                    + par
                    + "%' ");
        }
        lista = (List<Cliente>) dc.getByCriterios(c);
        listaObserver = FXCollections.observableArrayList(lista);
        tableViewCliente.setItems(listaObserver);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.dc = new DaoCliente(Conexao.getInstance().getConn());
        try {
            listarClientes();
        } catch (Exception ex) {
            Logger.getLogger(ClienteListaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableViewCliente.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue)
                        -> selecionaCliente(newValue));

    }

    private void selecionaCliente(Cliente c) {
        if (c == null) {
            lblNome.setText("");
            lblCpf.setText("");
            lblSituacao.setText("");
        } else {
            lblNome.setText(c.getNome());
            lblCpf.setText(c.getCnpj());
            lblSituacao.setText(c.getSituacao());
        }
    }

}
