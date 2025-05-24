package at.htl.helpr.util;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;

public class I18n {

    private static final I18n INSTANCE = new I18n();
    private static final String RESOURCE_BUNDLE_PATH = "i18n.translation";

    private static final ResourceBundle BACKUP_BUNDLE = ResourceBundle.getBundle(
            RESOURCE_BUNDLE_PATH);

    private final Map<String, StringProperty> observers = new HashMap<>();
    private final Map<String, ParameterizedStringProperty> parameterizedObservers = new HashMap<>();
    private Locale locale = Locale.getDefault();
    private ResourceBundle bundle = BACKUP_BUNDLE;

    private I18n() {
        updateBundle();
    }

    public static I18n get() {
        return INSTANCE;
    }

    private void updateBundle() {
        try {
            this.bundle = ResourceBundle.getBundle(
                    Paths.get(RESOURCE_BUNDLE_PATH).toString(), locale);
        } catch (Exception e) {
            this.bundle = BACKUP_BUNDLE;
        }
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        updateBundle();
        notifyObservers();
    }

    /**
     * Binds the given JavaFX {@link Control} to the translation for the specified key.
     */
    public <T extends Control> T bind(T control, String key) {
        try {
            var methodName = control.getClass().getMethod("textProperty");
            if (methodName.getReturnType().isAssignableFrom(StringProperty.class)) {
                StringProperty textProp = (StringProperty) methodName.invoke(control);
                textProp.bind(translate(key));
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return control;
    }

    /**
     * Binds the given JavaFX {@link Control} to the translation with parameters.
     */
    public <T extends Control> T bind(T control, String key, Object... params) {
        try {
            var methodName = control.getClass().getMethod("textProperty");
            if (methodName.getReturnType().isAssignableFrom(StringProperty.class)) {
                StringProperty textProp = (StringProperty) methodName.invoke(control);
                textProp.bind(translate(key, params));
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return control;
    }

    public void bind(StringProperty property, String key) {
        property.bind(translate(key));
    }

    public void bind(StringProperty property, String key, Object... params) {
        property.bind(translate(key, params));
    }

    /**
     * Returns a StringProperty that automatically updates with the translation for the given key.
     */
    public StringProperty translate(String key) {
        return observers.computeIfAbsent(
                key,
                k -> new SimpleStringProperty(rawTranslate(key))
        );
    }

    /**
     * Returns a StringProperty that automatically updates with the parameterized translation.
     */
    public StringProperty translate(String key, Object... params) {
        String cacheKey = key + ":" + java.util.Arrays.toString(params);
        return parameterizedObservers.computeIfAbsent(
                cacheKey,
                k -> new ParameterizedStringProperty(key, params)
        );
    }

    /**
     * Gets the raw translation without parameters.
     */
    public String rawTranslate(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            try {
                return BACKUP_BUNDLE.getString(key);
            } catch (MissingResourceException ex) {
                return "!" + key + "!";
            }
        }
    }

    /**
     * Gets the formatted translation with parameters using MessageFormat.
     */
    public String rawTranslate(String key, Object... params) {
        String pattern = rawTranslate(key);
        if (params.length == 0) {
            return pattern;
        }

        try {
            MessageFormat formatter = new MessageFormat(pattern, locale);
            return formatter.format(params);
        } catch (Exception e) {
            // Fallback to simple parameter replacement or return pattern as-is
            return pattern;
        }
    }

    private void notifyObservers() {
        System.out.println("Bundle: " + bundle.getLocale().toString());

        // Update simple observers
        for (var entry : observers.entrySet()) {
            System.out.println("Update: " + entry.getKey());
            entry.getValue().setValue(rawTranslate(entry.getKey()));
        }

        // Update parameterized observers
        for (var entry : parameterizedObservers.entrySet()) {
            entry.getValue().updateValue();
        }
    }

    /**
     * Internal class to handle parameterized string properties that update when locale changes.
     */
    private class ParameterizedStringProperty extends SimpleStringProperty {
        private final String key;
        private final Object[] params;

        public ParameterizedStringProperty(String key, Object[] params) {
            this.key = key;
            this.params = params.clone(); // Defensive copy
            updateValue();
        }

        public void updateValue() {
            setValue(rawTranslate(key, params));
        }
    }
}