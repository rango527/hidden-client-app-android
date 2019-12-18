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
import com.urbanairship.debug.R;
import com.urbanairship.debug.push.PushDetailsViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class UaFragmentPushDetailsBinding extends ViewDataBinding {
  @NonNull
  public final TextView details;

  @Bindable
  protected PushDetailsViewModel mViewModel;

  protected UaFragmentPushDetailsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView details) {
    super(_bindingComponent, _root, _localFieldCount);
    this.details = details;
  }

  public abstract void setViewModel(@Nullable PushDetailsViewModel viewModel);

  @Nullable
  public PushDetailsViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static UaFragmentPushDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.ua_fragment_push_details, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static UaFragmentPushDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<UaFragmentPushDetailsBinding>inflateInternal(inflater, R.layout.ua_fragment_push_details, root, attachToRoot, component);
  }

  @NonNull
  public static UaFragmentPushDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.ua_fragment_push_details, null, false, component)
   */
  @NonNull
  @Deprecated
  public static UaFragmentPushDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<UaFragmentPushDetailsBinding>inflateInternal(inflater, R.layout.ua_fragment_push_details, null, false, component);
  }

  public static UaFragmentPushDetailsBinding bind(@NonNull View view) {
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
  public static UaFragmentPushDetailsBinding bind(@NonNull View view, @Nullable Object component) {
    return (UaFragmentPushDetailsBinding)bind(component, view, R.layout.ua_fragment_push_details);
  }
}
