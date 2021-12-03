package br.com.vs.rangus.tables.model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_occupation")
public class Occupation {

    @Id
    @Column(name = "id_occupation", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_table", nullable = false)
    private br.com.vs.rangus.tables.model.Table table;

    @Column(name = "id_user", nullable = false)
    private String user;

    @Column(name = "dt_initial", nullable = false)
    private LocalDateTime initial;

    @Column(name = "dt_finish")
    @Setter
    private LocalDateTime finish;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_occupation")
    private List<Participant> participants;
}
