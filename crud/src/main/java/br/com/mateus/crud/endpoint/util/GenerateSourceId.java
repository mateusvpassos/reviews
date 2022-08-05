package br.com.mateus.crud.endpoint.util;

public class GenerateSourceId {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateSourceId() {
        StringBuilder stringBuilder = new StringBuilder(5);

        for (int i = 0; i < 5; i++) {
            final int index = (int) (ALPHA_NUMERIC_STRING.length()
                    * Math.random());

            stringBuilder.append(ALPHA_NUMERIC_STRING
                    .charAt(index));
        }

        return stringBuilder.toString();
    }
}
