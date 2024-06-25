package capstone_demo.domain.resident;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResidentDto {

   private String name;
   private String address;
   private String birth;

   public Resident toEntity(){
      return new Resident().builder()
              .name(name)
              .address(address)
              .birth(birth)
              .build();

   }
}
