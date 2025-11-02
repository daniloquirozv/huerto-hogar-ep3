package com.huertohogar.producto.producto.service;

import java.util.List;

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
        return productoRepository.findAll();
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
            productoExistente.setImagenProducto(producto.getImagenProducto());
            productoExistente.setIdCategoria(producto.getIdCategoria());
            return productoRepository.save(productoExistente);
        }
        return null;
    }

    public void deleteByIdProducto(Integer idProducto) {
        productoRepository.deleteById(idProducto);
    }



}
