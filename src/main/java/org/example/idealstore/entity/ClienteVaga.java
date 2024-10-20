package org.example.idealstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "clientes_tem_vagas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ClienteVaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 15)
    private String recibo;

    @Column(nullable = false, length = 8)
    private String placa;

    @Column(nullable = false, length = 45)
    private String marca;

    @Column(nullable = false, length = 45)
    private String modelo;

    @Column(nullable = false, length = 45)
    private String cor;

    @Column(nullable = false)
    private LocalDateTime dataEntrada;

    private LocalDateTime dataSaida;

    @Column(columnDefinition = "decimal(7,2)")
    private BigDecimal valor;

    @Column(columnDefinition = "decimal(7,2)")
    private BigDecimal desconto;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga vaga;

    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataModificacao;

    @CreatedBy
    private String criadoPor;

    @LastModifiedBy
    private String modificadoPor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteVaga that = (ClienteVaga) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
