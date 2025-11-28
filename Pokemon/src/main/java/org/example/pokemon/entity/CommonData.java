package org.example.pokemon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Version;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Embeddable
public class CommonData {

    @Version
    private Long version;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "last_modified_date_time")
    private LocalDateTime lastModified;
}
