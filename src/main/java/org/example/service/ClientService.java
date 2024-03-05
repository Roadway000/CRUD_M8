package org.example.service;

import org.example.entity.Client;
import org.example.repository.ClientDao;
import org.example.utils.MyFormat;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

import static org.example.service.ClientMapper.mapToClientList;

public class ClientService {
    private ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }
    /** додає нового клієнта з іменем name */
    public int create(String name) {
        Client client = new Client(name);
        return clientDao.insertClient(client);
    }
    /** повертає назву клієнта з ідентифікатором id */
    public String getById(int id) {
        Optional<Client> clientById = clientDao.selectClientById(id);
        if (clientById.isPresent()) {
            return clientById.get().getName();
        } else {
            MyFormat.logger.info("Client does not exist by id: "+id);
        }
        return "";
    }
    /** встановлює нове ім'я name для клієнта з ідентифікатором id */
    public void setName(int id, String name) {
        Optional<Client> clientById = clientDao.updateClientById(name, id);
        if (clientById.isPresent()) {
            System.out.println("new name of client by id: "+id+" is '" + clientById.get().getName()+"'");
        } else {
            throw new RuntimeException("name by id "+id+" is not found");
        }
    }
    /** видаляє клієнта з ідентифікатором id */
    public void deleteById(int id) {
        String clientNameForDelete = getById(id);
        if (clientNameForDelete.equals("")) {
            MyFormat.logger.info("Client by id: "+id+" does not exist");
        } else {
            if (clientDao.deleteClientById(id) > 0) {
                MyFormat.logger.info("Client by id: "+id+" and name "+clientNameForDelete+" successfully deleted");
            }
        }
    }
    /** повертає всіх клієнтів з БД у вигляді колекції об'єктів типу Client */
    public List<Client> listAllClients() {
        return mapToClientList(clientDao.selectAllClient());
    }
}
