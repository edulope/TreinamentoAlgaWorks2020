package com.algaworks.osworks.domain.model;

import com.algaworks.osworks.domain.ValidationGroups;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

//data para o lombok, gera to string, get, set automaticamente
@Data
//entity para definir que a classe vai ser uma entidade relacional do banco
@Entity
public class Cliente {

    //essa regra so serve para o grupo clienteId
    @NotNull(groups = ValidationGroups.ClienteId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //nao precisa de notation em colunas de nomes identicos ao bd
    //not blank vem do <groupId>javax.validation</groupId>, checa se nao e null, espaco em branco e etc
    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 20)
    @Column(name = "fone")
    private String telefone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return id.equals(cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
