package com.xuhao.myapp.utills;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.xuhao.myapp.Bean.BossCourseInfoBean;
import com.xuhao.myapp.Bean.CourseInfoBean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HBDatabaseDao {

    private DataBaseHelper helper;
    private Context mContext;

    public HBDatabaseDao(Context context) {
        helper = new DataBaseHelper(context);
        mContext = context;
    }

    /**
     * 添加数据
     * @param courseId 课程ID
     */
    public void add(int courseId){
        if (!find(courseId)) {
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("courseId", courseId);
            db.insert("historyBrowseId", null, values);
            db.close();
        }
    }

    /**
     * 查询数据库中是否有相关数据
     * @param courseId 课程ID
     * @return 布尔值
     */
    public Boolean find(int courseId){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from historyBrowseId where courseId = ?",new String[]{String.valueOf(courseId)});
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 删除数据
     * @param courseId 课程ID
     */
    public void delete(int courseId){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("historyBrowseId","courseId = ?",new String[]{String.valueOf(courseId)});
        db.close();
    }

    /**
     * 查询全部数据
     */
    public List<Integer> query(){
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Integer> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query("historyBrowseId",null,null,null,null,null,null);
        if (cursor.moveToNext()){
            do {
                int courseId = cursor.getInt(cursor.getColumnIndex("courseId"));
                list.add(courseId);
            }while (cursor.moveToNext());
        }else {
            Toast.makeText(mContext,"您的历史浏览记录是空的哟！",Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return list;
    }
}
