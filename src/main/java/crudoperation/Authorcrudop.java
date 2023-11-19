package crudoperation;

import model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Authorcrudop implements CrudOperations<Author>{





        private Connection connection;

        public void AuthorCrud(Connection connection) {
            this.connection = connection;
        }

        @Override
        public List<Author> findAll() throws SQLException {
            List<Author> allAuthors = new ArrayList<>();
            String sql = "SELECT * FROM Author";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    convertToList(allAuthors, result);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return allAuthors;
        }

    @Override
    public List<Author> saveAll(List<Author> toSave) {
        return null;
    }

    @Override
        public Author save(Author toSave) throws SQLException {
            String query = "INSERT INTO Author (Author_id, Name, Sex) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, toSave.getId_author());
                preparedStatement.setString(2, toSave.getName());
                preparedStatement.setString(3, toSave.getSex());
                preparedStatement.executeUpdate();
            }
            return toSave;
        }

        @Override
        public List<Author> saveAll(List<Author> toSave) throws SQLException {
            String query = "INSERT INTO Author (Author_id, Name, Sex) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (Author author : toSave) {
                    preparedStatement.setInt(1, author.getId_author());
                    preparedStatement.setString(2, author.getName());
                    preparedStatement.setString(3, author.getSex());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            return toSave;
        }

        @Override
        public Author delete(Author toDelete) throws SQLException {
            String sql = "DELETE FROM Author WHERE Author_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, toDelete.getId_author());
                preparedStatement.executeUpdate();
            }
            return toDelete;
        }

        private void convertToList(List<Author> allAuthors, ResultSet result) throws SQLException {
            allAuthors.add(new Author(
                    result.getInt("Author_id"),
                    result.getString("Name"),
                    result.getString("Sex")
            ));
        }
    }

