package hu.omixon.demultiplexer.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUtil {

    public String getAsAbsolutePath(String resourcePath) {
        return "src/test/resources/" + resourcePath;
    }

}
