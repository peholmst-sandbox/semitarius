package net.pkhapps.semitarius.desktop.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import net.pkhapps.semitarius.desktop.client.StatusDescriptorClient;
import net.pkhapps.semitarius.desktop.dto.StatusDescriptorDto;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * TODO Document me
 */
public class StatusDescriptorController {

    private final ObservableList<StatusDescriptorModel> model = FXCollections.observableArrayList();

    private final StatusDescriptorClient statusDescriptorClient;

    public StatusDescriptorController(@NotNull StatusDescriptorClient statusDescriptorClient) {
        this.statusDescriptorClient =
                Objects.requireNonNull(statusDescriptorClient, "statusDescriptorClient must not be null");
    }

    public void refresh(@Nullable ActionEvent event) {
        model.setAll(statusDescriptorClient.getStatusDescriptors().stream().map(StatusDescriptorModel::new).collect(
                Collectors.toList()));
    }

    @NotNull
    public ObservableList<StatusDescriptorModel> getModel() {
        return model;
    }

    @NotNull
    public Optional<StatusDescriptorModel> findById(@NotNull Long id) {
        Objects.requireNonNull(id, "id must not be null");
        return getModel().stream().filter(model -> model.id.equals(id)).findAny();
    }

    public static class StatusDescriptorModel {

        public static final String PROP_NAME = "name";
        public static final String PROP_COLOR = "color";

        private Long id;
        private final ObjectProperty<String> name = new SimpleObjectProperty<>();
        private final ObjectProperty<Color> color = new SimpleObjectProperty<>();

        StatusDescriptorModel(@NotNull StatusDescriptorDto statusDescriptorDto) {
            updateFromDto(statusDescriptorDto);
        }

        @NotNull
        public Property<String> nameProperty() {
            return name;
        }

        @NotNull
        public Property<Color> colorProperty() {
            return color;
        }

        final void updateFromDto(@NotNull StatusDescriptorDto statusDescriptorDto) {
            Objects.requireNonNull(statusDescriptorDto, "statusDescriptorDto must not be null");
            id = statusDescriptorDto.id;
            name.setValue(statusDescriptorDto.name);
            color.setValue(intToColor(statusDescriptorDto.color));
        }

        @Nullable
        private static Color intToColor(@Nullable Integer rgb) {
            if (rgb == null) {
                return null;
            }
            final int r = (rgb >> 16) & 0xff;
            final int g = (rgb >> 8) & 0xff;
            final int b = rgb & 0xff;
            return Color.rgb(r, g, b);
        }
    }
}
