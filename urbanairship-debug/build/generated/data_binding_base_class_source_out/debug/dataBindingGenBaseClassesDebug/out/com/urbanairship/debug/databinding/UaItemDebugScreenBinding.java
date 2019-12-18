// Generated by data binding compiler. Do not edit!
package com.urbanairship.debug.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.urbanairship.debug.DebugEntry;
import com.urbanairship.debug.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class UaItemDebugScreenBinding extends ViewDataBinding {
  @NonNull
  public final TextView description;

  @NonNull
  public final TextView name;

  @Bindable
  protected DebugEntry mScreen;

  protected UaItemDebugScreenBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView description, TextView name) {
    super(_bindingComponent, _root, _localFieldCount);
    this.description = description;
    this.name = name;
  }

  public abstract void setScreen(@Nullable DebugEntry screen);

  @Nullable
  public DebugEntry getScreen() {
    return mScreen;
  }

  @NonNull
  public static UaItemDebugScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.ua_item_debug_screen, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static UaItemDebugScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<UaItemDebugScreenBinding>inflateInternal(inflater, R.layout.ua_item_debug_screen, root, attachToRoot, component);
  }

  @NonNull
  public static UaItemDebugScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.ua_item_debug_screen, null, false, component)
   */
  @NonNull
  @Deprecated
  public static UaItemDebugScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<UaItemDebugScreenBinding>inflateInternal(inflater, R.layout.ua_item_debug_screen, null, false, component);
  }

  public static UaItemDebugScreenBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static UaItemDebugScreenBinding bind(@NonNull View view, @Nullable Object component) {
    return (UaItemDebugScreenBinding)bind(component, view, R.layout.ua_item_debug_screen);
  }
}
