# Varuzza - Sistema de Gestão de Ordens de Serviço

Sistema de backend desenvolvido para gerenciar o fluxo de trabalho de uma oficina ou prestadora de serviços, permitindo o controle de clientes, veículos e ordens de serviço.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.5.14**
- **Google Cloud Firestore** (NoSQL)
- **Maven**
- **Jackson** (Processamento JSON com Snake Case)

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura em camadas:

- **Controller:** Expõe os endpoints REST e valida os dados de entrada.
- **Service:** Contém as regras de negócio e validações lógicas.
- **Repository:** Camada de abstração de dados. Utiliza uma implementação customizada (`FirestoreCrudRepository`) para gerenciar documentos no NoSQL com IDs numéricos sequenciais.
- **Model:** Entidades da aplicação com validações Jakarta.

## 📋 Funcionalidades

- Cadastro e gestão de Clientes (com validação de CPF, endereço e contato).
- Gestão de Veículos vinculados a clientes.
- Controle de Serviços e Itens de Serviço.
- Emissão e acompanhamento de Ordens de Serviço.
- Processamento de Pagamentos.

## 🔧 Configuração e Instalação

### Pré-requisitos
- JDK 17
- Maven 3.x
- Conta no Firebase com Firestore ativo.

### Configuração do Firebase
1. No console do Firebase, gere uma nova chave privada (JSON) para a sua conta de serviço.
2. Use uma das opções abaixo:
   - Salve o arquivo em `Projeto.Integrador/.firebase/serviceAccountKey.json`.
   - Ou defina a variável de ambiente `FIREBASE_CREDENTIALS_JSON` com o conteúdo completo do JSON.
   - Ou configure credenciais padrão do Google na máquina.
3. Verifique as configurações no arquivo `src/main/resources/application.properties`:
   ```properties
   firebase.project-id=seu-projeto-id
   firebase.database-url=https://seu-projeto.firebaseio.com
   firebase.credentials-path=.firebase/serviceAccountKey.json
   ```

Se o arquivo `.firebase/serviceAccountKey.json` não existir, a aplicação tenta automaticamente `FIREBASE_CREDENTIALS_JSON` e depois as credenciais padrão do Google.

### Execução
```bash
cd Projeto.Integrador
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080/api/`.

## 🛣️ Endpoints Principais (Exemplos)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/clientes` | Lista todos os clientes |
| POST | `/api/clientes` | Cria um novo cliente |
| GET | `/api/veiculos` | Lista todos os veículos |
| POST | `/api/ordens-servico` | Cria uma nova OS |

## 🛠️ Desenvolvimento e Contribuição

O projeto utiliza o padrão **Snake Case** para as chaves JSON (ex: `id_cliente` em vez de `idCliente`). Certifique-se de seguir este padrão ao realizar requisições ou estender a API.

---
*Desenvolvido como Projeto Integrador - 2026*
