package com.oauth.server.database.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "used")
    private Boolean isUsed;

    @Column(name = "otp" , nullable = false)
    private String otp;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OTP otp = (OTP) o;
        return id != null && Objects.equals(id, otp.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @PrePersist
    public void createDate(){
        this.createDate = new Date();
    }
}
