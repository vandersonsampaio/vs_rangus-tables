package br.com.vs.rangus.tables.repository;

import br.com.vs.rangus.tables.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITableRepository extends JpaRepository<Table, String> {
}
