package modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cita {

    private int codCita;
    private int codPac;
    private int codEsp;
    private LocalDate fechaCita;
    private BigDecimal montoCita;

    public Cita() {
    }

    public Cita(int codCita, int codPac, int codEsp,
                LocalDate fechaCita, BigDecimal montoCita) {
        this.codCita = codCita;
        this.codPac = codPac;
        this.codEsp = codEsp;
        this.fechaCita = fechaCita;
        this.montoCita = montoCita;
    }

    public int getCodCita() {
        return codCita;
    }

    public void setCodCita(int codCita) {
        this.codCita = codCita;
    }

    public int getCodPac() {
        return codPac;
    }

    public void setCodPac(int codPac) {
        this.codPac = codPac;
    }

    public int getCodEsp() {
        return codEsp;
    }

    public void setCodEsp(int codEsp) {
        this.codEsp = codEsp;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public BigDecimal getMontoCita() {
        return montoCita;
    }

    public void setMontoCita(BigDecimal montoCita) {
        this.montoCita = montoCita;
    }
}