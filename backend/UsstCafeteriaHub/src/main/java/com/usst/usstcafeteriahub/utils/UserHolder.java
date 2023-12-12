package com.usst.usstcafeteriahub.utils;



//
//这个 UserHolder 类是一个使用 ThreadLocal 来存储用户信息的工具类。ThreadLocal
// 是Java中一种特殊的变量，它为每个使用该变量的线程提供了独立的变量副本，
//        因此每个线程都可以独立地改变自己的副本，而不会影响其他线程中的副本。
//Web服务和APIs：在处理HTTP请求时，可以在请求开始时将用户信息存储在 UserHolder 中，
// 这样在处理该请求的整个线程中，都可以轻松访问用户信息。
//
// 以前的做法是将用户信息存储在 HttpSession 中，但是这样做有两个问题：
// 1.每次都需要从 HttpSession 中获取用户信息，这样会增加代码的复杂度。
// 2.如果用户信息存储在 HttpSession 中，那么在分布式环境下，就需要将 HttpSession
// 存储在 Redis 中，这样会增加 Redis 的压力。
// 通过使用 ThreadLocal，可以解决上述两个问题。
// 1.使用 ThreadLocal，可以在任何地方轻松访问用户信息，不需要从 HttpSession 中获取。
// 2.使用 ThreadLocal，可以将用户信息存储在 ThreadLocal 中，这样就不需要将 HttpSession
// 存储在 Redis 中了。


import com.usst.usstcafeteriahub.model.entity.User;

public class UserHolder {
    private static final ThreadLocal<User> tl = new ThreadLocal<>();

    public static void saveUser(User user){
        tl.set(user);
    }

    public static User getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}