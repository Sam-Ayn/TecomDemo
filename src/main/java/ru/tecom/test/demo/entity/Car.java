package ru.tecom.test.demo.entity;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @SequenceGenerator(name = "carsIdSeq", sequenceName = "cars_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "carsIdSeq")
    @Column(name = "id")
    private long id;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "brand")
    private String brand;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "model")
    private String model;

    @NotNull(message = "is required")
    @Min(value = 1980, message = "must be greater than or equal to 1980")
    @Max(value = 2020, message = "must be less than or equal to 2020")
    @Column(name = "year")
    private int year;

    @NotNull(message = "is required")
    @Min(value = 1, message = "must be greater than or equal to 1")
    @Max(value = 12, message = "must be less than or equal to 12")
    @Column(name = "month")
    private int month;

    @Column(name = "engine_displacement")
    private int engineDisplacement;

    @Column(name = "turbo")
    private boolean turbo;

    @Column(name = "power")
    private int power;

    @Column(name = "transmission_type")
    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;

    @Column(name = "drive_type")
    @Enumerated(EnumType.STRING)
    private DriveType driveType;

    @Column(name = "body_type")
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "color")
    private String color;

    public Car() {
    }

    public Car(int id, String brand, String model, int year, int month, int engineDisplacement,
               boolean turbo, int power, TransmissionType transmissionType, DriveType driveType,
               BodyType bodyType, String color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.month = month;
        this.engineDisplacement = engineDisplacement;
        this.turbo = turbo;
        this.power = power;
        this.transmissionType = transmissionType;
        this.driveType = driveType;
        this.bodyType = bodyType;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(int engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public void setTurbo(boolean turbo) {
        this.turbo = turbo;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public void setDriveType(DriveType driveType) {
        this.driveType = driveType;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", engineDisplacement=" + engineDisplacement +
                ", turbo=" + turbo +
                ", power=" + power +
                ", transmissionType=" + transmissionType +
                ", driveType=" + driveType +
                ", bodyType=" + bodyType +
                ", color='" + color + '\'' +
                '}';
    }
}
