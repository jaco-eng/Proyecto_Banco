package com.ejemplo.mvc.model;

public class Corriente extends Cuenta {
	
	public Corriente(int numero, double saldo, Cliente cliente) {
		super(numero, saldo, cliente);
	}
	
	//metodo checkCuenta heredado
	@Override
	public boolean checkCuenta(double monto) {
        //restriccion de sobregiro
		if(monto>saldo) {
			System.out.println("Saldo insuficiente.");
			return false;
		}
		
		return true;
	}

}
