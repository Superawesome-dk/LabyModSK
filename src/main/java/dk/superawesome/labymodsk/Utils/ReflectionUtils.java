package dk.superawesome.labymodsk.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
    public static Method getMethod(Class<?> clz, String method, Class<?>... parameters) {
        try {
            return clz.getDeclaredMethod(method, parameters);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * @param clazz The class of the method
     * @param method The method to invoke
     * @param instance The instance for the method to be invoked from
     * @param parameters The parameters of the method
     * @return The result of the method, or null if the method was null or the invocation failed
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Class<?> clazz, String method, Object instance, Object... parameters) {
        try {
            Class<?>[] parameterTypes = new Class<?>[parameters.length];
            int x = 0;

            for (Object obj : parameters)
                parameterTypes[x++] = obj.getClass();

            Method m = clazz.getDeclaredMethod(method, parameterTypes);
            m.setAccessible(true);

            return (T) m.invoke(instance, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param clazz The class to create the instance of.
     * @return A instance object of the given class.
     */
    public static <T> T newInstance(Class<T> clz) {
        try {
            Constructor<T> c = clz.getDeclaredConstructor();
            c.setAccessible(true);
            return c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param from The class of the field
     * @param obj The instance of the class - you can use null if the field is static
     * @param field The field name
     * @return True if the field was successfully set
     */
    public static <T> boolean setField(Class<T> from, Object obj, String field, Object newValue) {
        try {
            Field f = from.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, newValue);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param from The class of the field
     * @param obj The instance of the class - you can use null if the field is static
     * @param field The field name
     * @return The field or null if it couldn't be gotten
     */
    @SuppressWarnings("unchecked")
    public static <T> T getField(Class<?> from, Object obj, String field) {
        try {
            Field f = from.getDeclaredField(field);
            f.setAccessible(true);
            return (T) f.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
