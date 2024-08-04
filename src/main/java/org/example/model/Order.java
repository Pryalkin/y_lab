package org.example.model;

public class Order {

    private String id;
    private String status;
    private String idCar;
    private String idUser;

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", status:'" + status + '\'' +
                ", idCar:'" + idCar + '\'' +
                ", idUser:'" + idUser + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getIdCar() {
        return idCar;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIdCar(String idCar) {
        this.idCar = idCar;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
