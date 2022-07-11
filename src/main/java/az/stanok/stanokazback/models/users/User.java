package az.stanok.stanokazback.models.users;

import az.stanok.stanokazback.models.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {
    @Column(unique = true, nullable = false)
    private String email;

    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;


    @Enumerated(EnumType.ORDINAL)
    @Column
    private UserRole authority = UserRole.ROLE_USER;


}
