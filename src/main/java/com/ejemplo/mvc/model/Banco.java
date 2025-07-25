package com.ejemplo.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    //Listas cuentas y clientes
    private List<Cliente> clientes;
    private List<Cuenta> cuentas;

    public Banco() {
        clientes = new ArrayList<>();
        cuentas = new ArrayList<>();
    }
    
    //getters
    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }
    
	//metodos deposito y retiro del banco
	public void deposito(Cliente cliente, Cuenta cuenta, double monto) {
		cliente.deposito(cuenta, monto);
		
	}
	public void retiro(Cliente cliente, Cuenta cuenta, double monto) {
		cliente.retiro(cuenta, monto);
		
	}

}
