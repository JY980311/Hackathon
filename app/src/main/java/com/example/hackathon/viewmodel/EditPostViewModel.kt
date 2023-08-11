package com.example.hackathon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class EditPostViewModel(val currentPostId: String): ViewModel(){

        companion object {
            const val TAG = "EditPostViewModel"
        }

        init {
            Log.d(TAG, "EditPostViewModel : init/ postId: $currentPostId")
        }

}