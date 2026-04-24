package com.example.Projeto.Integrador.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public abstract class FirestoreCrudRepository<T> {

    private final Firestore firestore;
    private final CounterRepository counterRepository;
    private final ObjectMapper objectMapper;
    private final Class<T> type;
    private final String collectionName;
    private final String idField;

    protected FirestoreCrudRepository(
            Firestore firestore,
            CounterRepository counterRepository,
            ObjectMapper objectMapper,
            Class<T> type,
            String collectionName,
            String idField) {
        this.firestore = firestore;
        this.counterRepository = counterRepository;
        this.objectMapper = objectMapper;
        this.type = type;
        this.collectionName = collectionName;
        this.idField = idField;
    }

    public List<T> findAll() {
        try {
            return firestore.collection(collectionName).get().get().getDocuments().stream()
                    .filter(DocumentSnapshot::exists)
                    .map(this::toEntity)
                    .sorted(Comparator.comparing(this::extractId))
                    .toList();
        } catch (Exception exception) {
            throw new IllegalStateException("Nao foi possivel consultar a colecao " + collectionName, exception);
        }
    }

    public Optional<T> findById(Long id) {
        try {
            DocumentSnapshot snapshot = firestore.collection(collectionName).document(id.toString()).get().get();
            return snapshot.exists() ? Optional.of(toEntity(snapshot)) : Optional.empty();
        } catch (Exception exception) {
            throw new IllegalStateException("Nao foi possivel consultar o registro " + id, exception);
        }
    }

    public T create(T entity) {
        Long id = counterRepository.nextId(collectionName);
        return save(id, entity);
    }

    public T update(Long id, T entity) {
        return save(id, entity);
    }

    public void delete(Long id) {
        try {
            firestore.collection(collectionName).document(id.toString()).delete().get();
        } catch (Exception exception) {
            throw new IllegalStateException("Nao foi possivel excluir o registro " + id, exception);
        }
    }

    protected String getCollectionName() {
        return collectionName;
    }

    private T save(Long id, T entity) {
        try {
            setId(entity, id);
            firestore.collection(collectionName).document(id.toString()).set(entity).get();
            return entity;
        } catch (Exception exception) {
            throw new IllegalStateException("Nao foi possivel salvar o registro " + id, exception);
        }
    }

    private T toEntity(DocumentSnapshot snapshot) {
        T entity = objectMapper.convertValue(snapshot.getData(), type);
        setId(entity, Long.valueOf(snapshot.getId()));
        return entity;
    }

    private void setId(T entity, Long id) {
        try {
            Field field = type.getDeclaredField(idField);
            field.setAccessible(true);
            field.set(entity, id);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Nao foi possivel definir o identificador em " + type.getSimpleName(), exception);
        }
    }

    private Long extractId(T entity) {
        try {
            Field field = type.getDeclaredField(idField);
            field.setAccessible(true);
            Object value = field.get(entity);
            return value == null ? 0L : (Long) value;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Nao foi possivel ler o identificador em " + type.getSimpleName(), exception);
        }
    }
}
