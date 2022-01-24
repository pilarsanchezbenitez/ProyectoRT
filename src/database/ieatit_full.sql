CREATE DATABASE ieatit;

CREATE TABLE usuario(
    IdUsuario 			int(11),
    Nombre				VARCHAR(50) 	NOT NULL,
	Paterno				VARCHAR(50) 	NOT NULL,
	Materno				VARCHAR(50) ,
	Genero				CHAR(1)		NOT NULL CHECK( genero IN ('H','M')),
    PRIMARY KEY (`IdUsuario`)
);
CREATE TABLE cliente(
	IdCliente 			SERIAL PRIMARY KEY,
	idUsuario			int(11),
	CONSTRAINT fk_cliente_usuario FOREIGN KEY(`idUsuario`) REFERENCES usuario(`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT uk_cliente UNIQUE(`idUsuario`)
);

CREATE TABLE repartidor(
	IdRepartidor		SERIAL PRIMARY KEY,
	idUsuario			INT NOT NULL,
	CONSTRAINT fk_repartidor_usuario FOREIGN KEY(`idUsuario`) REFERENCES usuario(`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT uk_repartidor UNIQUE(`idUsuario`)
);

CREATE TABLE administrador(
	IdAdministrador		SERIAL PRIMARY KEY,
	idUsuario			INT NOT NULL,
	CONSTRAINT fk_administrador_usuario FOREIGN KEY(`idUsuario`) REFERENCES usuario(`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT uk_administrador UNIQUE(`idUsuario`)
);

CREATE TABLE Alimento (
    IdAlimento INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Nombre varchar(45) NOT NULL,
    Precio int DEFAULT 0,
    Descripcion varchar(255)
);

CREATE TABLE Categoria (
    IdCategoria INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Nombre varchar(45) NOT NULL,
    Descripcion varchar(255)
);

CREATE TABLE Orden (
    IdOrden INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    NombreCliente varchar(45),
    DireccionCliente varchar(45),
    Estado varchar(45),
    HoraCreacion time,
    HoraEntrega time
);

/**
* Sección de tablas de atributos multivaluados.
**/


CREATE TABLE correo(
	Correo				VARCHAR(100) 	NOT NULL CHECK(Correo LIKE '%_@%_.%_'),
	idUsuario 			INT		NOT NULL,
	CONSTRAINT pk_correo PRIMARY KEY (Correo,`idUsuario`),
	CONSTRAINT fk_correo_usuario FOREIGN KEY(`idUsuario`) REFERENCES usuario(`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE telefono(
	Telefono			CHAR(10) 	NOT NULL CHECK(Telefono LIKE '[0-9]{8,10}'),
	idUsuario 			INT		NOT NULL,
	CONSTRAINT pk_telefono PRIMARY KEY (Telefono,`idUsuario`),
	CONSTRAINT fk_telefono_usuario FOREIGN KEY(`idUsuario`) REFERENCES usuario(`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE direccion(
	Direccion			VARCHAR(100) 	NOT NULL,
	idUsuario 			INT		NOT NULL,
	CONSTRAINT pk_direccion PRIMARY KEY (direccion,`idUsuario`),
	CONSTRAINT fk_direccion_usuario FOREIGN KEY(`idUsuario`) REFERENCES usuario(`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
);

/**
* Sección de tablas de atributos multivaluados.
**/

CREATE TABLE Administrar (
    idAdministrador  SERIAL ,
    idAlimento INT NOT NULL ,
    idCategoria INT NOT NULL ,
    FOREIGN KEY (`idAdministrador`) REFERENCES Administrador(`idAdministrador`),
    FOREIGN KEY (`idAlimento`) REFERENCES Alimento(`idAlimento`),
    FOREIGN KEY (`idCategoria`) REFERENCES Categoria(`idCategoria`)
);

CREATE TABLE Ver (
    IdCliente SERIAL,
    IdAlimento INT NOT NULL,
    IdCategoria INT NOT NULL,
    FOREIGN KEY (IdCliente) REFERENCES Cliente(IdCliente),
    FOREIGN KEY (IdAlimento) REFERENCES Alimento(IdAlimento),
    FOREIGN KEY (IdCategoria) REFERENCES Categoria(IdCategoria)
);

CREATE TABLE Pedir (
    IdCliente SERIAL,
    IdOrden INT NOT NULL,
    FOREIGN KEY (IdCliente) REFERENCES Cliente(IdCliente),
    FOREIGN KEY (IdOrden) REFERENCES Orden(IdOrden)
);

CREATE TABLE Seleccionar (
    IdRepartidor SERIAL,
    IdOrden INT NOT NULL,
    FOREIGN KEY (IdRepartidor) REFERENCES Repartidor(IdRepartidor),
    FOREIGN KEY (IdOrden) REFERENCES Orden(IdOrden)
);

CREATE TABLE Formar (
    IdOrden INT NOT NULL,
    IdAlimento INT NOT NULL,
    FOREIGN KEY (IdOrden) REFERENCES Orden(IdOrden),
    FOREIGN KEY (IdAlimento) REFERENCES Alimento(IdAlimento)
);

CREATE TABLE Pertenecer (
    IdAlimento INT NOT NULL,
    IdCategoria INT NOT NULL,
    FOREIGN KEY (IdAlimento) REFERENCES Alimento(IdAlimento),
    FOREIGN KEY (IdCategoria) REFERENCES Categoria(IdCategoria));