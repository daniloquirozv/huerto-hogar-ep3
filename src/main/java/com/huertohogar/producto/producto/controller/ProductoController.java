package com.huertohogar.producto.producto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huertohogar.producto.producto.model.Producto;
import com.huertohogar.producto.producto.service.ProductoService;

@RestController
@RequestMapping("api/v1/huertohogar/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){
        List<Producto> productos = productoService.findAllProductos();
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id){
        try{
            Producto producto = productoService.findByIdProducto(id);
            return ResponseEntity.ok(producto);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Producto> guardarProducto (@RequestBody Producto producto){
        Producto proNuevo = productoService.saveProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(proNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto){
        try{
            Producto pro = productoService.findByIdProducto(id);
            pro.setIdProducto(id);
            pro.setNombreProducto(producto.getNombreProducto());
            pro.setPrecioProducto(producto.getPrecioProducto());
            pro.setUnidadProducto(producto.getUnidadProducto());
            pro.setDescripcionProducto(producto.getDescripcionProducto());
            pro.setImagenProducto(producto.getImagenProducto());
            pro.setIdCategoria(producto.getIdCategoria());

            productoService.saveProducto(pro);
            return ResponseEntity.ok(producto);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Integer id){
        try {
            productoService.deleteByIdProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



}
