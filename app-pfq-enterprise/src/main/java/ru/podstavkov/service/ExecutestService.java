package ru.podstavkov.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExecutestService<E,D,DR> {
    Optional<E> convertDtoToEntityRequest(D dto);
    Optional<D> convertEntityToDTORequest(E entity);
    
    Optional<E> convertDtoToEntityResponse(DR dto);
    Optional<DR> convertEntityToDTOResponse(E entity);
    
    Page<DR> findPaginated(Pageable pageable); 

}
