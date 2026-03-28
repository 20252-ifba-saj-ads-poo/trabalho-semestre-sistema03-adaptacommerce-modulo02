### 📝 Resumo e Nota (OK)
- **Nota Final:** 77/100
- **Visão Geral:** Excelente trabalho da equipe. A aplicação foi plenamente desenvolvida para o Módulo 02 (Gestão de Clientes e Fornecedores), apresentando uma Interface Gráfica e funcional e uma boa arquitetura orientada a objetos (destaque para herança  `ClienteFisico` e `ClienteJuridico`). Contudo, pequenos deslizes em relação às exigências restritas de polimorfismo na camada de Serviço, ausência de Exceptions Customizadas (uso de IllegalArgumentException) e omissão do `equals`/`hashCode` na base Model impactaram a nota máxima.

### 📊 Detalhamento do Barema
- **[20/20] Interface Gráfica:** A interface atende plenamente ao escopo previsto para Clientes e Fornecedores. Possui telas de lista filtráveis (search funcional) e janelas de cadastro com comportamento orgânico. Validações estouradas na Camada de Negócio (`IllegalArgumentException`) são elegantemente percebidas nos blocos `try-catch` do Controller e desenhadas via `Alert`.
- **[15/30] Camada de Negócio:** Houve excelente trabalho ao isolar totalmente as validações de documentação (CPF/CNPJ e CEP) para dentro dos Serviços (`ClienteService` e `FornecedorService`). Contudo, dois fatores reduziram a nota: 1) utilização de instanceof para verificar o tipo de cliente. 2) Não foram projetadas Exceções Customizadas; usou-se a genérica do Java `IllegalArgumentException`, perdendo rastreabilidade do domínio.
- **[15/20] Camada de Dados (Repository/Modelagem):** Uso da tabela estática via genéricos (`GenericDAOImpl<>`). A classe `AbstractModel<UUID>` se encarrega fielmente pelas obrigações rotineiras de ID, `createdAt` e `updatedAt`. Contudo, faltou a sobrescrita universal dos métodos obrigatórios `equals()` e `hashCode()` baseados no UUID na entidade abstrata, um passo forte para checagem em listas.
- **[20/20] Separação em Camadas:** Trabalho exemplar. Nenhum vestígio da Camada de Visualização cruzou o oceano até a Base de Dados. Os fluxos trilharam pacificamente o caminho `Controller -> Service -> DAO`.
- **[7/10] Boas Práticas e POO:** Abstração e encapsulamento em alto nível (especialmente notar classes concretas herdando modelo de pessoa abstrata). 

### 🐛 Erros Lógicos, Arquiteturais e Execução
- **Sem Exceções Pessoalizadas:** Embora o fluxo funcione, jogar `IllegalArgumentException` mascara eventuais erros de parser contra erros de domínio restrito.
- **Falta do equals/hashCode no Base Model:** Objetos do Java compararão sempre endereços de memória na ausência destes métodos sobrescritos com base no `id` (chave de fato), dificultando verificações como `lista.contains(cliente)`.

### 💡 Refatoração / Código
**1. Criando Exceções Orientadas Ao Negócio:**
O recomendável no lugar de um `IllegalArgumentException`:
```java
// Criando uma exceção de modelo
public class ValidacaoDocumentoException extends Exception {
    public ValidacaoDocumentoException(String mensagem) {
        super(mensagem);
    }
}
```

E no seu serviço:
```java
public void salvar(Cliente cliente) throws ValidacaoDocumentoException {
    if (// regra falha) {
        throw new ValidacaoDocumentoException("CPF Incorreto!");
    }
}
```

**2. A Herança do Abstract Model:**
Deve-se assegurar a identidade do objeto (independente de endereço local) definindo-a na model principal:
```java
public abstract class AbstractModel<T> {
    private T id;
    // ... construtores

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractModel<?> that = (AbstractModel<?>) obj;
        return id != null ? id.equals(that.id) : false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
```
