package org.example.fm.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "countries")
@Data
public class Country {

    @Id
    private String name;

    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<League> leagues;

    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Player> players;

    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Team> teams;
}
