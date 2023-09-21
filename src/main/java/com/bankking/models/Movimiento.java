package com.bankking.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "movimiento")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date fecha;
    private String tipoMovimiento;
    private String valor;
    private Double saldo;
    private long clienteId;
    private long cuentaId;
}
