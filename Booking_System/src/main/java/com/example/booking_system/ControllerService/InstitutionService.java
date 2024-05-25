package com.example.booking_system.ControllerService;

import com.example.booking_system.Model.Institution;

public class InstitutionService {
    private static InstitutionService instance;
    private Institution institution;

    private InstitutionService() {}

    public static synchronized InstitutionService getInstance() {
        if (instance == null) {
            instance = new InstitutionService();
        }
        return instance;
    }

    public Institution getInstitution() {
        return this.institution;
    }
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public void clearService() {
        this.institution = null;
    }
}
