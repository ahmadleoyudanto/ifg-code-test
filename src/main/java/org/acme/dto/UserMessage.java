package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserMessage {
    @JsonProperty("id")
    public Long Id;
    @JsonProperty("name")
    public String Name;
    @JsonProperty("type")
    public String Type;

    public Long getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }
}
