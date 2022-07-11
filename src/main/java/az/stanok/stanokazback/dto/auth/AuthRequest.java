package az.stanok.stanokazback.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest implements Serializable {
    @NotNull(message = "email не может быть пустым")
    private String email;

    @NotNull(message = "Пароль не может быть пустой")
    private String password;
}
