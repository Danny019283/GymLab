package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.DTOs.SucursalDTO;
import Modelo.Servicios.ServicioSucursal;
import Vista.Formularios.FormularioSucursal;
import Vista.VistaSucursal;

import javax.swing.*;

public class ControladorSucursal {
    private final ServicioSucursal servicioSucursal;
    private final VistaSucursal vistaSucursal;
    private final ControladorClaseGrupal controladorClaseGrupal;
    private Runnable accionAtras;

    public ControladorSucursal() {
        this.controladorClaseGrupal = new ControladorClaseGrupal();
        this.vistaSucursal = new VistaSucursal();
        this.servicioSucursal = new ServicioSucursal();
    }

    ////////////////////////////////////////FUNCIONES
    /////////////////////////////////////////////////
    public void registrarSucursal() throws GlobalException, NoDataException {
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

        SucursalDTO sucursal = new SucursalDTO.Builder()
                .cod(cod)
                .provi(provi)
                .canton(canton)
                .correo(correo)
                .telef(telef)
                .build();

        switch (servicioSucursal.insertarSucursalEnBD(sucursal)) {
            case 0:
                vistaSucursal.getTablaSucursal().add(sucursal);
                refrescarTabla();
                break; // Éxito
            case 1:
                vistaSucursal.mostrarError("La sucursal ya existe");
                return;
            case 2:
                vistaSucursal.mostrarError("Limite de sucursales alcanzado");
                break;
        }
    }

    public void modificarSucursal() throws GlobalException, NoDataException {
        FormularioSucursal formulario = new FormularioSucursal();
        boolean resultado = formulario.mostrarDialogo("Modificar Sucursal");

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

        SucursalDTO sucursal = new SucursalDTO.Builder()
                .cod(cod)
                .provi(provi)
                .canton(canton)
                .correo(correo)
                .telef(telef)
                .build();

        if(!servicioSucursal.actualizarSucursalEnBD(sucursal)) {
            vistaSucursal.mostrarError("La sucursal no existe");
            return;
        }
        refrescarTabla();
    }

    public void refrescarTabla() {
        vistaSucursal.getTablaSucursal().refrescarData(servicioSucursal.obtenerSucursalesEnBD());
    }

    ////////////////////////////////ActionListener/Handle
    /////////////////////////////////////////////////////
    public void handlerRegistrar() {
        this.vistaSucursal.addRegistrarListener(e -> {
            try {
                registrarSucursal();
            } catch (GlobalException ex) {
                vistaSucursal.mostrarError("Error al registrar: " + ex.getMessage());
            } catch (NoDataException ex) {
                throw new RuntimeException(ex);
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
        refrescarTabla();
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