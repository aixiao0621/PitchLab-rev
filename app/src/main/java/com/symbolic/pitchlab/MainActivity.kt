package com.symbolic.pitchlab

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.symbolic.pitchlab.ui.theme.PitchlabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PitchlabTheme {
                // A surface container using the 'background' color from the theme
//                PitchLabNative.initialize()
                Log.d("azaz", "appPaused")
                synchronized(this){
//                    PitchLabNative.initialize()
//                    PitchLabNative.appPaused()
//                    PitchLabNative.surfaceCreated()
//                    PitchLabNative.getNumNoteNames()
//                    PitchLabNative.getNumViews()
//                    PitchLabNative.getNoteNameTitle(1)
//                    PitchLabNative.drawFrame(1,2,3,4,5,6)
//                    PitchLabNative.getNoteNameTitle(1)
//                    PitchLabNative.getViewName(1)
//                    PitchLabNative.processStreamedSamples(shortArrayOf(1,2),1,2)
//                    PitchLabNative.surfaceChanged(1,2)
//                    PitchLabNative.setAnalysisParameters("test", intArrayOf(1,2), floatArrayOf(2.2F,2.1F),3.3F, 4.4F,5,6)
                }

            }
        }
    }
}
