package com.flowershop.back.services.impl;

import com.flowershop.back.domain.activities.Activities;
import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.exceptions.UserNotFoundException;
import com.flowershop.back.repositories.ActivitiesRepository;
import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.services.ActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivitiesServiceImpl implements ActivitiesService {

    @Autowired
    ActivitiesRepository activitiesRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public void save(MessageDTO message) {
        User user = userRepository.findByHash(message.hash())
                .orElseThrow(() -> new UserNotFoundException("Usuário não foi encontrado ao salvar a sua atividade"));

        Activities activities = Activities.builder()
                .user(user.getLogin())
                .remittent(message.email())
                .datetime(LocalDateTime.now())
                .build();

        activitiesRepository.save(activities);
    }


    @Override
    public List<ActivitiesResponseDTO> findAllById(String id) {
       User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não existe"));
      return  this.activitiesRepository.findByUser(user.getLogin()).stream().map(ActivitiesResponseDTO::new).toList();
    }
}
