package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente extends Persona{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private TipoPersona tipoPersona;
    private String banco;
    private LocalDateTime fechaAlta;
    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Cuenta> cuentas = new HashSet<>();

    public Cliente() {
        this.fechaAlta = LocalDateTime.now();
    }

    public Cliente(String nombre, String apellido, long dni, TipoPersona tipoPersona) {
        super(nombre, apellido, dni);
        this.tipoPersona = tipoPersona;
        this.banco = "Banco De Ando";
        this.fechaAlta = LocalDateTime.now();
    }

    public Cliente(ClienteDto clienteDto){
        super(clienteDto.getNombre(), clienteDto.getApellido(), clienteDto.getDni());
        this.setFechaNacimiento(clienteDto.getFechaNacimiento());
        this.banco = "Banco De Ando";
        this.tipoPersona = TipoPersona.fromString(clienteDto.getTipoPersona());
        this.fechaAlta = LocalDateTime.now();
    }

    public void addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
        cuenta.setTitular(this);
    }

    public boolean tieneCuenta(TipoCuenta tipoCuenta, TipoMoneda moneda) {
        for (Cuenta cuenta:
                cuentas) {
            if (tipoCuenta.equals(cuenta.getTipoCuenta()) && moneda.equals(cuenta.getMoneda())) {
                return true;
            }
        }
        return false;
    }
}
