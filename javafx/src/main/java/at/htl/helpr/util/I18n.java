package at.htl.helpr.util;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
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
     * Binds the given JavaFX {@link Control} to the translation for the specified key.  If the
     * control does not have a textProperty, a {@link RuntimeException} is thrown. If binding
     * succeeds once, it will succeed again for the same control.
     *
     * @param control the JavaFX control to bind
     * @param key     the translation key
     * @return the same control, with its text bound to the translation
     * @throws RuntimeException if the control does not have a textProperty
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

    public void bind(StringProperty property, String key) {
        property.bind(translate(key));
    }

    /**
     * Returns a StringProperty that automatically updates with the translation for the given key
     * and current locale.
     *
     * @param key the translation key
     * @return a StringProperty bound to the translation for the key
     */
    public StringProperty translate(String key) {
        return observers.computeIfAbsent(
                key,
                k -> new SimpleStringProperty(rawTranslate(key))
        );
    }

    public String rawTranslate(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return BACKUP_BUNDLE.getString(key);
        }
    }


    private void notifyObservers() {
        System.out.println("Budnle: " + bundle.getLocale().toString());
        for (var set : observers.entrySet()) {
            System.out.println("Update: " + set.getKey());
            set.getValue().setValue(rawTranslate(set.getKey()));
        }
    }
}
