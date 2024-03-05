package org.example.repository;

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

public class WorkerDao {
    private static final String INSERT_WORKER = "insert into worker(name, birthday, level, salary) values(?,?,?,?)";
    private static final String SELECT_WORKER_BY_ID = "select * from worker where id=?";
    private static final String SELECT_WORKER_ALL = "select * from worker order by id";
    private static final String UPDATE_WORKER_BY_ID = "update worker set name=?, birthday=?, level=?, salary=? where id=?";
    private static final String DELETE_WORKER_BY_ID = "delete from worker where id=?";
    private static final String SELECT_LAST_WORKER = "select * from worker where id=(select max(id) from worker)";

    private Connection connection;
    private PreparedStatement insertWorker;
    private PreparedStatement selectWorkerById;
    private PreparedStatement selectWorkerAll;
    private PreparedStatement updateWorkerById;
    private PreparedStatement deleteWorkerById;
    private PreparedStatement selectLastWorker;
    private static final String NAME = "name";
    private static final String BIRTHDAY = "birthday";
    private static final String LEVEL = "level";
    private static final String SALARY = "salary";

    public WorkerDao(Connection connection) {
        this.connection = connection;
        try {
            this.insertWorker = connection.prepareStatement(INSERT_WORKER);
            this.selectWorkerById = connection.prepareStatement(SELECT_WORKER_BY_ID);
            this.selectWorkerAll = connection.prepareStatement(SELECT_WORKER_ALL);
            this.updateWorkerById = connection.prepareStatement(UPDATE_WORKER_BY_ID);
            this.deleteWorkerById = connection.prepareStatement(DELETE_WORKER_BY_ID);
            this.selectLastWorker = connection.prepareStatement(SELECT_LAST_WORKER);
        } catch (SQLException e) {
            MyFormat.logger.info("Worker construction exception: "+e.getMessage());
        }
    }

    public int insertWorker(Worker worker) {
        try {
            this.insertWorker.setString(1, worker.getName());
            this.insertWorker.setDate(2, java.sql.Date.valueOf(worker.getBirthday().toString()));
            this.insertWorker.setString(3, worker.getLevel());
            this.insertWorker.setInt(4, worker.getSalary());
            return this.insertWorker.executeUpdate();
        } catch (SQLException e) {
            MyFormat.logger.info("Insert Worker exception: "+e.getMessage());
            return -1;
        }
    }
    public Optional<Worker> selectWorkerById(int id) {
        try {
            this.selectWorkerById.setInt(1, id);
            try (ResultSet resultSet = this.selectWorkerById.executeQuery()) {
                if (resultSet.next()) {
                    Worker worker = new Worker(resultSet.getInt(id),
                            resultSet.getString(NAME),
                            LocalDate.parse(resultSet.getString(BIRTHDAY)),
                            resultSet.getString(LEVEL),
                            resultSet.getInt(SALARY));
                    return Optional.of(worker);
                }
            } catch (SQLException e) {
                MyFormat.logger.info(String.format("execute %s%d exception: %s ",SELECT_WORKER_BY_ID,id,e.getMessage()));
            }
        } catch (SQLException e) {
            MyFormat.logger.info("execute selectWorkerById exception: "+e.getMessage());
        }
        return Optional.empty();
    }
    public List<Worker> selectAllWorker() {
        List<Worker> workerList = new ArrayList<>();
        try (ResultSet resultSet = this.selectWorkerAll.executeQuery()) {
            while (resultSet.next()) {
                Worker worker = new Worker(resultSet.getInt("id"),
                        resultSet.getString(NAME),
                        LocalDate.parse(resultSet.getString(BIRTHDAY)),
                        resultSet.getString(LEVEL),
                        resultSet.getInt(SALARY));
                workerList.add(worker);
            }
            return workerList;
        } catch (SQLException e) {
            MyFormat.logger.info("Select All Worker exception: "+e.getMessage());
        }
        return workerList;
    }
    public Optional<Worker> updateWorkerById(String name, LocalDate birthday, String level, int salary, int id) {
        try {
            this.updateWorkerById.setString(1, name);
            this.updateWorkerById.setDate(2, java.sql.Date.valueOf(birthday));
            this.updateWorkerById.setString(3, level);
            this.updateWorkerById.setInt(4, salary);
            this.updateWorkerById.setInt(5, id);
            this.updateWorkerById.executeUpdate();
        } catch (SQLException e) {
            MyFormat.logger.info("Update worker exception: "+e.getMessage());
        }
        return selectWorkerById(id);
    }
    public int deleteWorkerById(int id) {
        try {
            this.deleteWorkerById.setInt(2, id);
            return this.deleteWorkerById.executeUpdate();
        } catch (SQLException e) {
            MyFormat.logger.info("Delete worker exception: "+e.getMessage());
        }
        return 0;
    }
    public Optional<Worker> selectLastWorker() {
        try (ResultSet resultSet = this.selectLastWorker.executeQuery()) {
            if(resultSet.next()) {

                Worker worker = new Worker(resultSet.getInt("id"),
                        resultSet.getString(NAME),
                        LocalDate.parse(resultSet.getString(BIRTHDAY)),
                        resultSet.getString(LEVEL),
                        resultSet.getInt(SALARY));
                return Optional.of(worker);
            }
        } catch(SQLException e) {
            System.out.println("Select last Worker exception: " + e.getMessage());
        }
        return Optional.empty();
    }
}
