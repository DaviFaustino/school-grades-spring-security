package com.davifaustino.schoolgradesspringsecurity.infrastructure;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.davifaustino.schoolgradesspringsecurity.domain.User;

public interface UserRepository extends CrudRepository<User, String> {
    
    @Modifying
    @Query("INSERT INTO tb_users VALUES (:#{#user.username}, :#{#user.password}, :#{#user.role.name})")
    void saveUser(@Param("user") User user);
}
