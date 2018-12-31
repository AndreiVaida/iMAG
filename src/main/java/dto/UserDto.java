package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Integer id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 1)
    private String name;

    @NotNull
    @Length(min = 1)
    private String password;

    private String birthDay;
}
