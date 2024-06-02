package com.example.booking_system.Persistence.DAO;

import java.util.List;

public interface InstitutionDAO<T> extends DAO<T> {
    List<T> readAllFromInstitution(int institutionID);
}
