package org.example.service.impl;

import lombok.extern.log4j.Log4j;
import org.example.dao.AppUserDao;
import org.example.entity.AppUser;
import org.example.service.AppUserService;
import org.example.utils.CryptoTool;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserDao appUserDao;
    private final CryptoTool cryptoTool;

    public AppUserServiceImpl(AppUserDao appUserDao, CryptoTool cryptoTool) {
        this.appUserDao = appUserDao;
        this.cryptoTool = cryptoTool;
    }

    @Override
    public String registerUser(AppUser appUser) {
        return null;
    }

    @Override
    public String setEmail(AppUser appUser, String email) {
        return null;
    }
}
