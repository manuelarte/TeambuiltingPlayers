package org.manuel.teambuilting.players;

import com.auth0.spring.security.api.authentication.PreAuthenticatedAuthenticationJsonWebToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;

/**
 * @author Manuel Doncel Martos
 * @since 30/07/2017.
 */
public class TestUtils {

    public static Authentication getAuthenticationFromUser(final TestUser testUser) {
        return PreAuthenticatedAuthenticationJsonWebToken.usingToken(testUser.getToken());
    }

    public static TestUser manuel() {
        return new TestUser("google-oauth2|115535991985670597779", "Manuel Doncel Martos",
                "manueldoncelmartos@gmail.com");
    }


    @Data
    @AllArgsConstructor
    @Builder
    public static final class TestUser {
        private final String user_id;
        private final String name;
        private final String email;
        private final String token;

        TestUser(final String user_id, final String name, final String email) {
            this.user_id = user_id;
            this.name = name;
            this.email = email;
            this.token = retrieveToken();
        }

        @SneakyThrows
        private String retrieveToken() {
            final HttpResponse<String> response = Unirest.post("https://manuelarte.eu.auth0.com/oauth/token")
                    .header("content-type", "application/json")
                    .body("{\"client_id\":\"XOBz4RdzWoMnpxAvXKtK9R8W9IODYKsl\",\"client_secret\":\"tvKvKZd1tigVIAGztcOELwKIj0B0DswEbLdRG1PWu7NfZXk6VGbGkWdQjFpTZmWp\",\"audience\":\"https://manuelarte.eu.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
                    .asString();
            return new JSONObject(response.getBody()).get("access_token").toString();
        }

    }
}
