package poc.config;

import java.util.Properties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("db.jpa.hibernate")
public class HibernateProperties {

  private String dialect;
  private String hbm2ddlAuto;
  private boolean showSql;
  private boolean formatSql;

  public String getDialect() {
    return dialect;
  }

  public void setDialect(String dialect) {
    this.dialect = dialect;
  }

  public String getHbm2ddlAuto() {
    return hbm2ddlAuto;
  }

  public void setHbm2ddlAuto(String hbm2ddlAuto) {
    this.hbm2ddlAuto = hbm2ddlAuto;
  }

  public boolean isShowSql() {
    return showSql;
  }

  public void setShowSql(boolean showSql) {
    this.showSql = showSql;
  }

  public boolean isFormatSql() {
    return formatSql;
  }

  public void setFormatSql(boolean formatSql) {
    this.formatSql = formatSql;
  }

  @Override
  public String toString() {
    return "HibernateProperties{" +
        "dialect='" + dialect + '\'' +
        ", hbm2ddlAuto='" + hbm2ddlAuto + '\'' +
        ", showSql=" + showSql +
        ", formatSql=" + formatSql +
        '}';
  }

  Properties toProperties() {
    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.dialect", dialect);
    // TODO: nader: Should we externalize this to yml, also why are we disabling it? Can the hibernate cache be
    // used for passive objects instead of in memory cacheable annotation and turned off for active models?
    jpaProperties.put("hibernate.cache.use_second_level_cache", false);
    jpaProperties.put("hibernate.cache.use_query_cache", false);
    jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
    jpaProperties.put("hibernate.show_sql", showSql);
    jpaProperties.put("hibernate.format_sql", formatSql);
    return jpaProperties;
  }
}
