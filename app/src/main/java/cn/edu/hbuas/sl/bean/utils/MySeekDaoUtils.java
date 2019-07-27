package cn.edu.hbuas.sl.bean.utils;

import android.content.Context;

import java.util.List;

import cn.edu.hbuas.sl.bean.DaoManager;
import cn.edu.hbuas.sl.bean.MySeek;
import cn.edu.hbuas.sl.bean.MySeekDao;

public class MySeekDaoUtils {

    /**
     * 添加数据至数据库
     *
     * @param context
     * @param mySeek
     */
    public static void insertData(Context context, MySeek mySeek) {
        DaoManager.getDaoSession(context).getMySeekDao().insert(mySeek);
    }


    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertMultData(Context context, List<MySeek> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        DaoManager.getDaoSession(context).getMySeekDao().insertInTx(list);
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param mySeek
     */
    public static void saveData(Context context, MySeek mySeek) {
        DaoManager.getDaoSession(context).getMySeekDao().save(mySeek);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param mySeek  删除具体内容
     */
    public static void deleteData(Context context, MySeek mySeek) {
        DaoManager.getDaoSession(context).getMySeekDao().delete(mySeek);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        DaoManager.getDaoSession(context).getMySeekDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        DaoManager.getDaoSession(context).getMySeekDao().deleteAll();
    }

    /**
     * 更新数据库
     *
     * @param context
     * @param mySeek
     */
    public static void updateData(Context context, MySeek mySeek) {
        DaoManager.getDaoSession(context).getMySeekDao().update(mySeek);
    }


    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public static MySeek queryMySeekById(Context context, long key) {
        return DaoManager.getDaoSession(context).getMySeekDao().load(key);
    }


    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<MySeek> queryAll(Context context) {
        return DaoManager.getDaoSession(context).getMySeekDao().loadAll();
    }


    /**
     * 分页加载
     *
     * @param context
     * @param pageSize 当前第几页(程序中动态修改pageSize的值即可)
     * @param pageNum  每页显示多少个
     * @return
     */
    public static List<MySeek> queryPaging(int pageSize, int pageNum, Context context) {
        MySeekDao mySeekDao = DaoManager.getDaoSession(context).getMySeekDao();
        List<MySeek> listMsg = mySeekDao.queryBuilder()
                .offset(pageSize * pageNum).limit(pageNum).list();
        return listMsg;
    }

}
