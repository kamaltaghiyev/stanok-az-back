package az.stanok.stanokazback.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthorizationUserResponse implements Serializable {
    @JsonProperty("username")
    private String email;
    private String token;
}