package poc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(
    name = "prefixed_user",
    indexes = {@Index(name="INDEX_NAME", columnList = "name", unique = false)}
)
public class PrefixedUser {

  private Long id;
  @Column(name="name")
  private String name;

  public PrefixedUser() {
  }

  public PrefixedUser(String name) {
    this.name = name;
  }

  @Id
  @GenericGenerator(
      name = "assigned-sequence",
      strategy = "poc.generator.PrefixedIdGenerator"
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
