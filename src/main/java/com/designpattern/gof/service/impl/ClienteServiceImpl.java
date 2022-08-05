package com.designpattern.gof.service.impl;

import com.designpattern.gof.model.Cliente;
import com.designpattern.gof.model.Endereco;
import com.designpattern.gof.repository.ClienteRepository;
import com.designpattern.gof.repository.EnderecoRepository;
import com.designpattern.gof.service.ClienteService;
import com.designpattern.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> retrieveAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();

    }

    @Override
    public void insert(Cliente cliente) {
        salvarCliente(cliente);
    }

    @Override
    public void update(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if(clienteBd.isPresent()){
            salvarCliente(cliente);
        }
    }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    private void salvarCliente(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
