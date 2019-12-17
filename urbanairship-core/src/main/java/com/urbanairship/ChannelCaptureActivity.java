/* Copyright Airship and Contributors */

package com.urbanairship;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.urbanairship.actions.ActionArguments;
import com.urbanairship.actions.ActionCompletionCallback;
import com.urbanairship.actions.ActionResult;
import com.urbanairship.actions.ActionRunRequest;
import com.urbanairship.actions.ClipboardAction;
import com.urbanairship.actions.OpenExternalUrlAction;
import com.urbanairship.actions.ShareAction;
import com.urbanairship.messagecenter.ThemedActivity;
import com.urbanairship.push.PushManager;
import com.urbanairship.util.UAStringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelCaptureActivity extends ThemedActivity {

    private static final String ALIAS_HEADER = "Alias";
    private static final String USER_NOTIFICATION_ENABLED_HEADER = "User Notifications Enabled";
    private static final String NAMED_USER_HEADER = "Named User";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ua_activity_channel_capture);

        Logger.debug("Creating channel capture activity.");

        Intent intent = getIntent();

        if (intent == null) {
            Logger.warn("ChannelCaptureActivity - Started activity with null intent");
            finish();
            return;
        }

        final String channelString = intent.getStringExtra(ChannelCapture.CHANNEL);
        final String urlString = intent.getStringExtra(ChannelCapture.URL);

        TextView channelID = findViewById(R.id.channel_id);
        channelID.setText(channelString);
        Button shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionRunRequest.createRequest(ShareAction.DEFAULT_REGISTRY_NAME)
                                .setValue(channelString)
                                .run();
            }
        });

        Button copyButton = findViewById(R.id.copy_button);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionRunRequest.createRequest(ClipboardAction.DEFAULT_REGISTRY_NAME)
                                .setValue(channelString)
                                .run(new ActionCompletionCallback() {
                                    @Override
                                    public void onFinish(@NonNull ActionArguments arguments, @NonNull ActionResult result) {
                                        Toast.makeText(getApplicationContext(), getString(R.string.ua_channel_copy_toast), Toast.LENGTH_SHORT).show();
                                    }
                                });
            }
        });

        Button urlButton = findViewById(R.id.open_button);
        if (urlString != null) {
            urlButton.setEnabled(true);
            urlButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActionRunRequest.createRequest(OpenExternalUrlAction.DEFAULT_REGISTRY_NAME)
                                    .setValue(urlString)
                                    .run();
                }
            });
        }

        ListView channelData = findViewById(R.id.channel_information);
        SimpleAdapter channelAdapter = new SimpleAdapter(
                this,
                getChannelData(),
                android.R.layout.simple_list_item_2,
                new String[] { "header", "data" },
                new int[] { android.R.id.text1, android.R.id.text2 }
        );
        channelData.setAdapter(channelAdapter);
    }

    @NonNull
    private List<Map<String, String>> getChannelData() {
        List<Map<String, String>> data = new ArrayList<>();
        PushManager pushManager = UAirship.shared().getPushManager();

        addChannelAttribute(data, NAMED_USER_HEADER, UAirship.shared().getNamedUser().getId());
        addChannelAttribute(data, USER_NOTIFICATION_ENABLED_HEADER, String.valueOf(pushManager.getUserNotificationsEnabled()));

        return data;
    }

    private void addChannelAttribute(@NonNull List<Map<String, String>> channelDataList, @NonNull String header, @Nullable String data) {
        if (!UAStringUtil.isEmpty(data)) {
            Map<String, String> channelDataMap = new HashMap<>();
            channelDataMap.put("header", header);
            channelDataMap.put("data", data);
            channelDataList.add(channelDataMap);
        }
    }

}

