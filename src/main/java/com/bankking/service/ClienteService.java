package com.bankking.service;


import com.bankking.models.Cliente;

import java.util.List;

public interface ClienteService {
    public List<Cliente> getClientes();

    public Cliente saveCliente(Cliente cliente);

    public Cliente updateCliente(Long id, Cliente cliente);

    public Boolean deleteCliente(Long id);
}
