package com.receitas.app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Receita - testes de modelo")
class ReceitaTest {

    @Test
    @DisplayName("Deve preencher data de registro automaticamente quando nula (prePersist)")
    void devePreencherDataRegistroAutomaticamente() {
        Receita receita = Receita.builder()
                .nome("Salada de Frutas")
                .descricao("Frutas variadas")
                .custo(new BigDecimal("10.00"))
                .tipoReceita(Receita.TipoReceita.DOCE)
                .build();

        assertThat(receita.getDataRegistro()).isNull();

        receita.prePersist();

        assertThat(receita.getDataRegistro()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("Não deve sobrescrever data de registro já definida")
    void naoDeveSobrescreverDataExistente() {
        LocalDate dataOriginal = LocalDate.of(2024, 1, 1);
        Receita receita = Receita.builder()
                .nome("Pão Caseiro")
                .descricao("Pão simples")
                .dataRegistro(dataOriginal)
                .custo(new BigDecimal("8.00"))
                .tipoReceita(Receita.TipoReceita.SALGADO)
                .build();

        receita.prePersist();

        assertThat(receita.getDataRegistro()).isEqualTo(dataOriginal);
    }

    @Test
    @DisplayName("Enum TipoReceita deve retornar os labels corretos")
    void tipoReceitaDeveRetornarLabelsCorretos() {
        assertThat(Receita.TipoReceita.DOCE.getLabel()).isEqualTo("Doce");
        assertThat(Receita.TipoReceita.SALGADO.getLabel()).isEqualTo("Salgado");
    }
}
