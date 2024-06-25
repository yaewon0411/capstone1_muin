package capstone_demo.domain;

import capstone_demo.domain.Id.DelivererId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
@IdClass(DelivererId.class)
@NoArgsConstructor
public class Deliverer implements UserDetails {
    @Id
    @Column(name = "deliverer_id")
    private String id;
    @Id
    @Column(name = "delivery_company")
    private String company;
    @Column(name = "deliverer_name")
    private String name;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
    public List<String> getRoles(){
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        return roles;
    }
    @Override
    public String getPassword() {
        return company;
    }

    @Override
    public String getUsername() {
        return id;
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
