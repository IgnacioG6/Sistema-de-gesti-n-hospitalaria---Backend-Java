package com.example.hospital.model;

import com.example.hospital.model.enums.Estado;
import com.example.hospital.model.enums.Genero;
import com.example.hospital.model.enums.TipoSangre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@NoArgsConstructor
@Setter
@Getter
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String dni;

    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    private String email;
    private String telefono;
    private String direccion;

    @Enumerated(EnumType.STRING)
    private TipoSangre tipoSangre;

    private String alergias;

    @Enumerated(EnumType.STRING)
    private Estado estado;

}
