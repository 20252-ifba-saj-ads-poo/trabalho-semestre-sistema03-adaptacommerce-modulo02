package br.edu.ifba.saj.fwads.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import br.edu.ifba.saj.fwads.model.Fornecedor;

public class CadFornecedorController {

    @FXML
    private TextField txtRazaoSocial;
    @FXML
    private TextField txtNomeFantasia;
    @FXML
    private TextField txtCnpj;
    @FXML
    private TextField txtIe;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtContato;

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

    private Fornecedor fornecedorAtual;
    private boolean isEditMode = false;

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedorAtual = fornecedor;
        this.isEditMode = true;
        preencherCampos();
    }

    private void preencherCampos() {
        if (fornecedorAtual == null)
            return;
        txtRazaoSocial.setText(fornecedorAtual.getRazaoSocial());
        txtNomeFantasia.setText(fornecedorAtual.getNomeFantasia());
        txtCnpj.setText(fornecedorAtual.getCnpj());
        txtIe.setText(fornecedorAtual.getIe());
        // Preencher demais campos...
    }

    @FXML
    public void handleSalvar() {
        if (!validarCampos())
            return;

        if (fornecedorAtual == null) {
            fornecedorAtual = new Fornecedor();
        }

        fornecedorAtual.setRazaoSocial(txtRazaoSocial.getText());
        fornecedorAtual.setCnpj(txtCnpj.getText());
        // Setar demais campos...

        System.out.println("Salvando: " + fornecedorAtual.getRazaoSocial());
        fecharJanela();
    }

    @FXML
    public void handleCancelar() {
        fecharJanela();
    }

    private boolean validarCampos() {
        return true;
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtRazaoSocial.getScene().getWindow();
        stage.close();
    }
}