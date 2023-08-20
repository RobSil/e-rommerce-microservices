package com.robsil.userservice.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robsil.erommerce.userentityservice.data.domain.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto {

    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("date_of_birth")
    private LocalDateTime dateOfBirth;

    private Gender gender;

    private boolean emailConfirmed;

}
