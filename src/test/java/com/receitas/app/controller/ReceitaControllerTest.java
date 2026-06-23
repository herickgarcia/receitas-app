package com.receitas.app.controller;

import com.receitas.app.model.Receita;
import com.receitas.app.service.ReceitaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceitaController.class)
@DisplayName("ReceitaController - testes de integração (MockMvc)")
class ReceitaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceitaService receitaService;

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("GET /receitas deve retornar a lista de receitas com status 200")
    void deveListarReceitasComSucesso() throws Exception {
        Receita receita = Receita.builder()
                .id(1L).nome("Bolo de Cenoura").descricao("Bolo simples")
                .dataRegistro(LocalDate.now()).custo(new BigDecimal("18.50"))
                .tipoReceita(Receita.TipoReceita.DOCE).build();

        when(receitaService.listarTodas()).thenReturn(List.of(receita));

        mockMvc.perform(get("/receitas"))
                .andExpect(status().isOk())
                .andExpect(view().name("receitas/lista"))
                .andExpect(model().attributeExists("receitas"));
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("GET /receitas/nova deve retornar o formulário de nova receita")
    void deveExibirFormularioNovaReceita() throws Exception {
        mockMvc.perform(get("/receitas/nova"))
                .andExpect(status().isOk())
                .andExpect(view().name("receitas/form"))
                .andExpect(model().attributeExists("receita"))
                .andExpect(model().attribute("action", "Nova"));
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("GET /receitas/visualizar/{id} deve redirecionar quando receita não existe")
    void deveRedirecionarQuandoReceitaNaoExisteAoVisualizar() throws Exception {
        when(receitaService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/receitas/visualizar/99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/receitas"))
                .andExpect(flash().attributeExists("erro"));
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("POST /receitas/deletar/{id} deve remover receita existente e redirecionar com sucesso")
    void deveDeletarReceitaExistente() throws Exception {
        when(receitaService.existePorId(1L)).thenReturn(true);

        mockMvc.perform(post("/receitas/deletar/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/receitas"))
                .andExpect(flash().attributeExists("sucesso"));
    }

    @Test
    @DisplayName("GET /receitas sem autenticação deve redirecionar para login")
    void deveRetornarErroOuRedirecionarSemAutenticacao() throws Exception {
        mockMvc.perform(get("/receitas"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    // import estático necessário para csrf()
    private static org.springframework.test.web.servlet.request.RequestPostProcessor csrf() {
        return org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf();
    }
}
