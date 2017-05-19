package net.pkhapps.semitarius.desktop.client;

import java.net.URI;

/**
 * TODO Document me!
 */
public class Context {

    private String tenantId = "pargasfbk";

    public static Context getInstance() {
        return new Context();
    }

    public String getTenantId() {
        return tenantId;
    }

    public URI getServerUri() {
        return URI.create("http://localhost:8080");
    }
}
