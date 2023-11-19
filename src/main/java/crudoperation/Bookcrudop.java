package crudoperation;

import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bookcrudop implements CrudOperations<Book> {





        private Connection connection;

        public BookCrud(Connection connection) {
            this.connection = connection;
        }

        @Override
        public List<Book> findAll() throws SQLException {
            List<Book> allBooks = new ArrayList<>();
            String sql = "SELECT * FROM Book";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    convertToList(allBooks, result);
                }
            }
            return allBooks;
        }

        @Override
        public List<Book> saveAll(List<Book> toSave) throws SQLException {
            String query = "INSERT INTO Book (book_id, book_name, topic, page_number, release_date, author_id, is_borrow) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (Book book : toSave) {
                    preparedStatement.setString(1, book.getBookId());
                    preparedStatement.setString(2, book.getBookName());
                    preparedStatement.setObject(3, book.getTopic().name(), Types.OTHER);
                    preparedStatement.setInt(4, book.getPageNumber());
                    preparedStatement.setTimestamp(5, Timestamp.valueOf(book.getReleaseDate()));
                    preparedStatement.setInt(6, book.getAuthorId());
                    preparedStatement.setBoolean(7, book.isBorrow());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return toSave;
        }

        @Override
        public Book save(Book toSave) throws SQLException {
            String query = "INSERT INTO Book (book_id, book_name, topic, page_number, release_date, author_id, is_borrow) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, toSave.getBookId());
                preparedStatement.setString(2, toSave.getBookName());
                preparedStatement.setObject(3, toSave.getTopic().name(), Types.OTHER);
                preparedStatement.setInt(4, toSave.getPageNumber());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(toSave.getReleaseDate()));
                preparedStatement.setInt(6, toSave.getAuthorId());
                preparedStatement.setBoolean(7, toSave.isBorrow());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return toSave;
        }

        @Override
        public Book delete(Book toDelete) throws SQLException {
            String sql = "DELETE FROM Book WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, toDelete.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return toDelete;
        }

        private void convertToList(List<Book> allBooks, ResultSet result) throws SQLException {
            allBooks.add(new Book(
                    result.getInt("id"),
                    result.getString("book_id"),
                    result.getString("book_name"),
                    Topic.valueOf(result.getString("topic")),
                    result.getInt("page_number"),
                    result.getTimestamp("release_date").toLocalDateTime(),
                    result.getInt("author_id"),
                    result.getBoolean("is_borrow")
            ));
        }
    }


