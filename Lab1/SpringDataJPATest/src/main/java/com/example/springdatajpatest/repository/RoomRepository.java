package com.example.springdatajpatest.repository;

import com.example.springdatajpatest.entity.Room;
import com.example.springdatajpatest.model.RoomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, RoomId> {
}
