package ru.psyfabriq.service.impl;

import org.springframework.stereotype.Service;
import ru.psyfabriq.entity.Client;
import ru.psyfabriq.repository.ClientRepository;
import ru.psyfabriq.service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean existsByClientIde(String clientId) {
        return clientRepository.findByClientId(clientId).map(o -> true).orElse(false);
    }

    @Override
    @Deprecated
    public Optional<Client> convertDtoToEntity(Client dto) {
        return Optional.empty();
    }

    @Override
    @Deprecated
    public Optional<Client> convertEntityToDTO(Client entity) {
        return Optional.empty();
    }

    @Override
    public Client add(Client entity) {
        return clientRepository.save(entity);
    }

    @Override
    public Client edit(Client entity) {
        return clientRepository.save(entity);
    }

    @Override
    public boolean delete(Client entity) {
        if (entity != null) {
            String id = entity.getClientId();
            clientRepository.delete(entity);
            return clientRepository.findByClientId(id).isPresent();
        } else {
            return false;
        }
    }

    @Override
    public List<Client> getAll() {
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
