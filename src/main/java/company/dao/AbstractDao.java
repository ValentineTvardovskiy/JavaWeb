package company.dao;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T, Long> implements GenericDao<T, Long> {

    protected final Connection connection;

    protected AbstractDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(T t) {
        String query = createInsertStatementSql();
        Class<?> zclass = t.getClass();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            Field[] fields = zclass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(t);
                statement.setObject((i + 1), value);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T read(Long id) {
        String query = "SELECT * FROM " + this.getClass().getName() + " WHERE ID = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        T result = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, (long)id);
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                result = (T) this.getClass();
                loadResultSetIntoObject(resultSet, result);
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public T update(T t) {
        String query;
        PreparedStatement statement;
        return null;
    }

    @Override
    public void delete(Long t) {
        String query = "DELETE FROM " + this.getClass().getName() + " WHERE NAME = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(query);
            statement.setObject(1, t.getClass().getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> readAll() {
        String query = "SELECT * FROM " + this.getClass().getName();
        Statement statement;
        ResultSet resultSet;
        List<T> result = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                T t = (T) this.getClass();
                loadResultSetIntoObject(resultSet, t);
                result.add(t);
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void loadResultSetIntoObject(ResultSet rst, Object object)
            throws IllegalArgumentException, IllegalAccessException, SQLException
    {
        Class<?> zclass = object.getClass();
        for(Field field : zclass.getDeclaredFields())
        {
            String name = field.getName();
            field.setAccessible(true);
            Object value = rst.getObject(name);
            Class<?> type = field.getType();
            if(isPrimitive(type))
            {
                Class<?> boxed = boxPrimitiveClass(type);
                value = boxed.cast(value);
            }
            field.set(object, value);
        }
    }

    private static boolean isPrimitive(Class<?> type)
    {
        return (type == int.class || type == long.class ||
                type == double.class  || type == float.class
                || type == boolean.class || type == byte.class
                || type == char.class || type == short.class);
    }

    private static Class<?> boxPrimitiveClass(Class<?> type)
    {
        if(type == int.class){return Integer.class;}
        else if(type == long.class){return java.lang.Long.class;}
        else if (type == double.class){return Double.class;}
        else if(type == float.class){return Float.class;}
        else if(type == boolean.class){return Boolean.class;}
        else if(type == byte.class){return Byte.class;}
        else if(type == char.class){return Character.class;}
        else if(type == short.class){return Short.class;}
        else
        {
            String string="class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }

    private String createInsertStatementSql()
    {
        Class<?> zclass = this.getClass();
        StringBuilder fields = new StringBuilder();
        StringBuilder vars = new StringBuilder();
        for(Field field : zclass.getDeclaredFields())
        {
            String name = field.getName();
            if(fields.length()>1)
            {
                fields.append(",");
                vars.append(",");
            }
            fields.append(name);
            vars.append("?");
        }
        String Sql = "INSERT INTO " + this.getClass().getName() + "(" + fields.toString() + ") VALUES(" + vars.toString() + ")";
        return Sql;
    }

    private String createUpdateStatementSql( String primaryKey)
    {
        Class<?> zclass = this.getClass();
        StringBuilder sets = new StringBuilder();
        String where = null;
        for(Field field : zclass.getDeclaredFields())
        {
            String name = field.getName() + " = ?";
            if(name.equals(primaryKey))
            {
                where=" WHERE " + name;
            }
            else
            {
                if(sets.length()>1)
                {
                    sets.append(", ");
                }
                sets.append(name);
            }
        }
        if(where == null)
        {
            String string = "Primary key not found in '" + zclass.getName() + "'";
            throw new IllegalArgumentException(string);
        }
        String Sql = "UPDATE " + this.getClass().getName() + " SET " + sets.toString() + where;
        return Sql;
    }
}
