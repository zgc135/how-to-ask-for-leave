package androidx.sqlite.db.framework;

import androidx.sqlite.db.SupportSQLiteOpenHelper;

public final class FrameworkSQLiteOpenHelperFactory implements SupportSQLiteOpenHelper.Factory {
    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        return new FrameworkSQLiteOpenHelper(configuration.context, configuration.f6name, configuration.callback);
    }
}
