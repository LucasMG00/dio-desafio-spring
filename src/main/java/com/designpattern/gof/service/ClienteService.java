package com.designpattern.gof.service;

import com.designpattern.gof.model.Cliente;

public interface ClienteService {

    Iterable<Cliente> retrieveAll();

    Cliente findById(Long id);

    void insert(Cliente cliente);

    void update(Long id, Cliente cliente);

    void delete(Long id);

}
