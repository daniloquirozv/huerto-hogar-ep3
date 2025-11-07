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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/huertohogar/productos")
@Tag(name = "Producto", description = "Operaciones relacionadas con los productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene una lista de todos los productos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos listados",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "Productos vacios")})
    public ResponseEntity<List<Producto>> listarProductos(){
        List<Producto> productos = productoService.findAllProductos();
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Busca y obtiene un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")})
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id){
        try{
            Producto producto = productoService.findByIdProducto(id);
            return ResponseEntity.ok(producto);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/guardar")
    @Operation(summary = "Guardar nuevo producto", description = "Crea y guarda un producto nuevo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Producto.class))),        
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
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
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Producto.class))),        
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
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
    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")})
    public ResponseEntity<?> eliminarProducto(@PathVariable Integer id){
        try {
            productoService.deleteByIdProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



}
