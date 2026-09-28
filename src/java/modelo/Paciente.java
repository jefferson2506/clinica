package modelo;

public class Paciente {

    private int codPac;
    private String apePac;
    private String dirPac;
    private String telefPac;
    private String correoPac;

    public Paciente() {
    }

    public Paciente(int codPac, String apePac, String dirPac,
                    String telefPac, String correoPac) {
        this.codPac = codPac;
        this.apePac = apePac;
        this.dirPac = dirPac;
        this.telefPac = telefPac;
        this.correoPac = correoPac;
    }

    public int getCodPac() {
        return codPac;
    }

    public void setCodPac(int codPac) {
        this.codPac = codPac;
    }

    public String getApePac() {
        return apePac;
    }

    public void setApePac(String apePac) {
        this.apePac = apePac;
    }

    public String getDirPac() {
        return dirPac;
    }

    public void setDirPac(String dirPac) {
        this.dirPac = dirPac;
    }

    public String getTelefPac() {
        return telefPac;
    }

    public void setTelefPac(String telefPac) {
        this.telefPac = telefPac;
    }

    public String getCorreoPac() {
        return correoPac;
    }

    public void setCorreoPac(String correoPac) {
        this.correoPac = correoPac;
    }
}