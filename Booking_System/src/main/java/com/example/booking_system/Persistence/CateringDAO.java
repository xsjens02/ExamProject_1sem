package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Catering;

import java.util.List;

public interface CateringDAO extends DAO<Catering> {
    List<Catering> readAll(int institutionID);
}
