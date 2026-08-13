# 🔍 Guia de Debug - Cadastro de Ordens de Serviço

## ✅ Mudanças Realizadas

### 1. **Correção do ID da Tabela**
- **Problema Anterior:** A tabela buscava `ordem.id_ordem` mas o modelo usa `ordem.id_os`
- **Solução:** Alterado para `ordem.id_os` em todos os lugares
- **Arquivo:** `ordens-servico.html` (linhas 388, 395, 396)

### 2. **Adicionados Logs de Debug**
```javascript
console.log("Enviando OS:", {
    metodo: method,
    url: url,
    dados: data
});
```
Agora você verá exatamente o que está sendo enviado para a API.

### 3. **Validações Adicionadas**
- Veículo é obrigatório
- Data de Abertura é obrigatória
- Status é obrigatório
- Mensagens de erro mais claras

### 4. **Teste de Conectividade**
A página testa automaticamente se:
- ✅ API de clientes está respondendo
- ✅ API de veículos está respondendo
- ✅ API de ordens está respondendo

---

## 🔧 Como Debugar o Problema

### **Passo 1: Abrir o Console do Navegador**
- Pressione `F12`
- Vá até a aba **Console**

### **Passo 2: Verificar os Logs**
Você deve ver mensagens como:
```
✅ Dados carregados com sucesso!
Clientes - Status: 200
Veículos - Status: 200
Ordens - Status: 200
✅ API conectada com sucesso!
```

### **Passo 3: Tentar Criar Uma Ordem**
1. Preencha o formulário:
   - **Cliente:** Escolha um (opcional)
   - **Veículo:** Escolha um (obrigatório)
   - **Data Abertura:** Escolha uma data (obrigatório)
   - **Status:** Deixe como "Aberta"

2. Clique em "Salvar ordem"

3. **No Console, você verá:**
```
Enviando OS: {
  metodo: "POST",
  url: "/api/ordens-servico",
  dados: {
    id_cliente: null,
    id_veiculo: 1,
    data_abertura: "2026-06-16",
    data_fechamento: null,
    status: "aberta",
    observacoes: ""
  }
}
Resposta status: 201
Sucesso: { id_os: 1, ... }
✅ Ordem cadastrada com sucesso.
```

---

## ❌ Se Aparecer Erro

### **Tipo 1: Erro de Validação**
```
Erro capturado: Dados invalidos | Veículo informado nao existe
```
**Solução:** Certifique-se de que o veículo existe e é válido.

### **Tipo 2: Erro 400 Bad Request**
```
Resposta status: 400
Erro na resposta: { errors: { ... } }
```
**Verificar:** Se todos os campos obrigatórios estão preenchidos.

### **Tipo 3: Erro 500 Internal Server Error**
```
Resposta status: 500
```
**Verificar:** Se o Firebase está configurado corretamente.

### **Tipo 4: Erro de Conexão**
```
❌ API conectada com erro
```
**Verificar:** Se o servidor está rodando corretamente.

---

## 🚀 Passos para Testar

1. **Abra** `http://localhost:8080/ordens-servico.html` (ou sua porta)
2. **Abra o Console** (`F12` → Console)
3. **Verifique os logs iniciais** ✅
4. **Selecione um cliente** (opcional)
5. **Selecione um veículo**
6. **Escolha uma data**
7. **Clique em "Salvar ordem"**
8. **Verifique o console** para mensagens de sucesso/erro

---

## 📊 Payload Sendo Enviado

O formato correto que o servidor espera é:
```json
{
  "id_cliente": 1,           // ou null se não selecionado
  "id_veiculo": 1,           // obrigatório
  "data_abertura": "2026-06-16",  // obrigatório (formato YYYY-MM-DD)
  "data_fechamento": null,   // opcional
  "status": "aberta",        // obrigatório (um dos: aberta, em_andamento, concluida, cancelada)
  "observacoes": ""          // opcional
}
```

---

## ✨ Se Ainda Tiver Problemas

**Cole no Console e execute:**
```javascript
// Testar se os elementos existem
console.log("id_cliente select:", document.getElementById("id_cliente"));
console.log("id_veiculo select:", document.getElementById("id_veiculo"));
console.log("data_abertura input:", document.getElementById("data_abertura"));
console.log("status select:", document.getElementById("status"));

// Testar se os dados estão sendo carregados
fetch("/api/clientes").then(r => r.json()).then(c => console.log("Clientes carregados:", c.length));
fetch("/api/veiculos").then(r => r.json()).then(v => console.log("Veículos carregados:", v.length));
fetch("/api/ordens-servico").then(r => r.json()).then(o => console.log("Ordens carregadas:", o.length));
```

---

## 📝 Resumo das Mudanças

| Arquivo | Mudança |
|---------|---------|
| `ordens-servico.html` | Corrigido `id_ordem` → `id_os` em 3 lugares |
| `ordens-servico.html` | Adicionados console.log para debug |
| `ordens-servico.html` | Adicionadas validações de formulário |
| `ordens-servico.html` | Adicionado teste automático de API |

---

**Status:** ✅ Pronto para testar!

