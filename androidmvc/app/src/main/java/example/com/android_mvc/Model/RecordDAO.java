package example.com.android_mvc.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by ChenSiyuan on 2018/12/7.
 */

public class RecordDAO {
    private static final String TAG = "RecordDAO";

    // 列定义
    private final String[] ORDER_COLUMNS = new String[] {"date", "current","target"};

    private Context context;
    private DbHelper dbHelper;

    public RecordDAO(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
//        simulation();

    }

    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(dbHelper.TABLE_NAME, new String[]{"COUNT(Id)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public boolean insert(int date,int current,int target){
        SQLiteDatabase db = null;

        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("date", date);
            contentValues.put("current", current);
            contentValues.put("target", target);
            db.insertOrThrow(dbHelper.TABLE_NAME, null, contentValues);

            db.setTransactionSuccessful();
            return true;
        }catch (SQLiteConstraintException e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(TAG, "", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 删除数据
     */
    public boolean delete(int date) {
        SQLiteDatabase db = null;

        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();

            // delete from Orders where Id = 7
            db.delete(dbHelper.TABLE_NAME, "date = ?", new String[]{String.valueOf(date)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     *  更新数据
     */
    public boolean update(int date,int current,int target){
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("current", current);
            contentValues.put("target", target);
            db.update(dbHelper.TABLE_NAME,
                    contentValues,
                    "date = ?",
                    new String[]{String.valueOf(date)});
            db.setTransactionSuccessful();
            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        return false;
    }

    /**
     *  查询数据
     */
    public  int[] getRecordByDay(int date){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();

            cursor = db.query(dbHelper.TABLE_NAME,
                    ORDER_COLUMNS,
                    "date = ?",
                    new String[]{String.valueOf(date)},
                    null, null, null);

            if (cursor.getCount() > 0) {
                cursor.moveToNext();
                int[]  list = new int[2];
                list[0] = cursor.getInt(cursor.getColumnIndex("current"));
                list[1] = cursor.getInt(cursor.getColumnIndex("target"));
                return list;
            }
            else {
            }
        }
        catch (Exception e) {
            Toast.makeText(context, "查询出错", Toast.LENGTH_SHORT).show();
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        int[] defaultRet = {0,0};

        return defaultRet;
    }
    public  int getRankByDay(int date){
        int rank;
        int[] record = getRecordByDay(date);
        if(record[1]==0){
            rank = 0;
        }
        else if(record[0]/record[1] <= 0.3){
            rank = 1;
        }
        else if(record[0]/record[1] <= 0.6){
            rank = 2;
        }
        else if(record[0]/record[1] <= 0.9){
            rank = 3;
        }
        else{
            rank = 4;
        }

        return rank;
    }

    private void simulation(){

        Random random = new Random();
        for (int i = 2018111;i<=2018115;i++){
            insert(i,random.nextInt(9000)%(9000-1000+1)+1000,random.nextInt(9000)%(9000-1000+1)+1000);
        }
//        for (int i = 20181110;i<=20181130;i++){
//            insert(i,random.nextInt(9000)%(9000-1000+1)+1000,random.nextInt(9000)%(9000-1000+1)+1000);
//        }
    }
}
