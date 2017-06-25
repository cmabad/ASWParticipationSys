package es.uniovi.asw.participants.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.uniovi.asw.model.User;


@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findUserByEmailAndPassword(String email, String password);
}