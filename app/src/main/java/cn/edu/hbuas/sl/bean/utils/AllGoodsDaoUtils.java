package cn.edu.hbuas.sl.bean.utils;

import android.content.Context;

import java.util.List;

import cn.edu.hbuas.sl.bean.AllGoods;
import cn.edu.hbuas.sl.bean.AllGoodsDao;
import cn.edu.hbuas.sl.bean.DaoManager;

public class AllGoodsDaoUtils {

    /**
     * 添加数据至数据库
     *
     * @param context
     * @param allGoods
     */
    public static void insertData(Context context, AllGoods allGoods) {
        DaoManager.getDaoSession(context).getAllGoodsDao().insert(allGoods);
    }


    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertMultData(Context context, List<AllGoods> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        DaoManager.getDaoSession(context).getAllGoodsDao().insertInTx(list);
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param allGoods
     */
    public static void saveData(Context context, AllGoods allGoods) {
        DaoManager.getDaoSession(context).getAllGoodsDao().save(allGoods);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param allGoods 删除具体内容
     */
    public static void deleteData(Context context, AllGoods allGoods) {
        DaoManager.getDaoSession(context).getAllGoodsDao().delete(allGoods);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        DaoManager.getDaoSession(context).getAllGoodsDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        DaoManager.getDaoSession(context).getAllGoodsDao().deleteAll();
    }

    /**
     * 更新数据库
     *
     * @param context
     * @param allGoods
     */
    public static void updateData(Context context, AllGoods allGoods) {
        DaoManager.getDaoSession(context).getAllGoodsDao().update(allGoods);
    }


    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public static AllGoods queryAllGoodsById(Context context, long key) {
        return DaoManager.getDaoSession(context).getAllGoodsDao().load(key);
    }


    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<AllGoods> queryAll(Context context) {
        return DaoManager.getDaoSession(context).getAllGoodsDao().loadAll();
    }


    /**
     * 分页加载
     *
     * @param context
     * @param pageSize 当前第几页(程序中动态修改pageSize的值即可)
     * @param pageNum  每页显示多少个
     * @return
     */
    public static List<AllGoods> queryPaging(int pageSize, int pageNum, Context context) {
        AllGoodsDao allGoodsDao = DaoManager.getDaoSession(context).getAllGoodsDao();
        List<AllGoods> listMsg = allGoodsDao.queryBuilder()
                .offset(pageSize * pageNum).limit(pageNum).list();
        return listMsg;
    }

}
