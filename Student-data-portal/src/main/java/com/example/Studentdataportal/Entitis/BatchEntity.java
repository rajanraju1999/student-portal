package com.example.Studentdataportal.Entitis;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchEntity {
    @Id
    @Column(name = "batch")
    private String batch;

    @Column(name = "regulation")
    private String regulation;


}
