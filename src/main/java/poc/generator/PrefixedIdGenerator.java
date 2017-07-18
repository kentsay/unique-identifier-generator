package poc.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class PrefixedIdGenerator implements IdentifierGenerator {

  private String DEFAULT_SEQUENCE_NAME = "user_auto_bigint_seq";

  @Override
  public Serializable generate(SessionImplementor sessionImpl, Object data)
      throws HibernateException {
    Serializable result = null;
    try {
      Connection connection = null;
      Statement statement = null;
      ResultSet resultSet = null;

      long prefix = 493000000000L;
      connection = sessionImpl.connection();
      statement = connection.createStatement();
      String insertStatement = "INSERT INTO " + DEFAULT_SEQUENCE_NAME + " VALUES (NULL)";
      try {
        statement.executeUpdate(insertStatement, Statement.RETURN_GENERATED_KEYS);
        resultSet = statement.getGeneratedKeys();
      } catch (Exception ex) {
        // if sequence table is not found then create it
        statement = connection.createStatement();
        statement.execute(
            "CREATE TABLE `" + DEFAULT_SEQUENCE_NAME + "` ("
            + "  `id` bigint(20) NOT NULL AUTO_INCREMENT,"
            + "  PRIMARY KEY (`id`)"
            + ") ENGINE=InnoDB");
        System.out.println("Sequence table created successfully");
        statement.executeUpdate(insertStatement, Statement.RETURN_GENERATED_KEYS);
        resultSet = statement.getGeneratedKeys();
      }

      if (resultSet.next()) {
        long nextValue = resultSet.getLong(1);
        result = prefix + nextValue;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }
}
