package com.huertohogar.producto.producto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTO")
public class Producto {

    @Id
    @Column(name = "id_producto" ,nullable = false)
    private Integer idProducto;
    
    @Column(name = "nombre", nullable = false) 
    private String nombreProducto;

    @Column(name = "precio", nullable = false)
    private int precioProducto;

    @Column(name = "unidad", nullable = false)
    private String unidadProducto;

    @Column(name = "descripcion", nullable = false)
    private String descripcionProducto;

    @Column(name = "imagen")
    private String imagenProducto;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = false)
    private Categoria idCategoria;
    


}
