package Controlador;
import AccesoADatos.*;
import Modelo.*;
import Modelo.Servicios.ServicioCliente;
import Vista.Formularios.FormularioCliente;
import Vista.VistaCliente;
import Modelo.DTOs.ClienteDTO;

import javax.swing.*;
import java.util.ArrayList;



public class ControladorCliente {

    ////////////////////////////////Instancias
    /// ///////////////////////////////////////
    private final ServicioCliente servicioCliente = new ServicioCliente();
    private final VistaCliente vistaCliente;
    private final ControladorMedicion controladorMedicion;
    private final DAORegistroClases DAORegistroClases;
    private Runnable accionAtras;


    //Constructor

    public ControladorCliente() {
        this.vistaCliente = new VistaCliente();
        this.controladorMedicion = new ControladorMedicion();
        this.DAORegistroClases = new DAORegistroClases();
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

        //se debe comprobar en servicio la existencia de la suscursal y el instructor
        ControladorInstructor controlInstructor = new ControladorInstructor();
        ControladorSucursal controladorSucursal = new ControladorSucursal();
        String instructor = controlInstructor.getServicioInstructor().buscarInstructor(instructorId);
        String sucursal = controladorSucursal.getServicioSucursal().buscarSucursal(sucursalCod);

        ClienteDTO dto = new ClienteDTO.Builder()
                .cedula(cedula)
                .nombre(nombre)
                .telefono(telefono)
                .correo(correo)
                .fechaNac(fechaNac)
                .sexo(sexo)
                .fechaInscrip(fechaInscrip)
                .edad(edad)
                .instructor(instructor)
                .sucursal(sucursal)
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
        FormularioCliente formulario = new FormularioCliente();
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

    public ArrayList<Cliente> mostrarClientesPorInstructor(String cedulaInstructor) throws GlobalException {
        return DAOCliente.listarClientesPorInstructor(cedulaInstructor);

    }

    public void mostrarClasesSegunCliente() throws GlobalException {
        //obtener el texto
        String cedula = vistaCliente.pedirDato("Ingrese la cedula del cliente");
        //validar
        if (cedula == null) {return;}
        if (cedula.isEmpty()) {
            vistaCliente.mostrarError("Debe ingresar una cedula valida");
            return;
        }
        if(DAOCliente.buscarCliente(cedula) == null) {
            vistaCliente.mostrarMensaje("Mensaje", "El cliente con la cedula ingresada no existe");
            return;
        }
        ArrayList<ClaseGrupal> clases = DAORegistroClases.buscarClasesSegunCliente(cedula);
        if(clases.isEmpty()) {
            vistaCliente.mostrarMensaje("Mensaje", "El cliente no tiene clases matriculadas");
            return;
        }
        vistaCliente.mostrarClases(clases);
    }

    public void verRutina() throws GlobalException {
        String cedula = vistaCliente.pedirDato("Ingrese la cedula del cliente");
        ControladorRutina controladorRutina = new ControladorRutina();
        Rutina rutina = controladorRutina.buscarRutina(cedula);

        if (rutina != null) {
            try {
                vistaCliente.mostrarMensaje("Rutina: ", rutina.toString());
                return;
            } catch (Exception e) {
                vistaCliente.mostrarError("Error al generar reporte: " + e.getMessage());
            }
        }
        vistaCliente.mostrarMensaje("Error", "No tiene una rutina asignada");
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
                        ControladorClaseGrupal controladorClaseGrupal = new ControladorClaseGrupal();
                        int resultadoMatricula = controladorClaseGrupal.matricularCliente();
                        if (resultadoMatricula == 1) {
                            vistaCliente.mostrarError("El cliente ya esta inscrito a 2 clases.");
                        } else if (resultadoMatricula == 2) {
                            vistaCliente.mostrarError("No hay cupos disponibles en la clase");
                        } else if (resultadoMatricula == 0) {
                            vistaCliente.mostrarMensaje("Confimacion", "Cliente matriculado con exito.");
                        }
                    } catch (NoDataException ex) {

                        throw new RuntimeException(ex);

                    } catch (GlobalException ex) {

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