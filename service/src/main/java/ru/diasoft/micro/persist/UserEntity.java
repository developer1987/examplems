package ru.diasoft.micro.persist;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author Zverev Artem
 */


@Entity
@Table(name = "t_user")
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_user_seq")
    @SequenceGenerator(name = "t_user_seq", sequenceName = "t_user_seq", allocationSize = 1, initialValue = 1001)
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<ContactEntity> contacts;
}
