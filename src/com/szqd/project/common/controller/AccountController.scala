package com.szqd.project.common.controller

import com.szqd.framework.controller.SpringMVCController
import com.szqd.project.common.model.account.AccountEntityDB
import com.szqd.project.common.service.AccountService
import org.springframework.web.bind.annotation.{RequestMethod, RequestMapping, RestController}

import scala.beans.BeanProperty

/**
 * Created by like on 8/18/15.
 */
@RestController
@RequestMapping(Array("/app-account"))
class AccountController extends SpringMVCController
{

  @BeanProperty var accountService:AccountService = null;

  @RequestMapping(value = Array("/create-account.do"),method = Array(RequestMethod.POST))
  def createAccount(newAccount:AccountEntityDB): Unit ={
    accountService.createAccount(newAccount)

  }

  @RequestMapping(value = Array("/login-account.do"),method = Array(RequestMethod.POST))
  def loginAccount(loginAccount:AccountEntityDB):Boolean ={
    accountService.loginAccount(loginAccount);
  }

}
