package net.pkhapps.semitarius.desktop.client;

import net.pkhapps.semitarius.desktop.dto.MemberSummaryDto;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * TODO Document me!
 */
public class MemberSummaryClient {

    private static final String PATH = "/api/1.0/{tenant}/members";
    private final Client client;
    private final Context context;

    public MemberSummaryClient(@NotNull Client client, @NotNull Context context) {
        this.client = Objects.requireNonNull(client, "client must not be null");
        this.context = Objects.requireNonNull(context, "context must not be null");
    }

    @NotNull
    public List<MemberSummaryDto> getMemberSummary() {
        return client.target(UriBuilder.fromUri(context.getServerUri()).path(PATH).path("/summary")
                .build(context.getTenantId())).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<MemberSummaryDto>>() {
                });
    }

    @NotNull
    public Optional<MemberSummaryDto> getMemberSummary(@NotNull Long memberId) {
        try {
            MemberSummaryDto dto = client.target(
                    UriBuilder.fromUri(context.getServerUri()).path(PATH).path("/{member}/summary")
                            .build(context.getTenantId(), memberId)).request(MediaType.APPLICATION_JSON)
                    .get(MemberSummaryDto.class);
            return Optional.of(dto);
        } catch (NotFoundException ex) {
            return Optional.empty();
        }
    }
}
