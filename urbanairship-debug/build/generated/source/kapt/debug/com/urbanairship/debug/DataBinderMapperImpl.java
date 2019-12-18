package com.urbanairship.debug;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.urbanairship.debug.databinding.UaFragmentDebugBindingImpl;
import com.urbanairship.debug.databinding.UaFragmentDeviceInfoAttributesBindingImpl;
import com.urbanairship.debug.databinding.UaFragmentDeviceInfoTagsBindingImpl;
import com.urbanairship.debug.databinding.UaFragmentEventDetailsBindingImpl;
import com.urbanairship.debug.databinding.UaFragmentEventListBindingImpl;
import com.urbanairship.debug.databinding.UaFragmentPushDetailsBindingImpl;
import com.urbanairship.debug.databinding.UaFragmentPushListBindingImpl;
import com.urbanairship.debug.databinding.UaItemDebugScreenBindingImpl;
import com.urbanairship.debug.databinding.UaItemEventBindingImpl;
import com.urbanairship.debug.databinding.UaItemEventFilterBindingImpl;
import com.urbanairship.debug.databinding.UaItemPushBindingImpl;
import com.urbanairship.debug.databinding.UaItemTagBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_UAFRAGMENTDEBUG = 1;

  private static final int LAYOUT_UAFRAGMENTDEVICEINFOATTRIBUTES = 2;

  private static final int LAYOUT_UAFRAGMENTDEVICEINFOTAGS = 3;

  private static final int LAYOUT_UAFRAGMENTEVENTDETAILS = 4;

  private static final int LAYOUT_UAFRAGMENTEVENTLIST = 5;

  private static final int LAYOUT_UAFRAGMENTPUSHDETAILS = 6;

  private static final int LAYOUT_UAFRAGMENTPUSHLIST = 7;

  private static final int LAYOUT_UAITEMDEBUGSCREEN = 8;

  private static final int LAYOUT_UAITEMEVENT = 9;

  private static final int LAYOUT_UAITEMEVENTFILTER = 10;

  private static final int LAYOUT_UAITEMPUSH = 11;

  private static final int LAYOUT_UAITEMTAG = 12;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(12);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_fragment_debug, LAYOUT_UAFRAGMENTDEBUG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_fragment_device_info_attributes, LAYOUT_UAFRAGMENTDEVICEINFOATTRIBUTES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_fragment_device_info_tags, LAYOUT_UAFRAGMENTDEVICEINFOTAGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_fragment_event_details, LAYOUT_UAFRAGMENTEVENTDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_fragment_event_list, LAYOUT_UAFRAGMENTEVENTLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_fragment_push_details, LAYOUT_UAFRAGMENTPUSHDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_fragment_push_list, LAYOUT_UAFRAGMENTPUSHLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_item_debug_screen, LAYOUT_UAITEMDEBUGSCREEN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_item_event, LAYOUT_UAITEMEVENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_item_event_filter, LAYOUT_UAITEMEVENTFILTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_item_push, LAYOUT_UAITEMPUSH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.urbanairship.debug.R.layout.ua_item_tag, LAYOUT_UAITEMTAG);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_UAFRAGMENTDEBUG: {
          if ("layout/ua_fragment_debug_0".equals(tag)) {
            return new UaFragmentDebugBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_fragment_debug is invalid. Received: " + tag);
        }
        case  LAYOUT_UAFRAGMENTDEVICEINFOATTRIBUTES: {
          if ("layout/ua_fragment_device_info_attributes_0".equals(tag)) {
            return new UaFragmentDeviceInfoAttributesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_fragment_device_info_attributes is invalid. Received: " + tag);
        }
        case  LAYOUT_UAFRAGMENTDEVICEINFOTAGS: {
          if ("layout/ua_fragment_device_info_tags_0".equals(tag)) {
            return new UaFragmentDeviceInfoTagsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_fragment_device_info_tags is invalid. Received: " + tag);
        }
        case  LAYOUT_UAFRAGMENTEVENTDETAILS: {
          if ("layout/ua_fragment_event_details_0".equals(tag)) {
            return new UaFragmentEventDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_fragment_event_details is invalid. Received: " + tag);
        }
        case  LAYOUT_UAFRAGMENTEVENTLIST: {
          if ("layout/ua_fragment_event_list_0".equals(tag)) {
            return new UaFragmentEventListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_fragment_event_list is invalid. Received: " + tag);
        }
        case  LAYOUT_UAFRAGMENTPUSHDETAILS: {
          if ("layout/ua_fragment_push_details_0".equals(tag)) {
            return new UaFragmentPushDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_fragment_push_details is invalid. Received: " + tag);
        }
        case  LAYOUT_UAFRAGMENTPUSHLIST: {
          if ("layout/ua_fragment_push_list_0".equals(tag)) {
            return new UaFragmentPushListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_fragment_push_list is invalid. Received: " + tag);
        }
        case  LAYOUT_UAITEMDEBUGSCREEN: {
          if ("layout/ua_item_debug_screen_0".equals(tag)) {
            return new UaItemDebugScreenBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_item_debug_screen is invalid. Received: " + tag);
        }
        case  LAYOUT_UAITEMEVENT: {
          if ("layout/ua_item_event_0".equals(tag)) {
            return new UaItemEventBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_item_event is invalid. Received: " + tag);
        }
        case  LAYOUT_UAITEMEVENTFILTER: {
          if ("layout/ua_item_event_filter_0".equals(tag)) {
            return new UaItemEventFilterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_item_event_filter is invalid. Received: " + tag);
        }
        case  LAYOUT_UAITEMPUSH: {
          if ("layout/ua_item_push_0".equals(tag)) {
            return new UaItemPushBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_item_push is invalid. Received: " + tag);
        }
        case  LAYOUT_UAITEMTAG: {
          if ("layout/ua_item_tag_0".equals(tag)) {
            return new UaItemTagBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for ua_item_tag is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(10);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "filter");
      sKeys.put(2, "isFilterSheetVisible");
      sKeys.put(3, "expandAlpha");
      sKeys.put(4, "collapseAlpha");
      sKeys.put(5, "viewModel");
      sKeys.put(6, "screen");
      sKeys.put(7, "tag");
      sKeys.put(8, "push");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(12);

    static {
      sKeys.put("layout/ua_fragment_debug_0", com.urbanairship.debug.R.layout.ua_fragment_debug);
      sKeys.put("layout/ua_fragment_device_info_attributes_0", com.urbanairship.debug.R.layout.ua_fragment_device_info_attributes);
      sKeys.put("layout/ua_fragment_device_info_tags_0", com.urbanairship.debug.R.layout.ua_fragment_device_info_tags);
      sKeys.put("layout/ua_fragment_event_details_0", com.urbanairship.debug.R.layout.ua_fragment_event_details);
      sKeys.put("layout/ua_fragment_event_list_0", com.urbanairship.debug.R.layout.ua_fragment_event_list);
      sKeys.put("layout/ua_fragment_push_details_0", com.urbanairship.debug.R.layout.ua_fragment_push_details);
      sKeys.put("layout/ua_fragment_push_list_0", com.urbanairship.debug.R.layout.ua_fragment_push_list);
      sKeys.put("layout/ua_item_debug_screen_0", com.urbanairship.debug.R.layout.ua_item_debug_screen);
      sKeys.put("layout/ua_item_event_0", com.urbanairship.debug.R.layout.ua_item_event);
      sKeys.put("layout/ua_item_event_filter_0", com.urbanairship.debug.R.layout.ua_item_event_filter);
      sKeys.put("layout/ua_item_push_0", com.urbanairship.debug.R.layout.ua_item_push);
      sKeys.put("layout/ua_item_tag_0", com.urbanairship.debug.R.layout.ua_item_tag);
    }
  }
}
