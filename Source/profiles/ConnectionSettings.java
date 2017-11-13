package cyanogenmod.profiles;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.net.wimax.WimaxHelper;
import android.nfc.NfcAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;

public final class ConnectionSettings implements Parcelable {
    public static final Creator CREATOR = new C0864c();
    public static final int PROFILE_CONNECTION_2G3G4G = 9;
    public static final int PROFILE_CONNECTION_BLUETOOTH = 7;
    public static final int PROFILE_CONNECTION_GPS = 4;
    public static final int PROFILE_CONNECTION_MOBILEDATA = 0;
    public static final int PROFILE_CONNECTION_NFC = 8;
    public static final int PROFILE_CONNECTION_SYNC = 5;
    public static final int PROFILE_CONNECTION_WIFI = 1;
    public static final int PROFILE_CONNECTION_WIFIAP = 2;
    public static final int PROFILE_CONNECTION_WIMAX = 3;
    private int f4890a;
    private int f4891b;
    private boolean f4892c;
    private boolean f4893d;

    public class BooleanState {
        public static final int STATE_DISALED = 0;
        public static final int STATE_ENABLED = 1;
    }

    public ConnectionSettings(Parcel parcel) {
        readFromParcel(parcel);
    }

    public ConnectionSettings(int i) {
        this(i, 0, false);
    }

    public ConnectionSettings(int i, int i2, boolean z) {
        this.f4890a = i;
        this.f4891b = i2;
        this.f4892c = z;
        this.f4893d = false;
    }

    public final int getConnectionId() {
        return this.f4890a;
    }

    public final int getValue() {
        return this.f4891b;
    }

    public final void setValue(int i) {
        this.f4891b = i;
        this.f4893d = true;
    }

    public final void setOverride(boolean z) {
        this.f4892c = z;
        this.f4893d = true;
    }

    public final boolean isOverride() {
        return this.f4892c;
    }

    public final boolean isDirty() {
        return this.f4893d;
    }

    public final void processOverride(Context context) {
        NfcAdapter nfcAdapter;
        boolean z = false;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        try {
            nfcAdapter = NfcAdapter.getNfcAdapter(context);
        } catch (UnsupportedOperationException e) {
            nfcAdapter = null;
        }
        boolean z2 = getValue() == 1;
        int i;
        switch (getConnectionId()) {
            case 0:
                if (z2 != telephonyManager.getDataEnabled()) {
                    int phoneCount = telephonyManager.getPhoneCount();
                    for (int i2 = 0; i2 < phoneCount; i2++) {
                        ContentResolver contentResolver = context.getContentResolver();
                        String str = "mobile_data" + i2;
                        if (z2) {
                            i = 1;
                        } else {
                            i = 0;
                        }
                        Global.putInt(contentResolver, str, i);
                        telephonyManager.setDataEnabled(SubscriptionManager.getSubId(i2)[0], z2);
                    }
                    return;
                }
                return;
            case 1:
                i = wifiManager.getWifiApState();
                if (wifiManager.isWifiEnabled() != z2) {
                    if ((z2 && i == 12) || i == 13) {
                        wifiManager.setWifiApEnabled(null, false);
                    }
                    wifiManager.setWifiEnabled(z2);
                    return;
                }
                return;
            case 2:
                i = wifiManager.getWifiState();
                if (wifiManager.isWifiApEnabled() != z2) {
                    if ((z2 && i == 2) || i == 3) {
                        wifiManager.setWifiEnabled(false);
                    }
                    wifiManager.setWifiApEnabled(null, z2);
                    return;
                }
                return;
            case 3:
                if (WimaxHelper.isWimaxSupported(context) && WimaxHelper.isWimaxEnabled(context) != z2) {
                    WimaxHelper.setWimaxEnabled(context, z2);
                    return;
                }
                return;
            case 4:
                if (locationManager.isProviderEnabled("gps") != z2) {
                    Secure.setLocationProviderEnabled(context.getContentResolver(), "gps", z2);
                    return;
                }
                return;
            case 5:
                if (z2 != ContentResolver.getMasterSyncAutomatically()) {
                    ContentResolver.setMasterSyncAutomatically(z2);
                    return;
                }
                return;
            case 7:
                i = defaultAdapter.getState();
                if (z2 && (i == 10 || i == 13)) {
                    defaultAdapter.enable();
                    return;
                } else if (!z2) {
                    if (i == 12 || i == 11) {
                        defaultAdapter.disable();
                        return;
                    }
                    return;
                } else {
                    return;
                }
            case 8:
                if (nfcAdapter != null) {
                    i = nfcAdapter.getAdapterState();
                    if (i == 3 || i == 2) {
                        z = true;
                    }
                    if (z == z2) {
                        return;
                    }
                    if (z2) {
                        nfcAdapter.enable();
                        return;
                    } else if (!z2 && i != 4) {
                        nfcAdapter.disable();
                        return;
                    } else {
                        return;
                    }
                }
                return;
            case 9:
                Intent intent = new Intent("com.android.internal.telephony.MODIFY_NETWORK_MODE");
                switch (getValue()) {
                    case 0:
                        intent.putExtra("networkMode", 1);
                        break;
                    case 1:
                        intent.putExtra("networkMode", 2);
                        break;
                    case 2:
                        intent.putExtra("networkMode", 11);
                        break;
                    case 3:
                        intent.putExtra("networkMode", 0);
                        break;
                    case 4:
                        intent.putExtra("networkMode", 9);
                        break;
                    default:
                        return;
                }
                context.sendBroadcast(intent);
                return;
            default:
                return;
        }
    }

    public static ConnectionSettings fromXml(XmlPullParser xmlPullParser, Context context) {
        int next = xmlPullParser.next();
        ConnectionSettings connectionSettings = new ConnectionSettings(0);
        while (true) {
            if (next == 3 && xmlPullParser.getName().equals("connectionDescriptor")) {
                return connectionSettings;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("connectionId")) {
                    connectionSettings.f4890a = Integer.parseInt(xmlPullParser.nextText());
                } else if (name.equals("value")) {
                    connectionSettings.f4891b = Integer.parseInt(xmlPullParser.nextText());
                } else if (name.equals("override")) {
                    connectionSettings.f4892c = Boolean.parseBoolean(xmlPullParser.nextText());
                }
            } else if (next == 1) {
                throw new IOException("Premature end of file while parsing connection settings");
            }
            next = xmlPullParser.next();
        }
    }

    public final void getXmlString(StringBuilder stringBuilder, Context context) {
        stringBuilder.append("<connectionDescriptor>\n<connectionId>");
        stringBuilder.append(this.f4890a);
        stringBuilder.append("</connectionId>\n<value>");
        stringBuilder.append(this.f4891b);
        stringBuilder.append("</value>\n<override>");
        stringBuilder.append(this.f4892c);
        stringBuilder.append("</override>\n</connectionDescriptor>\n");
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
        parcel.writeInt(this.f4890a);
        parcel.writeInt(this.f4892c ? 1 : 0);
        parcel.writeInt(this.f4891b);
        if (!this.f4893d) {
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
            this.f4890a = parcel.readInt();
            this.f4892c = parcel.readInt() != 0;
            this.f4891b = parcel.readInt();
            if (parcel.readInt() == 0) {
                z = false;
            }
            this.f4893d = z;
        }
        parcel.setDataPosition(dataPosition + readInt2);
    }
}
