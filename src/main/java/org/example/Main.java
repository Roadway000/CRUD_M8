package org.example;

import org.example.entity.Client;
import org.example.entity.Worker;
import org.example.repository.ClientDao;
import org.example.repository.WorkerDao;
import org.example.service.ClientService;
import org.example.utils.MyFormat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();
        } catch (SQLException e) {
            MyFormat.logger.info("getConnection() exception: "+e.getMessage());
        }
        List<Worker> listWorker = new WorkerDao(connection).selectAllWorker();
        System.out.println(listWorker.getFirst().getFieldList());
        listWorker.forEach(worker -> System.out.println(worker));

        ClientDao clientDao = new ClientDao(connection);

        ClientService clientService = new ClientService(clientDao);

        /** створення нового клієнта */
        int lastClientId = clientService.create("New Client");

        System.out.println("\nLast client id = "+lastClientId);

        /** список усіх клієнтів */
        List<Client> listClients = clientDao.selectAllClient();
        System.out.println(listClients.getFirst().getFieldList());
        listClients.forEach((client -> System.out.println(client)));

        /** пошук клієнта за ід кодом */
        System.out.println("\nclientService.getById(1) = '"+clientService.getById(1)+"'");

        /** Зміна імені клієнта */
        clientService.setName(9,"Lenovo"); // old name was "IBM"

        /** Зміна імені клієнта */
        clientService.setName(9,"IBM");

        /** спроба видалити запис кліента з обмеженням по foreign key (client_id) references client(id) */
        clientService.deleteById(9);

        /** Видалення клієнта не пов'язаного із залежними таблицями за вторинним ключем */
        clientService.deleteById(lastClientId);

        /** повертає всіх клієнтів з БД у вигляді колекції об'єктів типу Client */
        List<Client> ListClients = clientService.listAllClients();
        System.out.println(ListClients.getFirst().getFieldList());
        ListClients.forEach(client -> System.out.println(client));
    }
}