package capstone_demo.domain.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class ResidentId implements Serializable{
    @Column(name = "resident_name")
    private String name;
    @Column(name = "resident_address")
    private String address;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj==null || getClass() != obj.getClass()) return false;
        ResidentId residentId = (ResidentId) obj;
        return name.equals(residentId.name) &&
                address.equals(residentId.address);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
