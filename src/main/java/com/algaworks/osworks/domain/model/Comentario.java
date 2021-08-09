package com.algaworks.osworks.domain.model;

import com.algaworks.osworks.domain.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.time.OffsetDateTime;

@Data
@Entity
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.OrdemServicoId.class)
    private OrdemServico ordemServico;
    @NotBlank
    private String descricao;
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dataEnvio;
}
