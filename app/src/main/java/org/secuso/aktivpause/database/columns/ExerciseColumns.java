package org.secuso.aktivpause.database.columns;

import android.database.Cursor;

import org.secuso.aktivpause.database.data.Exercise;

/**
 * Database representation of an exercise.
 * @author Christopher Beckmann
 * @version 2.0
 * @since 23.08.2017
 * created 23.08.2017
 */
public final class ExerciseColumns {

    public static final String TABLE_NAME = "exercises";

    public static final String _ID = "exercise_id";
    public static final String SECTION = "section";
    public static final String IMAGE_ID = "image_id";
    public static final String DURATION = "duration";

    public static final String[] PROJECTION = {
            _ID,
            SECTION,
            IMAGE_ID,
            DURATION,
    };

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static Exercise fromCursor(Cursor c) {
        Exercise e = ExerciseLocalColumns.fromCursor(c);

        e.setId(c.getInt(c.getColumnIndexOrThrow(ExerciseColumns._ID)));
        e.setSection(c.getString(c.getColumnIndexOrThrow(ExerciseColumns.SECTION)));
        e.setImageID(c.getString(c.getColumnIndexOrThrow(ExerciseColumns.IMAGE_ID)));
        e.setDuration(c.getString(c.getColumnIndexOrThrow(ExerciseColumns.DURATION)));

        return e;
    }

    private ExerciseColumns() {}
}
