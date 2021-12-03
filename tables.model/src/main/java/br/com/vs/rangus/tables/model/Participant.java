package br.com.vs.rangus.tables.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_participant")
public class Participant {

    @Id
    @Column(name = "id_participant", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_occupation", nullable = false)
    private Occupation occupation;

    @Column(name = "id_user", nullable = false)
    private String user;

    @Column(name = "dt_sit")
    private LocalDateTime dateTimeSit;
}
