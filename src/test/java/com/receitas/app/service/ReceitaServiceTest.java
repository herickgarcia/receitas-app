package com.receitas.app.service;

import com.receitas.app.model.Receita;
import com.receitas.app.repository.ReceitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReceitaService - testes unitários")
class ReceitaServiceTest {

    @Mock
    private ReceitaRepository receitaRepository;

    @InjectMocks
    private ReceitaService receitaService;

    private Receita receitaExemplo;

    @BeforeEach
    void setUp() {
        receitaExemplo = Receita.builder()
                .id(1L)
                .nome("Bolo de Chocolate")
                .descricao("Bolo simples de chocolate")
                .dataRegistro(LocalDate.of(2024, 1, 10))
                .custo(new BigDecimal("20.00"))
                .tipoReceita(Receita.TipoReceita.DOCE)
                .build();
    }

    @Test
    @DisplayName("Deve listar todas as receitas cadastradas")
    void deveListarTodasReceitas() {
        when(receitaRepository.findAll()).thenReturn(List.of(receitaExemplo));

        List<Receita> resultado = receitaService.listarTodas();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Bolo de Chocolate");
        verify(receitaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há receitas")
    void deveRetornarListaVaziaQuandoNaoHaReceitas() {
        when(receitaRepository.findAll()).thenReturn(List.of());

        List<Receita> resultado = receitaService.listarTodas();

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve listar receitas filtradas por tipo DOCE")
    void deveListarReceitasPorTipoDoce() {
        when(receitaRepository.findByTipoReceita(Receita.TipoReceita.DOCE))
                .thenReturn(List.of(receitaExemplo));

        List<Receita> resultado = receitaService.listarPorTipo(Receita.TipoReceita.DOCE);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTipoReceita()).isEqualTo(Receita.TipoReceita.DOCE);
    }

    @Test
    @DisplayName("Deve listar receitas filtradas por tipo SALGADO")
    void deveListarReceitasPorTipoSalgado() {
        Receita salgada = Receita.builder()
                .id(2L).nome("Coxinha").descricao("Coxinha de frango")
                .dataRegistro(LocalDate.now()).custo(new BigDecimal("32.00"))
                .tipoReceita(Receita.TipoReceita.SALGADO).build();

        when(receitaRepository.findByTipoReceita(Receita.TipoReceita.SALGADO))
                .thenReturn(List.of(salgada));

        List<Receita> resultado = receitaService.listarPorTipo(Receita.TipoReceita.SALGADO);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Coxinha");
    }

    @Test
    @DisplayName("Deve buscar receitas por nome (ignorando case)")
    void deveBuscarReceitasPorNome() {
        when(receitaRepository.findByNomeContainingIgnoreCase("bolo"))
                .thenReturn(List.of(receitaExemplo));

        List<Receita> resultado = receitaService.buscarPorNome("bolo");

        assertThat(resultado).hasSize(1);
        verify(receitaRepository).findByNomeContainingIgnoreCase("bolo");
    }

    @Test
    @DisplayName("Deve buscar receita por ID existente")
    void deveBuscarReceitaPorIdExistente() {
        when(receitaRepository.findById(1L)).thenReturn(Optional.of(receitaExemplo));

        Optional<Receita> resultado = receitaService.buscarPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio ao buscar receita por ID inexistente")
    void deveRetornarVazioQuandoIdNaoExiste() {
        when(receitaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Receita> resultado = receitaService.buscarPorId(99L);

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve salvar uma nova receita")
    void deveSalvarReceita() {
        when(receitaRepository.save(any(Receita.class))).thenReturn(receitaExemplo);

        Receita salva = receitaService.salvar(receitaExemplo);

        assertThat(salva).isNotNull();
        assertThat(salva.getNome()).isEqualTo("Bolo de Chocolate");
        verify(receitaRepository, times(1)).save(receitaExemplo);
    }

    @Test
    @DisplayName("Deve deletar receita por ID")
    void deveDeletarReceitaPorId() {
        doNothing().when(receitaRepository).deleteById(1L);

        receitaService.deletar(1L);

        verify(receitaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve retornar true quando receita existe pelo ID")
    void deveRetornarTrueQuandoReceitaExiste() {
        when(receitaRepository.existsById(1L)).thenReturn(true);

        boolean existe = receitaService.existePorId(1L);

        assertThat(existe).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando receita não existe pelo ID")
    void deveRetornarFalseQuandoReceitaNaoExiste() {
        when(receitaRepository.existsById(anyLong())).thenReturn(false);

        boolean existe = receitaService.existePorId(123L);

        assertThat(existe).isFalse();
    }
}
