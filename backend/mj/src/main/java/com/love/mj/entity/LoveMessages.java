package com.love.mj.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Entity
public class LoveMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(1) // Minimum allowed value
    @Max(15) // Maximum allowed value
    private int priority;

    @Column(nullable = false, length = 255) // Ensures text cannot be null and limits its length
    private String messageText;

    @ManyToOne
    @JoinColumn(name = "bucket_id", nullable = false)
    private MessageBucket bucket;

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public MessageBucket getBucket() {
        return bucket;
    }

    public void setBucket(MessageBucket bucket) {
        this.bucket = bucket;
    }
}

