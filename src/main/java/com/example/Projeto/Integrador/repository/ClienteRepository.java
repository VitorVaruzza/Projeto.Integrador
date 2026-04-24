package com.example.Projeto.Integrador.repository;

import com.example.Projeto.Integrador.model.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository extends FirestoreCrudRepository<Cliente> {

    public ClienteRepository(Firestore firestore, CounterRepository counterRepository, ObjectMapper objectMapper) {
        super(firestore, counterRepository, objectMapper, Cliente.class, "clientes", "idCliente");
    }
}
