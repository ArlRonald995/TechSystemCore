package com.techsystem.Logica;

import java.sql.Date;

public class Valoracion {
    private int id;
    private String productoSku;
    private int usuarioId;
    private int puntuacion;
    private String comentario;
    private Date fecha;

    public Valoracion(int id, String sku, int uid, int pts, String coment, Date fecha) {
        this.id = id;
        this.productoSku = sku;
        this.usuarioId = uid;
        this.puntuacion = pts;
        this.comentario = coment;
        this.fecha = fecha;
    }

    public int getPuntuacion() { return puntuacion; }
    public String getComentario() { return comentario; }
    public String getProductoSku() { return productoSku; }
}