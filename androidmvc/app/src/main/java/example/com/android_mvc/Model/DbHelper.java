package example.com.android_mvc.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ChenSiyuan on 2018/12/7.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "records.db";
    public static final String TABLE_NAME = "records";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME +"(date integer primary key,current integer,target integer)";
        //execSQL函数用于执行SQL语句
        db.execSQL(sql);
    }
    // 当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
