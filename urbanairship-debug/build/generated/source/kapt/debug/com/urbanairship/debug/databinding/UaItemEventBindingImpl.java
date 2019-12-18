package com.urbanairship.debug.databinding;
import com.urbanairship.debug.R;
import com.urbanairship.debug.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class UaItemEventBindingImpl extends UaItemEventBinding  {

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

    public UaItemEventBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private UaItemEventBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            );
        this.icon.setTag(null);
        this.iconText.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.name.setTag(null);
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
        if (BR.viewModel == variableId) {
            setViewModel((com.urbanairship.debug.event.EventItem) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.urbanairship.debug.event.EventItem ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.viewModel);
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
        int eventInfoGetColorContextViewModelType = 0;
        com.urbanairship.debug.event.EventItem viewModel = mViewModel;
        java.lang.String eventInfoGetEventInitialsViewModelType = null;
        long viewModelTime = 0;
        java.lang.String viewModelType = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (viewModel != null) {
                    // read viewModel.time
                    viewModelTime = viewModel.getTime();
                    // read viewModel.type
                    viewModelType = viewModel.getType();
                }


                // read EventInfo.getColor(context, viewModel.type)
                eventInfoGetColorContextViewModelType = com.urbanairship.debug.event.EventInfo.getColor(getRoot().getContext(), viewModelType);
                // read EventInfo.getEventInitials(viewModel.type)
                eventInfoGetEventInitialsViewModelType = com.urbanairship.debug.event.EventInfo.getEventInitials(viewModelType);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.urbanairship.debug.event.BindingAdaptersKt.bindBackgroundTint(this.icon, eventInfoGetColorContextViewModelType);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.iconText, eventInfoGetEventInitialsViewModelType);
            com.urbanairship.debug.event.BindingAdaptersKt.bindEventName(this.name, viewModel);
            com.urbanairship.debug.event.BindingAdaptersKt.bindFormatTime(this.time, viewModelTime);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}