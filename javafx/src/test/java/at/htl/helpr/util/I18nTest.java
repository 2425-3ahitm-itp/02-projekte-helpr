package at.htl.helpr.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class I18nTest extends ApplicationTest {

    private I18n i18n;

    @BeforeEach
    void setUp() {
        i18n = I18n.get();
        // Reset to default locale for consistent tests
        i18n.setLocale(Locale.ENGLISH);
    }

    @Test
    void get_shouldReturnSameInstance() {
        I18n first = I18n.get();
        I18n second = I18n.get();

        Button button = new Button();

        assertThat(first).isSameAs(second);
    }

    @Test
    void setLocale_shouldUpdateBoundControls() {
        Button button = new Button();
        i18n.bind(button, "button.save");

        assertThat(button.getText()).isEqualTo("Save");

        i18n.setLocale(Locale.GERMAN);

        assertThat(button.getText()).isEqualTo("Speichern");
    }

    @Test
    void bind_withControl_shouldSetControlText() {
        Button button = new Button();
        Button result = i18n.bind(button, "button.save");

        assertThat(result).isSameAs(button);
        assertThat(button.getText()).isEqualTo("Save");
    }

    @Test
    void bind_withControlAndParameters_shouldSetParameterizedText() {
        Label label = new Label();
        String userName = "John";

        i18n.bind(label, "welcome.message", userName);

        assertThat(label.getText()).isEqualTo("Welcome, John!");
    }

    @Test
    void bind_withControlAndParameters_shouldUpdateWhenLocaleChanges() {
        Label label = new Label();
        String userName = "John";

        i18n.bind(label, "welcome.message", userName);
        assertThat(label.getText()).isEqualTo("Welcome, John!");

        i18n.setLocale(Locale.GERMAN);

        assertThat(label.getText()).isEqualTo("Willkommen, John!");
    }

    @Test
    void bind_withStringProperty_shouldBindProperty() {
        StringProperty property = new SimpleStringProperty();

        i18n.bind(property, "window.title");

        assertThat(property.get()).isEqualTo("Application Window");
    }

    @Test
    void bind_withStringPropertyAndParameters_shouldBindParameterizedProperty() {
        StringProperty property = new SimpleStringProperty();
        int itemCount = 5;

        i18n.bind(property, "status.items", itemCount);

        assertThat(property.get()).isEqualTo("5 items");
    }

    @Test
    void bind_withStringProperty_shouldUpdateWhenLocaleChanges() {
        StringProperty property = new SimpleStringProperty();

        i18n.bind(property, "window.title");
        assertThat(property.get()).isEqualTo("Application Window");

        i18n.setLocale(Locale.GERMAN);

        assertThat(property.get()).isEqualTo("Anwendungsfenster");
    }

    @Test
    void translate_shouldReturnStringPropertyThatUpdates() {
        StringProperty property = i18n.translate("dialog.title");
        assertThat(property.get()).isEqualTo("Confirmation Dialog");

        i18n.setLocale(Locale.GERMAN);

        assertThat(property.get()).isEqualTo("Bestätigungsdialog");
    }

    @Test
    void translate_withParameters_shouldReturnParameterizedStringProperty() {
        int count = 3;
        StringProperty property = i18n.translate("selection.count", count);

        assertThat(property.get()).isEqualTo("3 items selected");
    }

    @Test
    void translate_withParameters_shouldUpdateWhenLocaleChanges() {
        int count = 3;
        StringProperty property = i18n.translate("selection.count", count);
        assertThat(property.get()).isEqualTo("3 items selected");

        i18n.setLocale(Locale.GERMAN);

        assertThat(property.get()).isEqualTo("3 Elemente ausgewählt");
    }

    @Test
    void translate_withSameKey_shouldReturnSameStringProperty() {
        StringProperty first = i18n.translate("dialog.title");
        StringProperty second = i18n.translate("dialog.title");

        assertThat(first).isSameAs(second);
    }

    @Test
    void translate_withSameKeyAndParameters_shouldReturnSameStringProperty() {
        StringProperty first = i18n.translate("status.items", 5);
        StringProperty second = i18n.translate("status.items", 5);

        assertThat(first).isSameAs(second);
    }

    @Test
    void translate_withDifferentParameters_shouldReturnDifferentStringProperty() {
        StringProperty first = i18n.translate("status.items", 5);
        StringProperty second = i18n.translate("status.items", 10);

        assertThat(first).isNotSameAs(second);
    }

    @Test
    void rawTranslate_shouldReturnStaticString() {
        String translation = i18n.rawTranslate("error.file.not.found");

        assertThat(translation).isEqualTo("File not found");
    }

    @Test
    void rawTranslate_shouldNotUpdateWhenLocaleChanges() {
        assertThat(i18n.rawTranslate("error.file.not.found")).isEqualTo("File not found");

        i18n.setLocale(Locale.GERMAN);

        // rawTranslate should return current locale's translation
        assertThat(i18n.rawTranslate("error.file.not.found")).isEqualTo("Datei nicht gefunden");
    }

    @Test
    void rawTranslate_withMissingKey_shouldReturnKeyWithExclamations() {
        String result = i18n.rawTranslate("non.existent.key");

        assertThat(result).isEqualTo("!non.existent.key!");
    }

    @Test
    void rawTranslate_withParameters_shouldSubstituteParameters() {
        String fileName = "test.txt";
        String directory = "/home/user";

        String result = i18n.rawTranslate("error.file.missing", fileName, directory);

        assertThat(result)
                .isEqualTo("Could not find file \"test.txt\" in directory \"/home/user\"");
    }

    @Test
    void rawTranslate_withParameters_shouldHandleNumberFormatting() {
        int recordCount = 1000;
        double elapsedTime = 2.5;

        String result = i18n.rawTranslate("success.saved", recordCount, elapsedTime);

        assertThat(result).isEqualTo("Successfully saved 1,000 records in 2.5 seconds");
    }

    @Test
    void rawTranslate_withNoParameters_shouldReturnPattern() {
        String result = i18n.rawTranslate("welcome.message");

        assertThat(result).isEqualTo("Welcome, {0}!");
    }

    @Test
    void rawTranslate_withParametersButNoPlaceholders_shouldReturnOriginalPattern() {
        String result = i18n.rawTranslate("button.save", "extraParam");

        assertThat(result).isEqualTo("Save");
    }

    @Test
    void multipleControlsWithSameKey_shouldAllUpdateWhenLocaleChanges() {
        Button button1 = i18n.bind(new Button(), "button.save");
        Button button2 = i18n.bind(new Button(), "button.save");
        Label label = i18n.bind(new Label(), "button.save");

        assertThat(button1.getText()).isEqualTo("Save");
        assertThat(button2.getText()).isEqualTo("Save");
        assertThat(label.getText()).isEqualTo("Save");

        i18n.setLocale(Locale.GERMAN);

        assertThat(button1.getText()).isEqualTo("Speichern");
        assertThat(button2.getText()).isEqualTo("Speichern");
        assertThat(label.getText()).isEqualTo("Speichern");
    }

    @Test
    void boundPropertiesAndControls_shouldStayInSync() {
        Button button = i18n.bind(new Button(), "dialog.title");
        StringProperty property = new SimpleStringProperty();
        i18n.bind(property, "dialog.title");

        assertThat(button.getText()).isEqualTo("Confirmation Dialog");
        assertThat(property.get()).isEqualTo("Confirmation Dialog");

        i18n.setLocale(Locale.GERMAN);

        assertThat(button.getText()).isEqualTo("Bestätigungsdialog");
        assertThat(property.get()).isEqualTo("Bestätigungsdialog");
    }

    @Test
    void parameterizedTranslations_shouldCaptureParametersAtCreationTime() {
        // Simulate changing parameter value
        StringBuilder dynamicValue = new StringBuilder("initial");
        StringProperty property = i18n.translate("dynamic.message", dynamicValue.toString());

        assertThat(property.get()).isEqualTo("Dynamic value: initial");

        // Change the parameter source
        dynamicValue.append("_changed");

        // Property should still show the original value
        assertThat(property.get()).isEqualTo("Dynamic value: initial");
    }

    @Test
    void localeChanges_shouldUpdateAllBoundElements() {
        Button button = i18n.bind(new Button(), "button.save");
        Label label = i18n.bind(new Label(), "welcome.message", "User");
        StringProperty property = new SimpleStringProperty();
        i18n.bind(property, "window.title");
        StringProperty translatedProperty = i18n.translate("dialog.title");

        assertThat(button.getText()).isEqualTo("Save");
        assertThat(label.getText()).isEqualTo("Welcome, User!");
        assertThat(property.get()).isEqualTo("Application Window");
        assertThat(translatedProperty.get()).isEqualTo("Confirmation Dialog");

        i18n.setLocale(Locale.GERMAN);

        assertThat(button.getText()).isEqualTo("Speichern");
        assertThat(label.getText()).isEqualTo("Willkommen, User!");
        assertThat(property.get()).isEqualTo("Anwendungsfenster");
        assertThat(translatedProperty.get()).isEqualTo("Bestätigungsdialog");
    }

    @Test
    void methodChaining_shouldReturnSameControlInstance() {
        Button button = new Button();
        Button result = i18n.bind(button, "button.save");

        assertThat(result).isSameAs(button);
        assertThat(result.getText()).isEqualTo("Save");

        // Should be able to chain method calls
        Button chainedResult = i18n.bind(i18n.bind(new Button(), "button.save"), "button.cancel");
        assertThat(chainedResult).isNotNull();
        assertThat(chainedResult.getText()).isEqualTo("Cancel");
    }
}