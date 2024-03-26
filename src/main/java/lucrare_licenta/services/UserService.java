package lucrare_licenta.services;

import lucrare_licenta.entities.UserEntity;
import lucrare_licenta.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity updateUser(UUID id, UserEntity newUser) {
        UserEntity existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            newUser.setId(existingUser.getId()); // Make sure the ID remains the same
            return userRepository.save(newUser);
        }
        return null; // Or throw an exception, handle as needed
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}