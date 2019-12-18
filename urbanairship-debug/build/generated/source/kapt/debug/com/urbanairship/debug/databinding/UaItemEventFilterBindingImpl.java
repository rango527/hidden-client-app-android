package com.urbanairship.debug.databinding;
import com.urbanairship.debug.R;
import com.urbanairship.debug.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class UaItemEventFilterBindingImpl extends UaItemEventFilterBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener chipandroidCheckedAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of filter.isChecked.get()
            //         is filter.isChecked.set((boolean) callbackArg_0)
            boolean callbackArg_0 = chip.isChecked();
            // localize variables for thread safety
            // filter
            com.urbanairship.debug.event.EventFilter filter = mFilter;
            // filter != null
            boolean filterJavaLangObjectNull = false;
            // filter.isChecked
            androidx.databinding.ObservableBoolean filterIsChecked = null;
            // filter.isChecked != null
            boolean filterIsCheckedJavaLangObjectNull = false;
            // filter.isChecked.get()
            boolean filterIsCheckedGet = false;



            filterJavaLangObjectNull = (filter) != (null);
            if (filterJavaLangObjectNull) {


                filterIsChecked = filter.isChecked();

                filterIsCheckedJavaLangObjectNull = (filterIsChecked) != (null);
                if (filterIsCheckedJavaLangObjectNull) {




                    filterIsChecked.set(((boolean) (callbackArg_0)));
                }
            }
        }
    };

    public UaItemEventFilterBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 1, sIncludes, sViewsWithIds));
    }
    private UaItemEventFilterBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.google.android.material.chip.Chip) bindings[0]
            );
        this.chip.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
        if (BR.filter == variableId) {
            setFilter((com.urbanairship.debug.event.EventFilter) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setFilter(@Nullable com.urbanairship.debug.event.EventFilter Filter) {
        this.mFilter = Filter;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.filter);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeFilterIsChecked((androidx.databinding.ObservableBoolean) object, fieldId);
        }
        return false;
    }
    private boolean onChangeFilterIsChecked(androidx.databinding.ObservableBoolean FilterIsChecked, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        com.urbanairship.debug.event.EventFilter filter = mFilter;
        androidx.databinding.ObservableBoolean filterIsChecked = null;
        boolean filterIsCheckedGet = false;

        if ((dirtyFlags & 0x7L) != 0) {



                if (filter != null) {
                    // read filter.isChecked
                    filterIsChecked = filter.isChecked();
                }
                updateRegistration(0, filterIsChecked);


                if (filterIsChecked != null) {
                    // read filter.isChecked.get()
                    filterIsCheckedGet = filterIsChecked.get();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.chip, filterIsCheckedGet);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            com.urbanairship.debug.event.BindingAdaptersKt.bindChipFilterIconTint(this.chip, getColorFromResource(chip, android.R.color.white));
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setListeners(this.chip, (android.widget.CompoundButton.OnCheckedChangeListener)null, chipandroidCheckedAttrChanged);
        }
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            com.urbanairship.debug.event.BindingAdaptersKt.bindEventFilter(this.chip, filter);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): filter.isChecked
        flag 1 (0x2L): filter
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}