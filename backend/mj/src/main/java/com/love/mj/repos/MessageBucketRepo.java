package com.love.mj.repos;

import com.love.mj.entity.LoveUser;
import com.love.mj.entity.MessageBucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageBucketRepo extends JpaRepository<MessageBucket, Integer> {
    List<MessageBucket> findAllByUsersContaining(LoveUser user);
}