package com.bankking.service;


import com.bankking.models.Movimiento;

import java.util.List;

public interface MovimientoService {
    public List<Movimiento> getMovimientos();

    public Movimiento saveMovimiento(Movimiento movimiento);

    public Movimiento updateMovimiento(Long id, Movimiento cuenta);

    public Boolean deleteMovimiento(Long id);
}
