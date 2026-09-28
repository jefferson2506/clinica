package modelo;

public class Especialidad {

    private int codEsp;
    private String nombEsp;

    public Especialidad() {
    }

    public Especialidad(int codEsp, String nombEsp) {
        this.codEsp = codEsp;
        this.nombEsp = nombEsp;
    }

    public int getCodEsp() {
        return codEsp;
    }

    public void setCodEsp(int codEsp) {
        this.codEsp = codEsp;
    }

    public String getNombEsp() {
        return nombEsp;
    }

    public void setNombEsp(String nombEsp) {
        this.nombEsp = nombEsp;
    }
}