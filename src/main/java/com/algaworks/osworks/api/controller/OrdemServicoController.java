package com.algaworks.osworks.api.controller;

import com.algaworks.osworks.api.model.OrdemServicoInput;
import com.algaworks.osworks.api.model.OrdemServicoModel;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServicoService;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput){
        return toModel(gestaoOrdemServicoService.criar(toEntity(ordemServicoInput)));
    }

    @GetMapping
    public List<OrdemServicoModel> listar(){
        return toCollectionModel(ordemServicoRepository.findAll());
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId){
        Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);

        if(ordemServico.isPresent()){
            /* poderiamos fazer a atribuicao manual, mas é extremamente trabalhoso
            OrdemServicoModel model = new OrdemServicoModel();
            model.setId(ordemServico.get().getId());
            model.setDescricao(ordemServico.get().getDescricao());
            return ResponseEntity.ok(model);
            */
            OrdemServicoModel model = toModel(ordemServico.get());
            return ResponseEntity.ok(model);
        }

        return ResponseEntity.notFound().build();
    }

    private OrdemServicoModel toModel(OrdemServico ordemServico){
        return modelMapper.map(ordemServico, OrdemServicoModel.class);
    }

    private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordemServicos){
        return ordemServicos.stream()
                .map(ordemServico -> toModel(ordemServico))
                .collect(Collectors.toList());
    }

    private OrdemServico toEntity(OrdemServicoInput ordemServicoInput){
        return modelMapper.map(ordemServicoInput, OrdemServico.class);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{ordemServicoId}/finalizacao")
    public void finalizar(@PathVariable Long ordemServicoId){
        gestaoOrdemServicoService.finalizar(ordemServicoId);
    }

}
