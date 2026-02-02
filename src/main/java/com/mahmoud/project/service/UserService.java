package com.mahmoud.project.service;

import com.mahmoud.project.dto.*;
import com.mahmoud.project.entity.Profile;
import com.mahmoud.project.entity.Role;
import com.mahmoud.project.entity.User;
import com.mahmoud.project.exception.IncorrectPasswordException;
import com.mahmoud.project.exception.UserNotFoundException;
import com.mahmoud.project.mapper.UserMapper;
import com.mahmoud.project.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

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
        return userRepository.findByIdWithProfile(id)
                .map(userMapper::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public UserDto registerUserWithProfile(RegisterUserRequest registerUserRequest) {
        Profile profile = new Profile();

        User user = userMapper.toEntity(registerUserRequest);

        profile.setUser(user);
        user.setProfile(profile);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto updateUser(Long id, UpdateUserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userMapper.update(userRequest, user);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional
    public void updateUserPassword(Long id, ChangePasswordRequest passwordRequest) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public UserDto patchUser(Long id, UpdateUserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userMapper.patch(userRequest, user);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
