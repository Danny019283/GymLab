package Modelo.DTOs;

/**
 *
 * @author Danny
 */
public class ClienteDTO {
    private String cedula;
    private String nombre;
    private int telefono;
    private String correo;
    private String fechaNac;
    private String sexo;
    private String fechaInscrip;
    private int edad;
    private String instructor;
    private String sucursal;

    // Constructor privado para el Builder
    private ClienteDTO(Builder builder) {
        this.cedula = builder.cedula;
        this.nombre = builder.nombre;
        this.telefono = builder.telefono;
        this.correo = builder.correo;
        this.fechaNac = builder.fechaNac;
        this.sexo = builder.sexo;
        this.fechaInscrip = builder.fechaInscrip;
        this.edad = builder.edad;
        this.instructor = builder.instructor;
        this.sucursal = builder.sucursal;
    }

    // Builder class
    public static class Builder {
        private String cedula;
        private String nombre;
        private int telefono;
        private String correo;
        private String fechaNac;
        private String sexo;
        private String fechaInscrip;
        private int edad;
        private String instructor;
        private String sucursal;

        public Builder cedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder telefono(int telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder fechaNac(String fechaNac) {
            this.fechaNac = fechaNac;
            return this;
        }

        public Builder sexo(String sexo) {
            this.sexo = sexo;
            return this;
        }

        public Builder fechaInscrip(String fechaInscrip) {
            this.fechaInscrip = fechaInscrip;
            return this;
        }

        public Builder edad(int edad) {
            this.edad = edad;
            return this;
        }

        public Builder instructor(String instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder sucursal(String sucursal) {
            this.sucursal = sucursal;
            return this;
        }

        public ClienteDTO build() {
            return new ClienteDTO(this);
        }
    }

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaInscrip() {
        return fechaInscrip;
    }

    public void setFechaInscrip(String fechaInscrip) {
        this.fechaInscrip = fechaInscrip;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono=" + telefono +
                ", correo='" + correo + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", sexo='" + sexo + '\'' +
                ", fechaInscrip='" + fechaInscrip + '\'' +
                ", edad=" + edad +
                ", instructor="+ instructor +
                ", sucursal=" + sucursal +
                '}';
    }
}