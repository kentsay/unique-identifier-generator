package poc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(
    name = "seq_prefixed_user",
    indexes = {@Index(name = "INDEX_NAME", columnList = "name", unique = false)}
)
public class SeqPrefixedUser {

  private Long id;
  @Column(name="name")
  private String name;

  public SeqPrefixedUser() {
  }

  public SeqPrefixedUser(String name) {
    this.name = name;
  }

  @Id
  @GenericGenerator(
      name = "assigned-sequence",
      strategy = "poc.generator.SeqPrefixedIdGenerator",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_name",
              value = "abc"
          ),
          @org.hibernate.annotations.Parameter(
              name = "optimizer",
              value = "pooled"
          ),
          @org.hibernate.annotations.Parameter(
              name = "increment_size",
              value = "50"
          )
      }
  )
  @GeneratedValue(
      generator = "assigned-sequence",
      strategy = GenerationType.SEQUENCE
  )
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
