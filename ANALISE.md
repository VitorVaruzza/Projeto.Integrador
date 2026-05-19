# Análise Arquitetural - Projeto Integrador 2026

Esta análise avalia o projeto sob a ótica de um Arquiteto de Software, considerando os pilares de robustez, escalabilidade, manutenibilidade, segurança e boas práticas.

## 1. Robustez
*   **Pontos Fortes:**
    *   **Tratamento de Exceções:** O uso de `@RestControllerAdvice` no `ApiExceptionHandler` centraliza o tratamento de erros, garantindo respostas consistentes para o cliente.
    *   **Validação de Dados:** O uso de Bean Validation (`jakarta.validation`) nos modelos (ex: `Cliente.java`) impede a entrada de dados inconsistentes na camada de API.
    *   **Consistência de Identificadores:** O uso de transações no `CounterRepository` para gerenciar IDs incrementais no Firestore tenta emular o comportamento de bancos relacionais, garantindo unicidade.
*   **Fragilidades:**
    *   **Performance de Busca:** O método `existsByClienteId` no `VeiculoRepository` executa um `findAll()` e filtra em memória. Isso é extremamente perigoso para a robustez do sistema, pois o consumo de memória e o tempo de resposta crescerão linearmente com o número de veículos, podendo causar `OutOfMemoryError`.

## 2. Escalabilidade
*   **Avaliação:** O sistema possui sérias limitações de escalabilidade.
    *   **Uso Ineficiente do Firestore:** O Firestore é um banco NoSQL desenhado para escala massiva, mas a implementação atual utiliza o `findAll()` da `FirestoreCrudRepository` para quase todas as operações de lógica de negócio. Isso ignora o poder de indexação e consulta do Firestore.
    *   **Gargalo de Contadores:** A estratégia de `_counters` com transação cria um ponto único de contenção. Em um cenário de alta concorrência de escritas, o Firestore sofrerá com "contention" no documento do contador, limitando a taxa de transferência de novas inserções.

## 3. Manutenibilidade
*   **Pontos Fortes:**
    *   **Camadas Claras:** A separação em `Controller`, `Service`, `Repository` e `Model` segue o padrão de mercado, facilitando a localização de código.
    *   **DRY (Don't Repeat Yourself):** A `FirestoreCrudRepository` abstrai com sucesso as operações repetitivas de CRUD, facilitando a criação de novas entidades.
*   **Recomendações:**
    *   **Inversão de Dependência:** Embora use Injeção de Dependência, o código poderia se beneficiar de interfaces para os serviços, facilitando a criação de mocks em testes unitários complexos.

## 4. Segurança
*   **Avaliação:** Básica/Inexistente no nível de aplicação.
    *   **Autenticação/Autorização:** Não foram identificados mecanismos de segurança (Spring Security, JWT, etc.) nos endpoints da API.
    *   **Exposição de Dados:** O sistema utiliza os modelos de banco diretamente como DTOs (Data Transfer Objects). Isso pode expor campos sensíveis ou desnecessários em futuras evoluções.

## 5. Boas Práticas (SOLID, Clean Code)
*   **S (Single Responsibility Principle):** Bem aplicado na maioria das classes.
*   **O (Open/Closed Principle):** A `FirestoreCrudRepository` demonstra boa aplicação, permitindo extensões sem modificar a base.
*   **Clean Code:** Nomes de variáveis e métodos são expressivos. O uso de `Optional` no repositório ajuda a evitar `NullPointerException`.

## Conclusão Arquitetural
O projeto é um excelente protótipo funcional que demonstra domínio sobre Spring Boot e integração com Firebase. No entanto, para se tornar um sistema de produção "enterprise-ready", é necessário abandonar a estratégia de filtragem em memória (`findAll().stream()`) em favor de `Queries` reais do Firestore, além de implementar uma camada de segurança robusta.
