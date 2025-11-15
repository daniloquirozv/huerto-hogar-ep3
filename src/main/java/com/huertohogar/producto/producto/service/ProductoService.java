package com.huertohogar.producto.producto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huertohogar.producto.producto.model.Producto;
import com.huertohogar.producto.producto.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAllProductos() {
        try {
            List<Producto> productos = productoRepository.findAll();
            // Filtrar productos que tengan categoría válida
            return productos.stream()
                .filter(p -> p.getIdCategoria() != null)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener productos: " + e.getMessage(), e);
        }
    }

    public Producto findByIdProducto(Integer idProducto) {
        return productoRepository.findByIdProducto(idProducto);
    }

    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Integer idProducto ,Producto producto) {
        Producto productoExistente = productoRepository.findByIdProducto(idProducto);
        if (productoExistente != null) {
            productoExistente.setNombreProducto(producto.getNombreProducto());
            productoExistente.setPrecioProducto(producto.getPrecioProducto());
            productoExistente.setUnidadProducto(producto.getUnidadProducto());
            productoExistente.setDescripcionProducto(producto.getDescripcionProducto());
            productoExistente.setStockProducto(producto.getStockProducto());
            productoExistente.setIdCategoria(producto.getIdCategoria());
            return productoRepository.save(productoExistente);
        }
        return null;
    }

    public void deleteByIdProducto(Integer idProducto) {
        productoRepository.deleteById(idProducto);
    }



}
