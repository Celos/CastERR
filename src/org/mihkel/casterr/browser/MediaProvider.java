/*
 * Copyright (C) 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mihkel.casterr.browser;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.common.images.WebImage;

import org.json.JSONException;
import org.json.JSONObject;
import org.mihkel.casterr.R;
import org.mihkel.casterr.entities.Channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediaProvider {

    private static final String TAG = "MediaProvider";
    private static final String TAG_DASH = "application/dash+xml";
    private static final String TAG_HLS = "application/x-mpegurl";
    private static List<MediaInfo> mediaList;

    public static List<MediaInfo> buildMedia(Context context) throws JSONException {

        if (null != mediaList) {
            return mediaList;
        }

        List<String> channels = new ArrayList<>();
        channels.addAll(Arrays.asList(context.getResources().getStringArray(R.array.tv_channels)));
        channels.addAll(Arrays.asList(context.getResources().getStringArray(R.array.radio_channels)));
        channels.addAll(Arrays.asList(context.getResources().getStringArray(R.array.test_channels)));

        mediaList = new ArrayList<>();

        for (String channel : channels) {
            mediaList.add(buildMediaInfo(new Channel(channel)));
        }

        return mediaList;
    }

    private static MediaInfo buildMediaInfo(Channel channel) {
        MediaMetadata tvMetaData = new MediaMetadata(MediaMetadata.MEDIA_TYPE_GENERIC);

        tvMetaData.putString(MediaMetadata.KEY_TITLE, channel.getName());
        tvMetaData.addImage(new WebImage(Uri.parse(channel.getImage())));

        return new MediaInfo.Builder(channel.getUrl())
                .setStreamType(MediaInfo.STREAM_TYPE_LIVE)
                .setContentType(channel.getUrl().endsWith(".mpd") ? TAG_DASH : TAG_HLS)
                .setMetadata(tvMetaData)
                .build();
    }
}


