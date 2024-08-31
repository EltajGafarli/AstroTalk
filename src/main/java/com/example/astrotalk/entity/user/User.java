package com.example.astrotalk.entity.user;

import com.example.astrotalk.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "USER_EMAIL_UNIQUE", columnNames = "email"),
                @UniqueConstraint(name = "USER_USERNAME_UNIQUE", columnNames = "userName")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User extends BaseEntity implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private boolean privacyPolicyAccepted;
    private boolean isEnabled = false;

    @OneToMany(
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "user"
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = {
            CascadeType.ALL
    }, mappedBy = "user")
    private com.example.astrotalk.entity.user.UserDetails userDetails;

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.firstName,
                this.lastName,
                this.userName,
                this.email,
                this.password,
                this.privacyPolicyAccepted
        );
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof User user)) {
            return false;
        }

        return Objects.equals(id, user.id)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(userName, user.userName)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(privacyPolicyAccepted, user.privacyPolicyAccepted);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleEnum().name()))
                .toList();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void addRole(Role role) {
        if(this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
        role.setUser(this);
    }
}
