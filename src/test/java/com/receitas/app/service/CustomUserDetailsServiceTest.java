package com.receitas.app.service;

import com.receitas.app.model.Usuario;
import com.receitas.app.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomUserDetailsService - testes unitários")
class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private CustomUserDetailsService service;

    @Test
    @DisplayName("Deve carregar usuário ativo com sucesso")
    void deveCarregarUsuarioAtivo() {
        service = new CustomUserDetailsService(usuarioRepository);

        Usuario usuario = Usuario.builder()
                .id(1L).nome("Administrador").login("admin")
                .senha("admin123").situacao(Usuario.Situacao.ATIVO).build();

        when(usuarioRepository.findByLogin("admin")).thenReturn(Optional.of(usuario));

        UserDetails details = service.loadUserByUsername("admin");

        assertThat(details.getUsername()).isEqualTo("admin");
        assertThat(details.getAuthorities()).hasSize(1);
        assertThat(details.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_USER");
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não existe")
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        service = new CustomUserDetailsService(usuarioRepository);

        when(usuarioRepository.findByLogin("inexistente")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.loadUserByUsername("inexistente"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Usuário não encontrado");
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário está inativo")
    void deveLancarExcecaoQuandoUsuarioInativo() {
        service = new CustomUserDetailsService(usuarioRepository);

        Usuario usuario = Usuario.builder()
                .id(2L).nome("Inativo").login("inativo")
                .senha("123").situacao(Usuario.Situacao.INATIVO).build();

        when(usuarioRepository.findByLogin("inativo")).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> service.loadUserByUsername("inativo"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("inativo");
    }
}
