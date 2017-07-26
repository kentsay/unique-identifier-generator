package poc.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManager",
    basePackages = {"poc.repository"})
@EnableConfigurationProperties(HibernateProperties.class)
public class DataSourceConfiguration {

  @Autowired(required = false)
  private PersistenceUnitManager persistenceUnitManager;

  @Primary
  @Bean(name = "dataSource", destroyMethod = "close")
  @Qualifier("dataSource")

  public DataSource dataSource() {
    HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/poc_id_generator?characterEncoding=UTF-8&amp;useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
    hikariDataSource.setUsername("mysql");
    hikariDataSource.setPassword("mysql");
    hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    hikariDataSource.setMaximumPoolSize(500);
    hikariDataSource.setIdleTimeout(10000);
    return hikariDataSource;
  }

  @Primary
  @Bean(name = "entityManagerFactory")
  @Qualifier("entityManagerFactory")
  LocalContainerEntityManagerFactoryBean entityManagerFactory(
      @Qualifier("dataSource") final DataSource dataSource,
      HibernateProperties jpaProperties) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setPackagesToScan("poc.model");
    entityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManager);
    entityManagerFactoryBean.setPersistenceUnitName("persistence");
    AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    entityManagerFactoryBean.setJpaVendorAdapter(adapter);
    entityManagerFactoryBean.setJpaProperties(jpaProperties.toProperties());
    return entityManagerFactoryBean;
  }

  @Bean(name = "entityManager")
  @Qualifier("entityManager")
  EntityManagerFactory entityManager(
      @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
    return localContainerEntityManagerFactoryBean.getObject();
  }

  @Primary
  @Bean(name = "transactionManager")
  @Qualifier("transactionManager")
  JpaTransactionManager transactionManager(
      @Qualifier("entityManager") EntityManagerFactory entityManager,
      @Qualifier("dataSource") DataSource dataSource) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManager);
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }


}
