package ru.psyfabriq.service;

import ru.psyfabriq.entity.Client;

public interface ClientService extends ExecutestService<Client, Client> {
    boolean existsByClientIde(String clientId);
}
