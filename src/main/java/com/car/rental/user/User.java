package com.car.rental.user;

import com.car.rental.security.Role;
import com.car.rental.rent.Rent;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users", uniqueConstraints=
    @UniqueConstraint(columnNames = {"email"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birth;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Rent> rents;
    //TODO Collections.unmodifiableSet()
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    private boolean activated;
    private boolean deleted;

}
