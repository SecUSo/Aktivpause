package org.secuso.aktivpause.database.data;


import android.content.Context;
import androidx.annotation.DrawableRes;

import org.secuso.aktivpause.exercises.ExerciseSections;

/**
 * Model for an exercise.
 * @author Christopher Beckmann
 * @version 2.0
 */
public class Exercise {
    private int id;
    private int localId;
    private String duration = "30";
    private String section = "";
    private String execution = "";
    private String description = "";
    private String name = "";
    private String imageID;
    private String language = "";

    public Exercise() {
        this.localId = -1;
        this.id = -1;
        this.imageID = "-1";
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getLocalId() { return localId; }
    public void setLocalId(int exercise_id) { this.localId = exercise_id; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getName() {  return name; }
    public void setName(String name) { this.name = name; }

    public String getExecution() {
        return execution;
    }
    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String getImageID() {
        return imageID;
    }
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }
    public @DrawableRes int[] getImageResIds(Context context) {
        String[] imageIDSplit = imageID.split(",");

        int[] result = new int[imageIDSplit.length];

        for(int i = 0; i < result.length; ++i) {
            result[i] = context.getResources().getIdentifier(
                    "image_" + imageIDSplit[i],
                    "drawable",
                    context.getPackageName());
        }

        return result;
    }

    public String getDurationString() {
        return duration;
    }

    public int[] getDurations() {
        String[] durationsSplit = this.duration.split(",");
        int[] result = new int[durationsSplit.length];

        for(int i = 0; i < durationsSplit.length; i++) {
            result[i] = Integer.parseInt(durationsSplit[i]);
        }

        return result;
    }

    public String getSection(Context context) {
        String section = this.section;

        for(ExerciseSections es : ExerciseSections.getSectionList()) {
            section = section.replaceAll(es.name(), es.getLocalName(context));
        }

        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();

        sb.append(id)
                .append(localId)
                .append(section)
                .append(execution)
                .append(description)
                .append(name)
                .append(imageID)
                .append(language)
                .append(duration);

        return sb.toString().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Exercise)) return false;
        Exercise other = (Exercise) object;

        return this.id == other.id
            && this.localId == other.localId
            && this.section.equals(other.section)
            && this.execution.equals(other.execution)
            && this.description.equals(other.description)
            && this.name.equals(other.name)
            && this.imageID.equals(other.imageID)
            && this.language.equals(other.language)
            && this.duration.equals(other.duration);
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
