package com.huertohogar.producto.producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huertohogar.producto.producto.model.Categoria;
import com.huertohogar.producto.producto.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria findByIdCategoria(Integer id) {
        return categoriaRepository.findByIdCategoria(id);                
    }

    public Categoria findByNombreCategoria(String nombre) {
        return categoriaRepository.findByNombreCategoria(nombre);
    }   

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteByIdCategoria(Integer id) {
        categoriaRepository.deleteById(id);    
    }   
    
}
