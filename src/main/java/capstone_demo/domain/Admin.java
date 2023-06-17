package capstone_demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
public class Admin implements UserDetails {

    @Id @Column(name = "admin_id")
    private String id;
    @Column(name = "admin_password")
    private String password;
    private String residence;
    private String zipcode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorities;
    }
    public List<String> getRoles(){

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        return roles;
    }
    public static Admin create(String id, String password, String residence, String zipcode){
        Admin admin = new Admin();
        admin.setId(id);
        admin.setPassword(password);
        admin.setResidence(residence);
        admin.setZipcode(zipcode);
        return admin;
    }

    @Override
    public String getUsername() {
        return id;
    }
    public String getPassword() {return password;}

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
