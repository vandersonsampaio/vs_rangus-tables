package br.com.vs.rangus.tables.repository;

import br.com.vs.rangus.tables.model.Occupation;
import br.com.vs.rangus.tables.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOccupationRepository extends JpaRepository<Occupation, String> {

    List<Occupation> findAllByTableAndFinishIsNull(Table table);
}
