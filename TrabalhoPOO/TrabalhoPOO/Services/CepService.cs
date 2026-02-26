using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using TrabalhoPOO.Models;

namespace TrabalhoPOO.Services
{
    public class CepService
    {
        private readonly HttpClient _httpClient = new HttpClient();

        public async Task<Endereco> BuscarEnderecoAsync(string cep)
        {
            var response = await _httpClient.GetAsync($"https://viacep.com.br/ws/{cep}/json/");

            if (!response.IsSuccessStatusCode)
                return null;

            var json = await response.Content.ReadAsStringAsync();

            var endereco = JsonSerializer.Deserialize<Endereco>(json,
                new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });

            return endereco;
        }
    }
}