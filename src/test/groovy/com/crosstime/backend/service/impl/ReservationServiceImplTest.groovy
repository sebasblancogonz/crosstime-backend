package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.Role
import com.crosstime.backend.entity.Schedule
import com.crosstime.backend.enums.DayOfWeek
import com.crosstime.backend.enums.TrainingType
import com.crosstime.backend.enums.UserType
import com.crosstime.backend.exeption.SlotNotFoundException
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.repository.ReservationRepository
import com.crosstime.backend.repository.SlotRepository
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.request.ReservationRequest
import com.crosstime.backend.entity.Slot as SlotEntity
import com.crosstime.backend.entity.User as UserEntity
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.LocalTime

class ReservationServiceImplTest extends Specification {

    private ReservationRepository reservationRepository = Mock(ReservationRepository.class)
    private SlotRepository slotRepository = Mock(SlotRepository.class)
    private UsersRepository usersRepository = Mock(UsersRepository.class)
    private ReservationServiceImpl reservationService

    def setup() {
        reservationService = new ReservationServiceImpl(reservationRepository, usersRepository, slotRepository)
    }

    def "should create a reservation for an existing slot"() {
        given: "an existing slot and a user"
        def id = UUID.randomUUID()
        def reservationRequest = new ReservationRequest(id, id)


        when: "the method is called to create a reservation"
        def result = reservationService.createReservation(reservationRequest)

        then: "the repository is invoked"
        1 * slotRepository.findById(id) >> { Optional.of(new SlotEntity(id, 10, LocalDateTime.parse("2019-01-01T00:00:00"), 40, TrainingType.WOD, null, new Schedule(id, DayOfWeek.MONDAY, LocalTime.parse("00:00:00")))) }
        1 * usersRepository.findById(id) >> { Optional.of(new UserEntity(id, "username", "email", "password", Role.USER, UserType.ATHLETE)) }

        then: "the returned reservation is the correct one"
        1 * reservationRepository.save(_)
    }

    def "should throw an exception when the slot does not exist"() {
        given: "a non-existing slot and a user"
        def id = UUID.randomUUID()
        def reservationRequest = new ReservationRequest(id, id)

        when: "the method is called to create a reservation"
        def result = reservationService.createReservation(reservationRequest)

        then: "the repository is invoked"
        1 * slotRepository.findById(id) >> { Optional.empty() }
        0 * usersRepository.findById(id)

        then: "an exception is thrown"
        def exception = thrown(SlotNotFoundException)
        exception.message == "Slot with id $id not found."
    }

    def "should throw an exception when the user does not exist"() {
        given: "an existing slot and a non-existing user"
        def id = UUID.randomUUID()
        def reservationRequest = new ReservationRequest(id, id)

        when: "the method is called to create a reservation"
        def result = reservationService.createReservation(reservationRequest)

        then: "the repository is invoked"
        1 * slotRepository.findById(id) >> { Optional.of(new SlotEntity(id, 10, LocalDateTime.parse("2019-01-01T00:00:00"), 40, TrainingType.WOD, null, new Schedule(id, DayOfWeek.MONDAY, LocalTime.parse("00:00:00")))) }
        1 * usersRepository.findById(id) >> { Optional.empty() }

        then: "an exception is thrown"
        def exception = thrown(UserNotFoundException)
        exception.message == "User not found."
    }

}
