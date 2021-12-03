package br.com.vs.rangus.tables.model;

import br.com.vs.rangus.tables.enums.StatusTable;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@javax.persistence.Table(name = "tb_table")
public class Table {

    @Id
    @Column(name = "id_table", nullable = false)
    private String id;

    @Column(name = "id_merchant", nullable = false)
    private String merchant;

    @Column(name = "ds_label",nullable = false)
    private String label;

    @Column(name = "ds_qr_code", nullable = false)
    private String qrCode;

    @Column(name = "ds_status", nullable = false)
    @Setter
    private StatusTable status;

    @Column(name = "dt_register", nullable = false)
    private LocalDateTime register;
}
