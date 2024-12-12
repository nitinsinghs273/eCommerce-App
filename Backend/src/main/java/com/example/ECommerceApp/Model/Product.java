package com.example.ECommerceApp.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column(name = "description")
    private String description;  // Renamed from 'desc' to 'description'
    @Column
    private String brand;
    @Column
    private String category;
    @Column
    private int price;
    @Column
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "DD-MM-YYYY") handleded in Frontend
    private Date releaseDate;
    @Column
    private boolean productAvailable;
    @Column
    private int stockQuantity;
    @Column
    private String imageName;
    @Column
    private String imageType;

    @Column
    @Lob //largeObject Annotation
    private byte[] imageData;
}
