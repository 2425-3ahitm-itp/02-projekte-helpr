package at.htl.helpr.home;

import at.htl.helpr.components.TaskList;
import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.profile.ProfilView;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import at.htl.helpr.taskform.repository.filter.DateFromToFilter;
import at.htl.helpr.taskform.repository.filter.EffortFilter;
import at.htl.helpr.taskform.repository.filter.LocationFilter;
import at.htl.helpr.taskform.repository.filter.PaymentMinMaxFilter;
import at.htl.helpr.taskform.repository.filter.SearchFilter;
import at.htl.helpr.taskform.repository.filter.TaskQueryBuilder;
import java.time.LocalDate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class HomePresenter {

    private final HomeView view;
    private final TaskRepository repository = new TaskRepositoryImpl();
    private final TaskList taskList = new TaskList(false, repository::findAll,
            "Keine Aufgaben gefunden");
    private TaskQueryBuilder taskQueryBuilder = new TaskQueryBuilder();

    private final IntegerProperty minPaymentProperty = new SimpleIntegerProperty();
    private final IntegerProperty maxPaymentProperty = new SimpleIntegerProperty();
    private final IntegerProperty postalCodeProperty = new SimpleIntegerProperty();
    private final IntegerProperty effortProperty = new SimpleIntegerProperty();

    private final ObjectProperty<LocalDate> fromDateProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> toDateProperty = new SimpleObjectProperty<>();

    public HomePresenter(HomeView view) {
        this.view = view;
        view.setCenter(taskList);
        attachEvents();
        bindings();
    }

    private void attachEvents() {
        getView().getProfilePicture().setOnMouseClicked(mouseEvent -> openProfilView());
        getView().getUsernameLabel().setOnMouseClicked(mouseEvent -> openProfilView());
        getView().getSearchButton().setOnAction(mouseEvent -> updateCardsBySearch());
        getView().getFilterButton().setOnAction(mouseEvent -> updateCardsBySearch());
    }

    private void bindings() {
        getView().getMinPaymentField().disableProperty()
                .bind(getView().getPaymentToggle().selectedProperty().not());
        getView().getMaxPaymentField().disableProperty()
                .bind(getView().getPaymentToggle().selectedProperty().not());

        getView().getEffortField().disableProperty()
                .bind(getView().getEffortToggle().selectedProperty().not());
        getView().getPostalCodeField().disableProperty()
                .bind(getView().getPostalToggle().selectedProperty().not());
        getView().getCityField().disableProperty()
                .bind(getView().getCityToggle().selectedProperty().not());
        getView().getDateFields().disableProperty()
                .bind(getView().getDateToggle().selectedProperty().not());

        Bindings.bindBidirectional(getView().getMinPaymentField().textProperty(),
                minPaymentProperty, new NumberStringConverter());
        Bindings.bindBidirectional(getView().getMaxPaymentField().textProperty(),
                maxPaymentProperty, new NumberStringConverter());
        Bindings.bindBidirectional(getView().getPostalCodeField().textProperty(),
                postalCodeProperty, new NumberStringConverter());
        Bindings.bindBidirectional(getView().getEffortField().textProperty(),
                effortProperty, new NumberStringConverter());

        getView().getFromDateField().valueProperty().bindBidirectional(fromDateProperty);
        getView().getToDateField().valueProperty().bindBidirectional(toDateProperty);


    }

    private void handlePaymentFilter() {
        if (getView().getPaymentToggle().isSelected()) {
            taskQueryBuilder.addFilter(
                    new PaymentMinMaxFilter(minPaymentProperty.get(), maxPaymentProperty.get()));
        }
    }

    private void handleEffortFilter() {
        if (getView().getEffortToggle().isSelected()) {
            if (getView().getEffortField().getText() != null) {

                taskQueryBuilder.addFilter(
                        new EffortFilter(effortProperty.get()));
            }
        }
    }

    private void handlePlzFilter() {
        if (getView().getPostalToggle().isSelected()) {
            taskQueryBuilder.addFilter(
                    new LocationFilter(postalCodeProperty.get() + " %"));
        }
    }

    private void handleCityFilter() {
        if (getView().getCityToggle().isSelected()) {
            taskQueryBuilder.addFilter(
                    new LocationFilter("% " + getView().getCityField().getText()));
        }
    }

    private void handleDateFilter() {
        if (getView().getDateToggle().isSelected()) {
            taskQueryBuilder.addFilter(
                    new DateFromToFilter(fromDateProperty.get(), toDateProperty.get().plusDays(1)));
        }
    }


    private void updateCardsBySearch() {
        String searchQuery = getView().getSearchField().getText();

        taskQueryBuilder = new TaskQueryBuilder();

        addFiltersToQueryBuilder();

        if (!searchQuery.isBlank()) {
            taskQueryBuilder.addFilter(new SearchFilter(searchQuery));
        }

        if (taskQueryBuilder.getParamsCount() < 1) {
            taskList.setTaskSupplier(repository::findAll);
        } else {
            taskList.setTaskSupplier(() -> repository.getTasksWithFilter(taskQueryBuilder));
        }

        taskList.rerender();
    }

    private void addFiltersToQueryBuilder() {
        handleEffortFilter();
        handlePlzFilter();
        handleCityFilter();
        handlePaymentFilter();
        handleDateFilter();
    }

    private void openProfilView() {
        Stage currentStage = (Stage) getView().getProfilePicture().getScene().getWindow();

        var view = new ProfilView();
        var presenter = new ProfilPresenter(view);

        var scene = new Scene(view, 750, 650);

        currentStage.setTitle("Helpr Profil");
        currentStage.setScene(scene);
        currentStage.show();
    }

    public HomeView getView() {
        return view;
    }
}
