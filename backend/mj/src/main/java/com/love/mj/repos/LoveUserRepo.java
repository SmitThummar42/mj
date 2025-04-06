package com.love.mj.repos;

import com.love.mj.entity.LoveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoveUserRepo extends JpaRepository<LoveUser, Integer> {
    Optional<LoveUser> findByUsername(String username);

    Optional<LoveUser> findByUniqueCode(String uniqueCode);
}
