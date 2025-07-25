package com.ejemplo.mvc.controller;

import com.ejemplo.mvc.model.*;

public class BancoController {
	
	private Banco banco;
	
	//constructor
	public BancoController() {
		banco = new Banco();
	}
	//metodo creaCliente
	public Cliente creaCliente(int id, String nombre, String telefono) {
        Cliente cliente = new Cliente(id, nombre, telefono);
        banco.getClientes().add(cliente);
        return cliente;
	}
	//metodo creCuentaCorriente
	public Cuenta creaCuentaCorriente(int numero, double saldo, Cliente cliente) {
		Cuenta cuenta = new Corriente(numero, saldo, cliente);
		if (cliente == null) {
			throw new RuntimeException("Cliente no encontrado.");
        }
		if(saldo<0) {
			throw new RuntimeException("Debe ingresar un saldo positivo.");
		}
		banco.getCuentas().add(cuenta);
        cliente.agregarCuenta(cuenta);
		return cuenta;
	}
	//metodo creaCajaAhorro
	public Cuenta creaCajaAhorro(int numero, double saldo, Cliente cliente, int movAnuales){
		Cuenta cuenta = new CajaAhorro(numero, saldo, cliente, movAnuales);
		if (cliente == null) {
			throw new RuntimeException("Cliente no encontrado.");
        }
		if(saldo<0) {
			throw new RuntimeException("Debe ingresar un saldo positivo.");
		}
        banco.getCuentas().add(cuenta);
        cliente.agregarCuenta(cuenta);
		return cuenta;
	}
	//metodo depositar
	public double depositar(int numCuenta, double monto) {
		Cuenta cuenta = buscarCuenta(numCuenta);
		if (monto <= 0) {
            throw new RuntimeException("Monto invalido");
        }
		if(cuenta == null) {
			throw new RuntimeException("Cuenta no encontrada.");
		}
		
		cuenta.deposito(monto, cuenta.getCliente());
		return cuenta.getSaldo();
	}
	//metodo retirar
	public double retirar(int numCuenta, double monto) {
		Cuenta cuenta = buscarCuenta(numCuenta);
		if (monto <= 0) {
            throw new RuntimeException("Monto invalido");
        }
		if(cuenta == null) {
			throw new RuntimeException("Cuenta no encontrada.");
		}
		
		cuenta.retiro(monto, cuenta.getCliente());
		return cuenta.getSaldo();
	}
	//metodo busquedaCuenta
	public Cuenta buscarCuenta(int numero) {
		if (numero <= 0) {
	        throw new RuntimeException("Numero de cuenta invalido.");
	    }
		
	    for (Cuenta cuenta : banco.getCuentas()) {
	        if (cuenta.getNumero() == numero) {
	            return cuenta;
	        }
	    }
	    return null;
	}
	//metodo busquedaCliente
	public Cliente buscarCliente(int id) {
		if (id <= 0) {
	        throw new RuntimeException("Numero de cuenta invalido.");
	    }
		
        for (Cliente cliente : banco.getClientes()) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }
}



