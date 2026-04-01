package com.receitas.app.controller;

import com.receitas.app.model.Receita;
import com.receitas.app.service.ReceitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/receitas")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService receitaService;

    @GetMapping
    public String listar(@RequestParam(required = false) String tipo,
                         @RequestParam(required = false) String busca,
                         Model model) {
        if (busca != null && !busca.isBlank()) {
            model.addAttribute("receitas", receitaService.buscarPorNome(busca));
            model.addAttribute("busca", busca);
        } else if (tipo != null && !tipo.isBlank()) {
            model.addAttribute("receitas", receitaService.listarPorTipo(Receita.TipoReceita.valueOf(tipo)));
            model.addAttribute("tipoFiltro", tipo);
        } else {
            model.addAttribute("receitas", receitaService.listarTodas());
        }
        model.addAttribute("tipos", Receita.TipoReceita.values());
        return "receitas/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("receita", new Receita());
        model.addAttribute("tipos", Receita.TipoReceita.values());
        model.addAttribute("action", "Nova");
        return "receitas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Receita receita,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("tipos", Receita.TipoReceita.values());
            model.addAttribute("action", receita.getId() == null ? "Nova" : "Editar");
            return "receitas/form";
        }
        receitaService.salvar(receita);
        redirectAttributes.addFlashAttribute("sucesso", "Receita salva com sucesso!");
        return "redirect:/receitas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return receitaService.buscarPorId(id).map(r -> {
            model.addAttribute("receita", r);
            model.addAttribute("tipos", Receita.TipoReceita.values());
            model.addAttribute("action", "Editar");
            return "receitas/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("erro", "Receita não encontrada.");
            return "redirect:/receitas";
        });
    }

    @GetMapping("/visualizar/{id}")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return receitaService.buscarPorId(id).map(r -> {
            model.addAttribute("receita", r);
            return "receitas/detalhe";
        }).orElseGet(() -> {
            ra.addFlashAttribute("erro", "Receita não encontrada.");
            return "redirect:/receitas";
        });
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        if (receitaService.existePorId(id)) {
            receitaService.deletar(id);
            ra.addFlashAttribute("sucesso", "Receita removida com sucesso!");
        } else {
            ra.addFlashAttribute("erro", "Receita não encontrada.");
        }
        return "redirect:/receitas";
    }
}
