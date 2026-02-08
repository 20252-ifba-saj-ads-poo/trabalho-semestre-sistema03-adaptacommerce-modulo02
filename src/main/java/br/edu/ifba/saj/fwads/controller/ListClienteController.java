package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ListClienteController implements Initializable {

    @FXML
    private TableView<Cliente> tabelaClientes;
    @FXML
    private TableColumn<Cliente, String> colTipo;
    @FXML
    private TableColumn<Cliente, String> colNome;
    @FXML
    private TableColumn<Cliente, String> colDocumento;
    @FXML
    private TableColumn<Cliente, String> colEmail;
    @FXML
    private TableColumn<Cliente, String> colCidadeUf;

    @FXML
    private TextField txtPesquisa;
    @FXML
    private ComboBox<String> cbFiltro;

    private ObservableList<Cliente> listaMestre = FXCollections.observableArrayList();
    private FilteredList<Cliente> listaFiltrada;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        configurarPesquisa();
        // Simulação de dados (Remover quando tiver o banco)
        // listaMestre.add(new Cliente(...));
    }

    private void configurarColunas() {
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Formatação personalizada para Cidade/UF
        colCidadeUf.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getCidade() + "/" + cellData.getValue().getUf()));

        cbFiltro.getItems().addAll("Nome", "E-mail", "Documento");
        cbFiltro.getSelectionModel().selectFirst();
    }

    private void configurarPesquisa() {
        listaFiltrada = new FilteredList<>(listaMestre, p -> true);

        txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            listaFiltrada.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty())
                    return true;

                String lowerCaseFilter = newValue.toLowerCase();
                String tipoFiltro = cbFiltro.getValue();

                if ("Nome".equals(tipoFiltro))
                    return cliente.getNome().toLowerCase().contains(lowerCaseFilter);
                if ("E-mail".equals(tipoFiltro))
                    return cliente.getEmail().toLowerCase().contains(lowerCaseFilter);
                if ("Documento".equals(tipoFiltro))
                    return cliente.getDocumento().contains(lowerCaseFilter);

                return false; // Não encontrou
            });
        });

        tabelaClientes.setItems(listaFiltrada);
    }

    @FXML
    public void handleNovoCliente() {
        System.out.println("Abrindo cadastro de novo cliente...");
        // Código para abrir Stage CadCliente.fxml
    }

    @FXML
    public void handleEditarCliente() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            System.out.println("Editando cliente: " + selecionado.getNome());
            // Código para abrir Stage CadCliente.fxml passando o objeto
        } else {
            mostrarAlerta("Selecione um cliente para editar.");
        }
    }

    @FXML
    public void handleApagarCliente() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            listaMestre.remove(selecionado);
            // Remover do Banco de Dados aqui
        } else {
            mostrarAlerta("Selecione um cliente para apagar.");
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}