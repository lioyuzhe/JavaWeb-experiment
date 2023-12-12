package com.usst.usstcafeteriahub.utils;

import com.usst.usstcafeteriahub.model.entity.CafeteriaAdmin;

public class CafeteriaAdminHolder {
    private static final ThreadLocal<CafeteriaAdmin> tl = new ThreadLocal<>();

    public static void saveCafeteriaAdmin(CafeteriaAdmin CafeteriaAdmin){
        tl.set(CafeteriaAdmin);
    }

    public static CafeteriaAdmin getCafeteriaAdmin(){
        return tl.get();
    }

    public static void removeCafeteriaAdmin(){
        tl.remove();
    }
}
