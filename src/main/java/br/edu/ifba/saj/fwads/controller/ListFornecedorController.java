package br.edu.ifba.saj.fwads.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import br.edu.ifba.saj.fwads.model.Fornecedor;

import java.net.URL;
import java.util.ResourceBundle;

public class ListFornecedorController implements Initializable {

    @FXML
    private TableView<Fornecedor> tabelaFornecedores;
    @FXML
    private TableColumn<Fornecedor, String> colNome;
    @FXML
    private TableColumn<Fornecedor, String> colCnpj;
    @FXML
    private TableColumn<Fornecedor, String> colIe;
    @FXML
    private TableColumn<Fornecedor, String> colEmail;
    @FXML
    private TableColumn<Fornecedor, String> colCidadeUf;

    @FXML
    private TextField txtPesquisa;
    @FXML
    private ComboBox<String> cbFiltro;

    private ObservableList<Fornecedor> listaMestre = FXCollections.observableArrayList();
    private FilteredList<Fornecedor> listaFiltrada;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        carregarDadosFicticios();
        configurarPesquisa();
    }

    private void configurarColunas() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colIe.setCellValueFactory(new PropertyValueFactory<>("ie"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colCidadeUf.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCidade() + "/" + cellData.getValue().getUf()));

        cbFiltro.getItems().addAll("Nome", "CNPJ", "E-mail");
        cbFiltro.getSelectionModel().selectFirst();
    }

    private void configurarPesquisa() {
        listaFiltrada = new FilteredList<>(listaMestre, p -> true);

        txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            listaFiltrada.setPredicate(fornecedor -> {
                if (newValue == null || newValue.isEmpty())
                    return true;

                String lowerCaseFilter = newValue.toLowerCase();
                String tipoFiltro = cbFiltro.getValue();

                if ("Nome".equals(tipoFiltro))
                    return fornecedor.getNomeFantasia().toLowerCase().contains(lowerCaseFilter);
                if ("CNPJ".equals(tipoFiltro))
                    return fornecedor.getCnpj().contains(lowerCaseFilter);
                if ("E-mail".equals(tipoFiltro))
                    return fornecedor.getEmail().toLowerCase().contains(lowerCaseFilter);

                return false;
            });
        });

        tabelaFornecedores.setItems(listaFiltrada);
    }

    @FXML
    public void handleNovoFornecedor() {
        System.out.println("Abrir tela de cadastro");
        // Lógica para abrir Stage CadFornecedor.fxml
    }

    @FXML
    public void handleEditarFornecedor() {
        Fornecedor selecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            System.out.println("Editando: " + selecionado.getNomeFantasia());
            // Lógica para abrir Stage enviando o objeto 'selecionado'
        } else {
            mostrarAlerta("Selecione um fornecedor para editar.");
        }
    }

    @FXML
    public void handleApagarFornecedor() {
        Fornecedor selecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            listaMestre.remove(selecionado);
        } else {
            mostrarAlerta("Selecione um fornecedor para apagar.");
        }
    }

    @FXML
    public void handleMaisInformacoes() {
        Fornecedor selecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            System.out.println("Detalhes: " + selecionado.getNomeFantasia());
        } else {
            mostrarAlerta("Selecione um fornecedor.");
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void carregarDadosFicticios() {
        // Implementar carga de dados
    }
}