package com.love.mj.entity;

import jakarta.persistence.*;

@Entity
public class BucketAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private LoveUser user;

    @ManyToOne
    @JoinColumn(name = "bucket_id", nullable = false)
    private MessageBucket bucket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LoveUser getUser() {
        return user;
    }

    public void setUser(LoveUser user) {
        this.user = user;
    }

    public MessageBucket getBucket() {
        return bucket;
    }

    public void setBucket(MessageBucket bucket) {
        this.bucket = bucket;
    }
}