package cn.edu.hbuas.sl.bean.utils;

import android.content.Context;

import java.util.List;

import cn.edu.hbuas.sl.bean.DaoManager;
import cn.edu.hbuas.sl.bean.Feedback;
import cn.edu.hbuas.sl.bean.FeedbackDao;

public class FeedbackDaoUtils {

    /**
     * 添加数据至数据库
     *
     * @param context
     * @param feedback
     */
    public static void insertData(Context context, Feedback feedback) {
        DaoManager.getDaoSession(context).getFeedbackDao().insert(feedback);
    }


    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertMultData(Context context, List<Feedback> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        DaoManager.getDaoSession(context).getFeedbackDao().insertInTx(list);
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param feedback
     */
    public static void saveData(Context context, Feedback feedback) {
        DaoManager.getDaoSession(context).getFeedbackDao().save(feedback);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param feedback 删除具体内容
     */
    public static void deleteData(Context context, Feedback feedback) {
        DaoManager.getDaoSession(context).getFeedbackDao().delete(feedback);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        DaoManager.getDaoSession(context).getFeedbackDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        DaoManager.getDaoSession(context).getFeedbackDao().deleteAll();
    }

    /**
     * 更新数据库
     *
     * @param context
     * @param feedback
     */
    public static void updateData(Context context, Feedback feedback) {
        DaoManager.getDaoSession(context).getFeedbackDao().update(feedback);
    }


    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public static Feedback queryFeedbackById(Context context, long key) {
        return DaoManager.getDaoSession(context).getFeedbackDao().load(key);
    }


    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<Feedback> queryAll(Context context) {
        return DaoManager.getDaoSession(context).getFeedbackDao().loadAll();
    }


    /**
     * 分页加载
     *
     * @param context
     * @param pageSize 当前第几页(程序中动态修改pageSize的值即可)
     * @param pageNum  每页显示多少个
     * @return
     */
    public static List<Feedback> queryPaging(int pageSize, int pageNum, Context context) {
        FeedbackDao feedbackDao = DaoManager.getDaoSession(context).getFeedbackDao();
        List<Feedback> listMsg = feedbackDao.queryBuilder()
                .offset(pageSize * pageNum).limit(pageNum).list();
        return listMsg;
    }

}
