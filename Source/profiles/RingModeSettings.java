package cyanogenmod.profiles;

import android.content.Context;
import android.media.AudioManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;

public final class RingModeSettings implements Parcelable {
    public static final Creator CREATOR = new C0866e();
    public static final String RING_MODE_MUTE = "mute";
    public static final String RING_MODE_NORMAL = "normal";
    public static final String RING_MODE_VIBRATE = "vibrate";
    private String f4896a;
    private boolean f4897b;
    private boolean f4898c;

    public RingModeSettings(Parcel parcel) {
        readFromParcel(parcel);
    }

    public RingModeSettings() {
        this(RING_MODE_NORMAL, false);
    }

    public RingModeSettings(String str, boolean z) {
        this.f4896a = str;
        this.f4897b = z;
        this.f4898c = false;
    }

    public final String getValue() {
        return this.f4896a;
    }

    public final void setValue(String str) {
        this.f4896a = str;
        this.f4898c = true;
    }

    public final void setOverride(boolean z) {
        this.f4897b = z;
        this.f4898c = true;
    }

    public final boolean isOverride() {
        return this.f4897b;
    }

    public final boolean isDirty() {
        return this.f4898c;
    }

    public final void processOverride(Context context) {
        if (isOverride()) {
            int i = this.f4896a.equals(RING_MODE_MUTE) ? 0 : this.f4896a.equals("vibrate") ? 1 : 2;
            ((AudioManager) context.getSystemService("audio")).setRingerModeInternal(i);
        }
    }

    public static RingModeSettings fromXml(XmlPullParser xmlPullParser, Context context) {
        int next = xmlPullParser.next();
        RingModeSettings ringModeSettings = new RingModeSettings();
        while (true) {
            if ((next == 3 || next == 1) && xmlPullParser.getName().equals("ringModeDescriptor")) {
                return ringModeSettings;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("value")) {
                    ringModeSettings.f4896a = xmlPullParser.nextText();
                } else if (name.equals("override")) {
                    ringModeSettings.f4897b = Boolean.parseBoolean(xmlPullParser.nextText());
                }
            } else if (next == 1) {
                throw new IOException("Premature end of file while parsing ring mode settings");
            }
            next = xmlPullParser.next();
        }
    }

    public final void getXmlString(StringBuilder stringBuilder, Context context) {
        stringBuilder.append("<ringModeDescriptor>\n<value>");
        stringBuilder.append(this.f4896a);
        stringBuilder.append("</value>\n<override>");
        stringBuilder.append(this.f4897b);
        stringBuilder.append("</override>\n</ringModeDescriptor>\n");
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeInt(2);
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
        parcel.writeInt(this.f4897b ? 1 : 0);
        parcel.writeString(this.f4896a);
        if (!this.f4898c) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        int dataPosition3 = parcel.dataPosition() - dataPosition2;
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition3);
        parcel.setDataPosition(dataPosition3 + dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        boolean z = true;
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int dataPosition = parcel.dataPosition();
        if (readInt >= 2) {
            this.f4897b = parcel.readInt() != 0;
            this.f4896a = parcel.readString();
            if (parcel.readInt() == 0) {
                z = false;
            }
            this.f4898c = z;
        }
        parcel.setDataPosition(dataPosition + readInt2);
    }
}
