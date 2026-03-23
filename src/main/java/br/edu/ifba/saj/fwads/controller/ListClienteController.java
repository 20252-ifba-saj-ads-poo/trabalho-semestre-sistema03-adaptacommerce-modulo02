package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.dao.GenericDAO;
import br.edu.ifba.saj.fwads.dao.GenericDAOImpl;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.ClienteFisico;
import br.edu.ifba.saj.fwads.model.ClienteJuridico;
import javafx.beans.property.SimpleStringProperty;
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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

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

    private GenericDAO<Cliente, UUID> dao = new GenericDAOImpl<>(Cliente.class, UUID.class);

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
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colTipo.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof ClienteFisico) {
                return new SimpleStringProperty("Física");
            } else {
                return new SimpleStringProperty("Jurídica");
            }
        });

        colDocumento.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof ClienteFisico pf) {
                return new SimpleStringProperty(pf.getCpf());
            } else if (cellData.getValue() instanceof ClienteJuridico pj) {
                return new SimpleStringProperty(pj.getCnpj());
            }
            return new SimpleStringProperty("");
        });

        colCidadeUf.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getEndereco().getCidade() + "/" + cellData.getValue().getEndereco().getUf()));

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

                if ("Nome".equals(tipoFiltro) && cliente.getNome() != null) {
                    return cliente.getNome().toLowerCase().contains(lowerCaseFilter);
                }
                if ("E-mail".equals(tipoFiltro) && cliente.getEmail() != null) {
                    return cliente.getEmail().toLowerCase().contains(lowerCaseFilter);
                }
                if ("Documento".equals(tipoFiltro)) {
                    if (cliente instanceof ClienteFisico pf && pf.getCpf() != null) {
                        return pf.getCpf().toLowerCase().contains(lowerCaseFilter);
                    } else if (cliente instanceof ClienteJuridico pj && pj.getCnpj() != null) {
                        return pj.getCnpj().toLowerCase().contains(lowerCaseFilter);
                    }
                }
                return false;
            });
        });
        tabelaClientes.setItems(listaFiltrada);
    }

    @FXML
    public void handleNovoCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CadCliente.fxml"));
            Parent root = loader.load();

            BorderPane masterPane = (BorderPane) tabelaClientes.getScene().getRoot();
            masterPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao carregar a tela de cadastro.");
        }
    }

    @FXML
    public void handleEditarCliente() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CadCliente.fxml"));
                Parent root = loader.load();

                CadClienteController controller = loader.getController();
                controller.setCliente(selecionado);

                BorderPane masterPane = (BorderPane) tabelaClientes.getScene().getRoot();
                masterPane.setCenter(root);
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro ao carregar a tela de edição.");
            }
        } else {
            mostrarAlerta("Selecione um cliente para editar.");
        }
    }

    @FXML
    public void handleApagarCliente() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            dao.deletar(selecionado.getId());
            listaMestre.remove(selecionado);
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