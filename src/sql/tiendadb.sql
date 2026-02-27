-- Crear base de datos
CREATE DATABASE IF NOT EXISTS tiendadb;
USE tiendadb;

-- Tabla Cliente
CREATE TABLE IF NOT EXISTS Cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    correo VARCHAR(100),
    tipoCliente VARCHAR(20)
);

-- Tabla Producto
CREATE TABLE IF NOT EXISTS Producto (
    idProducto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    precio DOUBLE,
    categoria VARCHAR(50),
    stock INT
);

-- Tabla Venta
CREATE TABLE IF NOT EXISTS Venta (
    idVenta INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    idCliente INT,
    FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)
);

-- Tabla DetalleVenta
CREATE TABLE IF NOT EXISTS DetalleVenta (
    idDetalle INT AUTO_INCREMENT PRIMARY KEY,
    idVenta INT,
    idProducto INT,
    cantidad INT,
    subtotal DOUBLE,
    FOREIGN KEY (idVenta) REFERENCES Venta(idVenta),
    FOREIGN KEY (idProducto) REFERENCES Producto(idProducto)
);

-- Tabla Inventario
CREATE TABLE IF NOT EXISTS Inventario (
    idInventario INT AUTO_INCREMENT PRIMARY KEY,
    idProducto INT,
    cantidad INT,
    FOREIGN KEY (idProducto) REFERENCES Producto(idProducto)
);

-- ===========================
-- Insertar datos de prueba
-- ===========================

-- Clientes
INSERT INTO Cliente (nombre, correo, tipoCliente) VALUES
('Juan Pérez', 'juan@example.com', 'Normal'),
('María López', 'maria@example.com', 'Frecuente'),
('Carlos Sánchez', 'carlos@example.com', 'VIP'),
('Ana Torres', 'ana@example.com', 'Normal'),
('Luis Gómez', 'luis@example.com', 'Frecuente');

-- Productos
INSERT INTO Producto (nombre, precio, categoria, stock) VALUES
('Laptop Lenovo', 15000, 'Electrónica', 20),
('Smartphone Samsung', 12000, 'Electrónica', 30),
('Audífonos Sony', 1500, 'Accesorios', 50),
('Mouse Logitech', 800, 'Accesorios', 40),
('Teclado Mecánico', 2000, 'Accesorios', 25),
('Monitor LG 24"', 3500, 'Electrónica', 15),
('Impresora HP', 4500, 'Oficina', 10),
('Silla Gamer', 5000, 'Muebles', 12),
('Mesa Oficina', 3000, 'Muebles', 8),
('Cámara Canon', 10000, 'Fotografía', 5);

-- Inventario inicial
INSERT INTO Inventario (idProducto, cantidad) VALUES
(1, 20), (2, 30), (3, 50), (4, 40), (5, 25),
(6, 15), (7, 10), (8, 12), (9, 8), (10, 5);

-- Ventas
INSERT INTO Venta (fecha, idCliente) VALUES
('2026-01-10', 1),
('2026-01-11', 2),
('2026-01-12', 3),
('2026-01-13', 4),
('2026-01-14', 5);

-- Detalles de ventas
INSERT INTO DetalleVenta (idVenta, idProducto, cantidad, subtotal) VALUES
(1, 1, 1, 15000), -- Juan compra Laptop
(1, 4, 2, 1600),  -- Juan compra 2 Mouse
(2, 2, 1, 12000), -- María compra Smartphone
(2, 3, 2, 3000),  -- María compra 2 Audífonos
(3, 5, 1, 2000),  -- Carlos compra Teclado
(3, 6, 1, 3500),  -- Carlos compra Monitor
(4, 7, 1, 4500),  -- Ana compra Impresora
(5, 8, 1, 5000),  -- Luis compra Silla Gamer
(5, 9, 1, 3000);  -- Luis compra Mesa Oficina
