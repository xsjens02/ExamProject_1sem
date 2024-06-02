package com.example.booking_system.ControllerService.Managers;

import com.example.booking_system.ControllerService.PubSub.Subject;
import com.example.booking_system.ControllerService.PubSub.Subscriber;
import com.example.booking_system.Model.Institution;
import com.example.booking_system.Model.User;
import com.example.booking_system.Persistence.DAO.DAO;
import com.example.booking_system.Persistence.DAO.InstitutionDAO_Impl;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SystemManager {
    private static SystemManager instance;
    private Institution institution;
    private User user;
    private final DAO<Institution> institutionDAO;
    private final Hashtable<Subject, List<Subscriber>> subscribeList;

    /**
     * private constructor to ensure singleton pattern
     */
    private SystemManager() {
        institutionDAO = new InstitutionDAO_Impl();
        subscribeList = new Hashtable<>();
        for (Subject subject : Subject.values()) {
            subscribeList.put(subject, new ArrayList<>());
        }
    }

    /**
     * returns singleton instance of SystemManager
     * @return SystemManager instance
     */
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

    /**
     * initializes the manager with a specified institution
     * @param institutionID identification of institution
     */
    public void initManager(int institutionID) {
        setInstitution(institutionDAO.read(institutionID));
        setUser(new User(1));
    }

    /**
     * updates current institution by reloading it from database
     */
    public void updateManager() {
        setInstitution(institutionDAO.read(this.institution.getInstitutionID()));
    }

    /**
     * clears both current user and current institution of manager
     */
    public void clearManager() {
      clearUser();
      clearInstitution();
    }

    /**
     * clears current user of manager and notifies subscribers
     */
    public void clearUser(){
        this.user = null;
        notifySubscribers(Subject.User);
    }

    /**
     * clears current institution of manager and notifies subscribers
     */
    public void clearInstitution(){
        this.institution = null;
        notifySubscribers(Subject.Institution);
    }

    /**
     * subscribes a subscriber to updates for a specific subject
     * @param subject subject to subscribe to
     * @param subscriber subscriber to be notified
     */
    public void subscribe(Subject subject, Subscriber subscriber) {
        subscribeList.get(subject).add(subscriber);
    }

    /**
     * unsubscribes a subscriber from updates for a specific subject
     * @param subject subject to unsubscribe from
     * @param subscriber subscriber to be removed from notifications
     */
    public void unSubscribe(Subject subject, Subscriber subscriber) {
        subscribeList.get(subject).remove(subscriber);
    }

    /**
     * notifies subscribers of updates for a specific subject
     * @param subject subject for which subscribers should be notified
     */
    public void notifySubscribers(Subject subject) {
        List<Subscriber> subscribers = subscribeList.get(subject);
        for (Subscriber subscriber : subscribers) {
            subscriber.onUpdate();
        }
    }
}
