using System.Collections.Generic;

namespace TrabalhoPOO.Models
{
    public class Fornecedor
    {
        public string Nome { get; set; }
        public string CNPJ { get; set; }
        public string InscricaoEstadual { get; set; }
        public string CEP { get; set; }
        public string Logradouro { get; set; }
        public string Bairro { get; set; }
        public string Cidade { get; set; }
        public string Estado { get; set; }

        public List<Pedido> PedidosEmAberto { get; set; }
        public List<Mercadoria> HistoricoMercadorias { get; set; }

        public Fornecedor()
        {
            PedidosEmAberto = new List<Pedido>();
            HistoricoMercadorias = new List<Mercadoria>();
        }
    }
}