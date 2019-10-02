package org.secuso.aktivpause.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.secuso.aktivpause.R;
import org.secuso.aktivpause.database.data.Exercise;

/**
 * @author Christopher Beckmann
 * @version 2.0
 * Use {@link ExerciseDialog#showExerciseDialog(Context, Exercise)} to show the dialog.
 */
public final class ExerciseDialog {

    /**
     * Use {@link ExerciseDialog#showExerciseDialog(Context, Exercise)} to show the dialog.
     */
    private ExerciseDialog() {}

    public static void showExerciseDialog(@NonNull final Context context, @NonNull final Exercise e) {
        showExerciseDialog(context, e, null);
    }

    public static void showExerciseDialog(@NonNull final Context context, @NonNull final Exercise e, DialogInterface.OnDismissListener onDismissListener) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(FragmentActivity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_exercise, null);

        final TextView executionText = (TextView) v.findViewById(R.id.execution);
        final TextView descriptionText = (TextView) v.findViewById(R.id.description);
        final ImageView exerciseImage = (ImageView) v.findViewById(R.id.exercise_image);
        final TextView sectionText = (TextView) v.findViewById(R.id.section);
        final TextView exerciseName = (TextView) v.findViewById(R.id.name);

        if(executionText != null)
            executionText.setText(e.getExecution());
        if(exerciseName != null)
            exerciseName.setText(e.getName());
        if(descriptionText != null)
            descriptionText.setText(e.getDescription());
        if(sectionText != null)
            sectionText.setText(e.getSection(context));
        if(exerciseImage != null) {
            exerciseImage.setOnClickListener(new View.OnClickListener() {
                int currentlyShownExercise = 0;
                @Override
                public void onClick(View v) {
                    int[] resIds = e.getImageResIds(context);

                    if(resIds.length > 0) {
                        currentlyShownExercise = (currentlyShownExercise + 1) % resIds.length;
                        exerciseImage.setImageResource(resIds[currentlyShownExercise]);
                    }
                }
            });
            exerciseImage.setImageResource(e.getImageResIds(context)[0]);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v);
        builder.setOnDismissListener(onDismissListener);
        builder.show();
    }

}
