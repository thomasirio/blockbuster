package com.github.thomasirio.model;


import com.github.thomasirio.exception.FieldValidationException;
import com.github.thomasirio.util.DBHandler;
import com.google.common.collect.Iterables;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.reflections.ReflectionUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Created by pcthomas on 30/10/2014.
 */

public abstract class Model {

    public static <Type> Type validate(Class<Type> typeOfField, String value) throws FieldValidationException {
        Type validatedValue = null;

        try {
            if(value == null || value.isEmpty())
                throw new FieldValidationException(FieldValidationException.INVALID_VALUE, value);

            if(typeOfField.equals(String.class))
                validatedValue = (Type) value;
            else if(Model.class.isAssignableFrom(typeOfField)) {
                Set<Field> fields = ReflectionUtils.getAllFields(typeOfField, ReflectionUtils.withAnnotation(Id.class));

                if(fields.size() != 1)
                    throw new FieldValidationException(FieldValidationException.ID_FIELD_NOT_FOUND, typeOfField.getName());

                Object theId;

                if(Iterables.get(fields, 0).getType().getName().equals("java.lang.String"))
                    theId = value;
                else
                    theId = Long.valueOf(value);

                validatedValue = Model.find(typeOfField, theId);
            }
            else {
                Method valueOf = typeOfField.getDeclaredMethod("valueOf", String.class);
                validatedValue = (Type) valueOf.invoke(null, value);
            }
        } catch (NoSuchMethodException e) {
            throw new FieldValidationException(FieldValidationException.VALUEOF_METHOD_MISSING, e);
        } catch (InvocationTargetException e) {
            // called when valueOf method fails
            e.printStackTrace();
            throw new FieldValidationException(FieldValidationException.INVALID_VALUE, e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return validatedValue;
    }

    public static <Type> Type find(Class<Type> modelClass, Object id) {
        Type theModel = null;
        Session dbSession = DBHandler.getDbSession();

        if(id.getClass().getName().equals("java.lang.String"))
            theModel = (Type) dbSession.get(modelClass, (String) id);
        else
            theModel = (Type) dbSession.get(modelClass, (Long) id);

        dbSession.close();

        return theModel;
    }

    public static ModelList where(Class modelClass, List<String> fields, List values, Integer firstResult, Integer maxResult) {
        Session dbSession = DBHandler.getDbSession();

        Criteria query = dbSession.createCriteria(modelClass);

        for(int index = 0; index<fields.size(); index++)
            query.add(Restrictions.eq(fields.get(index), values.get(index)));

        if(firstResult != null)
            query.setFirstResult(firstResult);
        if(maxResult != null)
            query.setMaxResults(maxResult);

        ModelList modelList = new ModelList(query.list());

        dbSession.close();

        return modelList;
    }

    public static <Type> Type first(Class modelClass, String field, Object value) {
        Session dbSession = DBHandler.getDbSession();

        Criteria query = dbSession.createCriteria(modelClass);
        query.add(Restrictions.eq(field, value));
        Type model = (Type) query.uniqueResult();

        dbSession.close();

        return model;
    }

    public static Long count(Class modelClass, String fieldRestriction, Object value) {
        Session dbSession = DBHandler.getDbSession();

        Criteria query = dbSession.createCriteria(modelClass);
        query.setProjection(Projections.rowCount());
        if(fieldRestriction != null && value != null)
            query.add(Restrictions.eq(fieldRestriction, value));
        Long count = (Long) query.uniqueResult();

        dbSession.close();

        return count;
    }

    public void save() throws FieldValidationException {
        Session dbSession = DBHandler.getDbSession();

        try {
            dbSession.beginTransaction();
            dbSession.save(this);
            dbSession.getTransaction().commit();
        } catch(Exception e) {
            throw new FieldValidationException("Save failed", e);
        } finally {
            dbSession.close();
        }
    }

    public void update() throws FieldValidationException {
        Session dbSession = DBHandler.getDbSession();

        try {
            dbSession.beginTransaction();
            dbSession.update(this);
            dbSession.getTransaction().commit();
        } catch(Exception e) {
            throw new FieldValidationException("Update failed", e);
        } finally {
            dbSession.close();
        }
    }

    public void delete() {
        Session dbSession = DBHandler.getDbSession();

        dbSession.beginTransaction();
        dbSession.delete(this);
        dbSession.getTransaction().commit();

        dbSession.close();
    }

    public static ModelList all(Class modelClass) {
        Session dbSession = DBHandler.getDbSession();

        Criteria query = dbSession.createCriteria(modelClass);
        ModelList modelList = new ModelList(query.list());

        dbSession.close();

        return modelList;
    }
}
