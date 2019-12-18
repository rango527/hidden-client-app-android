package com.urbanairship.debug.databinding;
import com.urbanairship.debug.R;
import com.urbanairship.debug.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class UaItemPushBindingImpl extends UaItemPushBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public UaItemPushBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private UaItemPushBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            );
        this.alert.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.pushId.setTag(null);
        this.time.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.push == variableId) {
            setPush((com.urbanairship.debug.push.PushItem) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPush(@Nullable com.urbanairship.debug.push.PushItem Push) {
        this.mPush = Push;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.push);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String pushAlertJavaLangObjectNullAlertAndroidStringUaPushEmptyAlertPushAlert = null;
        java.lang.String pushPushId = null;
        java.lang.String pushAlert = null;
        long pushTime = 0;
        boolean pushAlertJavaLangObjectNull = false;
        com.urbanairship.debug.push.PushItem push = mPush;

        if ((dirtyFlags & 0x3L) != 0) {



                if (push != null) {
                    // read push.pushId
                    pushPushId = push.getPushId();
                    // read push.alert
                    pushAlert = push.getAlert();
                    // read push.time
                    pushTime = push.getTime();
                }


                // read push.alert == null
                pushAlertJavaLangObjectNull = (pushAlert) == (null);
            if((dirtyFlags & 0x3L) != 0) {
                if(pushAlertJavaLangObjectNull) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x3L) != 0) {

                // read push.alert == null ? @android:string/ua_push_empty_alert : push.alert
                pushAlertJavaLangObjectNullAlertAndroidStringUaPushEmptyAlertPushAlert = ((pushAlertJavaLangObjectNull) ? (alert.getResources().getString(R.string.ua_push_empty_alert)) : (pushAlert));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.alert, pushAlertJavaLangObjectNullAlertAndroidStringUaPushEmptyAlertPushAlert);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.pushId, pushPushId);
            com.urbanairship.debug.event.BindingAdaptersKt.bindFormatTime(this.time, pushTime);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): push
        flag 1 (0x2L): null
        flag 2 (0x3L): push.alert == null ? @android:string/ua_push_empty_alert : push.alert
        flag 3 (0x4L): push.alert == null ? @android:string/ua_push_empty_alert : push.alert
    flag mapping end*/
    //end
}