package io.github.hossensyedriadh.springbootjwtauthentication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public final class RefreshToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665129278607404295L;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Lob
    @Column(name = "token", nullable = false)
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user", nullable = false)
    private UserData user;
}