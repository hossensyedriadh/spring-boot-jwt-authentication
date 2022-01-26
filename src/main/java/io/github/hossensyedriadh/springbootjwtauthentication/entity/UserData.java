package io.github.hossensyedriadh.springbootjwtauthentication.entity;

import io.github.hossensyedriadh.springbootjwtauthentication.enumerator.Authority;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@Entity
@Table(name = "user_data")
public final class UserData implements Serializable {
    @Serial
    private static final long serialVersionUID = -7587280964588337891L;

    @Id
    @Column(name = "username", nullable = false, length = 75)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false)
    private Authority authority;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled = false;
}