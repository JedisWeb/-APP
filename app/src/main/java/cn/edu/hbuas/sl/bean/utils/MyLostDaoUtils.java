package cn.edu.hbuas.sl.bean.utils;

import android.content.Context;

import java.util.List;

import cn.edu.hbuas.sl.bean.DaoManager;
import cn.edu.hbuas.sl.bean.MyLost;
import cn.edu.hbuas.sl.bean.MyLostDao;

public class MyLostDaoUtils {

    /**
     * 添加数据至数据库
     *
     * @param context
     * @param myLost
     */
    public static void insertData(Context context, MyLost myLost) {
        DaoManager.getDaoSession(context).getMyLostDao().insert(myLost);
    }


    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertMultData(Context context, List<MyLost> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        DaoManager.getDaoSession(context).getMyLostDao().insertInTx(list);
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param myLost
     */
    public static void saveData(Context context, MyLost myLost) {
        DaoManager.getDaoSession(context).getMyLostDao().save(myLost);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param myLost  删除具体内容
     */
    public static void deleteData(Context context, MyLost myLost) {
        DaoManager.getDaoSession(context).getMyLostDao().delete(myLost);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        DaoManager.getDaoSession(context).getMyLostDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        DaoManager.getDaoSession(context).getMyLostDao().deleteAll();
    }

    /**
     * 更新数据库
     *
     * @param context
     * @param myLost
     */
    public static void updateData(Context context, MyLost myLost) {
        DaoManager.getDaoSession(context).getMyLostDao().update(myLost);
    }


    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public static MyLost queryMyLostById(Context context, long key) {
        return DaoManager.getDaoSession(context).getMyLostDao().load(key);
    }


    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<MyLost> queryAll(Context context) {
        return DaoManager.getDaoSession(context).getMyLostDao().loadAll();
    }


    /**
     * 分页加载
     *
     * @param context
     * @param pageSize 当前第几页(程序中动态修改pageSize的值即可)
     * @param pageNum  每页显示多少个
     * @return
     */
    public static List<MyLost> queryPaging(int pageSize, int pageNum, Context context) {
        MyLostDao myLostDao = DaoManager.getDaoSession(context).getMyLostDao();
        List<MyLost> listMsg = myLostDao.queryBuilder()
                .offset(pageSize * pageNum).limit(pageNum).list();
        return listMsg;
    }

}
