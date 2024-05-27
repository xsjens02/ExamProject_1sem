package com.example.booking_system.Model;

import com.example.booking_system.ControllerService.Subscriber;
import com.example.booking_system.Persistence.DAO;
import com.example.booking_system.Persistence.InstitutionDAO_Impl;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SystemManager {
    private static SystemManager instance;
    private Institution institution;
    private User user;
    private final DAO<Institution> institutionDAO;
    private final Hashtable<Subject, List<Subscriber>> subscribeList;
    private SystemManager() {
        institutionDAO = new InstitutionDAO_Impl();
        subscribeList = new Hashtable<>();
        for (Subject subject : Subject.values()) {
            subscribeList.put(subject, new ArrayList<>());
        }
    }

    public static synchronized SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager();
        }
        return instance;
    }

    public Institution getInstitution() {
        return institution;
    }
    public User getUser() {
        return user;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
        notifySubscribers(Subject.Institution);
    }
    public void setUser(User user) {
        this.user = user;
        notifySubscribers(Subject.User);
    }

    public void initManager(int institutionID) {
        setInstitution(institutionDAO.read(institutionID));
    }

    public void updateManager() {
        setInstitution(institutionDAO.read(this.institution.getInstitutionID()));
    }

    public void clearManager() {
      clearUser();
      clearInstitution();
    }

    public void clearUser(){
        this.user = null;
        notifySubscribers(Subject.User);
    }

    public void clearInstitution(){
        this.institution = null;
        notifySubscribers(Subject.Institution);
    }



    public void subscribe(Subject subject, Subscriber subscriber) {
        subscribeList.get(subject).add(subscriber);
    }

    public void unSubscribe(Subject subject, Subscriber subscriber) {
        subscribeList.get(subject).remove(subscriber);
    }

    public void notifySubscribers(Subject subject) {
        List<Subscriber> subscribers = subscribeList.get(subject);
        for (Subscriber subscriber : subscribers) {
            subscriber.onUpdate();
        }
    }
}
