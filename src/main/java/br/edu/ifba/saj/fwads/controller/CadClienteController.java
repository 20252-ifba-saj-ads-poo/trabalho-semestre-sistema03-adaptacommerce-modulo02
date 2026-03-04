package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.ClienteFisico;
import br.edu.ifba.saj.fwads.model.ClienteJuridico;
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

        // Verifica qual é o tipo do objeto para preencher o ComboBox e o Documento
        if (clienteAtual instanceof ClienteFisico pf) {
            cbTipo.setValue("Pessoa Física");
            txtDocumento.setText(pf.getCpf());
        } else if (clienteAtual instanceof ClienteJuridico pj) {
            cbTipo.setValue("Pessoa Jurídica");
            txtDocumento.setText(pj.getCnpj());
        }

        // Acessando o objeto Endereco (Composição)
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
        if (txtNome.getText().isEmpty()) {
            System.out.println("Nome é obrigatório");
            return;
        }

        // Se for um novo cadastro, decide qual classe instanciar
        if (clienteAtual == null) {
            if ("Pessoa Física".equals(cbTipo.getValue())) {
                clienteAtual = new ClienteFisico();
            } else {
                clienteAtual = new ClienteJuridico();
            }
        }

        // Preenche os dados básicos
        clienteAtual.setNome(txtNome.getText());
        clienteAtual.setEmail(txtEmail.getText());
        clienteAtual.setTelefone(txtTelefone.getText());

        // Preenche o documento fazendo a conversão para a classe correta
        if (clienteAtual instanceof ClienteFisico pf) {
            pf.setCpf(txtDocumento.getText());
        } else if (clienteAtual instanceof ClienteJuridico pj) {
            pj.setCnpj(txtDocumento.getText());
        }

        // Preenche o endereço usando getEndereco()
        clienteAtual.getEndereco().setCep(txtCep.getText());
        clienteAtual.getEndereco().setLogradouro(txtLogradouro.getText());
        clienteAtual.getEndereco().setNumero(txtNumero.getText());
        clienteAtual.getEndereco().setComplemento(txtComplemento.getText());
        clienteAtual.getEndereco().setBairro(txtBairro.getText());
        clienteAtual.getEndereco().setCidade(txtCidade.getText());
        clienteAtual.getEndereco().setUf(txtUf.getText());

        System.out.println("Salvando Cliente: " + clienteAtual.getNome());
        fecharJanela();
    }

    @FXML
    public void handleCancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
}