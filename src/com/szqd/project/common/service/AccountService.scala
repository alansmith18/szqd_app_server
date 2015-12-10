package com.szqd.project.common.service

import com.szqd.project.common.model.account.AccountEntityDB

/**
 * Created by like on 8/19/15.
 */
trait AccountService {
  /**
   * 创建账户
   * @param newAccount
   */
  def createAccount(newAccount: AccountEntityDB): Unit

  /**
   * 账号登陆
   * @param loginAccount
   * @return
   */
  def loginAccount(loginAccount: AccountEntityDB): Boolean
}
