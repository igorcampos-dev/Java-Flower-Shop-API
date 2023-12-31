package com.flowershop.back.services.repo.impl;

import com.flowershop.back.configuration.enums.StatusUser;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.exceptions.UserAlreadyExistsException;
import com.flowershop.back.exceptions.UserNotFoundException;
import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.services.repo.UserMethodsDbs;
import org.springframework.stereotype.Service;

@Service
public class UserMethodsDbsImpl implements UserMethodsDbs {

    private final UserRepository userRepository;

    public UserMethodsDbsImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(String id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não existe"));
    }

    @Override
    public User findByHash(String hash){
        return userRepository.findByHash(hash).orElseThrow(() -> new UserNotFoundException("Usuário não foi encontrado ao salvar a sua atividade"));
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("Usuário não existe."));
    }

    @Override
    public User findByLoginAndHash(String email, String hash) {
        return userRepository.findByLoginAndHash(email, hash).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public void loginExists(String login) {
        userRepository.findByLogin(login).ifPresent(s -> {
            throw new UserAlreadyExistsException("Já existe um Usuário com certas informações. Por favor, escolha credenciais diferentes.");
        });
    }

    @Override
    public void userExistsByHash(String hash) {
        if (userRepository.findByHash(hash).isEmpty()) {
            throw new UserNotFoundException("Usuário não encontrado para o hash: ");
        }
    }

    @Override
    public void alterStatusUserByHash(String hash) {
        userRepository.findByHash(hash)
                .filter(user -> user.getStatus() == StatusUser.P)
                .ifPresent(user -> {
                    user.setStatus(StatusUser.A);
                    userRepository.save(user);
                });
    }
}
