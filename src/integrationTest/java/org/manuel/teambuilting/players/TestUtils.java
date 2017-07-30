package org.manuel.teambuilting.players;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Manuel Doncel Martos
 * @since 30/07/2017.
 */
public class TestUtils {

    public static final String getTokenForUser(final TestUser testUser) {
        // now is hardcoded for user Manuel, but eventually I would like to create an user, and delete it after
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik1qbEVOVEJGT1VNek0wVXdRemRFT0RjMlJEUTBPVFJGUkVGQ05EUkRSams0T1VRM1JETXhOZyJ9.eyJlbWFpbCI6Im1hbnVlbGRvbmNlbG1hcnRvc0BnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Ik1hbnVlbCBEb25jZWwgTWFydG9zIiwiZ2l2ZW5fbmFtZSI6Ik1hbnVlbCIsImZhbWlseV9uYW1lIjoiRG9uY2VsIE1hcnRvcyIsInBpY3R1cmUiOiJodHRwczovL2xoNi5nb29nbGV1c2VyY29udGVudC5jb20vLUxmekJSNGNXZXFBL0FBQUFBQUFBQUFJL0FBQUFBQUFBY1NnL0JncVQ3V2d1VHU4L3Bob3RvLmpwZyIsImdlbmRlciI6Im1hbGUiLCJsb2NhbGUiOiJlbi1HQiIsImNsaWVudElEIjoiWE9CejRSZHpXb01ucHhBdlhLdEs5UjhXOUlPRFlLc2wiLCJ1cGRhdGVkX2F0IjoiMjAxNy0wNy0zMFQxNDo1Mzo1OC4xNTNaIiwidXNlcl9pZCI6Imdvb2dsZS1vYXV0aDJ8MTE1NTM1OTkxOTg1NjcwNTk3Nzc5Iiwibmlja25hbWUiOiJtYW51ZWxkb25jZWxtYXJ0b3MiLCJpZGVudGl0aWVzIjpbeyJwcm92aWRlciI6Imdvb2dsZS1vYXV0aDIiLCJ1c2VyX2lkIjoiMTE1NTM1OTkxOTg1NjcwNTk3Nzc5IiwiY29ubmVjdGlvbiI6Imdvb2dsZS1vYXV0aDIiLCJpc1NvY2lhbCI6dHJ1ZX1dLCJjcmVhdGVkX2F0IjoiMjAxNi0xMS0yOVQxODowMTo1My4xNzdaIiwidXNlcl9tZXRhZGF0YSI6eyJjb2xvciI6ImJsdWUifSwiYXBwX21ldGFkYXRhIjp7InJvbGVzIjpbImFkbWluIl19LCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImltcGVyc29uYXRlZCI6dHJ1ZSwiaW1wZXJzb25hdG9yIjp7InVzZXJfaWQiOiJnb29nbGUtb2F1dGgyfDExNTUzNTk5MTk4NTY3MDU5Nzc3OSIsImVtYWlsIjoibWFudWVsZG9uY2VsbWFydG9zQGdtYWlsLmNvbSJ9LCJwZXJzaXN0ZW50Ijp7fSwiaXNzIjoiaHR0cHM6Ly9tYW51ZWxhcnRlLmV1LmF1dGgwLmNvbS8iLCJzdWIiOiJnb29nbGUtb2F1dGgyfDExNTUzNTk5MTk4NTY3MDU5Nzc3OSIsImF1ZCI6IlhPQno0UmR6V29NbnB4QXZYS3RLOVI4VzlJT0RZS3NsIiwiZXhwIjoxNTAxNDYyNDM4LCJpYXQiOjE1MDE0MjY0Mzh9.P9XWBjkJyuLC6AWLdPCh0gdcbAaI0bxN4In5ZUp93T8vKMDlvlbvS_zO2q6hTcAU1LQo82v6NfBDTIagAb-lsqOxDXdOAg0humv-XOF8X2Y3kzaGyTK9fL52JOaSHzow9pL825pcH4WtWkvYAr_UuRe57XZ2QBcgNvr_y5-IqHyFM3R823TnYGb4iOrQn6BQ3jEoISNl6whPrhlvIWklGrU0W09cTpY_oLSdUFKTuPCtJ5YDMcmppgMtyQ4FTu0bx0B_XMdMac_mcDDqO_BpK3sTHkEhRlyzGeQx_lRnPuNA2EqLA_5Te_nSgX_hvDIwqb-IKMRyTx7DtqgCmqB1bA";
    }

    public static TestUser manuel() {
        return TestUser.builder().user_id("google-oauth2|115535991985670597779")
                .name("Manuel Doncel Martos").email("manueldoncelmartos@gmail.com").build();
    }


    @Data
    @AllArgsConstructor
    @Builder
    public static final class TestUser {
        private final String user_id;
        private final String name;
        private final String email;

    }
}
