package cn.edu.hbuas.sl.bean.utils;

import android.content.Context;

import java.util.List;

import cn.edu.hbuas.sl.bean.DaoManager;
import cn.edu.hbuas.sl.bean.User;
import cn.edu.hbuas.sl.bean.UserDao;

public class UserDaoUtils {

    /**
     * 添加数据至数据库
     *
     * @param context
     * @param user
     */
    public static void insertData(Context context, User user) {
        DaoManager.getDaoSession(context).getUserDao().insert(user);
    }


    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertMultData(Context context, List<User> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        DaoManager.getDaoSession(context).getUserDao().insertInTx(list);
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param user
     */
    public static void saveData(Context context, User user) {
        DaoManager.getDaoSession(context).getUserDao().save(user);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param user    删除具体内容
     */
    public static void deleteData(Context context, User user) {
        DaoManager.getDaoSession(context).getUserDao().delete(user);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        DaoManager.getDaoSession(context).getUserDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        DaoManager.getDaoSession(context).getUserDao().deleteAll();
    }

    /**
     * 更新数据库
     *
     * @param context
     * @param user
     */
    public static void updateData(Context context, User user) {
        DaoManager.getDaoSession(context).getUserDao().update(user);
    }


    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public static User queryUserById(Context context, long key) {
        return DaoManager.getDaoSession(context).getUserDao().load(key);
    }


    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<User> queryAll(Context context) {
        return DaoManager.getDaoSession(context).getUserDao().loadAll();
    }


    /**
     * 分页加载
     *
     * @param context
     * @param pageSize 当前第几页(程序中动态修改pageSize的值即可)
     * @param pageNum  每页显示多少个
     * @return
     */
    public static List<User> queryPaging(int pageSize, int pageNum, Context context) {
        UserDao userDao = DaoManager.getDaoSession(context).getUserDao();
        List<User> listMsg = userDao.queryBuilder()
                .offset(pageSize * pageNum).limit(pageNum).list();
        return listMsg;
    }

}
