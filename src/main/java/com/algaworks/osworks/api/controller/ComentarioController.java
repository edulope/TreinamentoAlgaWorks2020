package com.algaworks.osworks.api.controller;


import com.algaworks.osworks.api.model.ComentarioInput;
import com.algaworks.osworks.api.model.ComentarioModel;
import com.algaworks.osworks.api.model.OrdemServicoModel;
import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.model.Comentario;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.ComentarioRepository;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;
import net.bytebuddy.asm.Advice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
    @Autowired
    GestaoOrdemServicoService gestaoOrdemServicoService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    OrdemServicoRepository ordemServicoRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioModel adicionar(@PathVariable Long ordemServicoId, @Valid @RequestBody ComentarioInput comentarioInput){
        return toModel(gestaoOrdemServicoService.adicionarComentario(ordemServicoId,comentarioInput.getDescricao()));
    }

    @GetMapping
    public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
        //Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de servico nao encontrada"));
        return toCollectionModel(ordemServico.getComentarios());
        /* as tres formas funcionam
        1 return toCollectionModel(comentarioRepository.findAllByOrdemServico(ordemServico.get()));
        OrdemServico ordemServico1 = new OrdemServico();
        ordemServico1.setId(ordemServicoId);
        2 return toCollectionModel(comentarioRepository.findAllByOrdemServico(ordemServico1));
        if(ordemServico.isPresent()){
        *3     return toCollectionModel(ordemServico.get().getComentarios());
        }

        */
    }


    private ComentarioModel toModel(Comentario comentario){
        return modelMapper.map(comentario, ComentarioModel.class);
    }

    private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios){
        return comentarios.stream()
                .map(comentario -> toModel(comentario))
                .collect(Collectors.toList());
    }

}
