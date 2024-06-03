package com.example.booking_system.Controller.ControllerService;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Map;

public class ListenerService {
    /**
     * sets up a listener for the specified property and associates it with the provided listener map
     * @param property property to set up a listener for
     * @param listenerMap map to store the association between properties and listeners
     * @param listener listener to associate with the property
     */
    public static void setupListener(Property<?> property, Map<Property<?>, InvalidationListener> listenerMap, InvalidationListener listener) {
        removeCurrentListener(listenerMap, property);
        property.addListener(listener);
        listenerMap.put(property, listener);
    }

    /**
     * sets up listeners for the specified list of properties and associates them with the provided listener map
     * @param properties list of properties to set up listeners for
     * @param listenerMap map to store the associations between properties and listeners
     * @param listener listener to associate with the properties
     */
    public static void setupListeners(List<Property<?>> properties, Map<Property<?>, InvalidationListener> listenerMap, InvalidationListener listener) {
        removeCurrentListener(listenerMap, properties);
        for (Property<?> property : properties) {
            property.addListener(listener);
            listenerMap.put(property, listener);
        }
    }

    /**
     * removes the current listener associated with the specified property from the provided listener map
     * @param listenerMap map containing the association between properties and listeners
     * @param property property to remove the listener from
     */
    public static void removeCurrentListener(Map<Property<?>, InvalidationListener> listenerMap, Property<?> property) {
        InvalidationListener listener = listenerMap.get(property);
        if (listener != null) {
            property.removeListener(listener);
            listenerMap.put(property, null);
        }
    }

    /**
     * removes the current listeners associated with the specified list of properties from the provided listener map
     * @param listenerMap map containing the associations between properties and listeners
     * @param properties list of properties to remove listeners from
     */
    public static void removeCurrentListener(Map<Property<?>, InvalidationListener> listenerMap, List<Property<?>> properties) {
        for (Property<?> property : properties) {
            InvalidationListener listener = listenerMap.get(property);
            if (listener != null) {
                property.removeListener(listener);
                listenerMap.put(property, null);
            }
        }
    }

    /**
     * sets up a numeric listener for the specified text field to restrict input to numeric characters only
     * @param textField text field to set up the numeric listener for
     */
    public static void setupNumericListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }
}
