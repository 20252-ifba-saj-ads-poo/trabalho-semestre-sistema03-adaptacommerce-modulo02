package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.ClienteFisico;
import br.edu.ifba.saj.fwads.model.ClienteJuridico;
import br.edu.ifba.saj.fwads.service.ClienteService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CadClienteController implements Initializable {

    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtCep;
    @FXML
    private TextField txtLogradouro;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtComplemento;
    @FXML
    private TextField txtBairro;
    @FXML
    private TextField txtCidade;
    @FXML
    private TextField txtUf;

    private Cliente clienteAtual;
    private boolean isEditMode = false;
    private final ClienteService clienteService = new ClienteService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbTipo.getItems().addAll("Pessoa Física", "Pessoa Jurídica");
        cbTipo.getSelectionModel().selectFirst();
    }

    public void setCliente(Cliente cliente) {
        this.clienteAtual = cliente;
        this.isEditMode = true;
        preencherCampos();
    }

    private void preencherCampos() {
        if (clienteAtual == null)
            return;
        txtNome.setText(clienteAtual.getNome());
        txtEmail.setText(clienteAtual.getEmail());
        txtTelefone.setText(clienteAtual.getTelefone());

        if (clienteAtual instanceof ClienteFisico pf) {
            cbTipo.setValue("Pessoa Física");
            txtDocumento.setText(pf.getCpf());
            cbTipo.setDisable(true);
        } else if (clienteAtual instanceof ClienteJuridico pj) {
            cbTipo.setValue("Pessoa Jurídica");
            txtDocumento.setText(pj.getCnpj());
            cbTipo.setDisable(true);
        }

        txtCep.setText(clienteAtual.getEndereco().getCep());
        txtLogradouro.setText(clienteAtual.getEndereco().getLogradouro());
        txtNumero.setText(clienteAtual.getEndereco().getNumero());
        txtComplemento.setText(clienteAtual.getEndereco().getComplemento());
        txtBairro.setText(clienteAtual.getEndereco().getBairro());
        txtCidade.setText(clienteAtual.getEndereco().getCidade());
        txtUf.setText(clienteAtual.getEndereco().getUf());
    }

    @FXML
    public void handleSalvar() {
        try {
            if (clienteAtual == null) {
                if ("Pessoa Física".equals(cbTipo.getValue())) {
                    clienteAtual = new ClienteFisico();
                } else {
                    clienteAtual = new ClienteJuridico();
                }
            }
            clienteAtual.setNome(txtNome.getText());
            clienteAtual.setEmail(txtEmail.getText());
            clienteAtual.setTelefone(txtTelefone.getText());

            if (clienteAtual instanceof ClienteFisico pf) {
                pf.setCpf(txtDocumento.getText());
            } else if (clienteAtual instanceof ClienteJuridico pj) {
                pj.setCnpj(txtDocumento.getText());
            }

            clienteAtual.getEndereco().setCep(txtCep.getText());
            clienteAtual.getEndereco().setLogradouro(txtLogradouro.getText());
            clienteAtual.getEndereco().setNumero(txtNumero.getText());
            clienteAtual.getEndereco().setComplemento(txtComplemento.getText());
            clienteAtual.getEndereco().setBairro(txtBairro.getText());
            clienteAtual.getEndereco().setCidade(txtCidade.getText());
            clienteAtual.getEndereco().setUf(txtUf.getText());

            if (this.isEditMode) {
                clienteService.atualizar(clienteAtual);
            } else {
                clienteService.salvar(clienteAtual);
            }

            voltarParaLista();

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Validação");
            alert.setHeaderText("Não foi possível salvar o cliente.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void handleCancelar() {
        voltarParaLista();
    }

    private void voltarParaLista() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListCliente.fxml"));
            Parent root = loader.load();
            BorderPane masterPane = (BorderPane) txtNome.getScene().getRoot();
            masterPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}