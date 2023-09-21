package com.bankking.service.Impl;

import com.bankking.models.Cliente;
import com.bankking.models.Cuenta;
import com.bankking.models.Movimiento;
import com.bankking.repository.ClienteRepository;
import com.bankking.repository.CuentaRepository;
import com.bankking.repository.MovimientoRepository;
import com.bankking.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaRepository cuentaRepository;


    @Override
    public List<Movimiento> getMovimientos() {
        return movimientoRepository.findAll();
    }

    @Override
    public Movimiento saveMovimiento(Movimiento movimiento) {
        Optional<Cliente> cliente = clienteRepository.findById(movimiento.getClienteId());
        Optional<Cuenta> cuenta = cuentaRepository.findById(movimiento.getCuentaId());
        movimiento.setClienteId(cliente.get().getClienteId());
        movimiento.setCuentaId(cuenta.get().getClienteId());
        return movimientoRepository.save(movimiento);
    }

    @Override
    public Movimiento updateMovimiento(Long id, Movimiento movimiento) {
        Optional<Movimiento> movimientoOptional = movimientoRepository.findById(id);
        if (movimientoOptional.isPresent()) {
            Movimiento movimientoUpdate = movimientoOptional.get();
            movimientoUpdate.setFecha(movimiento.getFecha());
            movimientoUpdate.setTipoMovimiento(movimiento.getTipoMovimiento());
            movimientoUpdate.setValor(movimiento.getValor());
            movimientoUpdate.setSaldo(movimiento.getSaldo());
            return movimientoRepository.save(movimientoUpdate);
        } else {
            return null;
        }
    }

    @Override
    public Boolean deleteMovimiento(Long id) {
        Movimiento toDelete = movimientoRepository.findById(id).get();
        movimientoRepository.delete(toDelete);
        return true;
    }
}
