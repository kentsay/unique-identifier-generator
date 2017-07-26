package poc.generator;


import com.sun.corba.se.spi.ior.Identifiable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class SeqPrefixedIdGenerator extends SequenceStyleGenerator {

  private final String PREFIX = "prefix";
  private String prefix_value;

  @Override
  public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
    prefix_value = params.getProperty(PREFIX);
    super.configure(type, params, serviceRegistry);
  }

  @Override
  public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {

    if (obj instanceof Identifiable) {
      Identifiable identifiable = (Identifiable) obj;
      Serializable id = identifiable.getId();
      if (id != null) {
        return id;
      }
    }

    long prefix = Long.parseLong(prefix_value);
    return Long.parseLong(String.valueOf(prefix + (Long) super.generate(session, obj)));
  }
}
