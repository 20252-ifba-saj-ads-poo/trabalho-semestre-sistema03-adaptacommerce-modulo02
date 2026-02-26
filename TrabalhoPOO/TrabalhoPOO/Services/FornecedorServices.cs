using System.Collections.Generic;
using System.Linq;
using TrabalhoPOO.Models;

namespace TrabalhoPOO.Services
{
    public class FornecedorService
    {
        private List<Fornecedor> fornecedores = new List<Fornecedor>();

        public void AdicionarFornecedor(Fornecedor fornecedor)
        {
            fornecedores.Add(fornecedor);
        }

        public Fornecedor BuscarPorCNPJ(string cnpj)
        {
            return fornecedores.FirstOrDefault(f => f.CNPJ == cnpj);
        }

        public List<Fornecedor> ListarTodos()
        {
            return fornecedores;
        }
    }
}