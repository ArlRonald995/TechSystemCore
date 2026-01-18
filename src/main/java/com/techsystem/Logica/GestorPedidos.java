package com.techsystem.Logica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPedidos {

    // 1. MÉTODO MAESTRO PARA COMPRAR
    public boolean realizarCompra(Cliente cliente, IMetodoPago metodoPago) {
        CarritoCompra carrito = cliente.getCarrito();
        double total = carrito.calcularTotal();

        if (total <= 0) return false;

        // A) Procesar pago
        if (!metodoPago.procesarPago(total)) {
            System.out.println("Pago rechazado.");
            return false;
        }

        // B) Transacción en Base de Datos (Pedido + Detalles + Stock)
        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false); // ¡IMPORTANTE! Inicia transacción manual

            // B.1) Insertar Cabecera del Pedido
            String sqlPedido = "INSERT INTO pedidos (usuario_id, total, estado_envio, ubicacion_actual, fecha_compra) VALUES (?, ?, 'En Bodega', 'Almacén Central', NOW()) RETURNING id";
            PreparedStatement psPedido = con.prepareStatement(sqlPedido);
            psPedido.setInt(1, cliente.getId());
            psPedido.setDouble(2, total);

            ResultSet rs = psPedido.executeQuery();
            if (!rs.next()) throw new SQLException("No se generó ID de pedido");
            int idPedido = rs.getInt(1);

            // B.2) Insertar Detalles y Actualizar Stock
            String sqlDetalle = "INSERT INTO detalle_pedidos (pedido_id, producto_sku, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
            String sqlStock = "UPDATE productostienda_raw SET stock = stock - ? WHERE sku = ?";

            PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
            PreparedStatement psStock = con.prepareStatement(sqlStock);

            for (ItemCarrito item : carrito.getItems()) {
                // Detalle
                psDetalle.setInt(1, idPedido);
                psDetalle.setString(2, item.getProducto().getSku());
                psDetalle.setInt(3, item.getCantidad());
                psDetalle.setDouble(4, item.getProducto().getPrecio());
                psDetalle.executeUpdate();

                // Stock
                psStock.setInt(1, item.getCantidad());
                psStock.setString(2, item.getProducto().getSku());
                int stockUpdate = psStock.executeUpdate();

                if (stockUpdate == 0) {
                    throw new SQLException("Error actualizando stock o producto inexistente: " + item.getProducto().getSku());
                }
            }

            con.commit(); // Confirmar cambios
            System.out.println("✅ Pedido #" + idPedido + " creado exitosamente.");

            // C) Vaciar carrito
            carrito.vaciar();

            // D) Iniciar simulación de envío (Logística) en segundo plano
            iniciarSimulacionEnvio(idPedido);

            return true;

        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); con.close(); } catch (Exception e) {}
        }
    }

    // 2. SIMULACIÓN DE LOGÍSTICA (Hilo en 2do plano)
    private void iniciarSimulacionEnvio(int idPedido) {
        new Thread(() -> {
            try {
                // Espera inicial
                System.out.println("📦 Pedido " + idPedido + ": Preparando paquete...");
                Thread.sleep(5000); // 5 seg

                actualizarEstadoPedido(idPedido, "Enviado", "En Ruta Local");
                System.out.println("🚚 Pedido " + idPedido + ": Enviado.");

                // Tiempo de viaje (los 10 seg restantes para completar tus 15 aprox)
                Thread.sleep(10000);

                actualizarEstadoPedido(idPedido, "Entregado", "Domicilio Cliente");
                System.out.println("🏠 Pedido " + idPedido + ": Entregado. ¡Ya puede opinar!");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void actualizarEstadoPedido(int id, String estado, String ubicacion) {
        String sql = "UPDATE pedidos SET estado_envio = ?, ubicacion_actual = ? WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setString(2, ubicacion);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. RECUPERAR PEDIDOS DE UN USUARIO (Para la vista de "Mis Facturas")
    public List<Pedido> obtenerPedidosPorUsuario(int usuarioId) {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE usuario_id = ? ORDER BY fecha_compra DESC";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Pedido p = new Pedido(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getTimestamp("fecha_compra"),
                        rs.getDouble("total"),
                        rs.getString("estado_envio"),
                        rs.getString("ubicacion_actual")
                );
                // Aquí podrías cargar los detalles llamando a otro método si quieres ver el desglose
                lista.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    // 4. GUARDAR VALORACIÓN (Reseña)
    public boolean agregarValoracion(Valoracion val) {
        String sql = "INSERT INTO valoraciones (usuario_id, producto_sku, puntuacion, comentario, fecha) VALUES (?, ?, ?, ?, CURRENT_DATE)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Sesion.usuarioLogueado.getId()); // Usamos la sesión
            ps.setString(2, val.getProductoSku());
            ps.setInt(3, val.getPuntuacion());
            ps.setString(4, val.getComentario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean puedeValorarProducto(int usuarioId, String skuProducto) {
        String sql = "SELECT p.estado_envio " +
                "FROM pedidos p " +
                "JOIN detalle_pedidos dp ON p.id = dp.pedido_id " +
                "WHERE p.usuario_id = ? AND dp.producto_sku = ? " +
                "AND p.estado_envio = 'Entregado'";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.setString(2, skuProducto);

            try (ResultSet rs = ps.executeQuery()) {
                // Si devuelve al menos una fila, significa que sí lo compró y ya le llegó
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // MÉTODO PARA EL DASHBOARD DEL ADMINISTRADOR (Ver todas las ventas)
    public List<Pedido> obtenerTodosLosPedidos() {
        List<Pedido> lista = new ArrayList<>();
        // Traemos todo, ordenado del más reciente al más antiguo
        String sql = "SELECT p.*, u.nombre as nombre_cliente " +
                "FROM pedidos p " +
                "JOIN usuarios u ON p.usuario_id = u.id " +
                "ORDER BY p.fecha_compra DESC";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                // Reconstruimos el objeto Pedido
                Pedido p = new Pedido(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getTimestamp("fecha_compra"),
                        rs.getDouble("total"),
                        rs.getString("estado_envio"),
                        rs.getString("ubicacion_actual")
                );
                // Opcional: Podrías guardar el nombre del cliente en el objeto Pedido
                // si modificas la clase Pedido para tener un campo "nombreClienteAux"
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}