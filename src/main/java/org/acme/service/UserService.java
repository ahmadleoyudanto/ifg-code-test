package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.UserMessage;
import org.acme.entity.User;
import org.acme.repository.UserRepository;

import java.util.Optional;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @Transactional
    public void save(UserMessage user) {
        User userEntity = new User();
        userEntity.setName(user.getName());
        userRepository.persist(userEntity);
    }

    @Transactional
    public void edit(UserMessage user) {
        Optional<User> userEntity = userRepository.findByIdOptional(user.getId());
        if (userEntity.isPresent()) {
            User userEntityToEdit = userEntity.get();
            userEntityToEdit.setName(user.getName());
            userRepository.persist(userEntityToEdit);
        }
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
