package com.huertohogar.producto.producto.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {

    private Integer idPedido;
    private Date fechaPedido;
    private String estadoPedido;
    private Integer subtotalPedido;
    private String observaciones;
    private Integer usuarioIdUsuario;
    private Integer carritoIdCarrito;

}
