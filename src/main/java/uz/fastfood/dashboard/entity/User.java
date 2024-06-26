package uz.fastfood.dashboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.fastfood.dashboard.entity.enums.CurrierStatus;
import uz.fastfood.dashboard.entity.enums.Status;
import uz.fastfood.dashboard.entity.template.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails, Serializable {

    @Column(nullable = false)
    private String firstname;
    private String lastname;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private int orderVolume;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Token> tokens;

    @JoinColumn(name = "branch_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Branch branch;
    @Enumerated(EnumType.STRING)
    private CurrierStatus currierStatus = CurrierStatus.OFFLINE;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
        return true;
    }

}
