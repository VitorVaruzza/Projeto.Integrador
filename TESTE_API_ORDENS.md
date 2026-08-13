# 🧪 Teste Manual da API - Ordens de Serviço

## 📌 Pré-requisitos
- O servidor deve estar rodando em `http://localhost:8080`
- Você precisa ter clientes e veículos cadastrados
- Pode usar curl, Postman, ou qualquer cliente HTTP

---

## ✅ Teste 1: Verificar Conectividade

### Com curl:
```bash
curl -X GET http://localhost:8080/api/ordens-servico
```

### Resposta esperada:
```json
[]
```
(Lista vazia no início ou com as ordens existentes)

---

## ✅ Teste 2: Listar Clientes (Para Pegar IDs)

### Com curl:
```bash
curl -X GET http://localhost:8080/api/clientes
```

### Resposta esperada:
```json
[
  {
    "id_cliente": 1,
    "nome": "João Silva",
    "cpf": "12345678901",
    ...
  }
]
```

---

## ✅ Teste 3: Listar Veículos (Para Pegar IDs)

### Com curl:
```bash
curl -X GET http://localhost:8080/api/veiculos
```

### Resposta esperada:
```json
[
  {
    "id_veiculo": 1,
    "placa": "ABC1234",
    "modelo": "Civic",
    ...
  }
]
```

---

## ✅ Teste 4: Criar Uma Ordem de Serviço

### Com curl (Windows - PowerShell):
```powershell
$body = @{
    id_cliente = 1
    id_veiculo = 1
    data_abertura = "2026-06-16"
    data_fechamento = $null
    status = "aberta"
    observacoes = ""
} | ConvertTo-Json

curl -X POST http://localhost:8080/api/ordens-servico `
  -H "Content-Type: application/json" `
  -d $body
```

### Com curl (Linux/Mac):
```bash
curl -X POST http://localhost:8080/api/ordens-servico \
  -H "Content-Type: application/json" \
  -d '{
    "id_cliente": 1,
    "id_veiculo": 1,
    "data_abertura": "2026-06-16",
    "data_fechamento": null,
    "status": "aberta",
    "observacoes": ""
  }'
```

### Com Postman:
1. **Método:** POST
2. **URL:** `http://localhost:8080/api/ordens-servico`
3. **Headers:** 
   - `Content-Type: application/json`
4. **Body (raw):**
```json
{
  "id_cliente": 1,
  "id_veiculo": 1,
  "data_abertura": "2026-06-16",
  "status": "aberta"
}
```

### Resposta esperada (Sucesso - Status 201):
```json
{
  "id_os": 1,
  "id_cliente": 1,
  "id_veiculo": 1,
  "data_abertura": "2026-06-16",
  "data_fechamento": null,
  "status": "aberta",
  "observacoes": ""
}
```

### Resposta esperada (Erro - Status 400):
```json
{
  "timestamp": "2026-06-16T16:53:14.123456-03:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Dados invalidos",
  "errors": {
    "data_abertura": "Data de abertura e obrigatoria"
  }
}
```

---

## ❌ Erros Comuns

### Erro: "Veiculo informado nao existe"
```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Veiculo informado nao existe"
}
```
**Solução:** Use um `id_veiculo` que existe. Lista os veículos com:
```bash
curl -X GET http://localhost:8080/api/veiculos
```

### Erro: "Data de abertura e obrigatoria"
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Dados invalidos",
  "errors": {
    "data_abertura": "Data de abertura e obrigatoria"
  }
}
```
**Solução:** Certifique-se de incluir `data_abertura` no payload.

### Erro: "Status e obrigatorio"
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Dados invalidos",
  "errors": {
    "status": "Status e obrigatorio"
  }
}
```
**Solução:** Certifique-se de incluir `status` no payload com um valor válido.

---

## ✅ Teste 5: Obter Uma Ordem Específica

### Com curl:
```bash
curl -X GET http://localhost:8080/api/ordens-servico/1
```

### Resposta esperada:
```json
{
  "id_os": 1,
  "id_cliente": 1,
  "id_veiculo": 1,
  "data_abertura": "2026-06-16",
  "status": "aberta",
  "observacoes": ""
}
```

---

## ✅ Teste 6: Atualizar Uma Ordem

### Com curl:
```bash
curl -X PUT http://localhost:8080/api/ordens-servico/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id_cliente": 1,
    "id_veiculo": 1,
    "data_abertura": "2026-06-16",
    "status": "em_andamento"
  }'
```

### Resposta esperada:
```json
{
  "id_os": 1,
  "id_cliente": 1,
  "id_veiculo": 1,
  "data_abertura": "2026-06-16",
  "status": "em_andamento"
}
```

---

## ✅ Teste 7: Deletar Uma Ordem

### Com curl:
```bash
curl -X DELETE http://localhost:8080/api/ordens-servico/1
```

### Resposta esperada:
- Status 204 (No Content) - Sucesso
- Status 404 (Not Found) - Ordem não existe
- Status 409 (Conflict) - Ordem possui itens vinculados

---

## 📝 Valores Válidos para Status

- `aberta` - Ordem aberta
- `em_andamento` - Em andamento
- `concluida` - Concluída
- `cancelada` - Cancelada

---

## 🎯 Roteiro de Teste Completo

```bash
# 1. Listar clientes
curl -X GET http://localhost:8080/api/clientes

# 2. Listar veículos
curl -X GET http://localhost:8080/api/veiculos

# 3. Criar uma ordem (substitute IDs conforme necessário)
curl -X POST http://localhost:8080/api/ordens-servico \
  -H "Content-Type: application/json" \
  -d '{
    "id_cliente": 1,
    "id_veiculo": 1,
    "data_abertura": "2026-06-16",
    "status": "aberta"
  }'

# 4. Listar todas as ordens
curl -X GET http://localhost:8080/api/ordens-servico

# 5. Obter uma ordem específica
curl -X GET http://localhost:8080/api/ordens-servico/1

# 6. Atualizar uma ordem
curl -X PUT http://localhost:8080/api/ordens-servico/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id_cliente": 1,
    "id_veiculo": 1,
    "data_abertura": "2026-06-16",
    "status": "em_andamento"
  }'

# 7. Deletar uma ordem
curl -X DELETE http://localhost:8080/api/ordens-servico/1
```

---

## 💡 Dicas

- Use `curl -v` para ver headers da resposta
- Use `curl -i` para ver status + headers + body
- No Postman, clique em "Code" para gerar curl automaticamente
- Verifique os logs do servidor para mensagens de erro
- Use `jq` para formatar JSON: `curl ... | jq .`


