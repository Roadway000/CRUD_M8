package org.example.service;

import org.example.entity.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {
    public static List<Client> mapToClientList(List<Client> entities) {
        return entities.stream()
                .map(entity -> mapToClient(entity))
                .collect(Collectors.toList());
    }

    private static Client mapToClient(Client entity) {
        Client client = new Client();
        client.setId(entity.getId());
        client.setName(entity.getName());
        return client;
    }
}
