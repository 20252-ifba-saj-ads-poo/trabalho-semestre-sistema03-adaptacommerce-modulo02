### 📝 Resumo e Nota (OK)
- **Nota Final:** 32/100
- **Visão Geral:** O projeto apresentou um excelente alinhamento com a documentação do Módulo 2 (Gestão de Cadastros de Clientes e Fornecedores), implementando o layout exato e construindo um `CepService` funcional para atender ao requisito de consultas de endereço (RNF015). Contudo, a aplicação sucumbiu tecnicamente nas exigências de arquitetura do Barema: a interface gráfica jamais foi conectada ao back-end (Code-Behind vazio), as classes não respeitam a base genérica de herança de Repositório, e os fundamentos clássicos de POO (.NET) foram subutilizados.

### 📊 Detalhamento do Barema
- **[5/20] Interface Gráfica:** A equipe construiu o layout WPF caprichado com `TabControl` separando Fornecedores, Clientes e Consultas. Ponto positivo para o design. No entanto, o `MainWindow.xaml.cs` encontra-se 100% vazio (nenhum evento de `Click`, `Loaded` ou instâncias de injetáveis). Como a view não invoca os serviços, também falha absurdamente em capturar e exibir as diretrizes e Exceptions customizadas via `MessageBox`, impossibilitando o fluxo do software.
- **[10/30] Camada de Negócio:** Valorizou-se a criação do `CepService` para consumo via `HttpClient` (O que não era o objetivo da atividade). Porém, o `FornecedorService` foi estruturado de forma muito rudimentar. Requisitos vitais como a validação do CNPJ com exatos 14 dígitos (presente na documentação) não ocorrem antes do salvamento. Além disso, não arremessando nenhuma Exceção Customizada.
- **[3/20] Camada de Dados (Repository/Modelagem):** As entidades de dados (`Fornecedor`, `Mercadoria`) carecem da generalização comum exigida (`EntityBase<TId>`), perdendo controle nato de `Id` e timestamps (`CreatedAt`). Faltou a sobrescrita essencial de igualdade de banco em memória (`GetHashCode` e `Equals(object)`). Para agravar, a arquitetura negligenciou severamente a criação de um `IRepository`, mesclando a persistência crua (`List<Fornecedor>`) como propriedade de estado global dentro do Service.
- **[10/20] Separação em Camadas:** O código foi corretamente segregado em namespaces (Models, Services, Views). O Controller/View não carrega trechos de SQL ou regras lógicas embutidas. A penalidade decorre do fato da "tríade" MVC/MVVM estar interrompida e sem intercâmbio de dados real (nada liga o View ao Service, e o Service engoliu o papel do DAO).
- **[4/10] Boas Práticas e POO:** Bom uso de nomenclaturas em C# (`PascalCase`). Entretanto, faltou domínio de encapsulamento seguro: expor `public List<Pedido> PedidosEmAberto { get; set; }` permite manipulação indevida externa. A ausência massiva do uso de abstrações e interfaces rebaixou severamente a demonstração de maturidade em POO.

### 🐛 Erros Lógicos, Arquiteturais e Execução
- **UI Desconectada:** O arquivo `MainWindow.xaml.cs` está vazio sem injetar o `FornecedorService` ou criar lógicas de salvar dados capturados nos campos de texto.
- **Service Persistindo Dados:** A "Base de Dados" temporária do software amarra-se a um atributo `private List<Fornecedor>` dentro do Serviço, fundindo as camadas Model e DAO de maneira irregular.
- **Ausência de Comparadores Fundamentais:** Sem estender um modelo de `EntityBase` que declare o critério do `Equals`, a linguagem C# tentará comparar Endereços de Referência em eventuais buscas em lista (`.Contains()`), causando potenciais falsos-negativos.

### 💡 Refatoração / Código
**1. Abstração e Herança Genérica de Model:**
Sufocando as repetições através de classes abstratas provendo Polimorfismo e identidade:
```csharp
public abstract class EntityBase<TId> : IEquatable<EntityBase<TId>>
{
    public TId Id { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }

    public override bool Equals(object? obj) => Equals(obj as EntityBase<TId>);
    
    public bool Equals(EntityBase<TId>? other) => 
        other != null && EqualityComparer<TId>.Default.Equals(Id, other.Id);
        
    public override int GetHashCode() => HashCode.Combine(Id);
}

// Em Fornecedor:
public class Fornecedor : EntityBase<Guid> 
{ 
    public string CNPJ { get; set; }
    // ...
}
```

**2. Repository Isolado e Exceções no Service:**
O Serviço impõe regras, o Repositório isolará as listas ou interações de banco:
```csharp
public class FornecedorService : ServiceBase<Fornecedor, Guid> // Exemplo usando herança
{
    // Ao injetar IRepository no super da ServiceBase, você foca apenas nas regras locais:
    public override void Adicionar(Fornecedor entity) 
    {
        if (entity.CNPJ == null || entity.CNPJ.Length != 14) 
            throw new ValidacaoNegocioException("O CNPJ deve ter exatos 14 dígitos.");
            
        _repository.Add(entity); // Repositório focado lida com o add na lista
    }
}
```

**3. Amarrando o Code-Behind à Camada Service:**
Os formulários WPF jamais poderiam ter chegado em branco ao fim do projeto, devendo tratar e prender os eventos do usuário:
```csharp
private readonly FornecedorService _fornecedorService = new FornecedorService();

private void BtnSalvar_Click(object sender, RoutedEventArgs e)
{
    try
    {
        Fornecedor f = new Fornecedor { Nome = txtNome.Text, CNPJ = txtCNPJ.Text };
        _fornecedorService.Adicionar(f); // Repassa para o negócio
        
        MessageBox.Show("Cliente Cadastrado com Sucesso!", "Valido", MessageBoxButton.OK);
    }
    catch (ValidacaoNegocioException ex) // Lida com o erro arremessado do Service
    {
        MessageBox.Show(ex.Message, "Falha de Negócio", MessageBoxButton.OK, MessageBoxImage.Warning);
    }
}
```
