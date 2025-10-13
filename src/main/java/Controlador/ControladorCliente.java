package Controlador;
import AccesoADatos.*;
import Modelo.*;
import Modelo.Servicios.ServicioCliente;
import Modelo.Servicios.ServicioRutina;
import Vista.Formularios.FormularioCliente;
import Vista.VistaCliente;
import Modelo.DTOs.ClienteDTO;

import javax.swing.*;
import java.util.ArrayList;



public class ControladorCliente {

    ////////////////////////////////Instancias
    /// ///////////////////////////////////////
    private final ServicioCliente servicioCliente;
    private final VistaCliente vistaCliente;
    private final ControladorMedicion controladorMedicion;
    private final ControladorClaseGrupal controladorClaseGrupal;
    private final ServicioRutina servicioRutina;
    private Runnable accionAtras;


    //Constructor

    public ControladorCliente() {
        this.servicioCliente = new ServicioCliente();
        this.vistaCliente = new VistaCliente();
        this.controladorMedicion = new ControladorMedicion();
        this.controladorClaseGrupal = new ControladorClaseGrupal();
        this.servicioRutina = new ServicioRutina();
    }


    ////////////////////////////////////////FUNCIONES
    /// //////////////////////////////////////////////
    public void registrarCliente() throws GlobalException, NoDataException {
        FormularioCliente formulario = new FormularioCliente();
        boolean resultado = formulario.mostrarDialogo("Agregar Cliente");

        if (!resultado) {
            return;
        }
        if(!formulario.validarDatos()) {
            return;
        }

        String cedula = formulario.getCedula();
        String nombre = formulario.getNombre();
        int telefono = formulario.getTelefono();
        String correo = formulario.getCorreo();
        String fechaNac = formulario.getFechaNac();
        String sexo = formulario.getSexo();
        String fechaInscrip = formulario.getFechaInscrip();
        int edad = formulario.getEdad();
        String instructorId = formulario.getInstructor();
        String sucursalCod = formulario.getSucursal();

        ClienteDTO dto = new ClienteDTO.Builder()
                .cedula(cedula)
                .nombre(nombre)
                .telefono(telefono)
                .correo(correo)
                .fechaNac(fechaNac)
                .sexo(sexo)
                .fechaInscrip(fechaInscrip)
                .edad(edad)
                .instructor(instructorId)
                .sucursal(sucursalCod)
                .build();
        switch (servicioCliente.insertarClienteEnBD(dto)) {
            case 0:
                //exito
                //muestra el cliente en la tabla
                vistaCliente.getTablaCliente().add(dto);
                refrescarTabla();
            case 1:
                vistaCliente.mostrarError("El cliente ya existe");
                break;
            case 2:
                vistaCliente.mostrarError("El instructor no existe");
                break;
            case 3:
                vistaCliente.mostrarError("La sucursal no existe");
                break;
            default:
                vistaCliente.mostrarError("No se pudo registrar el cliente");
                break;
        }
    }

    public void modificarCliente() throws NoDataException, GlobalException {
        ClienteDTO clienteMod = vistaCliente.getClienteSeleccionado();
        if (clienteMod == null) {
            vistaCliente.mostrarError("Debe seleccionar un cliente de la tabla.");
            return;
        }
        FormularioCliente formulario = new FormularioCliente();
        formulario.llenarFormularioMod(clienteMod);
        boolean resultado = formulario.mostrarDialogo("Modificar Cliente");

        if (!resultado) {
            return;
        }
        if(!formulario.validarDatos()) {
            return;
        }

        String cedula = formulario.getCedula();
        String nombre = formulario.getNombre();
        int telefono = formulario.getTelefono();
        String correo = formulario.getCorreo();
        String fechaNac = formulario.getFechaNac();
        String sexo = formulario.getSexo();
        int edad = formulario.getEdad();
        String instructorId = formulario.getInstructor();
        String sucursalCod = formulario.getSucursal();

        ClienteDTO dto = new ClienteDTO.Builder()
                .cedula(cedula)
                .nombre(nombre)
                .telefono(telefono)
                .correo(correo)
                .fechaNac(fechaNac)
                .sexo(sexo)
                .edad(edad)
                .instructor(instructorId)
                .sucursal(sucursalCod)
                .build();

        if (!servicioCliente.actualizarClienteEnBD(dto)) {
            vistaCliente.mostrarError("No se pudo modificar el cliente. Verifique que la cédula exista.");
            return;
        }
        vistaCliente.mostrarMensaje("Exito", "Cliente modificado exitosamente.");
        refrescarTabla();
    }

    public void eliminarCliente() throws GlobalException, NoDataException {
        String cedula = vistaCliente.pedirDato("Ingrese la cedula");
        if (cedula == null) {
            return;
        }
        if (cedula.isEmpty()) {
            return;
        }
        if (!servicioCliente.eliminarClienteEnBD(cedula)) {
            vistaCliente.mostrarError("Cedela inexistente.");
            return;
        }
        refrescarTabla();
    }

    public void refrescarTabla() {
        ArrayList<ClienteDTO> clientes = servicioCliente.obtenerClientesEnBD();
        if (clientes == null || clientes.isEmpty()) {
            return;
        }
        vistaCliente.getTablaCliente().refrescarData(clientes);
    }

    public void mostrarClasesSegunCliente() throws GlobalException {
        //obtener el texto
        String cedula = vistaCliente.pedirDato("Ingrese la cedula del cliente");
        //validar
        if (cedula == null) {
            return;
        }
        if (cedula.isEmpty()) {
            vistaCliente.mostrarError("Debe ingresar una cedula valida");
            return;
        }
        if (servicioCliente.buscarClienteEnBD(cedula) == null) {
            vistaCliente.mostrarMensaje("Mensaje", "El cliente con la cedula ingresada no existe");
            return;
        }
        String clases = servicioCliente.obtenerClasesSegunCliente(cedula);
        if (clases.isEmpty()) {
            vistaCliente.mostrarMensaje("Clases", "El cliente no está matriculado en ninguna clase.");
            return;
        }
        vistaCliente.mostrarMensaje("Clases", clases);
    }

    public void verRutina() throws GlobalException {
        String cedula = vistaCliente.pedirDato("Ingrese la cedula del cliente");
        if (cedula == null) {
            return;
        }
        if (cedula.isEmpty()) {
            return;
        }
        Rutina rutina = servicioRutina.buscarRutinaEnBD(cedula);
        if (rutina == null) {
            vistaCliente.mostrarMensaje("Error", "El cliente no existe o no tiene una rutina asignada");
            return;
        }

        vistaCliente.mostrarMensaje("Rutina", rutina.toString());
    }

    ////////////////////////////////ActionListener/Handle
    /// //////////////////////////////////////////////////
    public void handlerRegistrar() {
        this.vistaCliente.addRegistrarListener(e ->
                {
                    try {
                        registrarCliente();
                    } catch (GlobalException | NoDataException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

    }

    public void handlerModificar() {
        this.vistaCliente.addModificarListener(e ->
                {
                    try {
                        modificarCliente();
                    } catch (GlobalException | NoDataException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

    }


    public void handlerEliminar() {
        this.vistaCliente.addEliminarListener(e ->
                {
                    try {
                        eliminarCliente();
                    } catch (GlobalException | NoDataException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }


    public void handlerMedicion() {
        this.vistaCliente.addMedicionListener(e -> {
            controladorMedicion.configurarBotonAtras(() -> {
                controladorMedicion.cerrarVentana();
                vistaCliente.setVisible(true); // Volver a clientes
            });
            vistaCliente.setVisible(false);
            controladorMedicion.mostrarVentana();
        });

    }


    public void handlerVerRutina() {
        this.vistaCliente.addVerRutinaListener(e ->
                {
                    try {
                        verRutina();

                    } catch (GlobalException ex) {

                        throw new RuntimeException(ex);

                    }
                }
        );

    }

    public void handlerBarraBusqueda() {
        this.vistaCliente.addBuscarListener(e ->
        {
            String cedula = vistaCliente.getTextoBusqueda();
            if (cedula.isEmpty()) {
                vistaCliente.getSorter().setRowFilter(null); // mostrar todo
            } else {
                vistaCliente.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + cedula));
            }
        });
    }

    public void handlerMatricular() {
        this.vistaCliente.addMatricularListener(e ->
                {
                    try {
                        controladorClaseGrupal.matricularCliente();
                    } catch (NoDataException | GlobalException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }

    public void handlerListarClases() {
        vistaCliente.addListarClasesListener(e ->
        {
            try {
                mostrarClasesSegunCliente();
            } catch (GlobalException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    /// ///////////////////////////Cargar datos a la tabla

    /// //////////////////////////////////////////////////////////////

    public void agregarTodosLosClientes() {
        refrescarTabla();
    }


    // Configurar para volver al menú principal
    public void configurarBotonAtras(Runnable accionAtras) {
        this.accionAtras = accionAtras;
        vistaCliente.addAtrasListener(e -> accionAtras.run());

    }


    public void mostrarVentana() {
        try {
            agregarTodosLosClientes();
            configurarListeners();
            vistaCliente.setVisible(true);

        } catch (Exception e) {
            vistaCliente.mostrarError("Error al cargar datos: " + e.getMessage());

        }
    }


    public void cerrarVentana() {
        vistaCliente.setVisible(false);
        vistaCliente.dispose();

    }


    private void configurarListeners() {
        handlerRegistrar();
        handlerModificar();
        handlerEliminar();
        handlerMedicion();
        handlerVerRutina();
        handlerMatricular();
        handlerBarraBusqueda();
        handlerListarClases();
    }
}