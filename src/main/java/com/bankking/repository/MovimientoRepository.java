package com.bankking.repository;

import com.bankking.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
