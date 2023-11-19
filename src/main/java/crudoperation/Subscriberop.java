package crudoperation;

import model.Subscriber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Subscriberop implements CrudOperations<Subscriber>{


        private Connection connection;

        public SubscriberCrud(Connection connection) {
            this.connection = connection;
        }

        @Override
        public List<Subscriber> findAll() throws SQLException {
            List<Subscriber> allSubs = new ArrayList<>();
            String sql = "SELECT * FROM subscribers"; // Utilisation du nom de table au pluriel
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    convertToList(allSubs, result);
                }
            }
            return allSubs;
        }

    @Override
    public List<Subscriber> saveAll(List<Subscriber> toSave) {
        return null;
    }

    @Override
        public List<Subscriber> saveAll(List<Subscriber> toSave) throws SQLException {
            String query = "INSERT INTO subscribers (subscriber_id, subscriber_name, sex) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (Subscriber subscriber : toSave) {
                    preparedStatement.setInt(1, subscriber.getSubscriberId()); // Utilisation de getSubscriberId au lieu de getId_user
                    preparedStatement.setString(2, subscriber.getSubscriberName()); // Utilisation de getSubscriberName au lieu de getName
                    preparedStatement.setString(3, String.valueOf(subscriber.getSex())); // Utilisation de getSex
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return toSave;
        }

        @Override
        public Subscriber save(Subscriber toSave) throws SQLException {
            String query = "INSERT INTO subscribers (subscriber_id, subscriber_name, sex) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, toSave.getSubscriberId());
                preparedStatement.setString(2, toSave.getSubscriberName());
                preparedStatement.setString(3, String.valueOf(toSave.getSex()));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return toSave;
        }

        @Override
        public Subscriber delete(Subscriber toDelete) throws SQLException {
            String sql = "DELETE FROM subscribers WHERE subscriber_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, toDelete.getSubscriberId()); // Utilisation de getSubscriberId
                preparedStatement.executeUpdate();
            }
            System.out.println("DELETE 01");
            return toDelete;
        }

        private void convertToList(List<Subscriber> allSubs, ResultSet result) throws SQLException {
            allSubs.add(new Subscriber(
                    result.getInt("subscriber_id"),
                    result.getString("subscriber_name"),
                    result.getString("sex").charAt(0) // Utilisation de charAt(0) pour obtenir le char à partir de la chaîne
            ));
        }
    }


