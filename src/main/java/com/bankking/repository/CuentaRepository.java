package com.bankking.repository;

import com.bankking.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository  extends JpaRepository<Cuenta, Long> {
}
