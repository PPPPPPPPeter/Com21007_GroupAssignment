package com.example.roomdemo.viewModel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/roomdemo/viewModel/UserViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "readAllData", "Landroidx/lifecycle/LiveData;", "", "Lcom/example/roomdemo/model/User;", "repository", "Lcom/example/roomdemo/repository/UserRepo;", "addUser", "", "user", "app_debug"})
public final class UserViewModel extends androidx.lifecycle.AndroidViewModel {
    private final androidx.lifecycle.LiveData<java.util.List<com.example.roomdemo.model.User>> readAllData = null;
    private final com.example.roomdemo.repository.UserRepo repository = null;
    
    public UserViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    public final void addUser(@org.jetbrains.annotations.NotNull()
    com.example.roomdemo.model.User user) {
    }
}