//package com.hidden.client.ui.custom
//
//import android.view.View
//import androidx.annotation.NonNull
//import androidx.annotation.Nullable
//import androidx.coordinatorlayout.widget.CoordinatorLayout
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import android.icu.lang.UCharacter.GraphemeClusterBreak.V
//import android.media.AudioRecord.MetricsConstants.SOURCE
//import androidx.annotation.IntDef
//import android.icu.lang.UCharacter.GraphemeClusterBreak.V
//import com.google.android.material.bottomsheet.BottomSheetBehavior.*
//import java.lang.annotation.RetentionPolicy
//
//
//class TopSheetBehavior<V : View>: CoordinatorLayout.Behavior<V>() {
//
//    abstract class TopSheetCallback {
//        /**
//         * Called when the bottom sheet changes its state.
//         *
//         * @param bottomSheet The bottom sheet view.
//         * @param newState The new state. This will be one of {@link #STATE_DRAGGING},
//         * {@link #STATE_SETTLING}, {@link #STATE_EXPANDED},
//         * {@link #STATE_COLLAPSED}, or {@link #STATE_HIDDEN}.
//         */
//        abstract fun onStateChanged(@NonNull bottomSheet:View, @BottomSheetBehavior.State newState:Int)
//        /**
//         * Called when the bottom sheet is being dragged.
//         *
//         * @param bottomSheet The bottom sheet view.
//         * @param slideOffset The new offset of this bottom sheet within its range, from 0 to 1
//         * when it is moving upward, and from 0 to -1 when it moving downward.
//         * @param isOpening detect showing
//         */
//        abstract fun onSlide(@NonNull bottomSheet:View, slideOffset:Float, @Nullable isOpening:Boolean)
//    }
//
//    /**
//     * The bottom sheet is dragging.
//     */
//    val STATE_DRAGGING = 1
//
//    /**
//     * The bottom sheet is settling.
//     */
//    val STATE_SETTLING = 2
//
//    /**
//     * The bottom sheet is expanded.
//     */
//    val STATE_EXPANDED = 3
//
//    /**
//     * The bottom sheet is collapsed.
//     */
//    val STATE_COLLAPSED = 4
//
//    /**
//     * The bottom sheet is hidden.
//     */
//    val STATE_HIDDEN = 5
//
//    /**
//     * @hide
//     */
//    @IntDef(STATE_EXPANDED, STATE_COLLAPSED, STATE_DRAGGING, STATE_SETTLING, STATE_HIDDEN)
//    @Retention(RetentionPolicy.SOURCE)
//    annotation class State {
//
//    }
//}