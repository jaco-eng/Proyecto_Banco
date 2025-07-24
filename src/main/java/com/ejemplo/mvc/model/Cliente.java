package com.ejemplo.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	private int id;
	private String nombre, telefono;
	private List<Cuenta> cuentas = new ArrayList<>();	
	
	public Cliente(int id, String nombre, String telefono) {
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		
	}
	
	public void agregarCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}
	
	public void deposito(Cuenta cuenta, double monto) {
		cuenta.deposito(monto, this);
	}
	
	public void retiro(Cuenta cuenta, double monto) {
		cuenta.retiro(monto, this);		
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}
	
}