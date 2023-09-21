package com.bankking.controllers;



import com.bankking.models.Cliente;
import com.bankking.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("clientes")
    public List<Cliente> getCliente() {
        return service.getClientes();
    }

    @PostMapping("clientes")
    public Cliente saveCliente(@RequestBody Cliente cliente) {
        return service.saveCliente(cliente);
    }

    @PutMapping("clientes/{id}")
    public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return service.updateCliente(id, cliente);
    }

    @DeleteMapping("clientes/{id}")
    public Boolean deleteCliente(@PathVariable Long id) {
        return service.deleteCliente(id);
    }

}
