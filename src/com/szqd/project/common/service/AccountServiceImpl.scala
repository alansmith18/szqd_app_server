package com.szqd.project.common.service

import com.szqd.framework.service.SuperService
import com.szqd.framework.util.StringUtil
import com.szqd.project.common.model.account.AccountEntityDB


import org.apache.log4j.Logger

import org.springframework.data.mongodb.core.query.{BasicQuery}

object AccountTypeEnum extends Enumeration
{
  val None = Value(-1,"不存在")
  val Email = Value(0,"邮箱")
  val Phone = Value(1,"手机")
}

object AccountSourceEnum extends Enumeration
{

  val Self = Value(0,"注册")
  val Qq = Value(1,"QQ")
  val Weichat = Value(2,"微信")
  val SinaWeibo = Value(3,"新浪微博")

}

/**
 * Created by like on 8/19/15.
 */
class AccountServiceImpl extends SuperService with AccountService{
  private val LOGGER = Logger.getLogger(this.getClass);

  def getUserType(accountName: String):Int =
  {
    if (Option(accountName).isDefined)
    {

      if (StringUtil.isValidEmail(accountName)) AccountTypeEnum.Email.id
      else if (StringUtil.isValidPhone(accountName)) AccountTypeEnum.Phone.id
    }
    AccountTypeEnum.None.id

  }

  /**
   * 创建账户
   * @param newAccount
   */
  def createAccount(newAccount: AccountEntityDB): Unit =
  {
    try
    {
      val isDefined = Option(newAccount).isDefined
      if (isDefined){
        val isExistsAccount = this.isExistsAccountByAccount(newAccount)
        if (isExistsAccount)
        {
          throw new RuntimeException("账户已经存在");
        }
        else
        {
          val accountName = newAccount.getName
          val accountType = getUserType(accountName)
          newAccount.setAccountType(accountType)
          val mongoTemplate = this.getCoreDao.getMongoTemplate
          newAccount.setId(this.getNextSequenceForFieldKey(newAccount.ENTITY_NAME))
          mongoTemplate.insert(newAccount, newAccount.ENTITY_NAME)
        }
      }
      else {
        throw new RuntimeException("没有传递账户信息")
      }
    }
    catch {
      case e:Exception =>
        LOGGER.error("com.szqd.project.common.service.AccountServiceImpl.createAccount(newAccount: AccountEntityDB)",e);
        throw new RuntimeException("创建账户出错");
    };
  }

  /**
   * 账号登陆
   * @param loginAccount
   * @return
   */
  def loginAccount(loginAccount: AccountEntityDB): Boolean =
  {
    try
    {
      val isLoginParamDefined = Option(loginAccount).isDefined
      if(isLoginParamDefined){

        val mongoTemplate = this.getCoreDao.getMongoTemplate
        var queryString = "{name:'%s',password:'%s'}"
        queryString = String.format(queryString, loginAccount.getName, loginAccount.getPassword)
        val queryField = "{}"
        val query = new BasicQuery(queryString, queryField)
        val queryEntity:Option[AccountEntityDB] = Option(mongoTemplate.findOne(query,loginAccount.getClass,loginAccount.ENTITY_NAME))
        queryEntity.isDefined

      }
      else throw new RuntimeException("没有传递正确的登录参数");


    }
    catch {
      case e:Exception =>
        LOGGER.error("com.szqd.project.common.service.AccountServiceImpl.loginAccount(loginAccount: AccountEntityDB)",e)
        throw new RuntimeException("账号登陆出错")
    }
  }


  /**
   * 判断账户是否存在
   * @param newAccount
   * @return
   */
  private def isExistsAccountByAccount(newAccount: AccountEntityDB): Boolean ={
    try {
      val mongoTemplate = this.getCoreDao.getMongoTemplate
      var queryString = "{name:'%s'}"
      queryString = String.format(queryString, newAccount.getName)
      val queryField = "{}"
      val query = new BasicQuery(queryString, queryField)
      val account = Option(mongoTemplate.findOne(query, newAccount.getClass, newAccount.ENTITY_NAME))
      account.isDefined
    }
    catch {
      case e:Exception =>
        LOGGER.error("com.szqd.project.common.service.AccountServiceImpl.isExistsAccountByAccount(newAccount: AccountEntityDB)",e)
        throw new RuntimeException("判断账户是否存在出错")
    }
  }


}
