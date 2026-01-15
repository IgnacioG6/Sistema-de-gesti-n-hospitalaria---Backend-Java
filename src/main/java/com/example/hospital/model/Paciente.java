package com.example.hospital.model;

import com.example.hospital.model.enums.Estado;
import com.example.hospital.model.enums.Genero;
import com.example.hospital.model.enums.TipoSangre;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
public class Paciente {

    private static long contador = 1;

    @Setter(AccessLevel.NONE)
    private Long id;

    private String nombre;
    private String dni;
    private LocalDate fechaNacimiento;
    private Genero genero;
    private String email;
    private String telefono;
    private String direccion;
    private TipoSangre tipoSangre;
    private String alergias;
    private Estado estado;

    public Paciente() {
        this.id = contador++;
    }

}
