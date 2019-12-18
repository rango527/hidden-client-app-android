package com.urbanairship.debug.databinding;
import com.urbanairship.debug.R;
import com.urbanairship.debug.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class UaFragmentEventListBindingImpl extends UaFragmentEventListBinding implements com.urbanairship.debug.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.events, 6);
        sViewsWithIds.put(R.id.filter_sheet, 7);
        sViewsWithIds.put(R.id.guide_peek_height, 8);
        sViewsWithIds.put(R.id.event_filters, 9);
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public UaFragmentEventListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private UaFragmentEventListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (android.widget.Button) bindings[3]
            , (android.widget.ImageButton) bindings[4]
            , (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[0]
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            , (androidx.recyclerview.widget.RecyclerView) bindings[6]
            , (android.widget.ImageButton) bindings[5]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[7]
            , (androidx.constraintlayout.widget.Guideline) bindings[8]
            , (android.widget.TextView) bindings[2]
            );
        this.clear.setTag(null);
        this.collapse.setTag(null);
        this.coordinatorLayout2.setTag(null);
        this.expand.setTag(null);
        this.fab.setTag(null);
        this.textView.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.urbanairship.debug.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x20L;
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
        if (BR.isFilterSheetVisible == variableId) {
            setIsFilterSheetVisible((androidx.databinding.ObservableBoolean) variable);
        }
        else if (BR.expandAlpha == variableId) {
            setExpandAlpha((androidx.databinding.ObservableFloat) variable);
        }
        else if (BR.viewModel == variableId) {
            setViewModel((com.urbanairship.debug.event.EventListViewModel) variable);
        }
        else if (BR.collapseAlpha == variableId) {
            setCollapseAlpha((androidx.databinding.ObservableFloat) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setIsFilterSheetVisible(@Nullable androidx.databinding.ObservableBoolean IsFilterSheetVisible) {
        updateRegistration(0, IsFilterSheetVisible);
        this.mIsFilterSheetVisible = IsFilterSheetVisible;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.isFilterSheetVisible);
        super.requestRebind();
    }
    public void setExpandAlpha(@Nullable androidx.databinding.ObservableFloat ExpandAlpha) {
        updateRegistration(2, ExpandAlpha);
        this.mExpandAlpha = ExpandAlpha;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.expandAlpha);
        super.requestRebind();
    }
    public void setViewModel(@Nullable com.urbanairship.debug.event.EventListViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }
    public void setCollapseAlpha(@Nullable androidx.databinding.ObservableFloat CollapseAlpha) {
        updateRegistration(3, CollapseAlpha);
        this.mCollapseAlpha = CollapseAlpha;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.collapseAlpha);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeIsFilterSheetVisible((androidx.databinding.ObservableBoolean) object, fieldId);
            case 1 :
                return onChangeViewModelActiveFiltersLiveData((androidx.lifecycle.MediatorLiveData<java.util.List<com.urbanairship.debug.event.EventFilter>>) object, fieldId);
            case 2 :
                return onChangeExpandAlpha((androidx.databinding.ObservableFloat) object, fieldId);
            case 3 :
                return onChangeCollapseAlpha((androidx.databinding.ObservableFloat) object, fieldId);
        }
        return false;
    }
    private boolean onChangeIsFilterSheetVisible(androidx.databinding.ObservableBoolean IsFilterSheetVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelActiveFiltersLiveData(androidx.lifecycle.MediatorLiveData<java.util.List<com.urbanairship.debug.event.EventFilter>> ViewModelActiveFiltersLiveData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeExpandAlpha(androidx.databinding.ObservableFloat ExpandAlpha, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCollapseAlpha(androidx.databinding.ObservableFloat CollapseAlpha, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
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
        boolean collapseAlphaFloat0f = false;
        java.util.List<com.urbanairship.debug.event.EventFilter> viewModelActiveFiltersLiveDataGetValue = null;
        androidx.databinding.ObservableBoolean isFilterSheetVisible = mIsFilterSheetVisible;
        boolean IsFilterSheetVisible1 = false;
        float collapseAlphaGet = 0f;
        float expandAlphaGet = 0f;
        boolean isFilterSheetVisibleGet = false;
        androidx.lifecycle.MediatorLiveData<java.util.List<com.urbanairship.debug.event.EventFilter>> viewModelActiveFiltersLiveData = null;
        androidx.databinding.ObservableFloat expandAlpha = mExpandAlpha;
        java.lang.String textViewAndroidStringUaActiveFiltersViewModelActiveFiltersLiveDataSize = null;
        java.lang.String viewModelActiveFiltersLiveDataSizeInt0TextViewAndroidStringUaActiveFiltersViewModelActiveFiltersLiveDataSizeTextViewAndroidStringUaNoFilters = null;
        int viewModelActiveFiltersLiveDataSize = 0;
        boolean expandAlphaFloat0f = false;
        com.urbanairship.debug.event.EventListViewModel viewModel = mViewModel;
        androidx.databinding.ObservableFloat collapseAlpha = mCollapseAlpha;
        boolean viewModelActiveFiltersLiveDataSizeInt0 = false;

        if ((dirtyFlags & 0x21L) != 0) {



                if (isFilterSheetVisible != null) {
                    // read isFilterSheetVisible.get()
                    isFilterSheetVisibleGet = isFilterSheetVisible.get();
                }


                // read !isFilterSheetVisible.get()
                IsFilterSheetVisible1 = !isFilterSheetVisibleGet;
        }
        if ((dirtyFlags & 0x24L) != 0) {



                if (expandAlpha != null) {
                    // read expandAlpha.get()
                    expandAlphaGet = expandAlpha.get();
                }


                // read expandAlpha.get() > 0f
                expandAlphaFloat0f = (expandAlphaGet) > (0f);
        }
        if ((dirtyFlags & 0x32L) != 0) {



                if (viewModel != null) {
                    // read viewModel.activeFiltersLiveData
                    viewModelActiveFiltersLiveData = viewModel.getActiveFiltersLiveData();
                }
                updateLiveDataRegistration(1, viewModelActiveFiltersLiveData);


                if (viewModelActiveFiltersLiveData != null) {
                    // read viewModel.activeFiltersLiveData.getValue()
                    viewModelActiveFiltersLiveDataGetValue = viewModelActiveFiltersLiveData.getValue();
                }


                if (viewModelActiveFiltersLiveDataGetValue != null) {
                    // read viewModel.activeFiltersLiveData.getValue().size
                    viewModelActiveFiltersLiveDataSize = viewModelActiveFiltersLiveDataGetValue.size();
                }


                // read viewModel.activeFiltersLiveData.getValue().size > 0
                viewModelActiveFiltersLiveDataSizeInt0 = (viewModelActiveFiltersLiveDataSize) > (0);
            if((dirtyFlags & 0x32L) != 0) {
                if(viewModelActiveFiltersLiveDataSizeInt0) {
                        dirtyFlags |= 0x80L;
                }
                else {
                        dirtyFlags |= 0x40L;
                }
            }
        }
        if ((dirtyFlags & 0x28L) != 0) {



                if (collapseAlpha != null) {
                    // read collapseAlpha.get()
                    collapseAlphaGet = collapseAlpha.get();
                }


                // read collapseAlpha.get() > 0f
                collapseAlphaFloat0f = (collapseAlphaGet) > (0f);
        }
        // batch finished

        if ((dirtyFlags & 0x80L) != 0) {

                // read @android:string/ua_active_filters
                textViewAndroidStringUaActiveFiltersViewModelActiveFiltersLiveDataSize = textView.getResources().getString(R.string.ua_active_filters, viewModelActiveFiltersLiveDataSize);
        }

        if ((dirtyFlags & 0x32L) != 0) {

                // read viewModel.activeFiltersLiveData.getValue().size > 0 ? @android:string/ua_active_filters : @android:string/ua_no_filters
                viewModelActiveFiltersLiveDataSizeInt0TextViewAndroidStringUaActiveFiltersViewModelActiveFiltersLiveDataSizeTextViewAndroidStringUaNoFilters = ((viewModelActiveFiltersLiveDataSizeInt0) ? (textViewAndroidStringUaActiveFiltersViewModelActiveFiltersLiveDataSize) : (textView.getResources().getString(R.string.ua_no_filters)));
        }
        // batch finished
        if ((dirtyFlags & 0x32L) != 0) {
            // api target 1

            this.clear.setEnabled(viewModelActiveFiltersLiveDataSizeInt0);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textView, viewModelActiveFiltersLiveDataSizeInt0TextViewAndroidStringUaActiveFiltersViewModelActiveFiltersLiveDataSizeTextViewAndroidStringUaNoFilters);
        }
        if ((dirtyFlags & 0x20L) != 0) {
            // api target 1

            this.clear.setOnClickListener(mCallback1);
        }
        if ((dirtyFlags & 0x28L) != 0) {
            // api target 11
            if(getBuildSdkInt() >= 11) {

                this.collapse.setAlpha(collapseAlphaGet);
            }
            // api target 1

            this.collapse.setClickable(collapseAlphaFloat0f);
        }
        if ((dirtyFlags & 0x24L) != 0) {
            // api target 11
            if(getBuildSdkInt() >= 11) {

                this.expand.setAlpha(expandAlphaGet);
            }
            // api target 1

            this.expand.setClickable(expandAlphaFloat0f);
        }
        if ((dirtyFlags & 0x21L) != 0) {
            // api target 1

            com.urbanairship.debug.event.BindingAdaptersKt.bindFabVisibility(this.fab, IsFilterSheetVisible1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        com.urbanairship.debug.event.EventListViewModel viewModel = mViewModel;
        // viewModel != null
        boolean viewModelJavaLangObjectNull = false;



        viewModelJavaLangObjectNull = (viewModel) != (null);
        if (viewModelJavaLangObjectNull) {


            viewModel.clearFilters();
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): isFilterSheetVisible
        flag 1 (0x2L): viewModel.activeFiltersLiveData
        flag 2 (0x3L): expandAlpha
        flag 3 (0x4L): collapseAlpha
        flag 4 (0x5L): viewModel
        flag 5 (0x6L): null
        flag 6 (0x7L): viewModel.activeFiltersLiveData.getValue().size > 0 ? @android:string/ua_active_filters : @android:string/ua_no_filters
        flag 7 (0x8L): viewModel.activeFiltersLiveData.getValue().size > 0 ? @android:string/ua_active_filters : @android:string/ua_no_filters
    flag mapping end*/
    //end
}