package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.dto.ContactDto;
import ru.diasoft.micro.dto.UserDto;
import ru.diasoft.micro.persist.ContactEntity;
import ru.diasoft.micro.persist.ContactTypeEntity;
import ru.diasoft.micro.persist.UserEntity;
import ru.diasoft.micro.repository.ContactTypeRepository;
import ru.diasoft.micro.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Zverev Artem
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactTypeRepository contactTypeRepository;

    @Override
    public UserDto create(UserDto userDto) {
        UserEntity userEntity = UserEntity.builder()
                .name(userDto.getName())
                .contacts(new HashSet<>())
                .build();

        userDto.getContacts().forEach( dto -> {  // перебираем контакты
            Optional<ContactTypeEntity> contactTypeOptional = contactTypeRepository.findFirstByNameEquals(dto.getType());
            ContactTypeEntity contactTypeEntity;
            if (!contactTypeOptional.isPresent()) { // если нет контакта, то сохраняем
                contactTypeEntity = contactTypeRepository.save(ContactTypeEntity.builder().name(dto.getType()).build());
            } else {
                contactTypeEntity = ContactTypeEntity.builder()
                        .id(contactTypeOptional.get().getId())
                        .name(dto.getType())
                        .build();
            }

            ContactEntity contactEntity = ContactEntity.builder()
                    .contactType(contactTypeEntity)
                    .user(userEntity)
                    .value(dto.getValue())
                    .build();

            userEntity.getContacts().add(contactEntity);
        });
        UserEntity newUserEntity = userRepository.save(userEntity);
        return remapUser(newUserEntity);
    }

    private UserDto remapUser(UserEntity newUserEntity) {
        return UserDto.builder()
                .id(newUserEntity.getId())
                .name(newUserEntity.getName())
                .contacts(newUserEntity.getContacts().stream().map(entity -> ContactDto.builder()
                        .id(entity.getId())
                        .type(entity.getContactType().getName())
                        .value(entity.getValue())
                        .build()).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Iterable<UserDto> findAll() {
        return userRepository.findAll().stream().map(this::remapUser).collect(Collectors.toSet());
    }
}
