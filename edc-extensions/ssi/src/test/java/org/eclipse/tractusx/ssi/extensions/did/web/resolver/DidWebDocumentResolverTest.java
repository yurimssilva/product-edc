package org.eclipse.tractusx.ssi.extensions.did.web.resolver;

import lombok.SneakyThrows;
import okhttp3.*;
import org.eclipse.tractusx.ssi.spi.did.Did;
import org.eclipse.tractusx.ssi.spi.did.DidDocument;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.DidMethodIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.nio.charset.Charset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class DidWebDocumentResolverTest {

  private DidWebDocumentResolver resolver;
  private OkHttpClient clientMock;

  @BeforeEach
  public void setUp(){
    clientMock = Mockito.mock(OkHttpClient.class);
    resolver = new DidWebDocumentResolver(new OkHttpClient());
  }


  @Test
  @SneakyThrows
  public void resolveDidWebDocumentSuccess(){
    // given
    Did toTest = new Did(new DidMethod("web"), new DidMethodIdentifier("someurl.com"));
    String testDidDocument = getTestDidDocument();
    /**ResponseBody body = ResponseBody.create(testDidDocument, MediaType.get("application/json"));
    Request req = new Request.Builder().get().url("https://www.someurl.com").build();
    Response response = new Response.Builder().body(body).code(200).request(req).build();

    doReturn(response).when(clientMock).newCall(any(Request.class)).execute();*/

    DidDocument expectedResult = null;
    // when
    DidDocument result = resolver.resolve(toTest);
    // then
    Assertions.assertTrue(result.equals(expectedResult));
  }

  @SneakyThrows
  private String getTestDidDocument(){
    InputStream in = DidWebDocumentResolverTest.class.getClassLoader().getResourceAsStream("did/webdid.json");
    String didDocumentString = new String(in.readAllBytes());
    return didDocumentString;
  }
}
