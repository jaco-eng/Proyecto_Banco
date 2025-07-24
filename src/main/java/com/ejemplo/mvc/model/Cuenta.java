package com.ejemplo.mvc.model;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public abstract class Cuenta {
	
	protected int numero;
	protected double saldo;
	protected List<Transaccion> transacciones = new ArrayList<>();
	protected Cliente cliente;
	
	

	public Cuenta(int numero, double saldo, Cliente cliente) {
		this.numero = numero;
		this.saldo = saldo;
		this.cliente = cliente;
	}

	public void deposito(double monto, Cliente cliente) {
		saldo += monto;
		Transaccion transaccion = new Transaccion(TipoTransaccion.DEPOSITO, monto, LocalDate.now());
		transacciones.add(transaccion);
	}
	
	public void retiro(double monto, Cliente cliente) {
		if (checkCuenta(monto) == false) {
            throw new RuntimeException("Error: No cumple las restricciones de la cuenta.");
        }
		saldo -= monto;
		Transaccion transaccion = new Transaccion(TipoTransaccion.RETIRO, monto, LocalDate.now());
		transacciones.add(transaccion);
	}
    
	public int getNumero() {
		return numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public abstract boolean checkCuenta(double monto);
}
