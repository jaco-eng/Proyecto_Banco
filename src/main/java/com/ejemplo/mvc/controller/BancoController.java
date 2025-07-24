package com.ejemplo.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import com.ejemplo.mvc.model.*;

public class BancoController {
	
	private List<Cuenta> cuentas;
	
	public BancoController() {
        cuentas = new ArrayList<>();
    }
	
	public Cuenta creaCuentaCorriente(int numero, double saldo, Cliente cliente) {
		Cuenta cuenta = new Corriente(numero, saldo, cliente);
		cuentas.add(cuenta);
		return cuenta;
	}
	
	public Cuenta creaCajaAhorro(int numero, double saldo, Cliente cliente, int movAnuales){
		Cuenta cuenta = new CajaAhorro(numero, saldo, cliente, movAnuales);
		cuentas.add(cuenta);
		return cuenta;
	}
	
	public boolean depositar(int numCuenta, double monto, Cliente cliente) {
		Cuenta cuenta = buscarCuenta(numCuenta);
		if(monto <= 0) {
			return false;
		}
		if(cuenta == null) {
			System.out.println("Cuenta no encontrada.");
			return false;
		}
		
		cuenta.deposito(monto, cliente);
		return true;
	}
	
	public boolean retirar(int numCuenta, double monto, Cliente cliente) {
		Cuenta cuenta = buscarCuenta(numCuenta);
		if(monto <= 0) {
			return false;
		}
		if(cuenta == null) {
			System.out.println("Cuenta no encontrada.");
			return false;
		}
		cuenta.retiro(monto, cliente);
		return true;
	}
	
	public Cuenta buscarCuenta(int numero) {
	
	}
}



