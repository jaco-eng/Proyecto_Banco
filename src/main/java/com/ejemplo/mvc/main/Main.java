package com.ejemplo.mvc.main;

import com.ejemplo.mvc.model.CajaAhorro;
import com.ejemplo.mvc.model.Cliente;
import com.ejemplo.mvc.model.Cuenta;
import com.ejemplo.mvc.model.Transaccion;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private Cuenta cuentaEjemplo;
    private Cliente clienteEjemplo;

    private Scene escenaPrincipal;
    private Scene escenaOperaciones;
    private Stage ventana;

    private Label resultado;
    private TextField tfMonto;
    private TextArea areaRegistro;

    @Override
    public void start(Stage primaryStage) {
        this.ventana = primaryStage;

        // Cliente y cuenta ejemplo
        clienteEjemplo = new Cliente(1, "Juan Pérez", "0999999999");
        cuentaEjemplo = new CajaAhorro(101, 500.0, clienteEjemplo, 0); // sin límite transacciones
        clienteEjemplo.agregarCuenta(cuentaEjemplo);

        // Escena principal
        Button btnIrOperaciones = new Button("Ir a operaciones");
        VBox layoutPrincipal = new VBox(20, new Label("Bienvenido al banco"), btnIrOperaciones);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setStyle("-fx-alignment: center;");
        escenaPrincipal = new Scene(layoutPrincipal, 350, 150);

        // Componentes escena operaciones
        Button btnDeposito = new Button("Depósito");
        Button btnRetiro = new Button("Retiro");
        Button btnVolver = new Button("Volver");

        tfMonto = new TextField();
        tfMonto.setPromptText("Ingrese monto");
        tfMonto.setMaxWidth(150);

        resultado = new Label("Saldo actual: $" + cuentaEjemplo.getSaldo());
        areaRegistro = new TextArea();
        areaRegistro.setEditable(false);
        areaRegistro.setPrefHeight(200);
        actualizarRegistro();

        // Acciones botones
        btnDeposito.setOnAction(e -> realizarDeposito());
        btnRetiro.setOnAction(e -> realizarRetiro());
        btnVolver.setOnAction(e -> ventana.setScene(escenaPrincipal));

        // Layout: botones y campo monto arriba
        HBox topControls = new HBox(10, new Label("Monto:"), tfMonto, btnDeposito, btnRetiro);
        topControls.setStyle("-fx-alignment: center-left;");

        // Spacer para empujar saldo y botón hacia abajo
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox layoutOperaciones = new VBox(10);
        layoutOperaciones.setPadding(new Insets(20));
        layoutOperaciones.setStyle("-fx-alignment: top-center;");
        layoutOperaciones.getChildren().addAll(topControls, areaRegistro, spacer, resultado, btnVolver);

        escenaOperaciones = new Scene(layoutOperaciones, 400, 400);

        // Acción ir a operaciones
        btnIrOperaciones.setOnAction(e -> ventana.setScene(escenaOperaciones));

        // Mostrar escena principal
        ventana.setScene(escenaPrincipal);
        ventana.setTitle("Banco");
        ventana.show();
    }

    private void realizarDeposito() {
        try {
            double monto = Double.parseDouble(tfMonto.getText());
            if (monto <= 0) {
                resultado.setText("Ingrese un monto positivo.");
                return;
            }
            clienteEjemplo.deposito(cuentaEjemplo, monto);
            resultado.setText("Depósito de $" + monto + " realizado.\nSaldo actual: $" + cuentaEjemplo.getSaldo());
            actualizarRegistro();
            tfMonto.clear();
        } catch (NumberFormatException ex) {
            resultado.setText("Monto inválido. Ingrese un número.");
        }
    }

    private void realizarRetiro() {
        try {
            double monto = Double.parseDouble(tfMonto.getText());
            if (monto <= 0) {
                resultado.setText("Ingrese un monto positivo.");
                return;
            }
            clienteEjemplo.retiro(cuentaEjemplo, monto);
            resultado.setText("Retiro de $" + monto + " realizado.\nSaldo actual: $" + cuentaEjemplo.getSaldo());
            actualizarRegistro();
            tfMonto.clear();
        } catch (NumberFormatException ex) {
            resultado.setText("Monto inválido. Ingrese un número.");
        } catch (RuntimeException ex) {
            resultado.setText("Error: " + ex.getMessage());
        }
    }

    private void actualizarRegistro() {
        StringBuilder sb = new StringBuilder();
        for (Transaccion t : cuentaEjemplo.getTransacciones()) {
            sb.append(t.getTipo())
              .append(" - ")
              .append(t.getFecha())
              .append(" - $")
              .append(t.getMonto())
              .append("\n");
        }
        areaRegistro.setText(sb.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}