package com.love.mj.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MessageBucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToMany
    @JoinTable(
            name = "message_bucket_users",
            joinColumns = @JoinColumn(name = "bucket_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<LoveUser> users;

    @OneToMany(mappedBy = "bucket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoveMessages> messages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<LoveUser> getUsers() {
        return users;
    }

    public void setUsers(List<LoveUser> users) {
        this.users = users;
    }

    public List<LoveMessages> getMessages() {
        return messages;
    }

    public void setMessages(List<LoveMessages> messages) {
        this.messages = messages;
    }
}
