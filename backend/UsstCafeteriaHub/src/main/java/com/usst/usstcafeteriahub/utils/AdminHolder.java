package com.usst.usstcafeteriahub.utils;

import com.usst.usstcafeteriahub.model.entity.Admin;

public class AdminHolder {
    private static final ThreadLocal<Admin> tl = new ThreadLocal<>();

    public static void saveAdmin(Admin Admin){
        tl.set(Admin);
    }

    public static Admin getAdmin(){
        return tl.get();
    }

    public static void removeAdmin(){
        tl.remove();
    }
}
