CREATE DATABASE IF NOT EXISTS sistema_banco;
USE sistema_banco;

CREATE TABLE Personas (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    email VARCHAR(100)
);

CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    dni_persona VARCHAR(20) NOT NULL,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol ENUM('ADMINISTRADOR', 'EMPLEADO', 'CLIENTE') NOT NULL,
    estado BOOLEAN DEFAULT TRUE, -- Activo/Inactivo
    FOREIGN KEY (dni_persona) REFERENCES Personas(dni) ON DELETE CASCADE
);

CREATE TABLE Empleados (
    id_empleado INT PRIMARY KEY,
    dni_persona VARCHAR(20) NOT NULL,
    fecha_contratacion DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (dni_persona) REFERENCES Personas(dni) ON DELETE CASCADE
);

CREATE TABLE Cuentas (
    numero_cuenta VARCHAR(50) PRIMARY KEY,
    dni_cliente VARCHAR(20) NOT NULL,
    tipo_moneda VARCHAR(10) NOT NULL,
    saldo DECIMAL(15, 2) DEFAULT 0.00,
    fecha_apertura DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (dni_cliente) REFERENCES Personas(dni)
);

CREATE TABLE Tarjetas (
    numero_tarjeta VARCHAR(20) PRIMARY KEY,
    dni_cliente VARCHAR(20) NOT NULL,
    tipo_tarjeta ENUM('DEBITO', 'CREDITO') NOT NULL,
    compania VARCHAR(50),
    cvv VARCHAR(4),
    fecha_vencimiento DATE,
    pin VARCHAR(255),
    FOREIGN KEY (dni_cliente) REFERENCES Personas(dni)
);

CREATE TABLE Tarjetas_Cuentas (
    id_relacion INT AUTO_INCREMENT PRIMARY KEY,
    numero_tarjeta VARCHAR(20) NOT NULL,
    numero_cuenta VARCHAR(50) NOT NULL,
    FOREIGN KEY (numero_tarjeta) REFERENCES Tarjetas(numero_tarjeta) ON DELETE CASCADE,
    FOREIGN KEY (numero_cuenta) REFERENCES Cuentas(numero_cuenta) ON DELETE CASCADE
);

CREATE TABLE Movimientos (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(50) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(50),
    monto DECIMAL(15, 2) NOT NULL,
    descripcion TEXT,
    FOREIGN KEY (numero_cuenta) REFERENCES Cuentas(numero_cuenta)
);

CREATE TABLE Cajeros (
    id_cajero VARCHAR(20) PRIMARY KEY,
    ubicacion VARCHAR(100),
    disponible BOOLEAN DEFAULT TRUE,
    saldo_efectivo DECIMAL(15, 2) DEFAULT 0.00
);