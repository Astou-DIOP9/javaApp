package models;

import java.sql.Date;
import java.util.Objects;

public class FichePedagogique {
    private int id;
    private int coursId;
    private Date dateGeneration;
    private String cheminFichier;
    private String format; // Doit être "pdf", "excel" ou "word"

    // Constructeur par défaut
    public FichePedagogique() {}

    // Getters
    public int getId() {
        return id;
    }

    public int getCoursId() {
        return coursId;
    }

    public Date getDateGeneration() {
        return dateGeneration;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public String getFormat() {
        return format;
    }

    // Setters avec validation
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("L'ID ne peut pas être négatif");
        }
        this.id = id;
    }

    public void setCoursId(int coursId) {
        if (coursId <= 0) {
            throw new IllegalArgumentException("L'ID du cours doit être positif");
        }
        this.coursId = coursId;
    }

    public void setDateGeneration(Date dateGeneration) {
        this.dateGeneration = Objects.requireNonNull(dateGeneration, "La date de génération ne peut pas être null");
    }

    public void setCheminFichier(String cheminFichier) {
        if (cheminFichier != null && cheminFichier.length() > 255) {
            throw new IllegalArgumentException("Le chemin du fichier ne peut excéder 255 caractères");
        }
        this.cheminFichier = cheminFichier;
    }

    public void setFormat(String format) {
        if (format != null && !format.matches("pdf|excel|word")) {
            throw new IllegalArgumentException("Le format doit être pdf, excel ou word");
        }
        this.format = format;
    }

    @Override
    public String toString() {
        return "FichePedagogique{" +
               "id=" + id +
               ", coursId=" + coursId +
               ", dateGeneration=" + dateGeneration +
               ", cheminFichier='" + cheminFichier + '\'' +
               ", format='" + format + '\'' +
               '}';
    }
}