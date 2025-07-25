package com.ejemplo.mvc.model;

public class Banco {
	
	//metodos deposito y retiro del banco
	public void deposito(Cliente cliente, Cuenta cuenta, double monto) {
		cliente.deposito(cuenta, monto);
		
	}
	public void retiro(Cliente cliente, Cuenta cuenta, double monto) {
		cliente.retiro(cuenta, monto);
		
	}

}
