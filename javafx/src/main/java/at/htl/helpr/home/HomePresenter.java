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
import at.htl.helpr.taskform.repository.filter.TaskQueryBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;
import java.time.LocalDate;

public class HomePresenter {

    private final HomeView view;
    private final TaskRepository repository = new TaskRepositoryImpl();
    private final TaskList taskList = new TaskList(false, repository::findAll,
            "Keine Aufgaben gefunden");
    private TaskQueryBuilder filterTasks = new TaskQueryBuilder();

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
        getView().getFilterButton().setOnAction(mouseEvent -> useFilter());
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

    private void useFilter() {
        filterTasks = new TaskQueryBuilder();
        handleEffortFilter();
        handlePlzFilter();
        handleCityFilter();
        handlePaymentFilter();
        handleDateFilter();
        taskList.rerender();


    }

    private void handlePaymentFilter() {
        if (getView().getPaymentToggle().isSelected()) {
            filterTasks.addFilter(
                    new PaymentMinMaxFilter(minPaymentProperty.get(), maxPaymentProperty.get()));
            taskList.setTaskSupplier(() -> repository.getTasksWithFilter(filterTasks));
        }
    }

    private void handleEffortFilter() {
        if (getView().getEffortToggle().isSelected()) {
            if (getView().getEffortField().getText() != null) {

                filterTasks.addFilter(
                        new EffortFilter(effortProperty.get()));
                taskList.setTaskSupplier(() -> repository.getTasksWithFilter(filterTasks));
            }
        }
    }

    private void handlePlzFilter() {
        if (getView().getPostalToggle().isSelected()) {
            filterTasks.addFilter(
                    new LocationFilter(postalCodeProperty.get() + " %"));
            taskList.setTaskSupplier(() -> repository.getTasksWithFilter(filterTasks));
        }
    }

    private void handleCityFilter() {
        if (getView().getCityToggle().isSelected()) {
            filterTasks.addFilter(new LocationFilter("% " + getView().getCityField().getText()));
            taskList.setTaskSupplier(() -> repository.getTasksWithFilter(filterTasks));
        }
    }

    private void handleDateFilter() {
        if (getView().getDateToggle().isSelected()) {
            filterTasks.addFilter(
                    new DateFromToFilter(fromDateProperty.get(), toDateProperty.get().plusDays(1)));
            taskList.setTaskSupplier(() -> repository.getTasksWithFilter(filterTasks));
        }
    }


    private void updateCardsBySearch() {
        String searchQuery = getView().getSearchField().getText();

        if (searchQuery.isBlank()) {
            taskList.setTaskSupplier(repository::findAll);
        } else {
            taskList.setTaskSupplier(() -> repository.getTaskBySearchQueryAndLimit(searchQuery));
        }
        taskList.rerender();

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
