package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.ServicioSucursal;
import Modelo.Sucursal;
import Vista.Formularios.FormularioSucursal;
import Vista.VistaSucursal;

import javax.swing.*;
import java.util.ArrayList;

public class ControladorSucursal {
    private final ServicioSucursal servicioSucursal;
    private final VistaSucursal vistaSucursal;
    private final ControladorClaseGrupal controladorClaseGrupal;
    private Runnable accionAtras;

    public ControladorSucursal() {
        this.controladorClaseGrupal = new ControladorClaseGrupal();
        this.servicioSucursal = new ServicioSucursal();
        this.vistaSucursal = new VistaSucursal();
    }

    public ServicioSucursal getServicioSucursal() {
        return servicioSucursal;
    }

    ////////////////////////////////////////FUNCIONES
    /////////////////////////////////////////////////
    public void registrarSucursal() throws GlobalException {
        FormularioSucursal formulario = new FormularioSucursal();
        boolean resultado = formulario.mostrarDialogo("Agregar Sucursal");

        if (!resultado) {
            return;
        }
        if(!formulario.validarDatos()) {
            return;
        }

        String cod = formulario.getCod();
        String provi = formulario.getProvi();
        String canton = formulario.getCanton();
        String correo = formulario.getCorreo();
        int telef = formulario.getTelef();

        Sucursal sucursal = new Sucursal.Builder()
                .cod(cod)
                .provi(provi)
                .canton(canton)
                .correo(correo)
                .telef(telef)
                .build();

        try {
            servicioSucursal.insertarSucursal(sucursal);
            vistaSucursal.getTablaSucursal().add(sucursal);
            vistaSucursal.mostrarToSting("Éxito", "Sucursal registrada correctamente");
        } catch (GlobalException | NoDataException e) {
            vistaSucursal.mostrarError("Error al registrar sucursal: " + e.getMessage());
        }
    }

    public void modificarSucursal() throws GlobalException, NoDataException {
        FormularioSucursal formulario = new FormularioSucursal();
        boolean resultado = formulario.mostrarDialogo("Modificar Sucursal");

        if (!resultado) {
            return;
        }

        String cod = formulario.getCod();
        String provi = formulario.getProvi();
        String canton = formulario.getCanton();
        String correo = formulario.getCorreo();
        int telef = formulario.getTelef();

        Sucursal sucursal = new Sucursal.Builder()
                .cod(cod)
                .provi(provi)
                .canton(canton)
                .correo(correo)
                .telef(telef)
                .build();

        try {
            servicioSucursal.modificarSucursal(sucursal);
            vistaSucursal.getTablaSucursal().refrescarData(listarSucursales());
            vistaSucursal.mostrarToSting("Éxito", "Sucursal modificada correctamente");
        } catch (GlobalException e) {
            vistaSucursal.mostrarError("Error al modificar sucursal: " + e.getMessage());
        }
    }

    public Sucursal buscarSucursal(String cod) throws GlobalException {
        return servicioSucursal.buscarSucursal(cod);
    }

    public ArrayList<Sucursal> listarSucursales() throws GlobalException, NoDataException {
        return servicioSucursal.listarSucursales();
    }

    ////////////////////////////////ActionListener/Handle
    /////////////////////////////////////////////////////
    public void handlerRegistrar() {
        this.vistaSucursal.addRegistrarListener(e -> {
            try {
                registrarSucursal();
            } catch (GlobalException ex) {
                vistaSucursal.mostrarError("Error al registrar: " + ex.getMessage());
            }
        });
    }

    public void handlerModificar() {
        this.vistaSucursal.addModificarListener(e -> {
            try {
                modificarSucursal();
            } catch (GlobalException | NoDataException ex) {
                vistaSucursal.mostrarError("Error al modificar: " + ex.getMessage());
            }
        });
    }

    public void handlerClaseGrupal() throws NoDataException, GlobalException {

        this.vistaSucursal.addClaseGrupalListener(e -> {

            // IMPORTANTE: Configurar el botón atrás ANTES de mostrar la ventana

           controladorClaseGrupal.configurarBotonAtras(() -> {

                controladorClaseGrupal.cerrarVentana();

                vistaSucursal.setVisible(true); // Volver a clientes

            });


            vistaSucursal.setVisible(false);

           controladorClaseGrupal.mostrarVentana();

        });
    }

    public void handlerBarraBusqueda() {
        vistaSucursal.addBuscarListener(e ->
        {
            String cedula = vistaSucursal.getTxtBuscarCodigo();
            if (cedula.isEmpty()) {
                vistaSucursal.getSorter().setRowFilter(null); // mostrar todo
            } else {
                vistaSucursal.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + cedula));
            }
        });
    }
    ///////////////////////////Cargar datos a la tabla
    //////////////////////////////////////////////////
    public void agregarTodasLasSucursales() throws NoDataException, GlobalException {
        vistaSucursal.getTablaSucursal().refrescarData(listarSucursales());
    }

    // Método para configurar el botón Atrás
    public void configurarBotonAtras(Runnable accionAtras) {
        this.accionAtras = accionAtras;
        vistaSucursal.addAtrasListener(e -> accionAtras.run());
    }

    // Método para mostrar la ventana
    public void mostrarVentana() {
        try {
            agregarTodasLasSucursales();
            // Configurar todos los listeners una sola vez
            configurarListeners();
            vistaSucursal.setVisible(true);
        } catch (Exception e) {
            vistaSucursal.mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }

    // Método para cerrar la ventana
    public void cerrarVentana() {
        vistaSucursal.setVisible(false);
        vistaSucursal.dispose();
    }

    // Configurar todos los listeners en un solo método
    private void configurarListeners() throws NoDataException, GlobalException {
        handlerRegistrar();
        handlerModificar();
        handlerClaseGrupal();
        handlerBarraBusqueda();
    }
}