package com.shuyu.core.uils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shuyu.core.CoreApplication;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * SharedPreferences 工具类
 */
public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "sp_main_file";
    /**
     * 不清除的文件
     */
    public static final String NO_CLEAR_FILE = "no_clear_file";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * Append methods of putting Double values by chen.
     *
     * @param key 键
     * @param object 值
     */
    public static void put(String key, Object object) {
        put(FILE_NAME, key, object);
    }

    public static void put(String fileName, String key, Object object) {
        if (object == null)
            return;
        SharedPreferences sp = CoreApplication.getApplication().getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Double) {
            editor.putLong(key, Double.doubleToRawLongBits((Double) object));
        }else if (object instanceof HashSet) {
            editor.putStringSet(key, (HashSet<String>) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * Append methods of getting Double values by chen.
     *
     * @param key 键
     * @param defaultObject 默认值
     * @return 返回值
     */
    public static Object get( String key, Object defaultObject) {
        return get(FILE_NAME, key, defaultObject);
    }

    public static Object get( String fileName, String key, Object defaultObject) {
        SharedPreferences sp = CoreApplication.getApplication().getSharedPreferences(fileName,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Double) {
            return Double.longBitsToDouble(sp.getLong(key,
                    Double.doubleToRawLongBits((Double) defaultObject)));
        } else if (defaultObject instanceof HashSet){
            return   sp.getStringSet(key, (Set<String>) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key 键
     */
    public static void remove(String key) {
        remove( FILE_NAME, key);
    }

    public static void remove(String fileName, String key) {
        SharedPreferences sp = CoreApplication.getApplication().getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     */
    public static void clear() {
        clear(FILE_NAME);
    }

    public static void clear(String fileName) {
        SharedPreferences sp = CoreApplication.getApplication().getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key 键
     * @return true存在 ; false不存在
     */
    public static boolean contains(String key) {
        return contains(FILE_NAME, key);
    }

    public static boolean contains(String fileName, String key) {
        SharedPreferences sp = CoreApplication.getApplication().getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        return getAll(FILE_NAME);
    }

    public static Map<String, ?> getAll(String fileName) {
        SharedPreferences sp = CoreApplication.getApplication().getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
        /**
         * 创建一个SharedPreferencesCompat解决apply方法的一个兼容类
         *
         * @author zhy
         */
        private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return Method
         */
        static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }
}