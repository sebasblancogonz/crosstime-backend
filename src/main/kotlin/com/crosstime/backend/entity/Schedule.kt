package com.crosstime.backend.entity

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
import jakarta.persistence.Table
import java.time.LocalTime
import java.util.UUID

@Entity
@Table(name = "schedules")
class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    var dayOfWeek: DayOfWeek? = null,
    @Column(name = "time_of_day")
    var timeOfDay: LocalTime? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id")
    val slot: Slot? = null
)