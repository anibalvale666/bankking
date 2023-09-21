package com.bankking.service.Impl;

import com.bankking.models.Cliente;
import com.bankking.repository.ClienteRepository;
import com.bankking.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    public Cliente saveCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, Cliente cliente) {
        Cliente update_cliente = clienteRepository.findById(id).get();
        update_cliente.setContrasena(cliente.getContrasena());
        update_cliente.setEstado(cliente.getEstado());
        update_cliente.setNombre(cliente.getNombre());
        update_cliente.setGenero(cliente.getGenero());
        update_cliente.setEdad(cliente.getEdad());
        update_cliente.setIdentificacion(cliente.getIdentificacion());
        update_cliente.setDireccion(cliente.getDireccion());
        update_cliente.setTelefono(cliente.getTelefono());
        return clienteRepository.save(update_cliente);
    }

    public Boolean deleteCliente(Long id) {
        Cliente toDelete = clienteRepository.findById(id).get();
        clienteRepository.delete(toDelete);
        return true;
    }
}
