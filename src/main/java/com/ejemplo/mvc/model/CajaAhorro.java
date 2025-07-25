package com.ejemplo.mvc.model;


public class CajaAhorro extends Cuenta {
	
	//atributo unico
	private int movAnuales;
	
	//constructor
	public CajaAhorro(int numero, double saldo, Cliente cliente, int movAnuales) {
		super(numero, saldo, cliente);
		this.movAnuales = movAnuales;
	}
	
	public int getMovAnuales() {
		return movAnuales;
	}
	
	//metodo checkCuenta heredado
	@Override
	public boolean checkCuenta(double monto) {
		int numTransacciones = 0;
        int añoActual = Hora.getInstance().year(Hora.getInstance().today());
        
        //contador de numTransacciones al año
        for (Transaccion transaccion : transacciones) {
            if (Hora.getInstance().year(transaccion.getFecha()) == añoActual) {
                numTransacciones++;
            }
        }
        
        //restriccion maximo de transacciones anuales
        if (numTransacciones >= movAnuales) {
            System.out.println("Limite anual de transacciones alcanzado.");
            return false;
        }
        //restriccion de sobregiro
        if (monto > saldo) {
            System.out.println("Saldo insuficiente.");
            return false;
        }
        
		return true;
	}

}
