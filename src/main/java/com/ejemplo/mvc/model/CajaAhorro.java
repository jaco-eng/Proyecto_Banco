package com.ejemplo.mvc.model;


public class CajaAhorro extends Cuenta {
	
	private int movAnuales;
	
	public CajaAhorro(int numero, double saldo, Cliente cliente, int movAnuales) {
		super(numero, saldo, cliente);
		this.movAnuales = movAnuales;
	}

	public int getMovAnuales() {
		return movAnuales;
	}

	public void setMovAnuales(int movAnuales) {
		this.movAnuales = movAnuales;
	}

	@Override
	public boolean checkCuenta(double monto) {
		int numTransacciones = 0;
        int añoActual = Hora.getInstance().year(Hora.getInstance().today());
        
        for (Transaccion transaccion : transacciones) {
            if (Hora.getInstance().year(transaccion.getFecha()) == añoActual) {
                numTransacciones++;
            }
        }
        
        if (numTransacciones >= movAnuales) {
            System.out.println("Limite anual de transacciones alcanzado.");
            return false;
        }

        if (monto > saldo) {
            System.out.println("Saldo insuficiente.");
            return false;
        }
        
		return true;
	}

}
