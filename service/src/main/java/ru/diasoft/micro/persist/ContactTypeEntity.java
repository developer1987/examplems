package ru.diasoft.micro.persist;

import lombok.*;
import org.omg.CORBA.UserException;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * @author Zverev Artem
 */


@Entity
@Table(name = "t_contact_type")
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ContactTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_contact_type_seq")
    @SequenceGenerator(name = "t_contact_type_seq", sequenceName = "t_contact_type_seq", allocationSize = 1, initialValue = 1001)
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

}
