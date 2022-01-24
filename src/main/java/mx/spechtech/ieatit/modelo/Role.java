package mx.spechtech.ieatit.modelo;

public enum Role {
    ADMINISTRADOR,
    CLIENTE,
    REPARTIDOR;

    public String getName() {
        return "ROLE_" + super.toString();
    }
}
