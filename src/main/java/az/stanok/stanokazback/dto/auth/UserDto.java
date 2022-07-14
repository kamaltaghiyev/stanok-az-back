package az.stanok.stanokazback.dto.auth;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("User Dto")
public class UserDto {
    private Long id;
    private String email;
}
