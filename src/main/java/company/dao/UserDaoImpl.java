package company.dao;


import company.model.User;
import static company.model.Role.RoleName.USER;

import java.sql.*;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public UserDaoImpl(Connection connection) {
        super(connection);
    }


    @Override
    public User addUser(User user) {

        String userQuery = "INSERT INTO USERS (EMAIL, TOKEN, PASSWORD, FIRST_NAME, LAST_NAME) VALUES(?,?,?,?,?)";
        String roleQuery = "INSERT INTO USER_TO_ROLE (FK_USER_ID, FK_ROLE_ID) VALUES(?,?)";
        PreparedStatement userStatement;
        PreparedStatement rolesStatement;



        try {
            connection.setAutoCommit(false);
            userStatement = connection.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, user.getEmail());
            userStatement.setString(2, user.getToken());
            userStatement.setString(3, user.getPassword());
            userStatement.setString(4, user.getFirstName());
            userStatement.setString(5, user.getLastName());
            userStatement.executeUpdate();

            ResultSet rs = userStatement.getGeneratedKeys();
            long userId = 0;
            if (rs.next()) {
                userId = rs.getLong(1);
            } else {
                connection.rollback();
            }


            rolesStatement = connection.prepareStatement(roleQuery);
            rolesStatement.setLong(1, userId);
            rolesStatement.setString(2, USER.toString());
            rolesStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e.getMessage());
            }

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
