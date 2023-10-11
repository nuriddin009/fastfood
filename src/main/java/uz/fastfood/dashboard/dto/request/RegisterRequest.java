package uz.fastfood.dashboard.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.fastfood.dashboard.entity.enums.RoleName;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String firstname;
  private String lastname;
  private String username;
  private String password;
}
