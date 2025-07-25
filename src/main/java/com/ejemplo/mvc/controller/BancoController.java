package com.ejemplo.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import com.ejemplo.mvc.model.*;

public class BancoController {
	
	private List<Cuenta> cuentas = new ArrayList<>();
	private List<Cliente> clientes = new ArrayList<>();
	
	public BancoController() {
		
	}
	
	public Cliente creaCliente(int id, String nombre, String telefono) {
        Cliente cliente = new Cliente(id, nombre, telefono);
        clientes.add(cliente);
        return cliente;
	}
	
	public Cuenta creaCuentaCorriente(int numero, double saldo, Cliente cliente) {
		Cuenta cuenta = new Corriente(numero, saldo, cliente);
		if (cliente == null) {
			throw new RuntimeException("Cliente no encontrado.");
        }
		if(saldo<0) {
			throw new RuntimeException("Debe ingresar un saldo positivo.");
		}
		cuentas.add(cuenta);
		return cuenta;
	}
	
	public Cuenta creaCajaAhorro(int numero, double saldo, Cliente cliente, int movAnuales){
		Cuenta cuenta = new CajaAhorro(numero, saldo, cliente, movAnuales);
		if (cliente == null) {
			throw new RuntimeException("Cliente no encontrado.");
        }
		if(saldo<0) {
			throw new RuntimeException("Debe ingresar un saldo positivo.");
		}
		cuentas.add(cuenta);
		return cuenta;
	}
	
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
	
	public Cuenta buscarCuenta(int numero) {
		if (numero <= 0) {
	        throw new RuntimeException("Numero de cuenta invalido.");
	    }
		
	    for (Cuenta cuenta : cuentas) {
	        if (cuenta.getNumero() == numero) {
	            return cuenta;
	        }
	    }
	    return null;
	}
	
	public Cliente buscarCliente(int id) {
		if (id <= 0) {
	        throw new RuntimeException("Numero de cuenta invalido.");
	    }
		
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

	public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}



