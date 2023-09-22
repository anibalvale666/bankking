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

import java.util.Comparator;
import java.util.Date;
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
    public Movimiento saveMovimiento(Movimiento movimiento) throws Exception {

        movimiento.setFecha(new Date());
        // si es debito validar si hay saldo suficiente

        //si es debito alidar cuanto ha retirado durante el dia tope max 10000
        Optional<Cliente> cliente = clienteRepository.findById(movimiento.getClienteId());
        Optional<Cuenta> cuenta = cuentaRepository.findById(movimiento.getCuentaId());
        if(cliente.isEmpty() || cuenta.isEmpty()) {
            throw new Exception("cliente o cuenta inexistente");
        }
        // preguntar si es debito (restar) o credito ( sumar)
//        Double saldo = calcularSaldo(cuenta.get().getClienteId(), cuenta.get().getSaldoInicial());
        Double saldo = obtenerUltimoSaldo(cuenta.get().getClienteId(), cuenta.get().getSaldoInicial());
        if(movimiento.getTipoMovimiento().equals("credito")) {
            movimiento.setSaldo(saldo + movimiento.getValor());
        } else {
            // obtener acumulado
            Double accum = obtenerAcumulado(cuenta.get().getClienteId());
            if(saldo < movimiento.getValor()) {
                throw new Exception("Saldo no disponible");
            }
            if(accum + movimiento.getValor() > 1000.0) {
                throw new Exception("Cupo diario excedido");
            }
            movimiento.setSaldo(saldo - movimiento.getValor());
        }
        movimiento.setClienteId(cliente.get().getClienteId());
        movimiento.setCuentaId(cuenta.get().getId());
        return movimientoRepository.save(movimiento);
    }

    private Double calcularSaldo(Long cuentaId, Double saldoInicial) {
        return movimientoRepository.findByCuentaId(cuentaId)
            .stream()
            .map(movimiento -> Double.valueOf(movimiento.getValor()))
            .reduce(saldoInicial, Double::sum);
    }

    private Double obtenerUltimoSaldo(Long cuentaId, Double SaldoInicial) {
        return movimientoRepository.findByCuentaId(cuentaId)
            .stream()
            .max(Comparator.comparing(Movimiento::getFecha))
            .map(ultimoMovimiento -> ultimoMovimiento.getSaldo())
            .orElse(SaldoInicial);
    }

    private Double obtenerAcumulado(Long cuentaId) {
        Date fechaActual = new Date();
        return movimientoRepository.findByCuentaId(cuentaId)
            .stream()
            .filter(movimiento -> movimiento.getTipoMovimiento().equals("debito"))
            .filter(movimiento -> movimiento.getFecha() == fechaActual)
            .map(Movimiento::getValor)
            .reduce(0.0, Double::sum);
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
