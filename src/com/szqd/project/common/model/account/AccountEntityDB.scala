package com.szqd.project.common.model.account

import scala.beans.BeanProperty

/**
 * Created by like on 8/19/15.
 */
class AccountEntityDB {
  @transient val ENTITY_NAME: String = "COMMON_ACCOUNT"
  @BeanProperty var id: java.lang.Long = null
  @BeanProperty var name: String = null
  @BeanProperty var password: String = null
  @BeanProperty var accountType: Integer = null
  @BeanProperty var source: Integer = null;

}
