package org.example.admin.Impl;

import lombok.RequiredArgsConstructor;
import org.example.admin.GenericRepository;
import org.example.model.admin.TeamBaseDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TeamBaseRepository implements GenericRepository<TeamBaseDto> {

    private final EntityManager entityManager;

    @Override
    public List<TeamBaseDto> findAll() {
        List<Object[]> records = entityManager.createNativeQuery("select * from team_base").getResultList();

        return records
                .stream()
                .map(record -> TeamBaseDto.builder()
                        .name((String) record[0])
                        .countryName((String) record[1])
                        .leagueName((String) record[2])
                        .backgroundUrl((String) record[3])
                        .logoUrl((String) record[4])
                        .shirtColors((String) record[5])
                        .build())
                .collect(Collectors.toList());
    }

    public List<TeamBaseDto> findAllByLeagueName(String name) {

        List<Object[]> records = entityManager.createNativeQuery("select * from team_base where league_name = ?1")
                .setParameter(1, name)
                .getResultList();

        return records
                .stream()
                .map(record -> TeamBaseDto.builder()
                        .name((String) record[0])
                        .countryName((String) record[1])
                        .leagueName((String) record[2])
                        .backgroundUrl((String) record[3])
                        .logoUrl((String) record[4])
                        .shirtColors((String) record[5])
                        .build())
                .collect(Collectors.toList());
    }

    public Optional<TeamBaseDto> findByName(String teamName) {

        List<Object[]> records = entityManager.createNativeQuery("select * from team_base where name = ?1")
                .setParameter(1, teamName)
                .getResultList();

        return records
                .stream()
                .map(record -> TeamBaseDto.builder()
                        .name((String) record[0])
                        .countryName((String) record[1])
                        .leagueName((String) record[2])
//                        .backgroundUrl((String) record[3])
                        .build())
                .findFirst();
    }

//    public List<TeamBaseDto> findAllByNames(String... names) {
//
//
//        final String queryParam = Arrays.stream(names).map(string -> "'" + string + "'").collect(Collectors.joining(",", "in (", ")"));
//
//        System.out.println(queryParam);
//
//        final List<Object[]> records = entityManager.createNativeQuery("select * from team_base where name ?1")
//                .setParameter(1, queryParam).getResultList();
//
//        return records
//                .stream()
//                .map(record -> TeamBaseDto.builder()
//                        .name((String) record[0])
//                        .countryName((String) record[1])
//                        .leagueName((String) record[2])
//                        .build())
//                .collect(Collectors.toList());
//    }
}
