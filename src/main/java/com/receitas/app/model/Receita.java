package com.receitas.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "receita")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_registro")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataRegistro;

    @NotNull(message = "Custo é obrigatório")
    @DecimalMin(value = "0.01", message = "Custo deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal custo;

    @NotNull(message = "Tipo de receita é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_receita", nullable = false)
    private TipoReceita tipoReceita;

    @PrePersist
    public void prePersist() {
        if (dataRegistro == null) {
            dataRegistro = LocalDate.now();
        }
    }

    public enum TipoReceita {
        DOCE("Doce"),
        SALGADO("Salgado");

        private final String label;

        TipoReceita(String label) { this.label = label; }

        public String getLabel() { return label; }
    }
}
