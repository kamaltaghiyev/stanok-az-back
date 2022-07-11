package az.stanok.stanokazback.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UpdatePasswordRequest implements Serializable {

    @NotEmpty(message = "Old password cannot be empty")
    @Size(min = 6, max = 40, message = "Length of the old password must be between 6 and 40 characters")
    @JsonProperty("old_password")
    private String oldPassword;

    @NotEmpty(message = "New password cannot be empty")
    @Size(min = 6, max = 40, message = "Length of the new password must be between 6 and 40 characters")
    @JsonProperty("new_password")
    private String newPassword;
}
