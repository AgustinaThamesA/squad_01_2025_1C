package org.psa.model;

public class Riesgo {
    private static int contadorRiesgo = 1;
    private int idRiesgo;
    private String descripcion;
    private Probabilidad probabilidad;
    private Impacto impacto;
    private Estado estado;

    public enum Probabilidad {
        BAJA("Baja"),
        MEDIA("Media"),
        ALTA("Alta");
        
        private final String displayName;
        
        Probabilidad(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() { return displayName; }
    }

    public enum Impacto {
        BAJO("Bajo"),
        MEDIO("Medio"),
        ALTO("Alto");
        
        private final String displayName;
        
        Impacto(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() { return displayName; }
    }

    public enum Estado {
        ACTIVO("Activo"),
        MITIGADO("Mitigado"),
        MATERIALIZADO("Materializado"),
        CERRADO("Cerrado");
        
        private final String displayName;
        Estado(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }

    public Riesgo(String descripcion, Probabilidad probabilidad, Impacto impacto) {
        this.idRiesgo = contadorRiesgo++;
        this.descripcion = descripcion;
        this.probabilidad = probabilidad;
        this.impacto = impacto;
        this.estado = Estado.ACTIVO;
    }

    // Getters
    public int getIdRiesgo() { return idRiesgo; }
    public String getDescripcion() { return descripcion; }
    public Probabilidad getProbabilidad() { return probabilidad; }
    public Impacto getImpacto() { return impacto; }
    public Estado getEstado() { return estado; }

    // Setters y métodos de negocio
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setProbabilidad(Probabilidad probabilidad) { this.probabilidad = probabilidad; }
    public void setImpacto(Impacto impacto) { this.impacto = impacto; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public int calcularNivelRiesgo() {
        int nivelProbabilidad = probabilidad.ordinal() + 1; // 1-3
        int nivelImpacto = impacto.ordinal() + 1; // 1-3
        return nivelProbabilidad * nivelImpacto; // 1-9
    }

    public boolean esRiesgoAlto() {
        return calcularNivelRiesgo() >= 6;
    }
}
