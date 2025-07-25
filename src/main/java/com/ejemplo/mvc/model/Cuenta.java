package com.ejemplo.mvc.model;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public abstract class Cuenta {
	//atributos
	protected int numero;
	protected double saldo;
	protected List<Transaccion> transacciones = new ArrayList<>();
	protected Cliente cliente;
	//constructor
	public Cuenta(int numero, double saldo, Cliente cliente) {
		this.numero = numero;
		this.saldo = saldo;
		this.cliente = cliente;
	}
	//metodo deposito
	public double deposito(double monto, Cliente cliente) {
		saldo += monto;
		Transaccion transaccion = new Transaccion(TipoTransaccion.DEPOSITO, monto, LocalDate.now());
		transacciones.add(transaccion);
		return saldo;
	}
	//metodo retiro
	public double retiro(double monto, Cliente cliente) {
		if (checkCuenta(monto) == false) {
            throw new RuntimeException("No cumple las restricciones de la cuenta.");
        }
		saldo -= monto;
		Transaccion transaccion = new Transaccion(TipoTransaccion.RETIRO, monto, LocalDate.now());
		transacciones.add(transaccion);
		return saldo;
	}
	
    public List<Transaccion> getTransacciones() {
		return transacciones;
	}
	//getters
	public int getNumero() {
		return numero;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public double getSaldo() {
		return saldo;
	}
	//metodo abstracto checkCuenta
	public abstract boolean checkCuenta(double monto);
}
