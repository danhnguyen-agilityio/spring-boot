package com.packt.example.clientauthorizationcode.user;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class ClientUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String acccessToken;
    private Calendar accessTokenValidity;
    private String refreshToken;

    @Transient
    private List<Entry> entries = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAcccessToken() {
        return acccessToken;
    }

    public void setAcccessToken(String acccessToken) {
        this.acccessToken = acccessToken;
    }

    public Calendar getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Calendar accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
