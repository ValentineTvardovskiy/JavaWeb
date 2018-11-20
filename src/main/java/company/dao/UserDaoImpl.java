package company.dao;


import company.model.Role;
import company.model.User;
import static company.model.Role.RoleName.USER;

import java.sql.*;

public class UserDaoImpl extends AbstractDao implements UserDao {

    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User addUser(User user) {
        String userQuery = "INSERT INTO USERS (EMAIL, TOKEN, PASSWORD, FIRST_NAME, LAST_NAME) VALUES (?, ?, ?, ?, ?);";
        String roleQuery = "INSERT INTO USER_TO_ROLE (FK_USER_ID, FK_ROLE_ID) VALUES (?, ?);";
        PreparedStatement userStatement;
        PreparedStatement roleStatement;

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

            roleStatement = connection.prepareStatement(roleQuery);
            roleStatement.setLong(1, userId);
            roleStatement.setString(2, USER.toString());
            roleStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.getMessage();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e.getMessage() + "\n" + e1.getMessage());
            }
        }
        return user;
    }

    @Override
    public User findByToken(String token) {
        String query = "SELECT U.ID, U.EMAIL, U.TOKEN, U.PASSWORD, U.FIRST_NAME, U.LAST_NAME, R.NAME FROM USERS U " +
                "JOIN USER_TO_ROLE UTR ON U.ID = UTR.FK_USER_ID " +
                "JOIN ROLE R ON UTR.FK_ROLE_ID = R.NAME " +
                "WHERE U.TOKEN = ?";
        PreparedStatement statement;
        ResultSet rs;
        User user = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, token);
            rs = statement.executeQuery();
            user = rs.next() ? getUserWithRoles(rs) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private User getUserWithRoles(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6)
        );
        while (!rs.isAfterLast()) {
            Role role = Role.of(rs.getString(7));
            user.addRole(role);
            rs.next();
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        String query = "SELECT ID, EMAIL, TOKEN, PASSWORD, FIRST_NAME, LAST_NAME FROM USERS WHERE EMAIL = ?";
        PreparedStatement statement;
        ResultSet rs;
        User user = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            rs = statement.executeQuery();
            user = rs.next() ? getUser(rs) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private User getUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6));
    }
}
