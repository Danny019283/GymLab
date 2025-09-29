package AccesoADatos;

import Modelo.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class CargadorDeDatos {

    public void cargarDatosPrueba() {
        try {
            System.out.println("Iniciando carga de datos de prueba...");

            // Cargar sucursales primero
            cargarSucursales();

            // Cargar instructores
            cargarInstructores();

            // Cargar clientes
            cargarClientes();

            // Cargar clases grupales
            cargarClasesGrupales();

            // Cargar mediciones
            cargarMediciones();

            // Cargar rutinas
            cargarRutinas();

            // Matricular clientes en clases
            matricularClientesEnClases();

            System.out.println("¡Carga de datos completada exitosamente!");

        } catch (Exception e) {
            System.err.println("Error durante la carga de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarSucursales() throws Exception {
        ServicioSucursal servicio = new ServicioSucursal();

        ArrayList<Sucursal> sucursales = new ArrayList<>(Arrays.asList(
                new Sucursal("SJ01", "San José", "Central", "sanjose@gym.com", 22220001),
                new Sucursal("SJ02", "San José", "Escazú", "escazu@gym.com", 22220002),
                new Sucursal("SJ03", "San José", "Curridabat", "curri@gym.com", 22220003),
                new Sucursal("AL01", "Alajuela", "Central", "alajuela@gym.com", 24420001),
                new Sucursal("HE01", "Heredia", "Central", "heredia@gym.com", 25520001)
        ));

        for (Sucursal sucursal : sucursales) {
            try {
                servicio.insertarSucursal(sucursal);
                System.out.println("Sucursal creada: " + sucursal.getCod());
            } catch (GlobalException e) {
                System.out.println("Sucursal ya existe: " + sucursal.getCod());
            }
        }
    }

    private void cargarInstructores() throws Exception {
        ServicioInstructor servicio = new ServicioInstructor();

        ArrayList<Instructor> instructores = new ArrayList<>(Arrays.asList(
                new Instructor("101110111", "María Rodríguez", 88881111, "maria@gym.com",
                        "1985-03-15", new ArrayList<>(Arrays.asList("Yoga", "Pilates")), "SJ01"),
                new Instructor("202220222", "Carlos Méndez", 88882222, "carlos@gym.com",
                        "1988-07-22", new ArrayList<>(Arrays.asList("CrossFit", "Funcional")), "SJ01"),
                new Instructor("303330333", "Ana Vargas", 88883333, "ana@gym.com",
                        "1990-11-30", new ArrayList<>(Arrays.asList("Spinning", "Cardio")), "SJ02"),
                new Instructor("404440444", "Javier Rojas", 88884444, "javier@gym.com",
                        "1987-05-10", new ArrayList<>(Arrays.asList("Boxeo", "Artes Marciales")), "AL01"),
                new Instructor("505550555", "Laura Chen", 88885555, "laura@gym.com",
                        "1992-09-18", new ArrayList<>(Arrays.asList("Danza", "Aeróbicos")), "HE01")
        ));

        for (Instructor instructor : instructores) {
            try {
                servicio.insertarInstructor(instructor);
                System.out.println("Instructor creado: " + instructor.getNombreCom());
            } catch (GlobalException e) {
                System.out.println("Instructor ya existe: " + instructor.getCedula());
            }
        }
    }

    private void cargarClientes() throws Exception {
        ServicioCliente servicio = new ServicioCliente();

        ArrayList<Cliente> clientes = new ArrayList<>(Arrays.asList(
                new Cliente("106540888", "Juan Pérez", 25, 88880001, "juan@email.com",
                        "1998-05-20", "Masculino", "2024-01-15",
                        new Instructor.Builder().cedula("101110111").build(),
                        new Sucursal.Builder().cod("SJ01").build()),
                new Cliente("206650999", "María López", 30, 88880002, "maria@email.com",
                        "1993-08-12", "Femenino", "2024-01-20",
                        new Instructor.Builder().cedula("101110111").build(),
                        new Sucursal.Builder().cod("SJ01").build()),
                new Cliente("307760111", "Pedro Sánchez", 22, 88880003, "pedro@email.com",
                        "2001-12-03", "Masculino", "2024-02-01",
                        new Instructor.Builder().cedula("202220222").build(),
                        new Sucursal.Builder().cod("SJ01").build()),
                new Cliente("408870222", "Ana Martínez", 35, 88880004, "ana@email.com",
                        "1988-03-25", "Femenino", "2024-02-05",
                        new Instructor.Builder().cedula("202220222").build(),
                        new Sucursal.Builder().cod("SJ02").build()),
                new Cliente("509980333", "Carlos González", 28, 88880005, "carlos@email.com",
                        "1995-07-14", "Masculino", "2024-02-10",
                        new Instructor.Builder().cedula("303330333").build(),
                        new Sucursal.Builder().cod("SJ02").build()),
                new Cliente("601090444", "Sofía Hernández", 32, 88880006, "sofia@email.com",
                        "1991-09-08", "Femenino", "2024-02-15",
                        new Instructor.Builder().cedula("303330333").build(),
                        new Sucursal.Builder().cod("SJ02").build()),
                new Cliente("702100555", "Diego Ramírez", 26, 88880007, "diego@email.com",
                        "1997-11-30", "Masculino", "2024-02-20",
                        new Instructor.Builder().cedula("404440444").build(),
                        new Sucursal.Builder().cod("AL01").build()),
                new Cliente("803210666", "Laura Navarro", 29, 88880008, "laura@email.com",
                        "1994-04-17", "Femenino", "2024-03-01",
                        new Instructor.Builder().cedula("404440444").build(),
                        new Sucursal.Builder().cod("AL01").build()),
                new Cliente("904320777", "Andrés Solís", 31, 88880009, "andres@email.com",
                        "1992-06-22", "Masculino", "2024-03-05",
                        new Instructor.Builder().cedula("505550555").build(),
                        new Sucursal.Builder().cod("HE01").build()),
                new Cliente("105430888", "Carmen Muñoz", 27, 88880010, "carmen@email.com",
                        "1996-10-11", "Femenino", "2024-03-10",
                        new Instructor.Builder().cedula("505550555").build(),
                        new Sucursal.Builder().cod("HE01").build())
        ));

        for (Cliente cliente : clientes) {
            try {
                servicio.insertarCliente(cliente);
                System.out.println("Cliente creado: " + cliente.getNombre());
            } catch (GlobalException e) {
                System.out.println("Cliente ya existe: " + cliente.getCedula());
            }
        }
    }

    private void cargarClasesGrupales() throws Exception {
        ServicioClaseGrupal servicio = new ServicioClaseGrupal();

        ArrayList<ClaseGrupal> clases = new ArrayList<>(Arrays.asList(
                new ClaseGrupal("YOGA01", 20, 101, "Yoga", "Lun-Mie 7:00 AM",
                        new Instructor.Builder().cedula("101110111").build()),
                new ClaseGrupal("PILA01", 15, 102, "Pilates", "Mar-Jue 8:00 AM",
                        new Instructor.Builder().cedula("101110111").build()),
                new ClaseGrupal("CROS01", 25, 201, "CrossFit", "Lun-Mie-Vie 6AM",
                        new Instructor.Builder().cedula("202220222").build()),
                new ClaseGrupal("SPIN01", 30, 202, "Spinning", "Lun-Vie 5:00 PM",
                        new Instructor.Builder().cedula("303330333").build()),
                new ClaseGrupal("BOXE01", 20, 301, "Boxeo", "Mar-Jue 7:00 PM",
                        new Instructor.Builder().cedula("404440444").build()),
                new ClaseGrupal("DANZ01", 25, 302, "Danza", "Lun-Mie 6:00 PM",
                        new Instructor.Builder().cedula("505550555").build()),
                new ClaseGrupal("FUNC01", 20, 103, "Funcional", "Mar-Jue 9:00 AM",
                        new Instructor.Builder().cedula("202220222").build()),
                new ClaseGrupal("CARD01", 35, 203, "Cardio", "Lun-Vie 7:00 AM",
                        new Instructor.Builder().cedula("303330333").build())
        ));

        for (ClaseGrupal clase : clases) {
            try {
                servicio.insertarClaseGrupal(clase);
                System.out.println("Clase grupal creada: " + clase.getCodigo());
            } catch (GlobalException e) {
                System.out.println("Clase ya existe: " + clase.getCodigo());
            }
        }
    }

    private void cargarMediciones() throws Exception {
        ServicioMedicion servicio = new ServicioMedicion();
        ServicioCliente servicioCliente = new ServicioCliente();

        // Obtener clientes existentes
        ArrayList<Cliente> clientes = servicioCliente.listarClientes();

        for (int i = 0; i < Math.min(8, clientes.size()); i++) {
            Cliente cliente = clientes.get(i);

            Medicion medicion = new Medicion.Builder()
                    .cliente(cliente)
                    .peso(65.0f + (i * 2))
                    .estatura(1.65f + (i * 0.05f))
                    .porcGrasa(18.0f + i)
                    .porcMusculo(35.0f - (i * 0.5f))
                    .porcGrasaVis(8.0f + (i * 0.3f))
                    .cintura(75.0f + i)
                    .pecho(95.0f + i)
                    .muslo(55.0f + i)
                    .fechaDeMedicion(LocalDate.now().minusDays(i * 7))
                    .build();

            try {
                servicio.insertarMedicion(medicion);
                System.out.println("Medición creada para: " + cliente.getNombre());
            } catch (GlobalException e) {
                System.out.println("Medición ya existe para: " + cliente.getCedula());
            }
        }
    }

    private void cargarRutinas() throws Exception {
        ServicioRutina servicio = new ServicioRutina();
        ServicioCliente servicioCliente = new ServicioCliente();

        // Obtener clientes existentes
        ArrayList<Cliente> clientes = servicioCliente.listarClientes();

        String[] ejerciciosPecho = {"Press de banca", "Press inclinado", "Fondos", "Aperturas"};
        String[] ejerciciosTriceps = {"Extensión tríceps", "Fondos paralelas", "Press cerrado"};
        String[] ejerciciosBiceps = {"Curl de bíceps", "Curl martillo", "Curl concentrado"};
        String[] ejerciciosPiernas = {"Sentadillas", "Prensa", "Extensiones", "Femorales"};
        String[] ejerciciosEspalda = {"Jalón al pecho", "Remo con barra", "Peso muerto"};

        for (int i = 0; i < Math.min(6, clientes.size()); i++) {
            Cliente cliente = clientes.get(i);

            Rutina rutina = new Rutina(
                    ejerciciosPecho[i % ejerciciosPecho.length] + " - 4x12",
                    ejerciciosTriceps[i % ejerciciosTriceps.length] + " - 3x15",
                    ejerciciosBiceps[i % ejerciciosBiceps.length] + " - 3x12",
                    ejerciciosPiernas[i % ejerciciosPiernas.length] + " - 4x10",
                    ejerciciosEspalda[i % ejerciciosEspalda.length] + " - 4x12"
            );

            try {
                servicio.insertarRutina(cliente.getCedula(), rutina);
                System.out.println("Rutina creada para: " + cliente.getNombre());
            } catch (GlobalException e) {
                System.out.println("Rutina ya existe para: " + cliente.getCedula());
            }
        }
    }

    private void matricularClientesEnClases() throws Exception {
        ServicioRegistroClases servicio = new ServicioRegistroClases();
        ServicioCliente servicioCliente = new ServicioCliente();

        // Obtener clientes existentes
        ArrayList<Cliente> clientes = servicioCliente.listarClientes();

        String[] clases = {"YOGA01", "PILA01", "CROS01", "SPIN01", "BOXE01", "DANZ01", "FUNC01", "CARD01"};

        int matriculasCreadas = 0;
        for (int i = 0; i < Math.min(12, clientes.size()); i++) {
            Cliente cliente = clientes.get(i);
            String clase = clases[i % clases.length];

            try {
                // Verificar si la clase existe primero
                ServicioClaseGrupal servicioClase = new ServicioClaseGrupal();
                ClaseGrupal claseExistente = servicioClase.buscarClaseGrupal(clase);

                if (claseExistente != null) {
                    servicio.insertarClaseCliente(clase, cliente.getCedula());
                    matriculasCreadas++;
                    System.out.println("Cliente " + cliente.getNombre() + " matriculado en " + clase);
                } else {
                    System.out.println("Clase no existe: " + clase);
                }
            } catch (GlobalException e) {
                System.out.println("Matrícula ya existe: " + cliente.getCedula() + " en " + clase);
            }
        }

        System.out.println("Total de matrículas creadas: " + matriculasCreadas);
    }

    // Método para limpiar y recrear datos (opcional - usar con cuidado)
    public void limpiarYRecrearDatos() {
        try {
            System.out.println("ADVERTENCIA: Esto eliminará todos los datos existentes");
            System.out.println("¿Está seguro? (s/n)");
            // En una aplicación real, aquí pedirías confirmación al usuario

            // Primero eliminar matrículas
            eliminarMatriculas();

            // Luego eliminar otras entidades en orden inverso
            eliminarRutinas();
            eliminarMediciones();
            eliminarClasesGrupales();
            eliminarClientes();
            eliminarInstructores();
            eliminarSucursales();

            System.out.println("Datos eliminados. Ahora recreando...");
            cargarDatosPrueba();

        } catch (Exception e) {
            System.err.println("Error al limpiar datos: " + e.getMessage());
        }
    }

    private void eliminarMatriculas() {
        // Implementar lógica para eliminar matrículas si es necesario
        System.out.println("Eliminando matrículas...");
    }

    private void eliminarRutinas() {
        // Implementar lógica para eliminar rutinas si es necesario
        System.out.println("Eliminando rutinas...");
    }

    private void eliminarMediciones() {
        // Implementar lógica para eliminar mediciones si es necesario
        System.out.println("Eliminando mediciones...");
    }

    private void eliminarClasesGrupales() {
        // Implementar lógica para eliminar clases grupales si es necesario
        System.out.println("Eliminando clases grupales...");
    }

    private void eliminarClientes() {
        // Implementar lógica para eliminar clientes si es necesario
        System.out.println("Eliminando clientes...");
    }

    private void eliminarInstructores() {
        // Implementar lógica para eliminar instructores si es necesario
        System.out.println("Eliminando instructores...");
    }

    private void eliminarSucursales() {
        // Implementar lógica para eliminar sucursales si es necesario
        System.out.println("Eliminando sucursales...");
    }

    // Método principal para probar la carga de datos
    public static void main(String[] args) {
        CargadorDeDatos cargador = new CargadorDeDatos();

        // Opción 1: Solo cargar datos (mantiene existentes)
        cargador.cargarDatosPrueba();

        // Opción 2: Limpiar y recrear todo (descomentar si necesitas)
        // cargador.limpiarYRecrearDatos();
    }
}