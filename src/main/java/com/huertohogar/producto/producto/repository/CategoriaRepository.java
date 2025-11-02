package com.huertohogar.producto.producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.huertohogar.producto.producto.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

    @Query("SELECT c FROM Categoria c WHERE c.nombreCategoria = :nombre")
    List<Categoria> findByNombre(String nombre);

    public Categoria findByIdCategoria(int id);

    public Categoria findByNombreCategoria(String nombreCategoria);    
    
}
