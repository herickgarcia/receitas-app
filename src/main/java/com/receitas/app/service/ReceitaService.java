package com.receitas.app.service;

import com.receitas.app.model.Receita;
import com.receitas.app.repository.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaRepository receitaRepository;

    public List<Receita> listarTodas() {
        return receitaRepository.findAll();
    }

    public List<Receita> listarPorTipo(Receita.TipoReceita tipo) {
        return receitaRepository.findByTipoReceita(tipo);
    }

    public List<Receita> buscarPorNome(String nome) {
        return receitaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Receita> buscarPorId(Long id) {
        return receitaRepository.findById(id);
    }

    public Receita salvar(Receita receita) {
        return receitaRepository.save(receita);
    }

    public void deletar(Long id) {
        receitaRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return receitaRepository.existsById(id);
    }
}
