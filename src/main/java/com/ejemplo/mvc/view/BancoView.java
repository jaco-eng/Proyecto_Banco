package com.ejemplo.mvc.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.ejemplo.mvc.controller.BancoController;
import com.ejemplo.mvc.model.CajaAhorro;
import com.ejemplo.mvc.model.Cliente;
import com.ejemplo.mvc.model.Cuenta;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BancoView {
    
    private static BancoController banco;
    private static Scene escenaOperaciones;

    public static void primaryStage(Stage stage) {
    	banco = new BancoController();
        stage.setTitle("Sistema Bancario");
        
        //Cliente y cuenta ejemplo
        Cliente clienteEjemplo = banco.creaCliente(1, "Juan Perez", "0999999999");
        banco.creaCajaAhorro(101, 500.0, clienteEjemplo, 1);

        //Escena principal
        Button btnIrOperaciones = new Button("Ir a operaciones");
        VBox layoutPrincipal = new VBox(20, new Label("Bienvenido al banco"), btnIrOperaciones);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setStyle("-fx-alignment: center;");
        Scene escenaPrincipal = new Scene(layoutPrincipal, 350, 150);
        
        //Escena operaciones
        Button btnCrearCliente = new Button("Crear Usuario");
        Button btnCrearCuenta = new Button("Crear Cuenta");
        Button btnDepositoRetiro = new Button("Hacer Deposito o  Retiro");

        btnCrearCliente.setOnAction(e -> mostrarCliente());
        btnCrearCuenta.setOnAction(e -> mostrarCuenta());
        btnDepositoRetiro.setOnAction(e -> realizarDepositoRetiro());

        VBox layoutOperaciones = new VBox(10);
        layoutOperaciones.setStyle("-fx-padding: 20px;");
        layoutOperaciones.getChildren().addAll(
            btnCrearCliente,
            btnCrearCuenta,
            btnDepositoRetiro
        );

        escenaOperaciones = new Scene(layoutOperaciones, 300, 150);

        btnIrOperaciones.setOnAction(e -> stage.setScene(escenaOperaciones));

        stage.setScene(escenaPrincipal);
        stage.show();
    }
    
    public static void mostrarCliente() {
        Stage ventanaCliente = new Stage();
        ventanaCliente.setTitle("Crear Usuario");

        Label lblId = new Label("ID:");
        TextField txtId = new TextField();

        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();

        Label lblTelefono = new Label("Telefono:");
        TextField txtTelefono = new TextField();

        Button btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText();
                String telefono = txtTelefono.getText();

                if (nombre.isEmpty() || telefono.isEmpty()) {
                    System.out.println("Debe llenar todos los campos.");
                    return;
                }

                Cliente cliente = new Cliente(id, nombre, telefono);
                banco.creaCliente(id, nombre, telefono);
                System.out.println("Cliente creado: " + cliente.getNombre() + " - " + cliente.getTelefono());

                ventanaCliente.close();
            } catch (NumberFormatException ex) {
                System.out.println("El ID debe ser un número entero.");
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(lblId, txtId, lblNombre, txtNombre, lblTelefono, txtTelefono, btnGuardar);

        Scene scene = new Scene(layout, 300, 300);
        ventanaCliente.setScene(scene);
        ventanaCliente.show();
    }
        
    public static void mostrarCuenta() {
        Stage ventana = new Stage();
        ventana.setTitle("Crear Cuenta");

        TextField txtNumero = new TextField();
        txtNumero.setPromptText("Numero de cuenta");

        TextField txtSaldo = new TextField();
        txtSaldo.setPromptText("Saldo inicial");

        TextField txtIdCliente = new TextField();
        txtIdCliente.setPromptText("ID Cliente");

        ComboBox<String> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll("Corriente", "Caja de Ahorro");
        comboTipo.setPromptText("Tipo de cuenta");

        TextField txtMovAnuales = new TextField();
        txtMovAnuales.setPromptText("Movimientos anuales (solo si es Caja Ahorro)");
        txtMovAnuales.setDisable(true);

        comboTipo.setOnAction(e -> {
            txtMovAnuales.setDisable(!"Caja de Ahorro".equals(comboTipo.getValue()));
        });

        Button btnCrear = new Button("Crear Cuenta");

        btnCrear.setOnAction(e -> {
            try {
                int numero = Integer.parseInt(txtNumero.getText());
                double saldo = Double.parseDouble(txtSaldo.getText());
                int idCliente = Integer.parseInt(txtIdCliente.getText());

                Cliente cliente = banco.buscarCliente(idCliente);
                if (cliente == null) {
                    System.out.println("Cliente no encontrado.");
                    return;
                }

                String tipo = comboTipo.getValue();
                Cuenta cuenta = null;

                if ("Corriente".equals(tipo)) {
                    cuenta = banco.creaCuentaCorriente(numero, saldo, cliente);
                } else if ("Caja de Ahorro".equals(tipo)) {
                    int movAnuales = Integer.parseInt(txtMovAnuales.getText());
                    cuenta = banco.creaCajaAhorro(numero, saldo, cliente, movAnuales);
                } else {
                    System.out.println("Debe seleccionar el tipo de cuenta.");
                    return;
                }

                System.out.println("Cuenta creada para el cliente " + cliente.getNombre());
                ventana.close();
            } catch (NumberFormatException ex) {
                System.out.println("Error: numero invalido en algun campo.");
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
            new Label("Numero de cuenta:"), txtNumero,
            new Label("Saldo inicial:"), txtSaldo,
            new Label("ID del Cliente:"), txtIdCliente,
            new Label("Tipo de cuenta:"), comboTipo,
            txtMovAnuales,
            btnCrear
        );

        Scene scene = new Scene(layout, 350, 400);
        ventana.setScene(scene);
        ventana.show();
    }
    
    public static void realizarDepositoRetiro() {
    	
    	Stage ventana = new Stage();
        ventana.setTitle("Deposito o Retiro");

        Label lblCuenta = new Label("Numero de Cuenta:");
        TextField txtCuenta = new TextField();

        Label lblMonto = new Label("Monto:");
        TextField txtMonto = new TextField();

        Label lblResultado = new Label();

        Button btnDeposito = new Button("Depositar");
        Button btnRetiro = new Button("Retirar");
        Button btnVolver = new Button("Volver");
        
        // Acciones botones
        btnDeposito.setOnAction(e -> {
            try {
                int numero = Integer.parseInt(txtCuenta.getText());
                double monto = Double.parseDouble(txtMonto.getText());

                Cuenta cuenta = banco.buscarCuenta(numero);
                if (cuenta == null) {
                    lblResultado.setText("Cuenta no encontrada.");
                    return;
                }

                Cliente cliente = cuenta.getCliente();
                cuenta.deposito(monto, cliente);
                lblResultado.setText("Deposito exitoso. Saldo actual: $" + cuenta.getSaldo());

            } catch (NumberFormatException ex) {
                lblResultado.setText("Datos invalidos.");
            }
        });

        btnRetiro.setOnAction(e -> {
            try {
                int numero = Integer.parseInt(txtCuenta.getText());
                double monto = Double.parseDouble(txtMonto.getText());

                Cuenta cuenta = banco.buscarCuenta(numero);
                if (cuenta == null) {
                    lblResultado.setText("Cuenta no encontrada.");
                    return;
                }
                
                Cliente cliente = cuenta.getCliente();
                cuenta.retiro(monto, cliente);
                lblResultado.setText("Retiro exitoso. Saldo actual: $" + cuenta.getSaldo());

            } catch (NumberFormatException ex) {
                lblResultado.setText("Datos invalidos.");
            } catch (RuntimeException ex) {
                lblResultado.setText("Error: " + ex.getMessage());
            }
        });
        btnVolver.setOnAction(e -> ventana.close());

        // Layout: botones y campo monto arriba
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
            lblCuenta, txtCuenta,
            lblMonto, txtMonto,
            new HBox(10, btnDeposito, btnRetiro),
            lblResultado,
            btnVolver
        );

        Scene scene = new Scene(layout, 350, 300);
        ventana.setScene(scene);
        ventana.show();
    }
    
}
