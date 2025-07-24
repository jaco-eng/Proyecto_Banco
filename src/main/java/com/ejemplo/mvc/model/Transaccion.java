package com.ejemplo.mvc.model;

import java.time.LocalDate;

public class Transaccion {
	private TipoTransaccion tipo;
	private double monto;
	private LocalDate fecha;
	
	
	public Transaccion(TipoTransaccion tipo, double monto, LocalDate fecha) {
		this.tipo = tipo;
		this.monto = monto;
		this.fecha = fecha;
	}


	public TipoTransaccion getTipo() {
		return tipo;
	}

	public double getMonto() {
		return monto;
	}

	public LocalDate getFecha() {
		return fecha;
	}
	
}
