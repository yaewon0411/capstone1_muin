package capstone_demo.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@IdClass(ResidentId.class)
public class Resident{
    @Id
    @Column(name = "resident_name")
    private String name;
    @Id
    @Column(name="resident_address")
    private String address;
    @Column(name = "resident_birth")
    private String birth;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities(){
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        return authorities;
//    }
//    @ManyToOne
//    @JoinColumn(name = "admin_id") 이건 나중에 생각하기로
//    private Admin admin;

//    @OneToMany(mappedBy = "resident")
//    private List<Parcel> parcels = new ArrayList<>();

//    @OneToMany(mappedBy = "resident")
//    private List<ParcelHistory> parcelHistories = new ArrayList<>();



//    @Override
//    public String getPassword() {
//        return name;
//    }
//
//    @Override
//    public String getUsername() {
//        return birth+address;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
