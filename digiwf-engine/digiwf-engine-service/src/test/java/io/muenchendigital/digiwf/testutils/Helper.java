package io.muenchendigital.digiwf.testutils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Helper {

    @NotNull
    public static JSONObject getJsonObject(final String path) throws IOException, URISyntaxException {
        final String schemaString = new String(Files.readAllBytes(Paths.get(Helper.class.getResource(path).toURI())));
        return new JSONObject(new JSONTokener(schemaString));
    }

}
