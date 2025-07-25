package com.ejemplo.mvc.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.ejemplo.mvc.controller.BancoController;
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
        Cliente cliente1 = banco.creaCliente(1, "Juan Perez", "0999999999");
        banco.creaCajaAhorro(101, 500.0, cliente1, 5);
        Cliente cliente2 = banco.creaCliente(2, "Adrian Flores", "0987654321");
        banco.creaCuentaCorriente(102, 300.0, cliente2);
        
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
        
        //Botones
        btnCrearCliente.setOnAction(e -> mostrarCliente());
        btnCrearCuenta.setOnAction(e -> mostrarCuenta());
        btnDepositoRetiro.setOnAction(e -> realizarDepositoRetiro());
        
        //Layout
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
        
        //Labels
        Label lblId = new Label("ID:");
        TextField txtId = new TextField();

        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();

        Label lblTelefono = new Label("Telefono:");
        TextField txtTelefono = new TextField();
        
        Label lblResultado = new Label();
        
        //Botones
        Button btnGuardar = new Button("Guardar");
        Button btnVolver = new Button("Volver");
        
        //Acciones botones
        btnGuardar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText();
                String telefono = txtTelefono.getText();

                if (nombre.isEmpty() || telefono.isEmpty()) {
                	lblResultado.setText("Debe llenar todos los campos.");
                    lblResultado.setStyle("-fx-text-fill: red;");
                    return;
                }

                Cliente cliente = new Cliente(id, nombre, telefono);
                banco.creaCliente(id, nombre, telefono);
                System.out.println("Cliente creado: " + cliente.getNombre() + " - " + cliente.getTelefono());

                ventanaCliente.close();
            } catch (NumberFormatException ex) {
            	lblResultado.setText("El ID debe ser un numero entero.");
                lblResultado.setStyle("-fx-text-fill: red;");
            }
        });
        
        btnVolver.setOnAction(e -> ventanaCliente.close());
        
        //Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
        	lblId,
        	txtId,
        	lblNombre,
        	txtNombre,
        	lblTelefono,
        	txtTelefono,
        	lblResultado,
        	btnGuardar,
        	btnVolver
        );

        Scene scene = new Scene(layout, 300, 300);
        ventanaCliente.setScene(scene);
        ventanaCliente.show();
    }
        
    public static void mostrarCuenta() {
        Stage ventanaCuenta = new Stage();
        ventanaCuenta.setTitle("Crear Cuenta");
        
        //Labels
        Label lblNumero = new Label("Número de cuenta:");
        TextField txtNumero = new TextField();

        Label lblSaldo = new Label("Saldo inicial:");
        TextField txtSaldo = new TextField();

        Label lblIdCliente = new Label("ID del Cliente:");
        TextField txtIdCliente = new TextField();

        Label lblTipo = new Label("Tipo de cuenta:");
        ComboBox<String> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll("Corriente", "Caja de Ahorro");

        Label lblMovAnuales = new Label("Movimientos anuales (solo si es Caja Ahorro):");
        TextField txtMovAnuales = new TextField();
        txtMovAnuales.setDisable(true);

        comboTipo.setOnAction(e -> {
            txtMovAnuales.setDisable(!"Caja de Ahorro".equals(comboTipo.getValue()));
        });

        Label lblResultado = new Label();
        
        //Botones
        Button btnCrear = new Button("Crear Cuenta");
        Button btnVolver = new Button("Volver");
        
        //Acciones botones
        btnCrear.setOnAction(e -> {
            try {
                int numero = Integer.parseInt(txtNumero.getText());
                double saldo = Double.parseDouble(txtSaldo.getText());
                int idCliente = Integer.parseInt(txtIdCliente.getText());

                Cliente cliente = banco.buscarCliente(idCliente);   

                String tipo = comboTipo.getValue();
                Cuenta cuenta = null;

                if ("Corriente".equals(tipo)) {
                    cuenta = banco.creaCuentaCorriente(numero, saldo, cliente);
                } else if ("Caja de Ahorro".equals(tipo)) {
                    int movAnuales = Integer.parseInt(txtMovAnuales.getText());
                    cuenta = banco.creaCajaAhorro(numero, saldo, cliente, movAnuales);
                } else {
                	lblResultado.setText("Debe seleccionar el tipo de cuenta.");
                    lblResultado.setStyle("-fx-text-fill: red;");
                    return;
                }

                System.out.println("Cuenta creada para el cliente " + cliente.getNombre());

                ventanaCuenta.close();
            } catch (NumberFormatException ex) {
            	lblResultado.setText("Error: datos invalidos.");
                lblResultado.setStyle("-fx-text-fill: red;");
            } catch (RuntimeException ex) {
                lblResultado.setText("Error: " + ex.getMessage());
                lblResultado.setStyle("-fx-text-fill: red;");
            }
        });
        
        btnVolver.setOnAction(e -> ventanaCuenta.close());
        
        //Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
        	lblNumero, txtNumero,
            lblSaldo, txtSaldo,
            lblIdCliente, txtIdCliente,
            lblTipo, comboTipo,
            lblMovAnuales, txtMovAnuales,
            lblResultado,
            btnCrear, 
            btnVolver
        );

        Scene scene = new Scene(layout, 350, 450);
        ventanaCuenta.setScene(scene);
        ventanaCuenta.show();
    }
    
    public static void realizarDepositoRetiro() {
    	Stage ventanaDepositoRetiro = new Stage();
        ventanaDepositoRetiro.setTitle("Deposito o Retiro");
        
        //Labels
        Label lblCuenta = new Label("Numero de Cuenta:");
        TextField txtCuenta = new TextField();

        Label lblMonto = new Label("Monto:");
        TextField txtMonto = new TextField();

        Label lblResultado = new Label();
        
        //Botones
        Button btnDeposito = new Button("Depositar");
        Button btnRetiro = new Button("Retirar");
        Button btnVolver = new Button("Volver");
        
        //Acciones botones
        btnDeposito.setOnAction(e -> {
            try {
                int numero = Integer.parseInt(txtCuenta.getText());
                double monto = Double.parseDouble(txtMonto.getText());
                double saldoActual = banco.depositar(numero, monto);

        		lblResultado.setText("Deposito exitoso. Saldo actual $" + saldoActual);
                lblResultado.setStyle("-fx-text-fill: green;");

            } catch (NumberFormatException ex) {
                lblResultado.setText("Error: datos invalidos.");
                lblResultado.setStyle("-fx-text-fill: red;");
            } catch (RuntimeException ex) {
                lblResultado.setText("Error: " + ex.getMessage());
                lblResultado.setStyle("-fx-text-fill: red;");
            }
        });

        btnRetiro.setOnAction(e -> {
            try {
                int numero = Integer.parseInt(txtCuenta.getText());
                double monto = Double.parseDouble(txtMonto.getText());
                double saldoActual = banco.retirar(numero, monto);
                
        		lblResultado.setText("Retiro exitoso. Saldo actual $" + saldoActual);
                lblResultado.setStyle("-fx-text-fill: green;");

            } catch (NumberFormatException ex) {
                lblResultado.setText("Error: datos invalidos.");
                lblResultado.setStyle("-fx-text-fill: red;");
            } catch (RuntimeException ex) {
                lblResultado.setText("Error: " + ex.getMessage());
                lblResultado.setStyle("-fx-text-fill: red;");
            }
        });
        
        btnVolver.setOnAction(e -> ventanaDepositoRetiro.close());
        
        //Layout
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
        ventanaDepositoRetiro.setScene(scene);
        ventanaDepositoRetiro.show();
    }    
}
