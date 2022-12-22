package net.learning.springreactivemongocurdpoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dto {

    private String id;
    private String name;
    private int qty;
    private double price;
}