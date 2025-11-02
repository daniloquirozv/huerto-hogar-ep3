package com.huertohogar.producto.producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.huertohogar.producto.producto.model.Categoria;
import com.huertohogar.producto.producto.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    
    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    List<Producto> findByNombre(String nombre);

    public Producto findByIdProducto(Integer idProducto);

    public Producto findByNombreProducto(String nombreProducto);

    @Query("SELECT p FROM Producto p WHERE p.idCategoria = :Categoria_id_categoria")
    List<Producto> findByIdCategoria(Categoria Categoria_id_categoria);

}
