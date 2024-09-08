package com.crosstime.backend.entity

import com.crosstime.backend.enums.TrainingType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnTransformer
import java.time.LocalDateTime
import java.util.UUID


@Entity
@Table(name = "slots")
class Slot(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null,
    @Column(name = "capacity") var capacity: Int? = null,
    @Column(name = "date_time") var dateTime: LocalDateTime,
    @Column(name = "duration") var duration: Int,
    @Enumerated(EnumType.STRING)
    @Column(name = "training_type")
    @ColumnTransformer(read = "UPPER(training_type)", write = "UPPER(?)")
    var trainingType: TrainingType? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    val instructor: User? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    var schedule: Schedule? = null
) {
    @OneToMany(mappedBy = "slot")
    var reservations: MutableList<Reservation> = mutableListOf()

    fun isFull() = reservations.size == capacity
}