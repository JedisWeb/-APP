package cn.edu.hbuas.sl.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 创建数据库、创建数据库表、包含增删改查的操作以及数据库的升级
 */

public class DaoManager {
    private static final String DB_NAME = "sl.db";

    private static DaoManager daoManager;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private Context mContext;

    private DaoManager(Context context) {
        this.mContext = context;
        // 初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        getDaoMaster(context);
        getDaoSession(context);
    }

    public static DaoManager getInstance(Context context) {
        if (null == daoManager) {
            synchronized (DaoManager.class) {
                if (null == daoManager) {
                    daoManager = new DaoManager(context);
                }
            }
        }
        return daoManager;
    }

    /**
     * 获取可读数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }

        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * 获取DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (null == mDaoMaster) {
            synchronized (DaoManager.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWritableDatabase(context));
                }
            }
        }
        return mDaoMaster;
    }


    /**
     * 获取DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (null == mDaoSession) {
            synchronized (DaoManager.class) {
                mDaoSession = getDaoMaster(context).newSession();
            }
        }
        return mDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

}
