package org.example.pokemon.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User  implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String username;
    private String email;
    @ToString.Exclude
    private String password;
    private List<UserRole> roles;

}
