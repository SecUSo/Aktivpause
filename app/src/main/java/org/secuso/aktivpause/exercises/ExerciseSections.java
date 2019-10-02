package org.secuso.aktivpause.exercises;

import android.content.Context;
import android.support.annotation.StringRes;

import org.secuso.aktivpause.R;

import java.util.Arrays;
import java.util.List;

/**
 * All available sections.
 * @author Christopher Beckmann
 * @version 2.0
 * @since 06.09.2017
 * created 06.09.2017
 */
public enum ExerciseSections {
    Neck(R.string.exercise_section_neck),
    Shoulder(R.string.exercise_section_shoulder),
    Arms(R.string.exercise_section_arms),
    Back(R.string.exercise_section_back),
    Face(R.string.exercise_section_face),
    Body(R.string.exercise_section_body),
    Legs(R.string.exercise_section_legs),
    Other(R.string.exercise_section_other);


    private final @StringRes int nameResId;

    ExerciseSections(@StringRes int resId) {
        this.nameResId = resId;
    }

    public String getLocalName(Context context) {
        return context.getString(nameResId);
    }
    public static List<ExerciseSections> getSectionList() { return Arrays.asList(Neck, Shoulder, Arms, Back, Face, Body, Legs, Other ); }
}
