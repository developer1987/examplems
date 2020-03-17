package ru.diasoft.micro.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Zverev Artem
 */

@Data
@Builder
public class ContactDto {

    private Long id;
    private String type;
    private String value;
}
