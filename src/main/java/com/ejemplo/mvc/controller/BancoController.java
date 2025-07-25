package com.ejemplo.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import com.ejemplo.mvc.model.*;

public class BancoController {
	
	private List<Cuenta> cuentas = new ArrayList<>();
	private List<Cliente> clientes = new ArrayList<>();
	
	public BancoController() {
		Cliente cliente1 = new Cliente(1, "Ana Garcia", "987654321");
        clientes.add(cliente1);
        Cuenta cuenta1 = new Corriente(1000, 1000.0, cliente1);
        cliente1.agregarCuenta(cuenta1); 
        cuentas.add(cuenta1);

        Cliente cliente2 = new Cliente(2, "Pedro Lopez", "123123123");
        clientes.add(cliente2);
        Cuenta cuenta2 = new CajaAhorro(2000, 2000.0, cliente2, 3);
        cliente2.agregarCuenta(cuenta2);  
        cuentas.add(cuenta2);
    }
	
	public Cliente creaCliente(int id, String nombre, String telefono) {
        Cliente cliente = new Cliente(id, nombre, telefono);
        clientes.add(cliente);
        return cliente;
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
	
	public boolean depositar(int numCuenta, double monto) {
		Cuenta cuenta = buscarCuenta(numCuenta);
		if(monto <= 0) {
			return false;
		}
		if(cuenta == null) {
			System.out.println("Cuenta no encontrada.");
			return false;
		}
		
		cuenta.deposito(monto, cuenta.getCliente());
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
	    for (Cuenta cuenta : cuentas) {
	        if (cuenta.getNumero() == numero) {
	            return cuenta;
	        }
	    }
	    return null;
	}
	
	public Cliente buscarCliente(int id) {
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



