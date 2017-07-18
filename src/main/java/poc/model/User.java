package poc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
public class User {
  private Long id;
  private String name;

  public User() { }

  public User(String name) {
    this.name = name;
  }

  @Id
  @GenericGenerator(
      name = "assigned-sequence",
      strategy = "poc.generator.StringSequenceGenerator",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_name", value = "hibernate_sequence"),
          @org.hibernate.annotations.Parameter(
              name = "sequence_prefix", value = "CTC_"),
      }
  )
  @GeneratedValue(
      generator = "assigned-sequence")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
