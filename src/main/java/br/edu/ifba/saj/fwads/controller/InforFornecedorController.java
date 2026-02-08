package br.edu.ifba.saj.fwads.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import br.edu.ifba.saj.fwads.model.Fornecedor;

public class InforFornecedorController {

    @FXML
    private Label lblNome;
    @FXML
    private Label lblRazao;
    @FXML
    private Label lblCnpj;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblEndereco;

    public void setFornecedor(Fornecedor f) {
        if (f == null)
            return;

        lblNome.setText(f.getNomeFantasia());
        lblRazao.setText(f.getRazaoSocial());
        lblCnpj.setText(f.getCnpj());
        lblEmail.setText(f.getEmail());

        String enderecoCompleto = String.format("%s, %s - %s, %s/%s",
                f.getLogradouro(), f.getNumero(), f.getBairro(), f.getCidade(), f.getUf());
        lblEndereco.setText(enderecoCompleto);
    }

    @FXML
    public void handleFechar() {
        Stage stage = (Stage) lblNome.getScene().getWindow();
        stage.close();
    }
}