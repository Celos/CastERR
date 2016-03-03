package org.mihkel.casterr.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Channel {

    public enum ChannelType {TV, RADIO}

    private String name;
    private String url;
    private String image;
    private ChannelType type;

    public Channel(String jsonString) throws JSONException {

        JSONObject json = new JSONObject(jsonString);

        this.name = json.getString("name");
        this.url = json.getString("manifest");
        this.image = json.getString("image");
        this.type = ChannelType.valueOf(json.getString("type"));
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public ChannelType getType() {
        return type;
    }
}
