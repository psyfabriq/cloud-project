package ru.psyfabriq.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.psyfabriq.dto.TegRequest;
import ru.psyfabriq.dto.TegResponse;
import ru.psyfabriq.entity.Teg;
import ru.psyfabriq.repository.TegRepository;
import ru.psyfabriq.service.TegService;

@Service
public class TegServiceImpl implements TegService {

    @Autowired
    TegRepository tegRepository;

    @Override
    @Transactional
    public Teg add(Teg teg) {
        return tegRepository.saveAndFlush(teg);
    }

    @Override
    @Transactional
    public void delete(String id) {
        tegRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Teg getByName(String name) {
        return tegRepository.findByName(name).get();
    }

    @Override
    @Transactional
    public Teg edit(Teg teg) {
        return tegRepository.saveAndFlush(teg);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teg> getAll() {
        return tegRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByName(String name) {
        return tegRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TegResponse> findPaginated(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<TegResponse> list;
        List<Teg> tegs = new ArrayList<>(getAll());

        if (tegs.size() < startItem) {
            list = Collections.emptyList();
        } else {
            list = new ArrayList<>();
            int toIndex = Math.min(startItem + pageSize, tegs.size());
            tegs.subList(startItem, toIndex).forEach((v) -> {
                list.add(convertEntityToDTOResponse(v).get());
            });
        }

        Page<TegResponse> tegPage = new PageImpl<TegResponse>(list, PageRequest.of(currentPage, pageSize), tegs.size());

        return tegPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByID(String id) {
        return tegRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Teg getByID(String id) {
        return tegRepository.getOne(id);
    }

    @Override
    public Optional<Teg> convertDtoToEntityRequest(TegRequest dto) {
        Optional<Teg> tmp = tegRepository.findById(dto.getId());
        Teg teg = tmp.isPresent() ? tmp.get() : new Teg();
        teg.setDescription(dto.getDescription());
        teg.setName(dto.getName());
        teg.setMenuname(dto.getMenuname());
        teg.setTeg(dto.getTeg());
        teg.setUpdatedAt(Instant.now());
        return Optional.ofNullable(teg);
    }

    @Override
    public Optional<TegRequest> convertEntityToDTORequest(Teg entity) {
        TegRequest request = new TegRequest();
        request.setId(entity.getId());
        request.setName(entity.getName());
        request.setTeg(entity.getTeg());
        request.setDescription(entity.getDescription());
        request.setMenuname(entity.getMenuname());
        return Optional.ofNullable(request);
    }

    @Override
    public Optional<Teg> convertDtoToEntityResponse(TegResponse dto) {
        Teg teg = tegRepository.findById(dto.getId()).get();
        teg.setDescription(dto.getDescription());
        teg.setName(dto.getName());
        teg.setMenuname(dto.getMenuname());
        teg.setTeg(dto.getTeg());
        return Optional.ofNullable(teg);
    }

    @Override
    public Optional<TegResponse> convertEntityToDTOResponse(Teg entity) {
        TegResponse response = new TegResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setTeg(entity.getTeg());
        response.setDescription(entity.getDescription());
        response.setMenuname(entity.getMenuname());
        return Optional.ofNullable(response);
    }


}
