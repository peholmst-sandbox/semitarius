package net.pkhapps.semitarius.desktop.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import net.pkhapps.semitarius.desktop.client.MemberSummaryClient;
import net.pkhapps.semitarius.desktop.dto.MemberSummaryDto;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TODO document me
 */
public class MemberSummaryController {

    private final ObservableList<MemberSummaryModel> model = FXCollections.observableArrayList();

    private final MemberSummaryClient memberSummaryClient;

    public MemberSummaryController(@NotNull MemberSummaryClient memberSummaryClient) {
        this.memberSummaryClient = Objects.requireNonNull(memberSummaryClient, "memberSummaryClient must not be null");
    }

    public void refresh(@Nullable ActionEvent event) {
        model.setAll(memberSummaryClient.getMemberSummary().stream().map(MemberSummaryModel::new)
                .collect(Collectors.toList()));
    }

    @NotNull
    public ObservableList<MemberSummaryModel> getModel() {
        return model;
    }

    public static class MemberSummaryModel {

        public static final String PROP_FIRST_NAME = "firstName";
        public static final String PROP_LAST_NAME = "lastName";
        public static final String PROP_EMAIL = "email";
        public static final String PROP_PHONE_NUMBER = "phoneNumber";

        private final ObjectProperty<String> firstName = new SimpleObjectProperty<>();
        private final ObjectProperty<String> lastName = new SimpleObjectProperty<>();
        private final ObjectProperty<String> email = new SimpleObjectProperty<>();
        private final ObjectProperty<String> phoneNumber = new SimpleObjectProperty<>();

        MemberSummaryModel(@NotNull MemberSummaryDto memberSummaryDto) {
            updateFromDto(memberSummaryDto);
        }

        @NotNull
        public Property<String> firstNameProperty() {
            return firstName;
        }

        @NotNull
        public Property<String> lastNameProperty() {
            return lastName;
        }

        @NotNull
        public Property<String> emailProperty() {
            return email;
        }

        @NotNull
        public Property<String> phoneNumberProperty() {
            return phoneNumber;
        }

        final void updateFromDto(@NotNull MemberSummaryDto memberSummaryDto) {
            Objects.requireNonNull(memberSummaryDto, "memberSummaryDto must not be null");
            firstName.setValue(memberSummaryDto.member.firstName);
            lastName.setValue(memberSummaryDto.member.lastName);
            email.setValue(memberSummaryDto.member.email);
            phoneNumber.setValue(memberSummaryDto.member.phoneNumber);
        }
    }
}
