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

/**
 * Handles translations for the application. Use I18n.get() to access it
 * anywhere.
 * <p>
 * Resource files go in src/main/resources/i18n/:
 * <ul>
 * <li>translation.properties (default)</li>
 * <li>translation_de.properties (German)</li>
 * </ul>
 * <p>
 * Basic usage:
 *
 * <pre>{@code
 * // Bind a control - text updates automatically when locale changes
 * Button saveBtn = I18n.get().bind(new Button(), "button.save");
 *
 * // With parameters
 * Label welcome = I18n.get().bind(new Label(), "welcome.message", userName);
 *
 * // Manually bind a control - get a StringProperty (parameterized is the same
 * // as above)
 * StringProperty errorMsg = I18n.get().translate("error.invalid.input");
 *
 * // Change language
 * I18n.get().setLocale(Locale.GERMAN);
 *
 * // Get translation as String (doesn't auto-update)
 * String msg = I18n.get().rawTranslate("error.invalid.input");
 * }</pre>
 * <p>
 * Example translation.properties
 *
 * <pre>{@code
 * button.save=Save
 * welcome.message=Welcome, {0}!
 * error.invalid.input=Invalid input, please try again.
 * }</pre>
 * <p>
 * Template format: <a href=
 * "https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/text/MessageFormat.html"
 * >here</a>
 */
public class I18n {

    private static final I18n INSTANCE = new I18n();
    private static final String RESOURCE_BUNDLE_PATH = "i18n.translation";

    private static final ResourceBundle BACKUP_BUNDLE = ResourceBundle
            .getBundle(RESOURCE_BUNDLE_PATH);

    private static final Locale[] SUPPORTED_LOCALES = {Locale.ENGLISH, Locale.GERMAN,
        Locale.of("ru")};

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
            this.bundle = ResourceBundle.getBundle(Paths.get(RESOURCE_BUNDLE_PATH).toString(),
                    locale);
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
     * Binds a control's text to a translation key. Text updates when locale
     * changes.
     *
     * @param control
     *            the control to bind (Button, Label, etc.)
     * @param key
     *            translation key from properties file
     * @return the control for chaining
     */
    public <T extends Control> T bind(T control, String key) {
        try {
            var methodName = control.getClass().getMethod("textProperty");
            if (methodName.getReturnType().isAssignableFrom(StringProperty.class)) {
                StringProperty textProp = (StringProperty) methodName.invoke(control);
                bind(textProp, key);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return control;
    }

    /**
     * Binds a control's text to a translation with parameters. Text updates when
     * locale changes. Use {0}, {1}, etc. in your translation string for parameters.
     *
     * @param control
     *            the control to bind
     * @param key
     *            translation key with parameter placeholders
     * @param params
     *            parameters to substitute
     * @return the control for chaining
     * @see MessageFormat
     */
    public <T extends Control> T bind(T control, String key, Object... params) {
        try {
            var methodName = control.getClass().getMethod("textProperty");
            if (methodName.getReturnType().isAssignableFrom(StringProperty.class)) {
                StringProperty textProp = (StringProperty) methodName.invoke(control);
                bind(textProp, key, params);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return control;
    }

    /**
     * Binds a StringProperty to a translation key. Updates when locale changes.
     *
     * @param property
     *            the property to bind
     * @param key
     *            translation key
     */
    public void bind(StringProperty property, String key) {
        property.bind(translate(key));
    }

    /**
     * Binds a StringProperty to a translation with parameters. Updates when locale
     * changes.
     *
     * @param property
     *            the property to bind
     * @param key
     *            translation key with parameter placeholders
     * @param params
     *            parameters to substitute
     */
    public void bind(StringProperty property, String key, Object... params) {
        property.bind(translate(key, params));
    }

    /**
     * Returns a StringProperty that updates when locale changes. Use this when you
     * need to bind the same translation to multiple places or the control doesn't
     * have "textProperty".
     *
     * @param key
     *            translation key
     * @return StringProperty that updates automatically
     */
    public StringProperty translate(String key) {
        return observers.computeIfAbsent(key, k -> new SimpleStringProperty(rawTranslate(key)));
    }

    /**
     * Returns a StringProperty with parameters that updates when locale changes.
     * Use this when you need to bind the same translation to multiple places or the
     * control doesn't have "textProperty".
     *
     * @param key
     *            translation key with parameter placeholders
     * @param params
     *            parameters to substitute
     * @return StringProperty that updates automatically
     */
    public StringProperty translate(String key, Object... params) {
        String cacheKey = key + ":" + java.util.Arrays.toString(params);
        return parameterizedObservers.computeIfAbsent(cacheKey,
                k -> new ParameterizedStringProperty(key, params));
    }

    /**
     * Gets translation as a String. Does not update when locale changes. Use for
     * one-time translations like error messages or alerts.
     *
     * @param key
     *            translation key
     * @return translated string, or "!key!" if not found
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
     * Gets translation with parameters as a String. Does not update when locale
     * changes.
     *
     * @param key
     *            translation key with parameter placeholders
     * @param params
     *            parameters to substitute
     * @return formatted translated string
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

        // Update simple observers
        for (var entry : observers.entrySet()) {
            entry.getValue().setValue(rawTranslate(entry.getKey()));
        }

        // Update parameterized observers
        for (var entry : parameterizedObservers.entrySet()) {
            entry.getValue().updateValue();
        }
    }

    /**
     * @return A List of all Locals for which translations exist
     */
    public Locale[] getSupportedLocales() {
        return SUPPORTED_LOCALES;
    }

    public Locale getLocale() {
        return locale;
    }

    /**
     * Internal class to handle parameterized string properties that update when
     * locale changes.
     */
    private class ParameterizedStringProperty extends SimpleStringProperty {

        private final String key;
        private final Object[] params;

        public ParameterizedStringProperty(String key, Object[] params) {
            this.key = key;
            this.params = params.clone();
            updateValue();
        }

        public void updateValue() {
            setValue(rawTranslate(key, params));
        }
    }
}