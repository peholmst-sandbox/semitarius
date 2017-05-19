package net.pkhapps.semitarius.desktop.client;

import net.pkhapps.semitarius.desktop.dto.StatusDescriptorDto;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Objects;

/**
 * TODO Document me!
 */
public class StatusDescriptorClient {

    private static final String PATH = "/api/1.0/{tenant}/statusDescriptors";
    private final Client client;
    private final Context context;

    public StatusDescriptorClient(@NotNull Client client, @NotNull Context context) {
        this.client = Objects.requireNonNull(client, "client must not be null");
        this.context = Objects.requireNonNull(context, "context must not be null");
    }

    @NotNull
    public List<StatusDescriptorDto> getStatusDescriptors() {
        return client.target(UriBuilder.fromUri(context.getServerUri()).path(PATH).build(context.getTenantId()))
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<StatusDescriptorDto>>() {
                });
    }
}
