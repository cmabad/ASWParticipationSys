package es.uniovi.asw.participants.repository;

import org.springframework.stereotype.Repository;

import es.uniovi.asw.model.User;

@Repository
public interface DBManagement {
    User getParticipant(String email, String password);
    void updateInfo(User user);
}