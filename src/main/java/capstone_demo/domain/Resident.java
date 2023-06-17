package capstone_demo.domain;

import capstone_demo.domain.Id.ResidentId;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter @Setter
@IdClass(ResidentId.class)
public class Resident implements UserDetails {
    @Id
    @Column(name = "resident_name")
    private String name;
    @Id
    @Column(name="resident_address")
    private String address;
    @Column(name = "resident_birth")
    private String birth;

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
        return name;
    }

    @Override
    public String getUsername() {
        return birth+address+name;
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

//    @ManyToOne
//    @JoinColumn(name = "admin_id") 이건 나중에 생각하기로
//    private Admin admin;

}
