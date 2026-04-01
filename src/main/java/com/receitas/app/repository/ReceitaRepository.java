package com.receitas.app.repository;

import com.receitas.app.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    List<Receita> findByTipoReceita(Receita.TipoReceita tipoReceita);
    List<Receita> findByNomeContainingIgnoreCase(String nome);
}
