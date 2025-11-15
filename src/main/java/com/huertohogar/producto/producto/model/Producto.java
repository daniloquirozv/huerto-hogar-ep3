package com.huertohogar.producto.producto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer idProducto;
    
    @Column(name = "nombre", nullable = false) 
    private String nombreProducto;

    @Column(name = "precio", nullable = false)
    private int precioProducto;

    @Column(name = "unidad", nullable = false)
    private String unidadProducto;

    @Column(name = "descripcion", nullable = false)
    private String descripcionProducto;
    
    @Column(name = "stock", nullable = false)
    private int stockProducto;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Categoria idCategoria;

}
