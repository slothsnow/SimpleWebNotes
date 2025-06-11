package slothsnow.simpleWebNotes;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("OAUTH_TOKENS")
public class OauthToken {
    @Id
    private String token;
    private User user;
    private LocalDateTime expirationTime;
    private LocalDateTime createdAt;

    public OauthToken() {}

    public OauthToken(String token, User user, LocalDateTime expirationTime) {
        this.token = token;
        this.user = user;
        this.expirationTime = expirationTime;
        this.createdAt = LocalDateTime.now();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}