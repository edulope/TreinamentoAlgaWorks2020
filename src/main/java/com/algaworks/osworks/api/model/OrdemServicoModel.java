package com.algaworks.osworks.api.model;

import com.algaworks.osworks.domain.model.StatusOrdemServico;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdemServicoModel {
    private Long id;
    private ClienteResumoModel cliente;
    private String descricao;
    private BigDecimal preco;
    private StatusOrdemServico status;
    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataFinalizacao;
}
