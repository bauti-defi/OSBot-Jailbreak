package org.osbot.jailbreak.util.reflection;

import org.osbot.jailbreak.data.Engine;
import org.osbot.jailbreak.ui.logger.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ReflectionEngine {

    private ClassLoader classLoader;

    public ReflectionEngine(ClassLoader classLoader) throws IOException {
        this.classLoader = classLoader;
    }

    public Object getBotAppInstance() {
        return Engine.getReflectionEngine().getFieldValue("org.osbot.BotApplication", "iiiiiiiiIiii");

    }

    public ReflectedClass getClass(String name, Object instance) {
        try {
            return new ReflectedClass(classLoader.loadClass(name), instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ReflectedClass getClass(String name) {
        return getClass(name, null);
    }

    public ReflectedClass getClass(Object instance) {
        return getClass(instance.getClass().getSimpleName(), instance);
    }

    public ReflectedField getField(String className, String fieldName, Object instance) {

        final ReflectedClass clazz = getClass(className, instance);
        final ReflectedField field = clazz.getField(new Modifiers.ModifierBuilder().name(fieldName).build());
        return field;
    }

    public ReflectedField getField(String className, String fieldName) {
        return getField(className, fieldName, null);
    }

    public ReflectedMethod getMethod(String className, String methodName, Object instance) {

        final ReflectedClass clazz = getClass(className, instance);
        final ReflectedMethod method = clazz.getMethod(new Modifiers.ModifierBuilder().name(methodName).build());
        return method;
    }

    public ReflectedMethod getMethod(String className, String methodName) {
        return getMethod(className, methodName, null);
    }

    public Object getFieldValue(String className, String fieldName, Object instance) {
        try {
            final ReflectedClass clazz = getClass(className, instance);
            final ReflectedField field = clazz.getField(new Modifiers.ModifierBuilder().name(fieldName).build());
            return field.getValue();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getFieldValue(String className, String fieldName) {
        return getFieldValue(className, fieldName, null);
    }

    public void setFieldValue(String className, String fieldName, Object value, Object instance) {
        try {
            final ReflectedClass clazz = getClass(className, instance);
            final ReflectedField field = clazz.getField(new Modifiers.ModifierBuilder().name(fieldName).build());
            field.setValue(value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFieldValue(String className, String fieldName, Object value) {
        setFieldValue(className, fieldName, value, null);
    }

    public Object getMethodValue(String className, String fieldName, int paramCount, Object instance) {
        try {
            final ReflectedClass clazz = getClass(className, instance);
            for (ReflectedMethod m : clazz.getMethods()) {
                if (m.getName().equals(fieldName)) {
                    if (m.getParameterCount() == paramCount) {
                        //   Logger.log(m.getReturnType().toGenericString());

                        Logger.log("We're invoking collection");
                        return m.invoke(instance);

                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void startScript(Object bot, Object randoms, String scriptName) {
        try {
            final ReflectedClass clazz = getClass("org.osbot.Gb");
            for (ReflectedMethod m : clazz.getMethods()) {
                if (m.getName().equals("iiIIiiiIiIii")) {
                    if (m.getParameterCount() == 4) {
                        Logger.log("We're invoking script start");
                        m.invoke(bot, randoms, scriptName, null);
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public Object getBotValue(String className, String fieldName, int paramCount, String returnType, Object instance) {
        try {
            final ReflectedClass clazz = getClass(className, instance);
            for (ReflectedMethod m : clazz.getMethods()) {
                if (m.getName().equals(fieldName)) {
                    if (m.getParameterCount() == paramCount) {
                    if(m.getReturnType().toGenericString().equals(returnType)) {
                        Logger.log("Getting Bot Value");
                        return m.invoke();
                    }

                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Object getMethodValue(String className, String fieldName, int paramCount, String returnType, Object instance) {
        try {
            final ReflectedClass clazz = getClass(className, instance);
            for (ReflectedMethod m : clazz.getMethods()) {
                if (m.getName().equals(fieldName)) {
                    if (m.getParameterCount() == paramCount) {
                        if(m.getReturnType().toGenericString().equals(returnType)) {
                            Logger.log("Getting method Value: "+m.getReturnType().toGenericString());
                            return m.invoke();
                        }

                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}