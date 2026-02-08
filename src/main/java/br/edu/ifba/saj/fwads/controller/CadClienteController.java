package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CadClienteController implements Initializable {

    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtDocumento; // CPF ou CNPJ
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;

    // Endereço
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializa as opções do ComboBox
        cbTipo.getItems().addAll("Pessoa Física", "Pessoa Jurídica", "Outros");
    }

    public void setCliente(Cliente cliente) {
        this.clienteAtual = cliente;
        this.isEditMode = true;
        preencherCampos();
    }

    private void preencherCampos() {
        if (clienteAtual == null)
            return;

        cbTipo.setValue(clienteAtual.getTipo());
        txtNome.setText(clienteAtual.getNome());
        txtDocumento.setText(clienteAtual.getDocumento());
        txtEmail.setText(clienteAtual.getEmail());
        txtTelefone.setText(clienteAtual.getTelefone());

        txtCep.setText(clienteAtual.getCep());
        txtLogradouro.setText(clienteAtual.getLogradouro());
        txtNumero.setText(clienteAtual.getNumero());
        txtComplemento.setText(clienteAtual.getComplemento());
        txtBairro.setText(clienteAtual.getBairro());
        txtCidade.setText(clienteAtual.getCidade());
        txtUf.setText(clienteAtual.getUf());
    }

    @FXML
    public void handleSalvar() {
        if (!validarCampos())
            return;

        if (clienteAtual == null) {
            clienteAtual = new Cliente();
        }

        // Passando dados da View para o Model
        clienteAtual.setTipo(cbTipo.getValue());
        clienteAtual.setNome(txtNome.getText());
        clienteAtual.setDocumento(txtDocumento.getText());
        clienteAtual.setEmail(txtEmail.getText());
        clienteAtual.setTelefone(txtTelefone.getText());

        clienteAtual.setCep(txtCep.getText());
        clienteAtual.setLogradouro(txtLogradouro.getText());
        clienteAtual.setNumero(txtNumero.getText());
        clienteAtual.setComplemento(txtComplemento.getText());
        clienteAtual.setBairro(txtBairro.getText());
        clienteAtual.setCidade(txtCidade.getText());
        clienteAtual.setUf(txtUf.getText());

        System.out.println("Salvando Cliente: " + clienteAtual.getNome());

        fecharJanela();
    }

    @FXML
    public void handleCancelar() {
        fecharJanela();
    }

    private boolean validarCampos() {
        // Exemplo simples de validação
        if (txtNome.getText() == null || txtNome.getText().isEmpty()) {
            System.out.println("Nome é obrigatório");
            return false;
        }
        return true;
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
}