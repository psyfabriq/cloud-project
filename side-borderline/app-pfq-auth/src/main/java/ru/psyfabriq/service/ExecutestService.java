package ru.psyfabriq.service;

import java.util.List;
import java.util.Optional;

public interface ExecutestService<E, D> {
    Optional<E> convertDtoToEntity(D dto);

    Optional<D> convertEntityToDTO(E entity);

    E add(E entity);

    E edit(E entity);

    boolean delete(E entity);

    List<E> getAll();
}
