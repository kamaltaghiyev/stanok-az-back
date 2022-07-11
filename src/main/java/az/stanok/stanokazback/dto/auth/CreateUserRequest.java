package az.stanok.stanokazback.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest implements Serializable {

    @NotNull(message = "Это поле не может быть пустым")
    private String email;
    @NotNull(message = "Это поле не может быть пустым")
    private String password;

}
