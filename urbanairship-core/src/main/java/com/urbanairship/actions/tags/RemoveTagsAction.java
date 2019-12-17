/* Copyright Airship and Contributors */

package com.urbanairship.actions.tags;

import androidx.annotation.NonNull;

import com.urbanairship.Logger;
import com.urbanairship.UAirship;
import com.urbanairship.actions.Action;
import com.urbanairship.actions.ActionArguments;
import com.urbanairship.actions.ActionRegistry;
import com.urbanairship.channel.TagGroupsEditor;

import java.util.Map;
import java.util.Set;

/**
 * An action that removes tags.
 * <p>
 * Accepted situations: all
 * <p>
 * Accepted argument value types: A string for a single tag, A Collection of Strings for multiple tags,
 * or a JSON payload for tag groups. An example JSON payload:
 * <pre>
 * {
 *   "channel": {
 *     "channel_tag_group": ["channel_tag_1", "channel_tag_2"],
 *     "other_channel_tag_group": ["other_channel_tag_1"]
 *   },
 *   "named_user": {
 *     "named_user_tag_group": ["named_user_tag_1", "named_user_tag_2"],
 *     "other_named_user_tag_group": ["other_named_user_tag_1"]
 *   },
 *   "device": ["tag 1", "tag 2"]
 * }
 * </pre>
 * <p>
 * Result value: null
 * <p>
 * Default Registration Names: ^-t, remove_tags_action
 * <p>
 * Default Registration Predicate: Rejects SITUATION_PUSH_RECEIVED
 */
public class RemoveTagsAction extends BaseTagsAction {

    /**
     * Default registry name
     */
    @NonNull
    public static final String DEFAULT_REGISTRY_NAME = "remove_tags_action";

    /**
     * Default registry short name
     */
    @NonNull
    public static final String DEFAULT_REGISTRY_SHORT_NAME = "^-t";

    @Override
    void applyChannelTags(@NonNull Set<String> tags) {
        Logger.info("RemoveTagsAction - Removing tags: %s", tags);
        Set<String> currentTags = getPushManager().getTags();
        currentTags.removeAll(tags);

        getPushManager().setTags(currentTags);
    }

    @Override
    void applyChannelTagGroups(@NonNull Map<String, Set<String>> tags) {
        Logger.info("RemoveTagsAction - Removing channel tag groups: %s", tags);
        TagGroupsEditor tagGroupsEditor = getPushManager().editTagGroups();
        for (Map.Entry<String, Set<String>> entry : tags.entrySet()) {
            tagGroupsEditor.removeTags(entry.getKey(), entry.getValue());
        }

        tagGroupsEditor.apply();
    }

    @Override
    void applyNamedUserTagGroups(@NonNull Map<String, Set<String>> tags) {
        Logger.info("RemoveTagsAction - Removing named user tag groups: %s", tags);

        TagGroupsEditor tagGroupsEditor = UAirship.shared().getNamedUser().editTagGroups();
        for (Map.Entry<String, Set<String>> entry : tags.entrySet()) {
            tagGroupsEditor.removeTags(entry.getKey(), entry.getValue());
        }

        tagGroupsEditor.apply();
    }

    /**
     * Default {@link RemoveTagsPredicate} predicate.
     */
    public static class RemoveTagsPredicate implements ActionRegistry.Predicate {

        @Override
        public boolean apply(@NonNull ActionArguments arguments) {
            return Action.SITUATION_PUSH_RECEIVED != arguments.getSituation();
        }

    }

}
