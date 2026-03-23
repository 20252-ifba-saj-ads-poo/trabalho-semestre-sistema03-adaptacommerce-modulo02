package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.dao.GenericDAO;
import br.edu.ifba.saj.fwads.dao.GenericDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import br.edu.ifba.saj.fwads.model.Fornecedor;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

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

    private GenericDAO<Fornecedor, UUID> dao = new GenericDAOImpl<>(Fornecedor.class, UUID.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        configurarPesquisa();
        carregarDados();
    }

    private void carregarDados() {
        listaMestre.clear();
        listaMestre.addAll(dao.buscarTodos());
    }

    private void configurarColunas() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colIe.setCellValueFactory(new PropertyValueFactory<>("ie"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colCidadeUf.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getEndereco().getCidade() + "/" + cellData.getValue().getEndereco().getUf()));

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

                if ("Nome".equals(tipoFiltro) && fornecedor.getNomeFantasia() != null) {
                    return fornecedor.getNomeFantasia().toLowerCase().contains(lowerCaseFilter);
                }
                if ("CNPJ".equals(tipoFiltro) && fornecedor.getCnpj() != null) {
                    return fornecedor.getCnpj().contains(lowerCaseFilter);
                }
                if ("E-mail".equals(tipoFiltro) && fornecedor.getEmail() != null) {
                    return fornecedor.getEmail().toLowerCase().contains(lowerCaseFilter);
                }
                return false;
            });
        });
        tabelaFornecedores.setItems(listaFiltrada);
    }

    @FXML
    public void handleNovoFornecedor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CadFornecedor.fxml"));
            Parent root = loader.load();

            BorderPane masterPane = (BorderPane) tabelaFornecedores.getScene().getRoot();
            masterPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao carregar a tela de cadastro.");
        }
    }

    @FXML
    public void handleEditarFornecedor() {
        Fornecedor selecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CadFornecedor.fxml"));
                Parent root = loader.load();

                CadFornecedorController controller = loader.getController();
                controller.setFornecedor(selecionado);

                BorderPane masterPane = (BorderPane) tabelaFornecedores.getScene().getRoot();
                masterPane.setCenter(root);
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro ao carregar a tela de edição.");
            }
        } else {
            mostrarAlerta("Selecione um fornecedor para editar.");
        }
    }

    @FXML
    public void handleApagarFornecedor() {
        Fornecedor selecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            dao.deletar(selecionado.getId());
            listaMestre.remove(selecionado);
        } else {
            mostrarAlerta("Selecione um fornecedor para apagar.");
        }
    }

    @FXML
    public void handleMaisInformacoes() {
        Fornecedor selecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InforFornecedor.fxml"));
                Parent root = loader.load();

                InforFornecedorController controller = loader.getController();
                controller.setFornecedor(selecionado);

                BorderPane masterPane = (BorderPane) tabelaFornecedores.getScene().getRoot();
                masterPane.setCenter(root);
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro ao carregar a tela de informações.");
            }
        } else {
            mostrarAlerta("Selecione um fornecedor para ver os detalhes.");
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}