package ru.diasoft.micro.persist;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author Zverev Artem
 */


@Entity
@Table(name = "t_contact")
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_contact_seq")
    @SequenceGenerator(name = "t_contact_seq", sequenceName = "t_contact_seq", allocationSize = 1, initialValue = 1001)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "contact_type_id")
    private ContactTypeEntity contactType;

    @Column(name = "value")
    private String value;

}
