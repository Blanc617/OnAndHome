package com.onandhome.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String status;
    private Date orderDate;
}
