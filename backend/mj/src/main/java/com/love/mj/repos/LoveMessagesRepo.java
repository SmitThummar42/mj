package com.love.mj.repos;

import com.love.mj.entity.LoveMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoveMessagesRepo extends JpaRepository<LoveMessages,Integer> {
}
