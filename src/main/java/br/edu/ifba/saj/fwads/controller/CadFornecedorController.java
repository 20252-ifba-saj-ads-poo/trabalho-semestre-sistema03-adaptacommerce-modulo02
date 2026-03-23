package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.Fornecedor;
import br.edu.ifba.saj.fwads.service.FornecedorService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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
    private final FornecedorService fornecedorService = new FornecedorService();

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
        txtEmail.setText(fornecedorAtual.getEmail());
        txtTelefone.setText(fornecedorAtual.getTelefone());
        txtContato.setText(fornecedorAtual.getContato());

        txtCep.setText(fornecedorAtual.getEndereco().getCep());
        txtLogradouro.setText(fornecedorAtual.getEndereco().getLogradouro());
        txtNumero.setText(fornecedorAtual.getEndereco().getNumero());
        txtComplemento.setText(fornecedorAtual.getEndereco().getComplemento());
        txtBairro.setText(fornecedorAtual.getEndereco().getBairro());
        txtCidade.setText(fornecedorAtual.getEndereco().getCidade());
        txtUf.setText(fornecedorAtual.getEndereco().getUf());
    }

    @FXML
    public void handleSalvar() {
        try {
            if (fornecedorAtual == null) {
                fornecedorAtual = new Fornecedor();
            }
            fornecedorAtual.setRazaoSocial(txtRazaoSocial.getText());
            fornecedorAtual.setNomeFantasia(txtNomeFantasia.getText());
            fornecedorAtual.setCnpj(txtCnpj.getText());
            fornecedorAtual.setIe(txtIe.getText());
            fornecedorAtual.setEmail(txtEmail.getText());
            fornecedorAtual.setTelefone(txtTelefone.getText());
            fornecedorAtual.setContato(txtContato.getText());

            fornecedorAtual.getEndereco().setCep(txtCep.getText());
            fornecedorAtual.getEndereco().setLogradouro(txtLogradouro.getText());
            fornecedorAtual.getEndereco().setNumero(txtNumero.getText());
            fornecedorAtual.getEndereco().setComplemento(txtComplemento.getText());
            fornecedorAtual.getEndereco().setBairro(txtBairro.getText());
            fornecedorAtual.getEndereco().setCidade(txtCidade.getText());
            fornecedorAtual.getEndereco().setUf(txtUf.getText());

            if (this.isEditMode) {
                fornecedorService.atualizar(fornecedorAtual);
            } else {
                fornecedorService.salvar(fornecedorAtual);
            }

            voltarParaLista();

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Validação");
            alert.setHeaderText("Não foi possível salvar o fornecedor.");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListFornecedor.fxml"));
            Parent root = loader.load();
            BorderPane masterPane = (BorderPane) txtRazaoSocial.getScene().getRoot();
            masterPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}