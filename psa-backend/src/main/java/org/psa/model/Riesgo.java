package org.psa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "riesgos")
public class Riesgo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRiesgo;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    private Probabilidad probabilidad;
    
    @Enumerated(EnumType.STRING)
    private Impacto impacto;
    
    @Enumerated(EnumType.STRING)
    private Estado estado;
    
    // ✅ SOLUCIÓN: Agregar @JsonBackReference para evitar bucle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id")
    @JsonBackReference("proyecto-riesgos") // ✅ Este lado NO se serializa
    private Proyecto proyecto;
    
    // Tus enums originales (SIN CAMBIOS - perfectos)
    public enum Probabilidad {
        BAJA("Baja"),
        MEDIA("Media"),
        ALTA("Alta");
        
        private final String displayName;
        
        Probabilidad(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum Impacto {
        BAJO("Bajo"),
        MEDIO("Medio"),
        ALTO("Alto");
        
        private final String displayName;
        
        Impacto(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum Estado {
        ACTIVO("Activo"),
        MITIGADO("Mitigado"),
        MATERIALIZADO("Materializado"),
        CERRADO("Cerrado");
        
        private final String displayName;
        
        Estado(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructor por defecto (requerido por JPA)
    public Riesgo() {
    }
    
    // Tu constructor original
    public Riesgo(String descripcion, Probabilidad probabilidad, Impacto impacto) {
        this.descripcion = descripcion;
        this.probabilidad = probabilidad;
        this.impacto = impacto;
        this.estado = Estado.ACTIVO;
    }
    
    // Getters
    public Long getIdRiesgo() {
        return idRiesgo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public Probabilidad getProbabilidad() {
        return probabilidad;
    }
    
    public Impacto getImpacto() {
        return impacto;
    }
    
    public Estado getEstado() {
        return estado;
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }
    
    // Setters y métodos de negocio (SIN CAMBIOS)
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void setProbabilidad(Probabilidad probabilidad) {
        this.probabilidad = probabilidad;
    }
    
    public void setImpacto(Impacto impacto) {
        this.impacto = impacto;
    }
    
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    // Tus métodos de cálculo (SIN CAMBIOS - perfectos)
    public int calcularNivelRiesgo() {
        int nivelProbabilidad = probabilidad.ordinal() + 1; // 1-3
        int nivelImpacto = impacto.ordinal() + 1; // 1-3
        return nivelProbabilidad * nivelImpacto; // 1-9
    }
    
    public boolean esRiesgoAlto() {
        return calcularNivelRiesgo() >= 6;
    }

}
