package com.flowershop.back.run;

import com.flowershop.back.configuration.enums.Role;
import com.flowershop.back.configuration.enums.StatusUser;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.exceptions.UserAlreadyExistsException;
import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MeuCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserServiceImpl usuarioService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        criarUsuarios();
    }

    private void criarUsuarios() {
        criarUsuarioSeNaoExistir("admin@example.com", "senhaAdmin", Role.ADMIN);
        criarUsuarioSeNaoExistir("user@example.com", "senhaUser", Role.USER);
    }

    private void criarUsuarioSeNaoExistir(String login, String senha, Role role) {
        userRepository.findByLogin(login)
                .ifPresentOrElse(
                        user -> {
                            System.out.println("Já existe no banco de dados");
                        },
                        () -> {
                            User novoUsuario = new User();
                            novoUsuario.setRole(role);
                            novoUsuario.setStatus(StatusUser.A);
                            novoUsuario.setLogin(login);
                            novoUsuario.setHash("hash" + role.name()); // Substitua pelo hash desejado
                            novoUsuario.setPassword(passwordEncoder.encode(senha));

                            usuarioService.save(novoUsuario);
                            System.out.println("Usuário " + login + " foi criado e salvo no banco de dados, sua senha é" + senha + ".");
                            System.out.println("Imprimo isto, pois há rotas ao qual apenas adm pode acessar, o crud das flores");
                        }
                );
    }
}