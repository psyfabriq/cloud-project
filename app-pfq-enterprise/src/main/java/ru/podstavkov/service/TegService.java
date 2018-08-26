package ru.podstavkov.service;

import java.util.List;

import ru.podstavkov.dto.TegRequest;
import ru.podstavkov.dto.TegResponse;
import ru.podstavkov.entity.Teg;

public interface TegService extends ExecutestService<Teg,TegRequest, TegResponse> {
    Teg add(Teg teg);
    void delete(String id);
    Teg getByName(String name);
    Teg getByID(String id);
    Teg edit(Teg teg);
    List<Teg > getAll();
    Boolean existsByName(String name);
    Boolean existsByID(String id);
}
