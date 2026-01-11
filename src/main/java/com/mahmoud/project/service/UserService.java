package com.mahmoud.project.service;

import com.mahmoud.project.dto.ChangePasswordRequest;
import com.mahmoud.project.dto.RegisterUserRequest;
import com.mahmoud.project.dto.UpdateUserRequest;
import com.mahmoud.project.dto.UserDto;
import com.mahmoud.project.entity.Profile;
import com.mahmoud.project.entity.User;
import com.mahmoud.project.exception.IncorrectPasswordException;
import com.mahmoud.project.exception.UserNotFoundException;
import com.mahmoud.project.mapper.UserMapper;
import com.mahmoud.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public List<UserDto> getAllUsers(String sort) {
        if (sort.isEmpty()) {
            return userRepository.findAllWithProfile()
                    .stream()
                    .map(userMapper::toDto)
                    .toList();
        }

        if (!Set.of("name", "email").contains(sort))
            sort = "name";

        return userRepository.findAllWithProfile(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUser(Long id) {
        UserDto userDto = userRepository.findByIdWithProfile(id)
                .map(userMapper::toDto)
                .orElse(null);

        if (userDto == null) {
            throw new UserNotFoundException();
        }

        return userDto;
    }

    @Transactional
    public UserDto addUserWithProfile(RegisterUserRequest registerUserRequest) {
        Profile profile = new Profile();

        User user = userMapper.toEntity(registerUserRequest);

        profile.setUser(user);
        user.setProfile(profile);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto updateUser(Long id, UpdateUserRequest userRequest) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        userMapper.update(userRequest, user);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional
    public void updateUserPassword(Long id, ChangePasswordRequest passwordequest) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!user.getPassword().equals(passwordequest.getOldPassword())) {
            throw new IncorrectPasswordException();
        }

        user.setPassword(passwordequest.getNewPassword());
        userRepository.save(user);
    }

    @Transactional
    public UserDto patchUser(Long id, UpdateUserRequest userRequest) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        userMapper.patch(userRequest, user);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        userRepository.delete(user);
    }
}
