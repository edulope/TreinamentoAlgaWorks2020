package com.algaworks.osworks.domain.repository;

import com.algaworks.osworks.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//primeiro campo: classe, segundo campo: tipo do id da classe
@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Long> {

    List<Cliente> findByNome(String nome);
    List<Cliente> findByNomeContaining(String nome);
    Cliente findByEmail(String email);
}
