package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.Fornecedor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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

    @FXML
    private TableView<PedidoMock> tabelaPedidos;
    @FXML
    private TableColumn<PedidoMock, String> colPedNumero;
    @FXML
    private TableColumn<PedidoMock, String> colPedData;
    @FXML
    private TableColumn<PedidoMock, String> colPedValor;
    @FXML
    private TableColumn<PedidoMock, String> colPedStatus;

    @FXML
    private TableView<HistoricoMock> tabelaHistorico;
    @FXML
    private TableColumn<HistoricoMock, String> colHistData;
    @FXML
    private TableColumn<HistoricoMock, String> colHistProduto;
    @FXML
    private TableColumn<HistoricoMock, String> colHistQtd;
    @FXML
    private TableColumn<HistoricoMock, String> colHistNotaFiscal;

    @FXML
    public void initialize() {
        configurarTabelaPedidos();
        configurarTabelaHistorico();
    }

    public void setFornecedor(Fornecedor f) {
        if (f == null)
            return;

        lblNome.setText(f.getNomeFantasia());
        lblRazao.setText(f.getRazaoSocial());
        lblCnpj.setText(f.getCnpj());
        lblEmail.setText(f.getEmail());

        // Formata o endereço acessando a classe Endereco
        String enderecoCompleto = String.format("%s, %s - %s, %s/%s",
                f.getEndereco().getLogradouro(),
                f.getEndereco().getNumero(),
                f.getEndereco().getBairro(),
                f.getEndereco().getCidade(),
                f.getEndereco().getUf());
        lblEndereco.setText(enderecoCompleto);

        carregarDadosFicticios();
    }

    private void configurarTabelaPedidos() {
        colPedNumero.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().numero));
        colPedData.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().dataPrevista));
        colPedValor.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().valor));
        colPedStatus.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().status));
    }

    private void configurarTabelaHistorico() {
        colHistData.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().dataChegada));
        colHistProduto.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().produto));
        colHistQtd.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().quantidade));
        colHistNotaFiscal.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().notaFiscal));
    }

    private void carregarDadosFicticios() {
        ObservableList<PedidoMock> pedidos = FXCollections.observableArrayList(
                new PedidoMock("PED-1001", "15/10/2025", "R$ 4.500,00", "Aguardando Faturamento"),
                new PedidoMock("PED-1045", "22/10/2025", "R$ 1.250,50", "Em Transporte"));
        tabelaPedidos.setItems(pedidos);

        ObservableList<HistoricoMock> historico = FXCollections.observableArrayList(
                new HistoricoMock("10/08/2025", "Cimento Votorantim 50kg", "200 sc", "NF-9980"),
                new HistoricoMock("05/07/2025", "Tijolo 8 Furos", "5000 un", "NF-8541"));
        tabelaHistorico.setItems(historico);
    }

    @FXML
    public void handleFechar() {
        Stage stage = (Stage) lblNome.getScene().getWindow();
        stage.close();
    }

    // Classes internas de Mock apenas para preencher a visualização das tabelas
    public static class PedidoMock {
        String numero, dataPrevista, valor, status;

        public PedidoMock(String numero, String dataPrevista, String valor, String status) {
            this.numero = numero;
            this.dataPrevista = dataPrevista;
            this.valor = valor;
            this.status = status;
        }
    }

    public static class HistoricoMock {
        String dataChegada, produto, quantidade, notaFiscal;

        public HistoricoMock(String dataChegada, String produto, String quantidade, String notaFiscal) {
            this.dataChegada = dataChegada;
            this.produto = produto;
            this.quantidade = quantidade;
            this.notaFiscal = notaFiscal;
        }
    }
}