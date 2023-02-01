package com.mainproject.server.constant;

import lombok.Getter;

public enum ImageProperty {
    BASIC_IMAGE_FILE_NAME("basic"),
    BASIC_IMAGE_DIR_NAME("profile_image");

    @Getter
    private final String name;

    ImageProperty(String name) {
        this.name = name;
    }

}
