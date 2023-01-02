package org.eclipse.tractusx.ssi.web.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Request;
import okhttp3.Response;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.tractusx.ssi.web.document.DidDocument;

import okhttp3.OkHttpClient;

import java.io.IOException;


public class WebDidResolver implements DidResolver {
    private static final String DID_METHOD = "web";

    private final OkHttpClient httpClient;
    private final ObjectMapper mapper;
    private final Monitor monitor;
    private final WebDidUrlResolver urlResolver;

    /**
     * Creates a resolver that executes standard DNS lookups.
     */
    public WebDidResolver(OkHttpClient httpClient, boolean useHttpsScheme, ObjectMapper mapper, Monitor monitor) {
        this.httpClient = httpClient;
        this.urlResolver = new WebDidUrlResolver(useHttpsScheme);
        this.mapper = mapper;
        this.monitor = monitor;
    }

    @Override
    public String getMethod() {
        return DID_METHOD;
    }

    @Override
    public DidDocument resolve(String didKey) {
        String url;
        try {
            url = urlResolver.apply(didKey);
        } catch (IllegalArgumentException e) {
            throw e; // TODO
        }

        var request = new Request.Builder().url(url).get().build();
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            if (response.code() != 200) {
                throw new RuntimeException(); // TODO
            }
            try (var body = response.body()) {
                if (body == null) {
                    throw new RuntimeException(); // TODO
                }
                return mapper.readValue(body.string(), DidDocument.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e); // TODO
            }
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO
        }

    }
}
