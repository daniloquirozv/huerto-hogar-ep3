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

import com.huertohogar.producto.producto.model.Categoria;
import com.huertohogar.producto.producto.model.Producto;
import com.huertohogar.producto.producto.service.CategoriaService;
import com.huertohogar.producto.producto.service.ProductoService;

@RestController
@RequestMapping("api/v1/huertohogar/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private CategoriaService categoriaService;

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

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarProducto (@RequestBody Producto producto){
        try {
            // Validar que la categoría existe
            if (producto.getIdCategoria() == null || producto.getIdCategoria().getIdCategoria() == 0) {
                return ResponseEntity.badRequest().body("La categoría es requerida");
            }
            
            Categoria categoria = categoriaService.findByIdCategoria(producto.getIdCategoria().getIdCategoria());
            if (categoria == null) {
                return ResponseEntity.badRequest().body("La categoría con ID " + producto.getIdCategoria().getIdCategoria() + " no existe");
            }
            
            // Asignar la categoría obtenida de la base de datos
            producto.setIdCategoria(categoria);
            
            Producto proNuevo = productoService.saveProducto(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(proNuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el producto: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity<?> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto){
        try{
            Producto pro = productoService.findByIdProducto(id);
            if (pro == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Validar que la categoría existe
            if (producto.getIdCategoria() != null && producto.getIdCategoria().getIdCategoria() != 0) {
                Categoria categoria = categoriaService.findByIdCategoria(producto.getIdCategoria().getIdCategoria());
                if (categoria == null) {
                    return ResponseEntity.badRequest().body("La categoría con ID " + producto.getIdCategoria().getIdCategoria() + " no existe");
                }
                pro.setIdCategoria(categoria);
            }
            
            pro.setNombreProducto(producto.getNombreProducto());
            pro.setPrecioProducto(producto.getPrecioProducto());
            pro.setUnidadProducto(producto.getUnidadProducto());
            pro.setDescripcionProducto(producto.getDescripcionProducto());
            pro.setImagenProducto(producto.getImagenProducto());

            Producto proActualizado = productoService.saveProducto(pro);
            return ResponseEntity.ok(proActualizado);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
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
