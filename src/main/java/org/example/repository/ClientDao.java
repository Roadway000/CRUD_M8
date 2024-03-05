package org.example.repository;

import org.example.entity.Client;
import org.example.entity.Worker;
import org.example.utils.MyFormat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDao {
    private static final String INSERT_CLIENT = "insert into client(name) values(?)";
    private static final String SELECT_CLIENT_BY_ID = "select id, name from client where id=?";
    private static final String SELECT_CLIENT_ALL = "select id, name from client order by id";
    private static final String UPDATE_CLIENT_BY_ID = "update client set name=? where id=?";
    private static final String DELETE_CLIENT_BY_ID = "delete from client where id=?";
    private static final String SELECT_LAST_CLIENT = "select * from client where id=(select max(id) from client)";

    private Connection connection;
    private PreparedStatement insertClient;
    public PreparedStatement selectClientByIdStatement;
    private PreparedStatement selectClientAll;
    private PreparedStatement updateClientById;
    private PreparedStatement deleteClientById;
    private PreparedStatement selectLastClient;
    private Client lastClient;

    public Client getLastClient() {
        return lastClient;
    }

    public void setLastClient(Client lastClient) {
        this.lastClient = lastClient;
    }

    public ClientDao(Connection connection) {
        this.connection = connection;
        try {
            this.insertClient = connection.prepareStatement(INSERT_CLIENT);
            this.selectClientByIdStatement = connection.prepareStatement(SELECT_CLIENT_BY_ID);
            this.selectClientAll = connection.prepareStatement(SELECT_CLIENT_ALL);
            this.updateClientById = connection.prepareStatement(UPDATE_CLIENT_BY_ID);
            this.deleteClientById = connection.prepareStatement(DELETE_CLIENT_BY_ID);
            this.selectLastClient = connection.prepareStatement(SELECT_LAST_CLIENT);
        } catch (SQLException e) {
            MyFormat.logger.info("Client construction exception: "+e.getMessage());
        }
    }

    public int insertClient(Client client) {
        try {
            this.insertClient.setString(1, client.getName());
            int result = this.insertClient.executeUpdate();
            Optional<Client> clientById = selectLastClient();
            if (clientById.isPresent()) {
                return clientById.get().getId();
            } else {
                return result;
            }
        } catch (SQLException e) {
            MyFormat.logger.info("Insert Client exception: "+e.getMessage());
            return -1;
        }
    }
    public Optional<Client> selectClientById(int id) {
        try {
            this.selectClientByIdStatement.setInt(1, id);
            try (ResultSet resultSet = this.selectClientByIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = new Client(resultSet.getInt("id"),
                            resultSet.getString("name"));
                    this.setLastClient(client);
                    return Optional.of(client);
                }
            } catch (SQLException e) {
                MyFormat.logger.info(String.format("execute %s%d exception: %s ", SELECT_CLIENT_BY_ID,id,e.getMessage()));
            }
        } catch (SQLException e) {
            MyFormat.logger.info("execute selectClientById exception: "+e.getMessage());
        }
        return Optional.empty();
    }
    public List<Client> selectAllClient() {
        List<Client> clientList = new ArrayList<>();
        try (ResultSet resultSet = this.selectClientAll.executeQuery()) {
            while (resultSet.next()) {
                Client client = new Client(resultSet.getInt("id"),
                        resultSet.getString("name"));
                clientList.add(client);
            }
            return clientList;
        } catch (SQLException e) {
            MyFormat.logger.info("Select All Client exception: "+e.getMessage());
        }
        return clientList;
    }
    public Optional<Client> updateClientById(String name, int id) {
        try {
            this.updateClientById.setString(1, name);
            this.updateClientById.setInt(2, id);
            this.updateClientById.executeUpdate();
        } catch (SQLException e) {
            MyFormat.logger.info("Update client exception: "+e.getMessage());
        }
        return selectClientById(id);
    }
    public int deleteClientById(int id) {
        try {
            this.deleteClientById.setInt(1, id);
            return this.deleteClientById.executeUpdate();
        } catch (SQLException e) {
            MyFormat.logger.info("Delete client exception: "+e.getMessage());
        }
        return 0;
    }
    public Optional<Client> selectLastClient() {
        try (ResultSet resultSet = this.selectLastClient.executeQuery()) {
            if(resultSet.next()) {
                Client client = new Client(resultSet.getInt("id"),
                        resultSet.getString("name"));
                return Optional.of(client);
            }
        } catch(SQLException e) {
            MyFormat.logger.info("Select last Client exception: " + e.getMessage());
        }
        return Optional.empty();
    }


}
