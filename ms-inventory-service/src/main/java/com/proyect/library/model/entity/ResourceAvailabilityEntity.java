package com.proyect.library.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "RESOURCE_AVAILABILITY_BOOKS")
public class ResourceAvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESOURCE_AVAILABILITY_BOOKS_SEQ")
    @SequenceGenerator(name = "RESOURCE_AVAILABILITY_BOOKS_SEQ", sequenceName = "RESOURCE_AVAILABILITY_BOOKS_SEQ", allocationSize = 1)
    @Column(name = "AVAILABILITY_ID")
    private Long id;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ACQUISITION_DATE")
    private LocalDate acquisitionDate;

    @Column(name = "ELIMINATION_DATE")
    private LocalDate eliminationDate;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")
    private BookEntity book;
}
