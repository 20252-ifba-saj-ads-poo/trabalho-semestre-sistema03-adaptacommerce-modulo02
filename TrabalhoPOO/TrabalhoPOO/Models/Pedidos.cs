using System;

namespace TrabalhoPOO.Models
{
    public class Pedido
    {
        public int Id { get; set; }
        public DateTime Data { get; set; }
        public decimal Valor { get; set; }
        public string Status { get; set; }
    }
}