package net.gan.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

//final так как мы не будем изменять сущность а будем копировать из неё данные и создавать новую
public class NoteEntity implements Parcelable {
    String id;
    String name;
    long dateCreation;
    String noteDescription;

    public NoteEntity(String id, String name, long dateCreation, String noteDescription) {
        this.id = id;
        this.name = name;
        this.dateCreation = dateCreation;
        this.noteDescription = noteDescription;
    }

    public NoteEntity() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getDateCreation() {
        return dateCreation;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    protected NoteEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        dateCreation = in.readLong();
        noteDescription = in.readString();
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    public static String idGeneration() {
        return UUID.randomUUID().toString();
    }

    public static long getCurrentDate() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String convertLongToDate(Long dateLong) {
        Date date = new Date(dateLong);
        SimpleDateFormat df2 = new SimpleDateFormat("EEEE dd MMM yyyy", Locale.getDefault());
        return df2.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeLong(dateCreation);
        dest.writeString(noteDescription);
    }


}
