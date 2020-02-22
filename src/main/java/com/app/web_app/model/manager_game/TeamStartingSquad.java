package com.app.web_app.model.manager_game;

        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "team_id", "squad_id"}), name = "team_starting_squads")
public class TeamStartingSquad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;
}
