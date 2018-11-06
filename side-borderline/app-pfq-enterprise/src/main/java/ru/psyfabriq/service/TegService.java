package ru.psyfabriq.service;

import java.util.List;

import ru.psyfabriq.dto.TegRequest;
import ru.psyfabriq.dto.TegResponse;
import ru.psyfabriq.entity.Teg;

public interface TegService extends ExecutestService<Teg, TegRequest, TegResponse> {
    Teg add(Teg teg);

    void delete(String id);

    Teg getByName(String name);

    Teg getByID(String id);

    Teg edit(Teg teg);

    List<Teg> getAll();

    Boolean existsByName(String name);

    Boolean existsByID(String id);
}
