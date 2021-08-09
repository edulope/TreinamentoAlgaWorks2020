package com.algaworks.osworks.domain.model;


import com.algaworks.osworks.domain.ValidationGroups;
import com.algaworks.osworks.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class OrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@JoinColumn( name = "cliente_id")

    //utiliza um grupo especifico de validacao
    @NotNull
    @ManyToOne
    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
    private Cliente cliente;
    @NotBlank
    private String descricao;
    @NotNull
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    //só o backend altera esses valores, entradas via json não podem fazer isso
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StatusOrdemServico status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dataAbertura;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dataFinalizacao;

    @OneToMany(mappedBy = "ordemServico")
    private List<Comentario> comentarios = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdemServico that = (OrdemServico) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    private boolean podeSerFinalizada(){
        return status == StatusOrdemServico.ABERTA;
    }
    public void finalizar(){
        if(podeSerFinalizada()){
            status = StatusOrdemServico.FINALIZADA;
            dataFinalizacao = OffsetDateTime.now();
        }
        else throw new NegocioException("ordem de servico nao pode ser finalizada");
    }
}
