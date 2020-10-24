package androidx.room.util;

import android.database.Cursor;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class ViewInfo {

    /* renamed from: name  reason: collision with root package name */
    public final String f5name;
    public final String sql;

    public ViewInfo(String str, String str2) {
        this.f5name = str;
        this.sql = str2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ViewInfo viewInfo = (ViewInfo) obj;
        String str = this.f5name;
        if (str == null ? viewInfo.f5name == null : str.equals(viewInfo.f5name)) {
            String str2 = this.sql;
            if (str2 != null) {
                if (str2.equals(viewInfo.sql)) {
                    return true;
                }
            } else if (viewInfo.sql == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String str = this.f5name;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.sql;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "ViewInfo{name='" + this.f5name + '\'' + ", sql='" + this.sql + '\'' + '}';
    }

    public static ViewInfo read(SupportSQLiteDatabase supportSQLiteDatabase, String str) {
        Cursor query = supportSQLiteDatabase.query("SELECT name, sql FROM sqlite_master WHERE type = 'view' AND name = '" + str + "'");
        try {
            if (query.moveToFirst()) {
                return new ViewInfo(query.getString(0), query.getString(1));
            }
            ViewInfo viewInfo = new ViewInfo(str, (String) null);
            query.close();
            return viewInfo;
        } finally {
            query.close();
        }
    }
}
