README – Sistema de Gestión de Tienda

Descripción
	Este proyecto implementa un sistema de gestión para una tienda utilizando Java Swing como 
	interfaz gráfica y MySQL como gestor de base de datos.
	
	El sistema permite administrar clientes, productos y ventas, generar reportes dinámicos y
	exportarlos a Excel y PDF.

Requisitos
	Java JDK 17+
	MySQL Server 8+
	Librerías externas:
	Apache POI (exportación a Excel)
	iText (exportación a PDF)
	JFreeChart (gráficos en Dashboard)
	Conector JDBC para MySQL (mysql-connector-java.jar)

Modelo de Base de Datos
	El sistema utiliza cuatro tablas principales:

Cliente
	idCliente (PK)
	nombre
	correo
	tipoCliente

Producto
	idProducto (PK)
	nombre
	precio
	categoria
	stock

Venta
	idVenta (PK)
	fecha
	idCliente (FK → Cliente)

DetalleVenta
	idDetalle (PK)
	idVenta (FK → Venta)
	idProducto (FK → Producto)
	cantidad
	subtotal

Conexión a la Base de Datos
	La clase ConexionBD centraliza la conexión:

	java
		public class ConexionBD {
			private static final String URL = "jdbc:mysql://localhost:3306/tienda";
			private static final String USER = "root";
			private static final String PASS = "tu_password";

			public static Connection getConnection() throws SQLException {
				return DriverManager.getConnection(URL, USER, PASS);
			}
		}

Estructura del Proyecto
	Código
	src/
		├── modelo/        # Clases Cliente, Producto, Venta, DetalleVenta
		├── dao/           # Clases DAO y ConexionBD
		├── vista/         # Ventanas Swing (Alta, Consulta, Dashboard)
		├── reportes/      # Exportación Excel y PDF
		└── grafico/       # Dashboard con JFreeChart

Manejo de Datos
	Inserción: Se realiza mediante DAOs (ClienteDAO, ProductoDAO, VentaDAO).
	Consultas: Se obtienen listas de clientes y productos para poblar combos en la interfaz.
	Transacciones: VentaDAO utiliza setAutoCommit(false) para garantizar integridad; si ocurre un error, se hace rollback.
	Actualización de stock: Al registrar una venta, se descuenta automáticamente la cantidad vendida del producto.
	Reportes: Se generan consultas agregadas para mostrar clientes con mayor gasto y exportar resultados.

Ejecución
	Crear la base de datos en MySQL:

	sql
		CREATE DATABASE tienda;
		USE tienda;
		
	Ejecutar los scripts de tablas (Cliente, Producto, Venta, DetalleVenta).
	Configurar usuario y contraseña en ConexionBD.java.
	Compilar y ejecutar el proyecto desde el MainMenu.

Notas
	El sistema está diseñado para ser escalable y permitir nuevas funcionalidades (reportes mensuales, productos más vendidos, etc.).
	Los diagramas UML y DBML incluidos documentan la arquitectura y el modelo de datos.