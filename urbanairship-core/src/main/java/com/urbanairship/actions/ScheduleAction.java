/* Copyright Airship and Contributors */

package com.urbanairship.actions;

import androidx.annotation.NonNull;

import com.urbanairship.UAirship;
import com.urbanairship.automation.ActionSchedule;
import com.urbanairship.automation.ActionScheduleInfo;
import com.urbanairship.json.JsonException;
import com.urbanairship.json.JsonValue;

import java.util.concurrent.ExecutionException;

/**
 * Action to schedule {@link ActionScheduleInfo}.
 * <p>
 * Accepted situations: SITUATION_MANUAL_INVOCATION, SITUATION_WEB_VIEW_INVOCATION,
 * SITUATION_AUTOMATION, and SITUATION_PUSH_RECEIVED.
 * <p>
 * Accepted argument value - JsonValue defined by {@link ActionScheduleInfo#fromJson(JsonValue)}.
 * <p>
 * Result value: Schedule ID.
 * <p>
 * Default Registration Names: {@link #DEFAULT_REGISTRY_NAME}, {@link #DEFAULT_REGISTRY_SHORT_NAME}
 * <p>
 * Default Registration Predicate: none
 */
public class ScheduleAction extends Action {

    /**
     * Default registry name
     */
    @NonNull
    public static final String DEFAULT_REGISTRY_NAME = "schedule_actions";

    /**
     * Default registry short name
     */
    @NonNull
    public static final String DEFAULT_REGISTRY_SHORT_NAME = "^sa";

    @Override
    public boolean acceptsArguments(@NonNull ActionArguments arguments) {
        switch (arguments.getSituation()) {
            case Action.SITUATION_MANUAL_INVOCATION:
            case Action.SITUATION_WEB_VIEW_INVOCATION:
            case Action.SITUATION_PUSH_RECEIVED:
            case Action.SITUATION_AUTOMATION:
                return arguments.getValue().toJsonValue().isJsonMap();

            case Action.SITUATION_BACKGROUND_NOTIFICATION_ACTION_BUTTON:
            case Action.SITUATION_FOREGROUND_NOTIFICATION_ACTION_BUTTON:
            case Action.SITUATION_PUSH_OPENED:
            default:
                return false;
        }
    }

    @NonNull
    @Override
    public ActionResult perform(@NonNull ActionArguments arguments) {
        try {
            ActionScheduleInfo info = ActionScheduleInfo.fromJson(arguments.getValue().toJsonValue());
            ActionSchedule schedule = UAirship.shared().getAutomation().schedule(info).get();
            return schedule == null ? ActionResult.newEmptyResult() : ActionResult.newResult(ActionValue.wrap(schedule.getId()));
        } catch (JsonException | InterruptedException | ExecutionException e) {
            return ActionResult.newErrorResult(e);
        }
    }

}
