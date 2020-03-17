package ru.diasoft.micro.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author Zverev Artem
 */

@Data
@Builder
public class UserDto {

    private Long id;
    private String name;
    private Set<ContactDto> contacts;

}
