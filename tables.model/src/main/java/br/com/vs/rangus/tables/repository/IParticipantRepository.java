package br.com.vs.rangus.tables.repository;

import br.com.vs.rangus.tables.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IParticipantRepository extends JpaRepository<Participant, String> {
}
