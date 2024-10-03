package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.Role
import com.crosstime.backend.entity.Schedule
import com.crosstime.backend.enums.DayOfWeek
import com.crosstime.backend.enums.TrainingType
import com.crosstime.backend.enums.UserType
import com.crosstime.backend.exeption.FullSlotException
import com.crosstime.backend.exeption.SlotAlreadyReservedException
import com.crosstime.backend.exeption.SlotNotFoundException
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.mapper.ReservationMapper
import com.crosstime.backend.model.Reservation as ReservationModel
import com.crosstime.backend.entity.Reservation as ReservationEntity
import com.crosstime.backend.model.Slot as SlotModel
import com.crosstime.backend.repository.ReservationRepository
import com.crosstime.backend.repository.SlotRepository
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.request.ReservationRequest
import com.crosstime.backend.entity.Slot as SlotEntity
import com.crosstime.backend.entity.User as UserEntity
import com.crosstime.backend.model.User as UserModel
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationServiceImplSpec extends Specification {

    private ReservationRepository reservationRepository = Mock(ReservationRepository.class)
    private SlotRepository slotRepository = Mock(SlotRepository.class)
    private UsersRepository usersRepository = Mock(UsersRepository.class)
    private ReservationMapper reservationMapper = Mock(ReservationMapper.class)
    private ReservationServiceImpl reservationService

    def setup() {
        reservationService = new ReservationServiceImpl(reservationRepository, reservationMapper, usersRepository, slotRepository)
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

    def "should return reservations for a given user"() {
        given: "a user"
        def id = UUID.randomUUID()
        def expected = buildReservationEntity()
        def expectedModel = buildReservationModel()

        when: "the method is called to get reservations"
        def result = reservationService.getReservationsByUserId(id)

        then: "the repository is invoked"
        1 * reservationRepository.findByUserId(id) >> [expected]

        then: "the mapper is invoked"
        1 * reservationMapper.mapEntitiesToModels([expected]) >> [expectedModel]

        then: "the returned reservations are the correct ones"
        result == [expectedModel]
    }

    def "should return reservations for a given slot"() {
        given: "a slot"
        def id = UUID.randomUUID()
        def expected = buildReservationEntity()
        def expectedModel = buildReservationModel()

        when: "the method is called to get reservations"
        def result = reservationService.getReservationsBySlotId(id)

        then: "the repository is invoked"
        1 * reservationRepository.findBySlotId(id) >> [expected]

        then: "the mapper is invoked"
        1 * reservationMapper.mapEntitiesToModels([expected]) >> [expectedModel]

        then: "the returned reservations are the correct ones"
        result == [expectedModel]
    }

    def "should throw an exception when the slot is full"() {
        given: "a reservation request and a slot"
        def id = UUID.randomUUID()
        def reservationRequest = new ReservationRequest(id, id)
        def slotMock = Mock(SlotEntity)
        def userMock = Mock(UserEntity)

        and: "the slot is full"
        slotMock.isFull() >> true

        when: "the method is called to create a reservation"
        reservationService.createReservation(reservationRequest)

        then: "the repository is called to find the slot"
        1 * slotRepository.findById(id) >> Optional.of(slotMock)

        then: "the repository is called to find the user"
        1 * usersRepository.findById(id) >> Optional.of(userMock)

        and: "an exception is thrown due to full slot"
        def exception = thrown(FullSlotException)
        exception.message == "Could not create reservation. Slot is full."
    }

    def "should throw an exception if the user has already reserved the slot"() {
        given: "a reservation request and a slot"
        def id = UUID.randomUUID()
        def reservationRequest = new ReservationRequest(id, id)
        def slotMock = Mock(SlotEntity)
        def userMock = Mock(UserEntity)

        and: "the slot is not full"
        slotMock.isFull() >> false

        and: "a user id"
        userMock.getId() >> id

        and: "the user has already reserved the slot"
        slotMock.slotAlreadyReservedByUser(id) >> true

        when: "the method is called to create a reservation"
        reservationService.createReservation(reservationRequest)

        then: "the repository is called to find the slot"
        1 * slotRepository.findById(id) >> Optional.of(slotMock)

        then: "the repository is called to find the user"
        1 * usersRepository.findById(id) >> Optional.of(userMock)

        and: "an exception is thrown due to full slot"
        def exception = thrown(SlotAlreadyReservedException)
        exception.message == "Slot cannot be reserved twice by the same user."
    }

    def "should delete a reservation"() {
        given: "a reservation"
        def id = UUID.randomUUID()
        
        when: "the method is called to delete a reservation"
        reservationService.deleteReservation(id)

        then: "the repository is invoked"
        1 * reservationRepository.deleteById(id) >> { }
    }

    def buildReservationEntity() {
        return new ReservationEntity(UUID.randomUUID(), LocalDate.parse("2019-01-01"), buildUserEntity(), buildSlotEntity())
    }

    def buildReservationModel() {
        return new ReservationModel(UUID.randomUUID(), buildUserModel(), buildSlotModel())
    }

    def buildSlotEntity() {
        return new SlotEntity(UUID.randomUUID(), 10, LocalDateTime.parse("2019-01-01T00:00:00"), 40, TrainingType.WOD, null, new Schedule(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.parse("00:00:00")))
    }

    def buildUserEntity() {
        return new UserEntity(UUID.randomUUID(), "username", "email", "password", Role.USER, UserType.ATHLETE)
    }

    def buildUserModel() {
        return new UserModel(UUID.randomUUID(), "username", "email")
    }

    def buildSlotModel() {
        return new SlotModel(UUID.randomUUID(), 10, LocalDateTime.parse("2019-01-01T00:00:00"), 40, TrainingType.WOD, null)
    }

}
