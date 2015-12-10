package com.szqd.project.mobile.lock.controller;

import com.szqd.framework.controller.SpringMVCController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by like on 4/29/15.
 */
@RestController
@RequestMapping("/mobilelock-security")
public class MobileLockSecurityController extends SpringMVCController
{

    private static final String SECURITY_CODE = "OpenSSLRSAPublicKey{modulus=a41d236014556d1d63de839d20aa05e6a4eb241cfcb56383af6004e771f3b26f01d98d291d7b1a194c9bb397339eba62dbb95328c24276908377531bf6161c98983337f8a3d5ec53e239311c1ab1a4fd4943856e5d9350958b7578b48cd8105775a846c039ee1e169d45f9f080d4cc78da655fda1e99392443dae5ab5549813ea83df54510a1d20bdf29250797a42d811db1e0b9d9aca71ac0a757c693d1f8a4c122af08861541e975323e03bdf8e8c61e55e8c76bfadb8b9fdde49915d2d1f7e90dd897407bda06ac416a98529e95d80379bb23ceb51b5731e850bff55e3d52a11b50eb25113b9b57d580e43543e6d11ae2f18cd0cd433838d356183fdadea9,publicExponent=10001}";
    @RequestMapping(value = "/check-security-code.do",method = RequestMethod.POST)
    public boolean checkSecurityCode(String clientKey)
    {
        boolean isValidate = SECURITY_CODE.equals(clientKey);
        return isValidate;
    }
}
