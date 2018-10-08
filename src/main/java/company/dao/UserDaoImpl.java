package company.dao;

import company.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public UserDaoImpl(Connection connection) {
        super(connection);
    }


    @Override
    public User addUser(User user) {

        String query = "INSERT INTO USERS (EMAIL, TOKEN, PASSWORD, FIRST_NAME, LAST_NAME) VALUES(?,?,?,?,?)";
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getToken());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        String query = "SELECT ID, EMAIL, TOKEN, PASSWORD, FIRST_NAME, LAST_NAME FROM USERS WHERE EMAIL = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        User user = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            user = resultSet.next() ? getObjectFromResultSet(resultSet) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findByToken(String token) {

        String query = "SELECT ID, EMAIL, TOKEN, PASSWORD, FIRST_NAME, LAST_NAME FROM USERS WHERE TOKEN = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        User user = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, token);
            resultSet = statement.executeQuery();
            user = resultSet.next() ? getObjectFromResultSet(resultSet) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    protected User getObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6));
    }
}
