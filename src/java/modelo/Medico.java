package modelo;

public class Medico {

    private int codMed;
    private int codEsp;
    private String apeMed;
    private String dirMed;
    private String telefMed;
    private String correoMed;

    public Medico() {
    }

    public Medico(int codMed, int codEsp, String apeMed,
                   String dirMed, String telefMed, String correoMed) {
        this.codMed = codMed;
        this.codEsp = codEsp;
        this.apeMed = apeMed;
        this.dirMed = dirMed;
        this.telefMed = telefMed;
        this.correoMed = correoMed;
    }

    public int getCodMed() {
        return codMed;
    }

    public void setCodMed(int codMed) {
        this.codMed = codMed;
    }

    public int getCodEsp() {
        return codEsp;
    }

    public void setCodEsp(int codEsp) {
        this.codEsp = codEsp;
    }

    public String getApeMed() {
        return apeMed;
    }

    public void setApeMed(String apeMed) {
        this.apeMed = apeMed;
    }

    public String getDirMed() {
        return dirMed;
    }

    public void setDirMed(String dirMed) {
        this.dirMed = dirMed;
    }

    public String getTelefMed() {
        return telefMed;
    }

    public void setTelefMed(String telefMed) {
        this.telefMed = telefMed;
    }

    public String getCorreoMed() {
        return correoMed;
    }

    public void setCorreoMed(String correoMed) {
        this.correoMed = correoMed;
    }
}