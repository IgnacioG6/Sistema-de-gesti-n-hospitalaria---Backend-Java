package com.example.hospital.model;

import com.example.hospital.model.enums.Estado;
import com.example.hospital.model.enums.Genero;
import com.example.hospital.model.enums.TipoSangre;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class Paciente {

    private Long id;
    private String nombre;
    private String dni;
    private LocalDate fechaNacimiento;
    private Genero genero;
    private String email;
    private String telefono;
    private String direccion;
    private TipoSangre tipoSangre;
    private String string;
    private Estado estado;

}
