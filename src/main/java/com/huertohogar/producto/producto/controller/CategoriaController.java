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
import com.huertohogar.producto.producto.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/v1/huertohogar/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Listar categorias", description = "Obtiene una lista de todas las categorias disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorias listadas",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "204", description = "Categorias vacias")})
    public ResponseEntity<List<Categoria>> listarCategorias(){
        List<Categoria> categorias = categoriaService.findAllCategorias();
        if(categorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoria por ID", description = "Busca y obtiene una categoria por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada")})
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Integer id){
        try {
            Categoria categoria = categoriaService.findByIdCategoria(id);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/guardar")
    @Operation(summary = "Guardar nueva categoria", description = "Crea y guarda una categoria nueva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria creada",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Categoria.class))),        
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    public ResponseEntity<Categoria> guardarCategoria(@RequestBody Categoria categoria){
        Categoria nuevaCategoria = categoriaService.saveCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    @PutMapping("/{id}/actualizar")
    @Operation(summary = "Actualizar categoria", description = "Actualiza una categoria existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria actualizada",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Categoria.class))),        
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada")})
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoria){
        try {
            Categoria cat = categoriaService.findByIdCategoria(id);
            cat.setIdCategoria(id);
            cat.setNombreCategoria(categoria.getNombreCategoria());
            cat.setDescripcionCategoria(categoria.getDescripcionCategoria());
            categoriaService.saveCategoria(cat);
            return ResponseEntity.ok(cat);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar categoria", description = "Elimina una categoria por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoria eliminada"),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada")})
    public ResponseEntity<?> eliminarCategoria(@PathVariable Integer id){
        try {
            categoriaService.deleteByIdCategoria(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }




}
